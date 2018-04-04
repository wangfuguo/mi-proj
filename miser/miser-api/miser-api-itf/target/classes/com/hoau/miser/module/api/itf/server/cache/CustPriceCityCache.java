package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.CustPriceCityEntity;
import org.springframework.stereotype.Component;

/**
 * @Description: 物流标准价格映射运输类型缓存
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class CustPriceCityCache extends DefaultTTLRedisCache<CustPriceCityEntity>{
	public static final String UUID = "CustPriceCityCache";
	@Override
	public String getUUID() {
		return UUID;
	}

}
