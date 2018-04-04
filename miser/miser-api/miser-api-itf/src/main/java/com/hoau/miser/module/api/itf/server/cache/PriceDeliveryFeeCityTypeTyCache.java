package com.hoau.miser.module.api.itf.server.cache;

import java.util.List;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceDeliveryFeeCityTypeEntity;

/**
 * 送货费分级收费缓存
 * 
 * @author 蒋落琛
 * @date 2016-6-29上午8:52:45
 */
public class PriceDeliveryFeeCityTypeTyCache extends
		DefaultTTLRedisCache<List<PriceDeliveryFeeCityTypeEntity>> {

	public static String UUID = "PriceDeliveryFeeCityTypeTyCache-api";

	public String getUUID() {
		return UUID;
	}
}
