package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpAgingCityService;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IAvailableTransportTypeService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.TransportTypeQueryEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.*;
import com.hoau.miser.module.api.itf.server.cache.AvailableTransportTypeCache;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.constants.PriceConstants;
import com.hoau.miser.module.api.itf.server.dao.AvailableTransportTypeDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yulin.chen@newhoau.com.cn
 * @version V1.0
 * @Description 可用运输类型查询接口实现类
 * @date 2016年06月07日21:33:05
 */
@Service
public class AvailableTransportTypeService implements IAvailableTransportTypeService {

	@Resource
	AvailableTransportTypeDao availableTransportTypeDao;
	@Resource
	ICorpAgingCityService corpAgingCityService;
	@Resource
	ICorpPriceCityService corpPriceCityService;
	@Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;

	/**
	 * @Description: 运输类型集合交集(标准和时效)
	 * @param availableTransportTypeQueryParam :出发，到达。。。
	 * @return List<TransTypeFyDto>
	 * @author 廖文强
	 * @date 2016年06月02日
	 */
	@Override
	public List<AvailableTransportTypeQueryResult> queryAvailableTransTypes(AvailableTransportTypeQueryParam availableTransportTypeQueryParam) {
		//入参校验
		if (availableTransportTypeQueryParam.isHistory() && availableTransportTypeQueryParam.getBillTime() == null) {
			throw new ChargeException("历史数据未提供开单时间！");
		}
		if (StringUtils.isEmpty(availableTransportTypeQueryParam.getOriginCode()) || StringUtils.isEmpty(availableTransportTypeQueryParam.getDestCode())) {
			throw new ChargeException("起运地与目的地不能为空！");
		}
		//查询时效可用的产品
		List<AvailableTransportTypeQueryResult> timeAvailableTransportTypes = this.queryTimeTransTypes(availableTransportTypeQueryParam);
		//查询价格可用的产品
		List<AvailableTransportTypeQueryResult> priceAvailableTransportTypes = this.queryPriceTransTypes(availableTransportTypeQueryParam);
		//去时效和价格可用的交集
		List<AvailableTransportTypeQueryResult> result = new ArrayList<AvailableTransportTypeQueryResult>();
		if (timeAvailableTransportTypes != null) {
			result.addAll(timeAvailableTransportTypes);
		}
		if (priceAvailableTransportTypes != null) {
			result.retainAll(priceAvailableTransportTypes);
		}
		return result;
	}

	/**
	 * 根据条件查询时效可用的产品
	 * @param availableTransportTypeQueryParam
	 * @return
	 */
	private List<AvailableTransportTypeQueryResult> queryTimeTransTypes(AvailableTransportTypeQueryParam availableTransportTypeQueryParam) {
		//获取出发时效城市
		CorpAgingCityEntity sendCity = corpAgingCityService.queryAgingCityByOrgCode(availableTransportTypeQueryParam.getOriginCode());
		if (sendCity == null) {
			return null;
		}
		//获取到达时效城市
		CorpAgingCityEntity arrivalCity = corpAgingCityService.queryAgingCityByOrgCode(availableTransportTypeQueryParam.getDestCode());
		if (arrivalCity == null) {
			return null;
		}
		//如果不是历史,走缓存
		if (!availableTransportTypeQueryParam.isHistory()) {
			String param = new CacheKey(sendCity.getSendAgingCityCode(), arrivalCity.getArrivalAgingCityCode(), "TIME").generateKey();
			ICache<String, List<AvailableTransportTypeQueryResult>> cache = CacheManager.getInstance().getCache(AvailableTransportTypeCache.TRANSTYPE_CACHE_UUID);
			return cache.get(param);
		} else {
			//如果是历史,直接查询数据库
			TransportTypeQueryEntity entity = new TransportTypeQueryEntity();
			entity.setSendTimeCity(sendCity.getSendAgingCityCode());
			entity.setArriveTimeCity(arrivalCity.getArrivalAgingCityCode());
			entity.setQueryTime(availableTransportTypeQueryParam.getBillTime());
			return availableTransportTypeDao.queryTimeAvailableTransportTypes(entity);
		}
	}

	/**
	 * 根据条件查询价格可用的产品
	 * @param availableTransportTypeQueryParam
	 * @return
	 */
	private List<AvailableTransportTypeQueryResult> queryPriceTransTypes(AvailableTransportTypeQueryParam availableTransportTypeQueryParam) {
		// 起运地
  		OrgPositionInfoTyEntity originPositionInfo = null;
        OrgPositionInfoTyEntity currOrgPositionInfoTyEntity = availableTransportTypeQueryParam.getOriginPositionInfo();
  		if(currOrgPositionInfoTyEntity != null
  				&& !StringUtil.isEmpty(currOrgPositionInfoTyEntity
  						.getProvinceCode())
  				&& !StringUtil.isEmpty(currOrgPositionInfoTyEntity.getCityCode()) && !StringUtil
  					.isEmpty(currOrgPositionInfoTyEntity.getCountyCode())){
  			originPositionInfo = currOrgPositionInfoTyEntity;
  		} else if (!StringUtil.isEmpty(availableTransportTypeQueryParam.getOriginCode())) {
  			originPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(availableTransportTypeQueryParam.getOriginCode());
  			if(originPositionInfo == null){
  				throw new ChargeException("起运地门店省市区信息为空！");
  			}
  		} else {
  			throw new ChargeException("起运地不能为空！");
  		}
  		// 目的地
  		OrgPositionInfoTyEntity destPositionInfo = null;
  		OrgPositionInfoTyEntity currDestPositionInfo = availableTransportTypeQueryParam.getDestPositionInfo();
  		if(currDestPositionInfo != null
  				&& !StringUtil.isEmpty(currDestPositionInfo
  						.getProvinceCode())
  				&& !StringUtil.isEmpty(currDestPositionInfo.getCityCode()) && !StringUtil
  					.isEmpty(currDestPositionInfo.getCountyCode())){
  			destPositionInfo = currDestPositionInfo;
  		}else if (!StringUtil.isEmpty(availableTransportTypeQueryParam.getDestCode())) {
  			destPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(availableTransportTypeQueryParam.getDestCode());
  			if(destPositionInfo == null){
  				throw new ChargeException("目的地门店省市区信息为空！");
  			}
  		} else {
  			throw new ChargeException("目的地不能为空！");
  		}
		//查询出发价格城市
		CorpPriceCityEntity sendCity = corpPriceCityService.queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_SEND);
		if (sendCity == null) {
			return null;
		}
		//查询到达价格城市
		CorpPriceCityEntity arrivalCity = corpPriceCityService.queryPriceCity(destPositionInfo, PmsConstants.PRICE_CITY_ARRIVAL);
		if (arrivalCity == null) {
			return null;
		}
		//如果不是历史,走缓存查询
		if (!availableTransportTypeQueryParam.isHistory()) {
			String param = new CacheKey(sendCity.getSendPriceCityCode(), arrivalCity.getArrivalPriceCityCode(), "PRICE").generateKey();
			ICache<String, List<AvailableTransportTypeQueryResult>> cache = CacheManager.getInstance().getCache(AvailableTransportTypeCache.TRANSTYPE_CACHE_UUID);
			return cache.get(param);
		} else {
			//如果是历史,走数据库查询
			TransportTypeQueryEntity entity = new TransportTypeQueryEntity();
			entity.setSendTimeCity(sendCity.getSendPriceCityCode());
			entity.setArriveTimeCity(arrivalCity.getArrivalPriceCityCode());
			entity.setQueryTime(availableTransportTypeQueryParam.getBillTime());
			return availableTransportTypeDao.queryTimeAvailableTransportTypes(entity);
		}
	}


}
