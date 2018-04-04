package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceCorpTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.PriceCorpTyCache;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.dao.PriceCorpTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceCorpTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/13 17:11
 */
@Service
public class PriceCorpTyService implements IPriceCorpTyService {
    @Resource
    private PriceCorpTyDao priceCorpTyDao;
    @Resource
    private ICorpPriceCityService corpPriceCityService;
    @Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;
    
    public PriceCorpTyEntity queryPriceCorpTyByQueryParam(PriceQueryParam baseTyParam) {
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
        PriceCorpTyEntity entity = null;
        if (!baseTyParam.isHistory()) {//不是历史
            String keyStr = new CacheKey(baseTyParam.getOriginCode(), cityEntity.getArrivalPriceCityCode(), baseTyParam.getTransTypeCode()).generateKey();
            ICache<String, PriceCorpTyEntity> cache = CacheManager.getInstance().getCache(PriceCorpTyCache.UUID);
            entity = cache.get(keyStr);
        } else {
            PriceQueryParamInSide priceQueryParamInSide = new PriceQueryParamInSide();
            priceQueryParamInSide.setBaseTyParam(baseTyParam);
            priceQueryParamInSide.setArrivePriceCityCode(cityEntity.getArrivalPriceCityCode());
            List<PriceCorpTyEntity> list = priceCorpTyDao.queryPriceCorpTyByParamInSide(priceQueryParamInSide);
            entity = (list != null && list.size() > 0) ? list.get(0) : null;
        }
        return entity;
    }
}
