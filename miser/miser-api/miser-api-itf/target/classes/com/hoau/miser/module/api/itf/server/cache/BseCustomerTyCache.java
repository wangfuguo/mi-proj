package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.BseCustomerTyEntity;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BseCustomerTyCache
 * @Package com.hoau.miser.module.api.itf.server.cache
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/8 17:24
 */
public class BseCustomerTyCache extends DefaultTTLRedisCache<BseCustomerTyEntity>{

    public static String UUID="BseCustomerTyCache-api";
    public String getUUID() {
        return UUID;
    }
}
