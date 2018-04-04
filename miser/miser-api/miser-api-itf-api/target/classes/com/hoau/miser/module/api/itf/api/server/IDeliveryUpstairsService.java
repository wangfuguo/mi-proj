package com.hoau.miser.module.api.itf.api.server;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 送货上楼费接口
 * 
 * @author 蒋落琛
 * @date 2016-6-8下午2:21:56
 */
public interface IDeliveryUpstairsService {

	/**
	 * 计算送货上楼费
	 * 
	 * @param deliveryMethod
	 * @param weight
	 * @param volume
	 * @param orderOrign
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-8下午2:22:05
	 * @update
	 */
	SurchargeDto calculateDeliveryUpstairsCharge(String deliveryMethod, String productType,
			BigDecimal weight, BigDecimal volume, String orderOrign, String omsBizType, String customerCode, boolean isInternalBelt, boolean isHistory, Date billTime);
	
	/**
	 * 计算标准送货上楼费
	 * 
	 * @param deliveryMethod
	 * @param weight
	 * @param volume
	 * @param orderOrign
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-8下午2:22:05
	 * @update
	 */
	SurchargeDto calculateStandardDeliveryUpstairsCharge(String deliveryMethod, String productType,
			BigDecimal weight, BigDecimal volume, String orderOrign, String omsBizType, String customerCode, boolean isInternalBelt, boolean isHistory, Date billTime);

}
