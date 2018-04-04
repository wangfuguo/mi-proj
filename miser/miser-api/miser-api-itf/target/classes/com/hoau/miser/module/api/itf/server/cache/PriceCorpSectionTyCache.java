package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpSectionTyEntity;
import org.springframework.stereotype.Component;

/**
 * @Description: 提货费缓存
 * @author 廖文强
 * @date 2016年06月07日
 * @version V1.0
 */
@Component
public class PriceCorpSectionTyCache extends DefaultTTLRedisCache<PriceCorpSectionTyEntity>{
	public static final String UUID = "priceCorpSectionTyCache-api";
	@Override
	public String getUUID() {
		return UUID;
	}

}
