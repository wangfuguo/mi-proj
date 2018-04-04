/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceCollectDeliveryFeeEntity;

/**
 * 代收货款手续费管理
 * @author dengyin
 */

@Repository
public interface PriceCollectDeliveryFeeDao {

	List<PriceCollectDeliveryFeeEntity> queryListByEntity(PriceCollectDeliveryFeeEntity entity,RowBounds rowbounds);
	
	List<PriceCollectDeliveryFeeEntity> queryListByEntity2(PriceCollectDeliveryFeeEntity entity,RowBounds rowbounds);
	
	Long queryCountByEntity(PriceCollectDeliveryFeeEntity entity);
	
	void add(PriceCollectDeliveryFeeEntity entity);
	
	void update(PriceCollectDeliveryFeeEntity entity);
	
	void delete(Map<String,String> paramMap);
	
	
}
