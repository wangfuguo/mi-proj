package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.TransportTypeQueryEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.AvailableTransportTypeQueryResult;
import com.hoau.miser.module.api.itf.server.dao.AvailableTransportTypeDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 运输类型缓存
 * ClassName: AvailableTransportTypeCacheProvider
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class AvailableTransportTypeCacheProvider implements ITTLCacheProvider<List<AvailableTransportTypeQueryResult>> {
	@Resource
	AvailableTransportTypeDao availableTransportTypeDao;

	public List<AvailableTransportTypeQueryResult> get(String param) {
		CacheKey key = CacheKey.convertToCacheKey(param);
		if (key == null || key.length() != 3) {
			return null;
		}
		TransportTypeQueryEntity entity = new TransportTypeQueryEntity();
		if ("TIME".equals(key.get(2))) {
			entity.setSendTimeCity(key.get(0));
			entity.setArriveTimeCity(key.get(1));
			return availableTransportTypeDao.queryTimeAvailableTransportTypes(entity);
		} else if ("PRICE".equals(key.get(2))) {
			entity.setSendPriceCity(key.get(0));
			entity.setArrivePriceCity(key.get(1));
			return availableTransportTypeDao.queryPriceAvailableTransportTypes(entity);
		} else {
			return null;
		}
	}

}
