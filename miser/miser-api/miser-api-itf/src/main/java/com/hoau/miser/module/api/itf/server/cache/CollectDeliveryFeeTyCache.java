package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.CityTypeEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCollectDeliveryFeeEntity;

import java.util.List;

/**
* @Description: 代收货款手续费管理缓存
* @author 蒋落琛
* @date 2016/7/14 17:21:59
* @version V1.0
*/
public class CollectDeliveryFeeTyCache extends
		DefaultTTLRedisCache<List<PriceCollectDeliveryFeeEntity>> {

	public static String UUID = "CollectDeliveryFeeTyCache-api";

	public String getUUID() {
		return UUID;
	}
}
