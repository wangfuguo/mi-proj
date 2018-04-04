package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.server.IPricePickupFeeTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PricePickupFeeTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.PricePickupFeeTyCache;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.dao.PricePickupFeeTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PricePickupFeeTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/6 17:30
 */
@Service
public class PricePickupFeeTyService implements IPricePickupFeeTyService {

    @Resource
    private PricePickupFeeTyDao pickupFeeTyDao;
    @Resource
    private ICorpPriceCityService corpPriceCityService;
    @Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;
    
    public PricePickupFeeTyEntity queryPricePickupFeeByQueryParam(PriceQueryParam baseTyParam) {
        PricePickupFeeTyEntity entity=null;
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
        CorpPriceCityEntity oriCityEntity = corpPriceCityService.queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_SEND);
        if (oriCityEntity == null) {
			return null;
        }

        if(baseTyParam.isHistory()){
            PriceQueryParamInSide paramInSide=new PriceQueryParamInSide();
            paramInSide.setSendPriceCityCode(oriCityEntity.getSendPriceCityCode());
            paramInSide.setBaseTyParam(baseTyParam);
            List<PricePickupFeeTyEntity> list =pickupFeeTyDao.queryPricePickupFeeByParamInSide(paramInSide);
            entity=(list != null && list.size() > 0) ? list.get(0) : null;
        }else{
            String keyStr=new CacheKey(oriCityEntity.getSendPriceCityCode(),baseTyParam.getTransTypeCode()).generateKey();
            ICache<String,PricePickupFeeTyEntity> cache=CacheManager.getInstance().getCache(PricePickupFeeTyCache.UUID);
            entity=cache.get(keyStr);
        }
        return entity;
    }
}
