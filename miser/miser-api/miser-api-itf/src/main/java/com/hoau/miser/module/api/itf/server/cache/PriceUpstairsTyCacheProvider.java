package com.hoau.miser.module.api.itf.server.cache;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceUpstairsEntity;
import com.hoau.miser.module.api.itf.server.dao.PriceUpstairsTyDao;

/**
 * 上楼费缓存
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:52:58
 */
@Component
public class PriceUpstairsTyCacheProvider implements
		ITTLCacheProvider<List<PriceUpstairsEntity>> {

	@Resource
	PriceUpstairsTyDao priceUpstairsTyDao;

	public List<PriceUpstairsEntity> get(String keyStr) {
		List<PriceUpstairsEntity> list = null;
		CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
		if (cacheKey != null && cacheKey.length() == 2) {
			PriceUpstairsEntity entity = new PriceUpstairsEntity();
			// 类型为大件上楼
			entity.setType(cacheKey.get(1));
			// 产品类型
			entity.setTransportType(cacheKey.get(0));
			// 缓存只查当前时间的数据
			entity.setBillTime(new Date());
			list = priceUpstairsTyDao.queryUpstairsPrices(entity);
		}
		return list;
	}

}
