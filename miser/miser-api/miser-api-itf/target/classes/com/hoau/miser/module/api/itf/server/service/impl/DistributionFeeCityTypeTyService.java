package com.hoau.miser.module.api.itf.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IDistributionFeeCityTypeTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceDeliveryFeeCityTypeEntity;
import com.hoau.miser.module.api.itf.server.cache.DistributionFeeCityTypeTyCache;
import com.hoau.miser.module.api.itf.server.dao.DistributionFeeCityTypeTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;

/**
 * 配送费分级收费service
 *
 * @author 蒋落琛
 * @date 2016-7-6下午2:40:48
 */
@Service
public class DistributionFeeCityTypeTyService implements IDistributionFeeCityTypeTyService {

	@Resource
	DistributionFeeCityTypeTyDao distributionFeeCityTypeTyDao;

	@Override
	public List<PriceDeliveryFeeCityTypeEntity> queryListByParam(
			PriceDeliveryFeeCityTypeEntity priceDeliveryFeeCityType) {
		if (priceDeliveryFeeCityType == null || StringUtil.isEmpty(priceDeliveryFeeCityType.getCityType())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_CITYTYPE_ISNULL_EXCEPTION);
		}
		if (StringUtil.isEmpty(priceDeliveryFeeCityType.getTransTypeCode())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
		}
		List<PriceDeliveryFeeCityTypeEntity> list = null;
		if (!priceDeliveryFeeCityType.isHistory()) {
			@SuppressWarnings("unchecked")
			ICache<String, List<PriceDeliveryFeeCityTypeEntity>> cache = CacheManager
					.getInstance().getCache(DistributionFeeCityTypeTyCache.UUID);
			String keyStr = new CacheKey(priceDeliveryFeeCityType.getCityType(), priceDeliveryFeeCityType.getTransTypeCode()).generateKey();
			list = cache.get(keyStr);
		} else {
			list = distributionFeeCityTypeTyDao.queryListByParam(priceDeliveryFeeCityType);
		}
		return list;
	}
}
