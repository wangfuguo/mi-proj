package com.hoau.miser.module.api.itf.server.service.impl;

import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.miser.module.api.itf.api.server.ICustPriceCityService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CustPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.server.cache.CustPriceCityCache;

/**
 * @ClassName: CorpPriceCityService
 * @Description: 价格城市维护
 * @author: Chenyl yulin.chen@hoau.net
 * @date: 2016/3/9 18:44
 */
@Service
public class CustPriceCityService implements ICustPriceCityService {

    /**
     * @param orgCode 组织编码
     * @return CorpPriceCityEntity
     * @throws
     * @Description: 查询物流客户价格城市映射
     * @author 廖文强
     * @date 2016年06月02日
     */
    @Override
    public CustPriceCityEntity queryPriceCity(OrgPositionInfoTyEntity entity, String priceCityType) {
		ICache<String, CustPriceCityEntity> cache = CacheManager.getInstance().getCache(CustPriceCityCache.UUID);
		String keyStr=new CacheKey(entity.getProvinceCode(), entity.getCityCode(), entity.getCountyCode(), priceCityType).generateKey();
        return cache.get(keyStr);
    }
}
