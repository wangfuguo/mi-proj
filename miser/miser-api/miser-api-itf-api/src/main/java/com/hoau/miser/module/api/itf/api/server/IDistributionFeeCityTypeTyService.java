package com.hoau.miser.module.api.itf.api.server;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceDeliveryFeeCityTypeEntity;

/**
 * 配送费分级收费service
 *
 * @author 蒋落琛
 * @date 2016-7-6下午2:40:27
 */
public interface IDistributionFeeCityTypeTyService {

	/**
	 * 查询配送费分级收费
	 * 
	 * @param priceDeliveryFeeCityType
	 * @return
	 * @author 蒋落琛
	 * @date 2016-7-6下午2:40:33
	 * @update
	 */
	public List<PriceDeliveryFeeCityTypeEntity> queryListByParam(
			PriceDeliveryFeeCityTypeEntity priceDeliveryFeeCityType);
}
