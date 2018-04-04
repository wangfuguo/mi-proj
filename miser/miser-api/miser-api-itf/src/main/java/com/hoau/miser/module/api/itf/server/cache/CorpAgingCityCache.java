package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import org.springframework.stereotype.Component;

/**
 * @Description: 物流时效价格映射运输类型缓存
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class CorpAgingCityCache extends DefaultTTLRedisCache<CorpAgingCityEntity>{
	public static final String UUID = "CorpAgingCityCache";
	@Override
	public String getUUID() {
		return UUID;
	}

}
