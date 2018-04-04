package com.hoau.miser.module.api.itf.server.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.server.dao.CorpPriceCityDao;

/**
 * @description 运输类型缓存
 * ClassName: CorpPriceCityCacheProvider
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class CorpPriceCityCacheProvider implements ITTLCacheProvider<CorpPriceCityEntity> {

	@Resource
	CorpPriceCityDao corpPriceCityDao;

	public CorpPriceCityEntity get(String keyStr) {
		if (StringUtil.isEmpty(keyStr)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 起运地组织编号，运输类型
		CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
		if(cacheKey != null && cacheKey.length() == 4){
			map.put("provinceCode", cacheKey.get(0));
			map.put("cityCode", cacheKey.get(1));
			map.put("countyCode", cacheKey.get(2));
			map.put("type", cacheKey.get(3));
		}
		return corpPriceCityDao.queryPriceCityByOrgCode(map);
	}

}
