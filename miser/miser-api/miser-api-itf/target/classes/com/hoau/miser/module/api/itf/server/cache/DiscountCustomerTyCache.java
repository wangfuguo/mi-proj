package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCustomerTyEntity;


/**
 * @author 廖文强
 * @version V1.0
 * @Title: DiscountCustomerTyCache
 * @Package com.hoau.miser.module.api.itf.server.cache
 * @Description: 客户折扣
 * @date 2016/6/8 17:24
 */
public class DiscountCustomerTyCache extends DefaultTTLRedisCache<DiscountCustomerTyEntity>{

    public static String UUID="discountCustomerTyCache-api";
    public String getUUID() {
        return UUID;
    }
}
