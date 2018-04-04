package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceEventTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceSectionTyService;
import com.hoau.miser.module.api.itf.api.shared.define.ProdTypeConstant;
import com.hoau.miser.module.api.itf.api.shared.domain.*;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceEventTyQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.PriceEventDiscountDetailTyCache;
import com.hoau.miser.module.api.itf.server.cache.PriceEventWithDetailTyCache;
import com.hoau.miser.module.api.itf.server.cache.PriceEventWithoutDetailTyCache;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.dao.PriceEventTyDao;

import javax.annotation.Resource;

import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: PriceEventTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: 活动查询接口实现
 * @date 16/6/8 10:40
 */
@Service
public class PriceEventTyService implements IPriceEventTyService {

	@Resource
	ICorpPriceCityService corpPriceCityService;

	@Resource
	PriceEventTyDao priceEventTyDao;
	
	@Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;

	@Resource
	private IPriceSectionTyService priceSectionTyService;

	/**
	 * 根据条件查询所有可用的活动,不含活动明细
	 *
	 * @param queryParam
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日10:37:26
	 */
	@Override
	public List<PriceEventEntity> queryPriceEventsWithoutDetailByCondition(PriceEventTyQueryParam queryParam) {
		//校验参数是否完整
		if (StringUtil.isEmpty(queryParam.getOrderChannel())
				&& StringUtil.isEmpty(queryParam.getSendOrgCode())
				&& StringUtil.isEmpty(queryParam.getArrivalOrgCode())
				&& StringUtil.isEmpty(queryParam.getCustomerCode())) {
			return null;
		}
		if (queryParam.isHistory() && queryParam.getQueryTime() == null) {
			return null;
		}

		// 起运地
		OrgPositionInfoTyEntity originPositionInfo = null;
        OrgPositionInfoTyEntity currOrgPositionInfoTyEntity = queryParam.getOriginPositionInfo();
		if(currOrgPositionInfoTyEntity != null
				&& !StringUtil.isEmpty(currOrgPositionInfoTyEntity
						.getProvinceCode())
				&& !StringUtil.isEmpty(currOrgPositionInfoTyEntity.getCityCode()) && !StringUtil
					.isEmpty(currOrgPositionInfoTyEntity.getCountyCode())){
			originPositionInfo = currOrgPositionInfoTyEntity;
		} else if (!StringUtil.isEmpty(queryParam.getSendOrgCode())) {
			originPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(queryParam.getSendOrgCode());
			if(originPositionInfo == null){
				return null;
			}
		} else {
			return null;
		}
		// 目的地
		OrgPositionInfoTyEntity destPositionInfo = null;
		OrgPositionInfoTyEntity currDestPositionInfo = queryParam.getDestPositionInfo();
		if(currDestPositionInfo != null
  				&& !StringUtil.isEmpty(currDestPositionInfo
  						.getProvinceCode())
  				&& !StringUtil.isEmpty(currDestPositionInfo.getCityCode()) && !StringUtil
  					.isEmpty(currDestPositionInfo.getCountyCode())){
  			destPositionInfo = currDestPositionInfo;
  		}else if (!StringUtil.isEmpty(queryParam.getArrivalOrgCode())) {
  			destPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(queryParam.getArrivalOrgCode());
  			if(destPositionInfo == null){
				return null;
  			}
  		} else {
			return null;
		}
		PriceEventTyQueryEntity queryEntity = new PriceEventTyQueryEntity();
		queryEntity.setCustomerCode(queryParam.getCustomerCode());
		//根据发货门店获取发货价卡城市
		CorpPriceCityEntity sendPriceCity = corpPriceCityService.queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_SEND);
		if (sendPriceCity != null) {
			queryEntity.setSendPriceCity(sendPriceCity.getSendPriceCityCode());
		}
		//根据到货门店获取到货价卡城市
		CorpPriceCityEntity arrivalPriceCity = corpPriceCityService.queryPriceCity(destPositionInfo, PmsConstants.PRICE_CITY_ARRIVAL);
		if (arrivalPriceCity != null) {
			queryEntity.setArrivalPriceCity(arrivalPriceCity.getArrivalPriceCityCode());
		}

		//首先查询发货门店
		List<PriceEventEntity> sendCorpResult = null;
		queryEntity.setOrgType("SEND");
		queryEntity.setOrgCode(queryParam.getSendOrgCode());
		//历史,走数据库查询
		if (queryParam.isHistory()) {
			queryEntity.setQueryTime(queryParam.getQueryTime());
			sendCorpResult = priceEventTyDao.queryEventsWithoutDetail(queryEntity);
		} else {
			//当前,走缓存查询
			String key = new CacheKey(queryEntity.getOrderChannel(), queryEntity.getOrgType(), queryEntity.getOrgCode(),
					queryEntity.getSendPriceCity(), queryEntity.getArrivalPriceCity(), queryEntity.getCustomerCode())
					.generateKey();
			ICache<String, List<PriceEventEntity>> cache = CacheManager.getInstance().getCache(PriceEventWithoutDetailTyCache.UUID);
			sendCorpResult = cache.get(key);
		}

		//然后查询到货门店
		List<PriceEventEntity> arrivalCorpResult = null;
		queryEntity.setOrgCode("ARRIVAL");
		queryEntity.setOrgCode(queryParam.getArrivalOrgCode());
		if (queryParam.isHistory()) {
			arrivalCorpResult = priceEventTyDao.queryEventsWithoutDetail(queryEntity);
		} else {
			//当前,走缓存查询
			String key = new CacheKey(queryEntity.getOrderChannel(), queryEntity.getOrgType(), queryEntity.getOrgCode(),
					queryEntity.getSendPriceCity(), queryEntity.getArrivalPriceCity(), queryEntity.getCustomerCode())
					.generateKey();
			ICache<String, List<PriceEventEntity>> cache = CacheManager.getInstance().getCache(PriceEventWithoutDetailTyCache.UUID);
			sendCorpResult = cache.get(key);
		}

		return unionTwoEventListUnRepeat(sendCorpResult, arrivalCorpResult);
	}

	/**
	 * 根据活动编号查询活动明细
	 *
	 * @param eventCode
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日10:39:19
	 */
	@Override
	public List<PriceEventDiscountSubEntity> queryPriceEventDetails(String eventCode) {
		ICache<String, List<PriceEventDiscountSubEntity>> cache = CacheManager.getInstance().getCache(PriceEventDiscountDetailTyCache.UUID);
		return cache.get(eventCode);
	}

	/**
	 * 根据活动编号及运输类型查询一条活动折扣信息
	 *
	 * @param eventCode
	 * @param transportType
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日14:22:14
	 */
	@Override
	public PriceEventDiscountSubEntity queryPriceEventDetail(String eventCode, String transportType) {
		ICache<String, List<PriceEventDiscountSubEntity>> cache = CacheManager.getInstance().getCache(PriceEventDiscountDetailTyCache.UUID);
		String key = new CacheKey(eventCode, transportType).generateKey();
		List<PriceEventDiscountSubEntity> details = cache.get(key);
		if (details != null && details.size() > 0) {
			return details.get(0);
		}
		return null;
	}

	/**
	 * 根据条件带明细的活动
	 *
	 * @param queryParam
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日10:40:15
	 */
	@Override
	public List<PriceEventEntity> queryPriceEventWithDetailByCondition(PriceEventTyQueryParam queryParam) {
		List<PriceEventEntity> result = new ArrayList<PriceEventEntity>(0);
		//校验参数是否完整
		if (StringUtil.isEmpty(queryParam.getOrderChannel())
				&& StringUtil.isEmpty(queryParam.getSendOrgCode())
				&& StringUtil.isEmpty(queryParam.getArrivalOrgCode())
				&& StringUtil.isEmpty(queryParam.getCustomerCode())) {
			return result;
		}
		if (queryParam.isHistory() && queryParam.getQueryTime() == null) {
			throw new ChargeException("查询历史活动时未获取到时间");
		}

		// 起运地
		OrgPositionInfoTyEntity originPositionInfo = null;
        OrgPositionInfoTyEntity currOrgPositionInfoTyEntity = queryParam.getOriginPositionInfo();
		if(currOrgPositionInfoTyEntity != null
				&& !StringUtil.isEmpty(currOrgPositionInfoTyEntity
						.getProvinceCode())
				&& !StringUtil.isEmpty(currOrgPositionInfoTyEntity.getCityCode()) && !StringUtil
					.isEmpty(currOrgPositionInfoTyEntity.getCountyCode())){
			originPositionInfo = currOrgPositionInfoTyEntity;
		} else if (!StringUtil.isEmpty(queryParam.getSendOrgCode())) {
			originPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(queryParam.getSendOrgCode());
			if(originPositionInfo == null){
				return result;
			}
		} else {
			return result;
		}
		// 目的地
		OrgPositionInfoTyEntity destPositionInfo = null;
		OrgPositionInfoTyEntity currDestPositionInfo = queryParam.getDestPositionInfo();
		if(currDestPositionInfo != null
  				&& !StringUtil.isEmpty(currDestPositionInfo
  						.getProvinceCode())
  				&& !StringUtil.isEmpty(currDestPositionInfo.getCityCode()) && !StringUtil
  					.isEmpty(currDestPositionInfo.getCountyCode())){
  			destPositionInfo = currDestPositionInfo;
  		}else if (!StringUtil.isEmpty(queryParam.getArrivalOrgCode())) {
  			destPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(queryParam.getArrivalOrgCode());
  			if(destPositionInfo == null){
				return result;
  			}
  		} else {
			return result;
		}
		PriceEventTyQueryEntity queryEntity = new PriceEventTyQueryEntity();
		queryEntity.setCustomerCode(queryParam.getCustomerCode());
		//根据发货门店获取发货价卡城市
		CorpPriceCityEntity sendPriceCity = corpPriceCityService.queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_SEND);
		if (sendPriceCity != null) {
			queryEntity.setSendPriceCity(sendPriceCity.getSendPriceCityCode());
		}
		//根据到货门店获取到货价卡城市
		CorpPriceCityEntity arrivalPriceCity = corpPriceCityService.queryPriceCity(destPositionInfo, PmsConstants.PRICE_CITY_ARRIVAL);
		if (arrivalPriceCity != null) {
			queryEntity.setArrivalPriceCity(arrivalPriceCity.getArrivalPriceCityCode());
		}
		queryEntity.setTransportType(queryParam.getTransportType());

		//首先查询发货门店
		List<PriceEventEntity> sendCorpResult = null;
		queryEntity.setOrgType("SEND");
		queryEntity.setOrgCode(queryParam.getSendOrgCode());
		queryEntity.setOrderChannel(queryParam.getOrderChannel());
		//历史,走数据库查询
		if (queryParam.isHistory()) {
			queryEntity.setQueryTime(queryParam.getQueryTime());
			sendCorpResult = priceEventTyDao.queryEventsWithDetail(queryEntity);
		} else {
			//当前,走缓存查询
			String key = new CacheKey(queryEntity.getOrderChannel(), queryEntity.getOrgType(), queryEntity.getOrgCode(),
					queryEntity.getSendPriceCity(), queryEntity.getArrivalPriceCity(), queryEntity.getCustomerCode(), queryEntity.getTransportType())
					.generateKey();
			ICache<String, List<PriceEventEntity>> cache = CacheManager.getInstance().getCache(PriceEventWithDetailTyCache.UUID);
			sendCorpResult = cache.get(key);
		}

		//然后查询到货门店
		List<PriceEventEntity> arrivalCorpResult = null;
		queryEntity.setOrgCode("ARRIVAL");
		queryEntity.setOrgCode(queryParam.getArrivalOrgCode());
		if (queryParam.isHistory()) {
			arrivalCorpResult = priceEventTyDao.queryEventsWithDetail(queryEntity);
		} else {
			//当前,走缓存查询
			String key = new CacheKey(queryEntity.getOrderChannel(), queryEntity.getOrgType(), queryEntity.getOrgCode(),
					queryEntity.getSendPriceCity(), queryEntity.getArrivalPriceCity(), queryEntity.getCustomerCode(), queryEntity.getTransportType())
					.generateKey();
			ICache<String, List<PriceEventEntity>> cache = CacheManager.getInstance().getCache(PriceEventWithDetailTyCache.UUID);
			arrivalCorpResult = cache.get(key);
		}

		return unionTwoEventListUnRepeat(sendCorpResult, arrivalCorpResult);
	}

	/**
	 * 将两个集合中的活动去重合并到一个集合中
	 * @param listOne
	 * @param listTwo
	 * @return
	 */
	private List<PriceEventEntity> unionTwoEventListUnRepeat(List<PriceEventEntity> listOne, List<PriceEventEntity> listTwo){
		List<PriceEventEntity> result = new ArrayList<PriceEventEntity>();
		if (listOne != null) {
			result.addAll(listOne);
		}
		if (listTwo != null) {
			//取出send中已经包含的活动
			List<PriceEventEntity> remindArrival = new ArrayList<PriceEventEntity>(listTwo.size());
			for (int i = 0; i < listTwo.size(); i ++) {
				boolean contains = false;
				for (int j = 0; j < result.size(); j++) {
					if (result.get(j).getEventCode().equals(listTwo.get(i).getEventCode())) {
						contains = true;
						break;
					}
				}
				if (!contains) {
					remindArrival.add(listTwo.get(i));
				}
			}
			result.addAll(remindArrival);
		}
		return  result;
	}


	/**
	 * 拿到活动中的运费优惠分段集合
	 *
	 * @Param [queryParam]
	 * @Return java.util.List<java.util.List<com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity>>
	 * @Throws
	 * @Author 廖文强
	 * @Date 2016/6/16 17:24
	 * @Version v1
	 */
	@Override
	public List<List<PriceSectionSubEntity>> getEventSection(PriceEventTyQueryParam queryParam, String chargeType) {
		List<List<PriceSectionSubEntity>> list = null;
		//偏线使用经济快运的活动
		if (ProdTypeConstant.TYPE_PX.equals(queryParam.getTransportType())) {
			queryParam.setTransportType(ProdTypeConstant.TYPE_LD);
		}
		List<PriceEventEntity> eventList = queryPriceEventWithDetailByCondition(queryParam);
		for (PriceEventEntity eventEntity : eventList) {
			List<PriceEventDiscountSubEntity> subEntitys = eventEntity.getPriceEventDiscountSubs();
			for (PriceEventDiscountSubEntity subEntity : subEntitys) {
				String sectionCode = subEntity.getSectionByType(chargeType);
				if (!StringUtil.isEmpty(sectionCode)) {
					List<PriceSectionSubEntity> psSubs = priceSectionTyService.querySectionDetailByCode(sectionCode);
					if (psSubs != null && psSubs.size() > 0) {//一个活动中的同一种产品只能定义一条明细,如果有多个，只取一个
						if (list == null) {
							list = new ArrayList<List<PriceSectionSubEntity>>();
						}
						list.add(psSubs);
						break;
					}
				}
			}

		}
		return list;
	}
}
