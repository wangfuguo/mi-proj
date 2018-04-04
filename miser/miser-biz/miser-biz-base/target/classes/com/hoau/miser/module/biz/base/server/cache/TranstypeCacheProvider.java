package com.hoau.miser.module.biz.base.server.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.server.dao.TranstypeDao;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @description 运输类型缓存
 * ClassName: TranstypeCacheProvider 
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0   
 */
@Component
public class TranstypeCacheProvider implements ITTLCacheProvider<TranstypeEntity> {
	@Resource
	private TranstypeDao transtypeDao;
	@Override
	public TranstypeEntity get(String code) {
		TranstypeEntity entity = new TranstypeEntity();
		entity.setCode(code);
		entity.setInvalid(MiserConstants.NO);
		entity.setActive(MiserConstants.ACTIVE);
		return transtypeDao.queryTranstypeByCode(entity);
	}

}
