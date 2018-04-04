package com.hoau.miser.module.api.itf.server.cache;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceDeliveryFeeCityTypeEntity;
import com.hoau.miser.module.api.itf.server.dao.DistributionFeeCityTypeTyDao;

/**
 * 配送费分级收费缓存
 *
 * @author 蒋落琛
 * @date 2016-7-6下午2:50:34
 */
@Component
public class DistributionFeeCityTypeTyCacheProvider implements
		ITTLCacheProvider<List<PriceDeliveryFeeCityTypeEntity>> {

	@Resource
	DistributionFeeCityTypeTyDao distributionFeeCityTypeTyDao;

	public List<PriceDeliveryFeeCityTypeEntity> get(String keyStr) {
		List<PriceDeliveryFeeCityTypeEntity> list = null;
		CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
		if (cacheKey != null && cacheKey.length() == 2) {
			PriceDeliveryFeeCityTypeEntity priceDeliveryFeeCityType = new PriceDeliveryFeeCityTypeEntity();
			// 城市类别
			priceDeliveryFeeCityType.setCityType(cacheKey.get(0));
			// 运输类型
			priceDeliveryFeeCityType.setTransTypeCode(cacheKey.get(1));
			// 缓存只查当前时间的数据
			priceDeliveryFeeCityType.setBillTime(new Date());
			list = distributionFeeCityTypeTyDao.queryListByParam(priceDeliveryFeeCityType);
		}
		return list;
	}

}
