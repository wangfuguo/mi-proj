package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.OutLineTyEntity;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: OutLineTyCache
 * @Package com.hoau.miser.module.api.itf.server.cache
 * @Description: 外发偏线缓存
 * @date 2016/6/13
 */
public class OutLineTyCache extends DefaultTTLRedisCache<OutLineTyEntity>{

    public static String UUID="OutLineTyCache-api";
    public String getUUID() {
        return UUID;
    }
}
