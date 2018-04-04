package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCorpTyEntity;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: DiscountCropTyCache
 * @Package com.hoau.miser.module.api.itf.server.cache
 * @Description: 网点折扣
 * @date 2016/6/8 17:24
 */
public class DiscountCorpTyCache extends DefaultTTLRedisCache<DiscountCorpTyEntity>{

    public static String UUID="discountCropTyCache-api";
    public String getUUID() {
        return UUID;
    }
}
