package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 邮费分段缓存
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class PriceSectionTyCache extends DefaultTTLRedisCache<List<PriceSectionSubEntity>>{
	public static final String UUID = "PriceSectionTyCache";
	@Override
	public String getUUID() {
		return UUID;
	}

}
