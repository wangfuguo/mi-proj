package com.hoau.miser.module.api.itf.server.cache;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PricePackageFeePcEntity;
import com.hoau.miser.module.api.itf.server.dao.PricePackageFeePcTyDao;

/**
 * 标准价格城市包装费缓存
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:52:58
 */
@Component
public class PricePackageFeePcCacheProvider implements
		ITTLCacheProvider<List<PricePackageFeePcEntity>> {

	@Resource
	PricePackageFeePcTyDao pricePackageFeePcTyDao;

	public List<PricePackageFeePcEntity> get(String keyStr) {
		List<PricePackageFeePcEntity> list = null;
		CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
		if (cacheKey != null && cacheKey.length() >= 2) {
			PricePackageFeePcEntity p = new PricePackageFeePcEntity();
			if(cacheKey.length() == 2){
				p.setTransTypeCode(cacheKey.get(0));
				p.setPriceCity(cacheKey.get(1));
			} else if(cacheKey.length() == 3){
				p.setTransTypeCode(cacheKey.get(0));
				p.setPriceCity(cacheKey.get(1));
				p.setCode(cacheKey.get(2));
			}
			// 缓存只查当前时间的数据
			p.setBillTime(new Date());
			list = pricePackageFeePcTyDao.queryListByParam(p);
		}
		return list;
	}

}
