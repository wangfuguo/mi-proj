package com.hoau.miser.module.biz.base.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.server.dao.PriceCityDao;
import com.hoau.miser.module.biz.base.server.dao.TranstypeDao;
import com.hoau.miser.module.util.define.MiserConstants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description 价格城市缓存
 * ClassName: PriceCityCacheProvider
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0   
 */
@Component
public class PriceCityCacheProvider implements ITTLCacheProvider<PriceCityEntity> {
	@Resource
	private PriceCityDao priceCityDao;

	@Override
	public PriceCityEntity get(String nameAndType) {
		PriceCityEntity entity = new PriceCityEntity();
		String[] nameAndTypes = nameAndType.split("~!@");
		if (nameAndTypes != null && nameAndTypes.length == 3) {
			entity.setName(nameAndTypes[0]);
			entity.setType(nameAndTypes[1]);
			entity.setPriceCityScope(nameAndTypes[2]);
		}
		entity.setActive(MiserConstants.ACTIVE);
		return priceCityDao.queryPriceCityByName(entity);
	}

}
