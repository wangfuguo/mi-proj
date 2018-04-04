package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 活动缓存
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class PriceEventWithoutDetailTyCache extends DefaultTTLRedisCache<List<PriceEventEntity>>{
	public static final String UUID = "PriceEventWithoutDetailTyCache";
	@Override
	public String getUUID() {
		return UUID;
	}

}
