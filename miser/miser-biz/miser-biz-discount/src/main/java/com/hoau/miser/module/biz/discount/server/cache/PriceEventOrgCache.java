package com.hoau.miser.module.biz.discount.server.cache;

import java.util.List;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventOrgEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.sys.base.api.shared.vo.OrgTreeNode;
/**
 * 
 * ClassName: PriceSectionCache 
 * @Description: TODO(缓存活动区域) 
 * @author mao.wang@newHoau.com.cn
 * @date 2016年4月8日
 * @version V1.0
 */
public class PriceEventOrgCache extends DefaultTTLRedisCache<String>{
	public static final String PRICE_EVENT_ORG_CACHE_UUID = PriceEventOrgCache.class.getName();
	@Override
	public String getUUID() {
		return PRICE_EVENT_ORG_CACHE_UUID;
	}

}
