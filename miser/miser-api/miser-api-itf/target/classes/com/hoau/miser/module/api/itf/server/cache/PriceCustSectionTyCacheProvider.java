package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCustSectionTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCustTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionBaseTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.dao.PriceCustSectionTyDao;
import com.hoau.miser.module.api.itf.server.dao.PriceCustTyDao;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;
import com.hoau.miser.module.api.itf.server.param.PriceSectionQueryParamInSide;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Description: 提货费缓存
 * @date 2016年06月07日
 */
@Component
public class PriceCustSectionTyCacheProvider implements ITTLCacheProvider<PriceCustSectionTyEntity> {

    @Resource
    private PriceCustSectionTyDao priceCustSectionTyDao;

    public PriceCustSectionTyEntity get(String keyStr) {
        PriceCustSectionTyEntity entity = null;
        // 客户编号，出发价格城市，到达价格城市，运输类型
        CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
        if (cacheKey != null && cacheKey.length() >= 4) {
            PriceQueryParam queryParam = new PriceQueryParam();
            queryParam.setBillTime(new Date());
            queryParam.setCustNo(cacheKey.get(0));
            queryParam.setTransTypeCode(cacheKey.get(3));
            PriceSectionQueryParamInSide paramInSide = new PriceSectionQueryParamInSide();
            paramInSide.setSendPriceCityCode(cacheKey.get(1));
            paramInSide.setArrivePriceCityCode(cacheKey.get(2));
            paramInSide.setBaseTyParam(queryParam);
            entity = priceCustSectionTyDao.queryPriceCustSectionTyByParamInSide(paramInSide);
        }

        return entity;
    }
}
