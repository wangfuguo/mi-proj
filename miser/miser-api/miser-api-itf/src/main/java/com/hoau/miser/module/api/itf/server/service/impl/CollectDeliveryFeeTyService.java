package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICollectDeliveryFeeTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCollectDeliveryFeeEntity;
import com.hoau.miser.module.api.itf.server.cache.CollectDeliveryFeeTyCache;
import com.hoau.miser.module.api.itf.server.dao.PriceCollectDeliveryFeeTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *  代收货款手续费管理service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午11:18:43
 */
@Service
public class CollectDeliveryFeeTyService implements ICollectDeliveryFeeTyService {

	@Resource
	PriceCollectDeliveryFeeTyDao priceCollectDeliveryFeeTyDao;

	@Override
	public PriceCollectDeliveryFeeEntity queryListByParam(PriceCollectDeliveryFeeEntity priceCollectDeliveryFeeEntity) {
		if (priceCollectDeliveryFeeEntity == null || StringUtil.isEmpty(priceCollectDeliveryFeeEntity.getBeginPriceCityCode())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_SEND_PRICE_CITY_ISNULL_EXCEPTION);
		}
		if (priceCollectDeliveryFeeEntity.getCollectDeliveryType() == null) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_COLLECTIONTYPE_ISNULL_EXCEPTION);
		}
		if (StringUtil.isEmpty(priceCollectDeliveryFeeEntity.getTransTypeCode())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
		}
		List<PriceCollectDeliveryFeeEntity> list = null;
		if (!priceCollectDeliveryFeeEntity.isHistory()) {
			@SuppressWarnings("unchecked")
			ICache<String, List<PriceCollectDeliveryFeeEntity>> cache = CacheManager
					.getInstance().getCache(CollectDeliveryFeeTyCache.UUID);
			String keyStr = new CacheKey(priceCollectDeliveryFeeEntity.getBeginPriceCityCode(), String.valueOf(priceCollectDeliveryFeeEntity.getCollectDeliveryType()), priceCollectDeliveryFeeEntity.getTransTypeCode()).generateKey();
			list = cache.get(keyStr);
		} else {
			list = priceCollectDeliveryFeeTyDao.queryListByParams(priceCollectDeliveryFeeEntity);
		}
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}
