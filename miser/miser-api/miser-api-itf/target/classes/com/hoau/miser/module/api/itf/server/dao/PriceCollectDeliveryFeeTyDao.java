package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceCollectDeliveryFeeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Description: 代收货款手续费管理
* @author 蒋落琛
* @date 2016/7/14 16:47:11
* @version V1.0
*/
@Repository
public interface PriceCollectDeliveryFeeTyDao {

	/**
	* @Description: 根据条件查询代收货款手续费管理
	* @params
	* @return
	* @author 蒋落琛
	* @date 2016/7/14 16:48:40
	* @version V1.0
	*/
	List<PriceCollectDeliveryFeeEntity> queryListByParams(PriceCollectDeliveryFeeEntity entity);
}
