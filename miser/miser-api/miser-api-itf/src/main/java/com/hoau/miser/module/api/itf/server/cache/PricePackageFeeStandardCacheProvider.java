package com.hoau.miser.module.api.itf.server.cache;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PricePackageFeeStandardEntity;
import com.hoau.miser.module.api.itf.server.dao.PricePackageFeeStandardTyDao;

/**
 * 标准包装费缓存
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:52:58
 */
@Component
public class PricePackageFeeStandardCacheProvider implements
		ITTLCacheProvider<List<PricePackageFeeStandardEntity>> {

	@Resource
	PricePackageFeeStandardTyDao pricePackageFeeStandardTyDao;

	public List<PricePackageFeeStandardEntity> get(String keyStr) {
		List<PricePackageFeeStandardEntity> list = null;
		CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
		if (cacheKey != null && cacheKey.length() >= 1) {
			PricePackageFeeStandardEntity params = new PricePackageFeeStandardEntity();
			if(cacheKey.length() == 1){
				params.setTransTypeCode(cacheKey.get(0));
			} else if(cacheKey.length() == 2){
				params.setTransTypeCode(cacheKey.get(0));
				params.setCode(cacheKey.get(1));
			}
			// 缓存只查当前时间的数据
			params.setBillTime(new Date());
			list = pricePackageFeeStandardTyDao.queryListByParam(params);
		}
		return list;
	}

}
