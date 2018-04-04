package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceDeliveryFeeCityTypeEntity;

/**
 * 送货分级收费Dao
 * 
 * @author 蒋落琛
 * @date 2016-6-29上午8:41:07
 */
public interface PriceDeliveryFeeCityTypeTyDao {

	/**
	 * 送货分级收费查询
	 * 
	 * @param priceDeliveryFeeCityType
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-29上午8:41:01
	 * @update
	 */
	List<PriceDeliveryFeeCityTypeEntity> queryListByParam(
			PriceDeliveryFeeCityTypeEntity priceDeliveryFeeCityType);

}
