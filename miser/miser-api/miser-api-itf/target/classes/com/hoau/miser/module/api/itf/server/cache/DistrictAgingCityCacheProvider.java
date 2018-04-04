package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.DistrictAgingCityRequestEntity;
import com.hoau.miser.module.api.itf.server.dao.CorpAgingCityDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description 省市区县对应时效城市缓存
 * ClassName: CorpAgingCityCacheProvider
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class DistrictAgingCityCacheProvider implements ITTLCacheProvider<CorpAgingCityEntity> {

	@Resource
	CorpAgingCityDao corpAgingCityDao;

	public CorpAgingCityEntity get(String key) {
		CacheKey cacheKey = CacheKey.convertToCacheKey(key);
		if (cacheKey == null || cacheKey.length() != 4) {
			return null;
		}
		DistrictAgingCityRequestEntity queryParam = new DistrictAgingCityRequestEntity();
		queryParam.setProvinceCode(cacheKey.get(0));
		queryParam.setCityCode(cacheKey.get(1));
		queryParam.setAreaCode(cacheKey.get(2));
		queryParam.setAgingCityType(cacheKey.get(3));
		return corpAgingCityDao.queryAgingCityByDistrict(queryParam);
	}

}
