package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceCorpSectionTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpSectionTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.PriceCorpSectionTyCache;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.dao.PriceCorpSectionTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.param.PriceSectionQueryParamInSide;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceCorpSectionTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: 分段网点价格
 * @date 2016/7/05
 */
@Service
public class PriceCorpSectionTyService implements IPriceCorpSectionTyService {
    @Resource
    private PriceCorpSectionTyDao priceCorpSectionTyDao;
    @Resource
    private ICorpPriceCityService corpPriceCityService;
    @Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;
    
    public PriceCorpSectionTyEntity queryPriceSectionQueryParam(PriceQueryParam baseTyParam) {
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
        CorpPriceCityEntity cityEntity = corpPriceCityService.queryPriceCity(destPositionInfo, PmsConstants.PRICE_CITY_ARRIVAL);
        if (cityEntity == null) {
			return null;
        }
        PriceCorpSectionTyEntity entity = null;
        if (!baseTyParam.isHistory()) {//不是历史
            String keyStr = new CacheKey(baseTyParam.getOriginCode(), cityEntity.getArrivalPriceCityCode(), baseTyParam.getTransTypeCode()).generateKey();
            ICache<String, PriceCorpSectionTyEntity> cache = CacheManager.getInstance().getCache(PriceCorpSectionTyCache.UUID);
            entity = cache.get(keyStr);
        } else {
            PriceSectionQueryParamInSide paramInSide = new PriceSectionQueryParamInSide();
            paramInSide.setBaseTyParam(baseTyParam);
            paramInSide.setArrivePriceCityCode(cityEntity.getArrivalPriceCityCode());
            entity = priceCorpSectionTyDao.queryPriceSectionQueryParamInSide(paramInSide);
        }
        return entity;
    }


}
