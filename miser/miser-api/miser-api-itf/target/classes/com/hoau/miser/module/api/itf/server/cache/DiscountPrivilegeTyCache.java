package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountPrivilegeTyEntity;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: DiscountPrivilegeTyCache
 * @Package com.hoau.miser.module.api.itf.server.cache
 * @Description: 越发越惠折扣
 * @date 2016/06/13
 */
public class DiscountPrivilegeTyCache extends DefaultTTLRedisCache<DiscountPrivilegeTyEntity>{

    public static String UUID="DiscountPrivilegeTyCache-api";
    public String getUUID() {
        return UUID;
    }
}
