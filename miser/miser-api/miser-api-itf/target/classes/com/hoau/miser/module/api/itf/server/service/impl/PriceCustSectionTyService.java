package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceCustSectionTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCustSectionTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.PriceCustSectionTyCache;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.dao.PriceCustSectionTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.param.PriceSectionQueryParamInSide;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceCustTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/13 17:14
 */
@Service
public class PriceCustSectionTyService implements IPriceCustSectionTyService {
    @Resource
    private PriceCustSectionTyDao priceCustSectionTyDao;
    @Resource
    private ICorpPriceCityService corpPriceCityService;
    @Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;

    public PriceCustSectionTyEntity queryPriceCustSectionTyByQueryParam(PriceQueryParam baseTyParam) {
        PriceCustSectionTyEntity entity = null;
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
		if (oriCityEntity == null) {
			return null;
		}
        CorpPriceCityEntity destCityEntity = corpPriceCityService.queryPriceCity(destPositionInfo, PmsConstants.PRICE_CITY_ARRIVAL);
        if (destCityEntity == null) {
			return null;
        }
            if (StringUtil.isNotEmpty(oriCityEntity.getSendPriceCityCode()) && StringUtil.isNotEmpty(destCityEntity.getArrivalPriceCityCode())) {
                if (baseTyParam.isHistory()) {//历史情况
                    PriceSectionQueryParamInSide paramInSide = new PriceSectionQueryParamInSide();
                    paramInSide.setSendPriceCityCode(oriCityEntity.getSendPriceCityCode());
                    paramInSide.setArrivePriceCityCode(destCityEntity.getArrivalPriceCityCode());
                    paramInSide.setBaseTyParam(baseTyParam);
                    entity = priceCustSectionTyDao.queryPriceCustSectionTyByParamInSide(paramInSide);
                } else {
                    String keyStr = new CacheKey(baseTyParam.getCustNo(), oriCityEntity.getSendPriceCityCode(), destCityEntity.getArrivalPriceCityCode(), baseTyParam.getTransTypeCode()).generateKey();
                    ICache<String, PriceCustSectionTyEntity> cache = CacheManager.getInstance().getCache(PriceCustSectionTyCache.UUID);
                    entity = cache.get(keyStr);
                }
            }
        if(entity!=null){
            if(entity.getPriceCustSubSectionTyEntity()==null){//如果明细为空，也认为是空
                entity=null;
            }
        }
        return entity;
    }
}
