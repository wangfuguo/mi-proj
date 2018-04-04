package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceDeliveryFeeCityTypeEntity;

/**
 * 配送费分级收费Dao
 *
 * @author 蒋落琛
 * @date 2016-7-6下午2:44:15
 */
public interface DistributionFeeCityTypeTyDao {

	/**
	 * 配送费分级收费Dao
	 * 
	 * @param priceDeliveryFeeCityType
	 * @return
	 * @author 蒋落琛
	 * @date 2016-7-6下午2:44:27
	 * @update
	 */
	List<PriceDeliveryFeeCityTypeEntity> queryListByParam(
			PriceDeliveryFeeCityTypeEntity priceDeliveryFeeCityType);

}
