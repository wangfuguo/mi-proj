package com.hoau.miser.module.biz.base.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import org.springframework.stereotype.Component;

/**
 * @Description: 价格城市缓存
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0   
 */
@Component
public class PriceCityCache extends DefaultTTLRedisCache<TranstypeEntity>{
	public static final String PRICECITY_CACHE_UUID = "priceCity";
	@Override
	public String getUUID() {
		return PRICECITY_CACHE_UUID;
	}

}
