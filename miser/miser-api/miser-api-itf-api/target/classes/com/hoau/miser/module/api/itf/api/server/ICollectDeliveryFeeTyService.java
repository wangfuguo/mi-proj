package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceCollectDeliveryFeeEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceDeliveryFeeCityTypeEntity;

import java.util.List;

/**   
* @Description: 代收货款手续费管理service
* @author 蒋落琛
* @date 2016/7/14 18:30:33
* @version V1.0   
*/
public interface ICollectDeliveryFeeTyService {

	/**   
	* @Description:
	* @params
	* @return  
	* @author 蒋落琛
	* @date 2016/7/14 18:30:41
	* @version V1.0   
	*/
	public PriceCollectDeliveryFeeEntity queryListByParam(PriceCollectDeliveryFeeEntity priceCollectDeliveryFeeEntity);
}
