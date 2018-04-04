package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.vo.AvailableTransportTypeQueryResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 运输类型缓存
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class AvailableTransportTypeCache extends DefaultTTLRedisCache<List<AvailableTransportTypeQueryResult>>{
	public static final String TRANSTYPE_CACHE_UUID = "AvailableTransportTypeCache";
	@Override
	public String getUUID() {
		return TRANSTYPE_CACHE_UUID;
	}

}
