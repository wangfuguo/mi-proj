package com.hoau.miser.module.biz.extrafee.server.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.biz.extrafee.server.dao.PriceUpstairsDao;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceUpstairsEntity;

/**
 * @description 运输类型缓存
 * ClassName: TranstypeCacheProvider 
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0   
 */
@Component
public class PriceUpstairsCacheProvider implements ITTLCacheProvider<PriceUpstairsEntity> {
	@Resource
	private PriceUpstairsDao priceUpstairsDao;
	@Override
	public PriceUpstairsEntity get(String id) {
		return priceUpstairsDao.queryUpstairsEntityById(id);
	}

}
