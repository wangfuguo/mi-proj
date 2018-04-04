package com.hoau.miser.module.biz.pricecard.server.cache;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
/**
 * 优惠分段缓存
 * ClassName: SectionCache 
 * @author 王茂
 * @date 2016年1月7日
 * @version V1.0
 */
public class PriceSectionCache extends DefaultTTLRedisCache<PriceSectionEntity>{
	public static final String PRICE_SECTION_CACHE_UUID = PriceSectionCache.class.getName();
	@Override
	public String getUUID() {
		return PRICE_SECTION_CACHE_UUID;
	}

}
