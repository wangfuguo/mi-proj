package com.hoau.miser.module.biz.base.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import org.springframework.stereotype.Component;

/**
 * @Description: 时效城市缓存
 * @author 何羽
 * @date 2016年7月7日
 * @version V1.0   
 */
@Component
public class TimeCityCache extends DefaultTTLRedisCache<TranstypeEntity>{
	public static final String TIMECITY_CACHE_UUID = "timeCity";
	@Override
	public String getUUID() {
		return TIMECITY_CACHE_UUID;
	}

}
