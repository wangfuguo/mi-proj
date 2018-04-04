package com.hoau.miser.module.api.itf.server.cache;

import java.util.List;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.PricePackageFeePcEntity;

/**
 * 标准价格城市包装费缓存
 *
 * @author 蒋落琛
 * @date 2016-6-16上午9:52:31
 */
public class PricePackageFeePcCache extends
		DefaultTTLRedisCache<List<PricePackageFeePcEntity>> {

	public static String UUID = "PricePackageFeePcCache-api";

	public String getUUID() {
		return UUID;
	}
}
