package com.hoau.miser.module.api.itf.server.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CityTypeEntity;
import com.hoau.miser.module.api.itf.server.dao.CityTypeTyDao;

/**
 * 城市类别缓存
 *
 * @author 蒋落琛
 * @date 2016-6-29上午8:53:44
 */
@Component
public class CityTypeTyCacheProvider implements
		ITTLCacheProvider<List<CityTypeEntity>> {

	@Resource
	CityTypeTyDao cityTypeTyDao;

	public List<CityTypeEntity> get(String keyStr) {
		List<CityTypeEntity> list = null;
		CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
		if (cacheKey != null && cacheKey.length() == 3) {
			CityTypeEntity cityTypeEntity = new CityTypeEntity();
			// 省
			cityTypeEntity.setProvinceCode(cacheKey.get(0));
			// 市
			cityTypeEntity.setCityCode(cacheKey.get(1));
			// 区
			cityTypeEntity.setAreaCode(cacheKey.get(2));
			list = cityTypeTyDao.queryCityTypeByEntity(cityTypeEntity);
		}
		return list;
	}

}
