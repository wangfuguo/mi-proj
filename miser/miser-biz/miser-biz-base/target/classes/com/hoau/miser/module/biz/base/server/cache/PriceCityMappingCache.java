package com.hoau.miser.module.biz.base.server.cache;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
/**
 * ClassName: 价格城市映射关系缓存
 * @author 刘海飞
 * @date 2016年1月8日
 * @version V1.0
 */
@Component
public class PriceCityMappingCache extends DefaultTTLRedisCache<TranstypeEntity>{
	public static final String PRICE_CITY_MAPPING_CACHE_UUID = "priceCityMapping";
	@Override
	public String getUUID() {
		return PRICE_CITY_MAPPING_CACHE_UUID;
	}

}
