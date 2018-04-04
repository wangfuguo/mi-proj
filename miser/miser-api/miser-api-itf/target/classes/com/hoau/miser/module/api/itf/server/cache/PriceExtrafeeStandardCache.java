package com.hoau.miser.module.api.itf.server.cache;

import java.util.List;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceExtrafeeStandardEntity;

/**
 * 标准附加费缓存
 *
 * @author 蒋落琛
 * @date 2016-6-16上午9:52:31
 */
public class PriceExtrafeeStandardCache extends
		DefaultTTLRedisCache<List<PriceExtrafeeStandardEntity>> {

	public static String UUID = "PriceExtrafeeStandardCache-api";

	public String getUUID() {
		return UUID;
	}
}
