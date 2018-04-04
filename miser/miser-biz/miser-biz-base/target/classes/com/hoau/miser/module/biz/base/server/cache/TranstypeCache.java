package com.hoau.miser.module.biz.base.server.cache;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
/**
 * @Description: 运输类型缓存 
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0   
 */
@Component
public class TranstypeCache extends DefaultTTLRedisCache<TranstypeEntity>{
	public static final String TRANSTYPE_CACHE_UUID = "transtype";
	@Override
	public String getUUID() {
		return TRANSTYPE_CACHE_UUID;
	}

}
