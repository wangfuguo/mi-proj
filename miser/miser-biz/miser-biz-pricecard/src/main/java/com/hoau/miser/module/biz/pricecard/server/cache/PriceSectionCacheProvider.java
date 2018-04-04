package com.hoau.miser.module.biz.pricecard.server.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceSectionDao;

/**
 * 优惠分段缓存
 * ClassName: PriceSectionCacheProvider 
 * @author 王茂
 * @date 2016年1月7日
 * @version V1.0
 */
@Component
public class PriceSectionCacheProvider implements ITTLCacheProvider<PriceSectionEntity> {
	@Resource
	private PriceSectionDao priceSectionDao;
	@Override
	public PriceSectionEntity get(String code) {
		return priceSectionDao.queryPriceSectionByCode(code);
	}

}
