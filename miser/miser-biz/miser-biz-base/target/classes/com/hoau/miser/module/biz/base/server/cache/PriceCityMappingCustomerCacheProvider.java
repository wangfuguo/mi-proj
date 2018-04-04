package com.hoau.miser.module.biz.base.server.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingCustomerEntity;
import com.hoau.miser.module.biz.base.server.dao.PriceCityMappingCustomerDao;

/**
 * @description 价格城市映射关系缓存
 * ClassName: PriceCityMappingCacheProvider
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0   
 */
@Component
public class PriceCityMappingCustomerCacheProvider implements ITTLCacheProvider<PriceCityMappingCustomerEntity> {
	@Resource
	private PriceCityMappingCustomerDao priceCityCustomerDao;
	@Override
	public PriceCityMappingCustomerEntity get(String key) {
		
		return null;
	}

}
