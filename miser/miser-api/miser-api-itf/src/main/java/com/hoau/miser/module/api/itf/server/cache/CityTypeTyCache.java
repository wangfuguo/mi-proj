package com.hoau.miser.module.api.itf.server.cache;

import java.util.List;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.CityTypeEntity;

/**
 * 城市类别缓存
 * 
 * @author 蒋落琛
 * @date 2016-6-29上午8:52:45
 */
public class CityTypeTyCache extends
		DefaultTTLRedisCache<List<CityTypeEntity>> {

	public static String UUID = "CityTypeTyCache-api";

	public String getUUID() {
		return UUID;
	}
}
