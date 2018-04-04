package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceStandardSectionTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardSectionTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.PriceStandardSectionTyCache;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.dao.PriceStandardSectionTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.param.PriceSectionQueryParamInSide;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceStandardSectionTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/7/5 10:37
 */
@Service
public class PriceStandardSectionTyService implements IPriceStandardSectionTyService {

    @Resource
    private PriceStandardSectionTyDao priceStandardSectionTyDao;
    @Resource
    private ICorpPriceCityService corpPriceCityService;
    @Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;
    public PriceStandardSectionTyEntity queryByPriceSectionQueryParam(PriceQueryParam queryParam) {
        // 起运地
 		OrgPositionInfoTyEntity originPositionInfo = null;
         OrgPositionInfoTyEntity currOrgPositionInfoTyEntity = queryParam.getOriginPositionInfo();
 		if(currOrgPositionInfoTyEntity != null
 				&& !StringUtil.isEmpty(currOrgPositionInfoTyEntity
 						.getProvinceCode())
 				&& !StringUtil.isEmpty(currOrgPositionInfoTyEntity.getCityCode()) && !StringUtil
 					.isEmpty(currOrgPositionInfoTyEntity.getCountyCode())){
 			originPositionInfo = currOrgPositionInfoTyEntity;
 		} else if (!StringUtil.isEmpty(queryParam.getOriginCode())) {
 			originPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(queryParam.getOriginCode());
 			if(originPositionInfo == null){
				return null;
 			}
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
  		}else if (!StringUtil.isEmpty(queryParam.getDestCode())) {
  			destPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(queryParam.getDestCode());
  			if(destPositionInfo == null){
				return null;
  			}
  		}
        CorpPriceCityEntity oriCityEntity = corpPriceCityService.queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_SEND);
		if (oriCityEntity == null) {
			return null;
		}
        CorpPriceCityEntity destCityEntity = corpPriceCityService.queryPriceCity(destPositionInfo, PmsConstants.PRICE_CITY_ARRIVAL);
        if (destCityEntity == null) {
			return null;
        }
        PriceStandardSectionTyEntity entity=null;
        if(queryParam.isHistory()){
            PriceSectionQueryParamInSide paramInSide=new PriceSectionQueryParamInSide();
            paramInSide.setBaseTyParam(queryParam);
            paramInSide.setSendPriceCityCode(oriCityEntity.getSendPriceCityCode());
            paramInSide.setArrivePriceCityCode(destCityEntity.getArrivalPriceCityCode());
            entity=priceStandardSectionTyDao.queryByPriceSectionQueryParamInSide(paramInSide);
        }else{
            String keyStr = new CacheKey(oriCityEntity.getSendPriceCityCode(), destCityEntity.getArrivalPriceCityCode(), queryParam.getTransTypeCode()).generateKey();
            ICache<String, PriceStandardSectionTyEntity> cache = CacheManager.getInstance().getCache(PriceStandardSectionTyCache.UUID);
            entity = cache.get(keyStr);
        }
        return entity;
    }
}
