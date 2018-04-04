package com.hoau.miser.module.api.itf.api.server;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceDeliveryFeeCityTypeEntity;

/**
 * 送货分级收费service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:50:31
 */
public interface IPriceDeliveryFeeCityTypeTyService {

	/**
	 * 查询送货分级收费
	 * 
	 * @param priceDeliveryFeeCityType
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-29上午9:56:24
	 * @update
	 */
	public List<PriceDeliveryFeeCityTypeEntity> queryListByParam(
			PriceDeliveryFeeCityTypeEntity priceDeliveryFeeCityType);
}
