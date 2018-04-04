package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IBseCustomerTyService;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.ICustPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceCustTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.CustPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCustTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.PriceCustTyCache;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.dao.PriceCustTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceCustTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/13 17:14
 */
@Service
public class PriceCustTyService implements IPriceCustTyService {
    @Resource
    private PriceCustTyDao priceCustTyDao;
    @Resource
    private ICorpPriceCityService corpPriceCityService;
    @Resource
    private ICustPriceCityService custPriceCityService;
    @Resource
    private IBseCustomerTyService bseCustomerTyService;
    @Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;
    public PriceCustTyEntity queryPriceCustTyByQueryParam(PriceQueryParam baseTyParam) {
        PriceCustTyEntity entity = null;
        if(StringUtil.isEmpty(baseTyParam.getCustNo())){
			return null;
        }
        // 起运地
 		OrgPositionInfoTyEntity originPositionInfo = null;
        OrgPositionInfoTyEntity currOrgPositionInfoTyEntity = baseTyParam.getOriginPositionInfo();
 		if(currOrgPositionInfoTyEntity != null
 				&& !StringUtil.isEmpty(currOrgPositionInfoTyEntity
 						.getProvinceCode())
 				&& !StringUtil.isEmpty(currOrgPositionInfoTyEntity.getCityCode()) && !StringUtil
 					.isEmpty(currOrgPositionInfoTyEntity.getCountyCode())){
 			originPositionInfo = currOrgPositionInfoTyEntity;
 		} else if (!StringUtil.isEmpty(baseTyParam.getOriginCode())) {
 			originPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(baseTyParam.getOriginCode());
 			if(originPositionInfo == null){
				return null;
 			}
 		}
 		// 目的地
 		OrgPositionInfoTyEntity destPositionInfo = null;
 		OrgPositionInfoTyEntity currDestPositionInfo = baseTyParam.getDestPositionInfo();
 		if(currDestPositionInfo != null
  				&& !StringUtil.isEmpty(currDestPositionInfo
  						.getProvinceCode())
  				&& !StringUtil.isEmpty(currDestPositionInfo.getCityCode()) && !StringUtil
  					.isEmpty(currDestPositionInfo.getCountyCode())){
  			destPositionInfo = currDestPositionInfo;
  		}else if (!StringUtil.isEmpty(baseTyParam.getDestCode())) {
  			destPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(baseTyParam.getDestCode());
  			if(destPositionInfo == null){
				return null;
  			}
  		}
        CorpPriceCityEntity oriCityEntity = corpPriceCityService.queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_SEND);
        CorpPriceCityEntity destCityEntity = corpPriceCityService.queryPriceCity(destPositionInfo, PmsConstants.PRICE_CITY_ARRIVAL);
        CustPriceCityEntity oriCustCityEntity = custPriceCityService.queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_SEND);
        CustPriceCityEntity destCustCityEntity = custPriceCityService.queryPriceCity(destPositionInfo, PmsConstants.PRICE_CITY_ARRIVAL);
        if (oriCityEntity == null && oriCustCityEntity == null) {
			return null;
        }
        if (destCityEntity == null && destCustCityEntity == null) {
			return null;
        }

        if (StringUtil.isNotEmpty(oriCustCityEntity.getSendPriceCityCode()) && StringUtil.isNotEmpty(destCustCityEntity.getArrivalPriceCityCode())) {
            if (baseTyParam.isHistory()) {//历史情况
                PriceQueryParamInSide priceQueryParamInSide = new PriceQueryParamInSide();
                priceQueryParamInSide.setSendPriceCityCode(oriCustCityEntity.getSendPriceCityCode());
                priceQueryParamInSide.setArrivePriceCityCode(destCustCityEntity.getArrivalPriceCityCode());
                priceQueryParamInSide.setBaseTyParam(baseTyParam);
                List<PriceCustTyEntity> lists = priceCustTyDao.queryPriceCustTyByBaseTyParamInSide(priceQueryParamInSide);
                entity = (lists != null && lists.size() > 0 ? lists.get(0) : null);
            } else {
                String keyStr = new CacheKey(baseTyParam.getCustNo(), oriCustCityEntity.getSendPriceCityCode(), destCustCityEntity.getArrivalPriceCityCode(), baseTyParam.getTransTypeCode()).generateKey();
                ICache<String, PriceCustTyEntity> cache = CacheManager.getInstance().getCache(PriceCustTyCache.UUID);
                entity = cache.get(keyStr);
            }
        }
        if (entity == null) {
            if (StringUtil.isNotEmpty(oriCityEntity.getSendPriceCityCode()) && StringUtil.isNotEmpty(destCityEntity.getArrivalPriceCityCode())) {
                if (baseTyParam.isHistory()) {//历史情况
                    PriceQueryParamInSide priceQueryParamInSide = new PriceQueryParamInSide();
                    priceQueryParamInSide.setSendPriceCityCode(oriCityEntity.getSendPriceCityCode());
                    priceQueryParamInSide.setArrivePriceCityCode(destCityEntity.getArrivalPriceCityCode());
                    priceQueryParamInSide.setBaseTyParam(baseTyParam);
                    List<PriceCustTyEntity> lists = priceCustTyDao.queryPriceCustTyByBaseTyParamInSide(priceQueryParamInSide);
                    entity = (lists != null && lists.size() > 0 ? lists.get(0) : null);
                } else {
                    String keyStr = new CacheKey(baseTyParam.getCustNo(), oriCityEntity.getSendPriceCityCode(), destCityEntity.getArrivalPriceCityCode(), baseTyParam.getTransTypeCode()).generateKey();
                    ICache<String, PriceCustTyEntity> cache = CacheManager.getInstance().getCache(PriceCustTyCache.UUID);
                    entity = cache.get(keyStr);
                }
            }
        }
        if(entity!=null){
            if(entity.getPriceCustSubTyEntity()==null){//如果明细为空，也认为是空
                entity=null;
            }
        }
        return entity;
    }
}
