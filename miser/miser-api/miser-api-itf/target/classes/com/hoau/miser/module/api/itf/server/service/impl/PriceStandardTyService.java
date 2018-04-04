package com.hoau.miser.module.api.itf.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceStandardTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.PriceStandardTyCache;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.dao.PriceStandardTyDao;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceStandardTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/13 17:17
 */
@Service
public class PriceStandardTyService implements IPriceStandardTyService {
    @Resource
    private PriceStandardTyDao priceStandardTyDao;
    @Resource
    private ICorpPriceCityService corpPriceCityService;
    @Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;
    public PriceStandardTyEntity queryPriceStandardTyByQueryParam(PriceQueryParam baseTyParam) {
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
        PriceStandardTyEntity entity = null;
        if (baseTyParam.isHistory()) {//走数据库查询
            PriceQueryParamInSide priceQueryParamInSide = new PriceQueryParamInSide();
            priceQueryParamInSide.setBaseTyParam(baseTyParam);
            priceQueryParamInSide.setSendPriceCityCode(oriCityEntity.getSendPriceCityCode());
            priceQueryParamInSide.setArrivePriceCityCode(destCityEntity.getArrivalPriceCityCode());
            List<PriceStandardTyEntity> lists = priceStandardTyDao.queryPriceStandardTyByParamInSide(priceQueryParamInSide);
            entity = (lists != null && lists.size() > 0) ? lists.get(0) : null;
        } else {
            String keyStr = new CacheKey(oriCityEntity.getSendPriceCityCode(), destCityEntity.getArrivalPriceCityCode(), baseTyParam.getTransTypeCode()).generateKey();
            ICache<String, PriceStandardTyEntity> cache = CacheManager.getInstance().getCache(PriceStandardTyCache.UUID);
            entity = cache.get(keyStr);
        }
        return entity;
    }
}
