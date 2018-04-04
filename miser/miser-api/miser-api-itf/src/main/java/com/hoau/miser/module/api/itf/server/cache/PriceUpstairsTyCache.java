package com.hoau.miser.module.api.itf.server.cache;

import java.util.List;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceUpstairsEntity;

/**
 * 上楼费缓存
 *
 * @author 蒋落琛
 * @date 2016-6-16上午9:52:31
 */
public class PriceUpstairsTyCache extends
		DefaultTTLRedisCache<List<PriceUpstairsEntity>> {

	public static String UUID = "PriceUpstairsTyCache-api";

	public String getUUID() {
		return UUID;
	}
}
