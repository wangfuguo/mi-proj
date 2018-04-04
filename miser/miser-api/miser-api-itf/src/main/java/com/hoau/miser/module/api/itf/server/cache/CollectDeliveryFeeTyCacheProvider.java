package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCollectDeliveryFeeEntity;
import com.hoau.miser.module.api.itf.server.dao.PriceCollectDeliveryFeeTyDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @Description: 代收货款手续费管理缓存
* @author 蒋落琛
* @date 2016/7/14 17:22:34
* @version V1.0
*/
@Component
public class CollectDeliveryFeeTyCacheProvider implements
		ITTLCacheProvider<List<PriceCollectDeliveryFeeEntity>> {

	@Resource
	PriceCollectDeliveryFeeTyDao priceCollectDeliveryFeeTyDao;

	public List<PriceCollectDeliveryFeeEntity> get(String keyStr) {
		List<PriceCollectDeliveryFeeEntity> list = null;
		CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
		if (cacheKey != null && cacheKey.length() == 3) {
			PriceCollectDeliveryFeeEntity entity = new PriceCollectDeliveryFeeEntity();
			// 出发价格城市
			entity.setBeginPriceCityCode(cacheKey.get(0));
			// 代收类型标识 0即日退 1三日退
			entity.setCollectDeliveryType(Integer.parseInt(cacheKey.get(1)));
			// 运输类型
			entity.setTransTypeCode(cacheKey.get(2));
			// 数据时间
			entity.setBillTime(new Date());
			list = priceCollectDeliveryFeeTyDao.queryListByParams(entity);
		}
		return list;
	}

}
