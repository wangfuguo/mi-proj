package com.hoau.miser.module.api.itf.server.cache;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceExtrafeeStandardEntity;
import com.hoau.miser.module.api.itf.server.dao.PriceExtrafeeStandardTyDao;

/**
 * 标准附加费缓存
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:52:58
 */
@Component
public class PriceExtrafeeStandardCacheProvider implements
		ITTLCacheProvider<List<PriceExtrafeeStandardEntity>> {

	@Resource
	PriceExtrafeeStandardTyDao priceExtrafeeStandardTyDao;

	public List<PriceExtrafeeStandardEntity> get(String keyStr) {
		List<PriceExtrafeeStandardEntity> list = null;
		CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
		if (cacheKey != null && cacheKey.length() == 2) {
			PriceExtrafeeStandardEntity params = new PriceExtrafeeStandardEntity();
			// 附加费类型
			params.setType(cacheKey.get(1));
			// 运输类型
			params.setTransTypeCode(cacheKey.get(0));
			// 缓存只查当前时间的数据
			params.setBillTime(new Date());
			list = priceExtrafeeStandardTyDao.queryListByParam(params);
		}
		return list;
	}

}
