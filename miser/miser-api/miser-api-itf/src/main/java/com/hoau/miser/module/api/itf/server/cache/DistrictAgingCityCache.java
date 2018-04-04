package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.DistrictAgingCityRequestEntity;
import org.springframework.stereotype.Component;

/**
 * @Description: 省市区县对应时效城市缓存
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class DistrictAgingCityCache extends DefaultTTLRedisCache<CorpAgingCityEntity>{
	public static final String UUID = DistrictAgingCityCache.class.getName();
	@Override
	public String getUUID() {
		return UUID;
	}

}
