package com.hoau.miser.module.biz.extrafee.server.cache;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceUpstairsEntity;
/**
 * @Description: 运输类型缓存 
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0   
 */
@Component
public class PriceUpstairsCache extends DefaultTTLRedisCache<PriceUpstairsEntity>{
	public static final String PRICEUPSTAIRS_CACHE_UUID = "priceUpstairs";
	@Override
	public String getUUID() {
		return PRICEUPSTAIRS_CACHE_UUID;
	}

}
