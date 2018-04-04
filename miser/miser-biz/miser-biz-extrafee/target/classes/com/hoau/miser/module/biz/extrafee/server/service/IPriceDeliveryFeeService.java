/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.server.service;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceDeliveryFeeVo;

/**
 * 配送费管理
 * @author dengyin
 *
 */
public interface IPriceDeliveryFeeService {

	/**
	 * 
	 * @Description: 根据参数查询送货费集合
	 * @param @param PriceDeliveryFeeVo
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PriceDeliveryFeeEntity> 
	 * @throws
	 * @author dengyin
	 * @date 2016-7-6 15:37:00
	 */
	List<PriceDeliveryFeeEntity> queryListByParam(
			PriceDeliveryFeeVo priceDeliveryVo, int limit,
			int start);
	
	/**
	 * 
	 * @Description: 根据参数统计送货费条数
	 * @param @param PriceDeliveryFeeVo
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author dengyin
	 * @date 2016-7-6 15:37:00
	 */
	Long queryCountByParam(PriceDeliveryFeeVo priceDeliveryVo);

	/**
	 * 
	 * @Description: 根据id拿到送货费
	 * @param @param id
	 * @param @return   
	 * @return PriceDeliveryFeeEntity 
	 * @throws
	 * @author dengyin
	 * @date 2016-7-6 15:37:00
	 */
	PriceDeliveryFeeEntity queryPriceDeliveryFeeById(String id);

	/**
	 * 
	 * @Description: 增加送货费
	 * @param @param pdfctEntity
	 * @return 
	 * @throws
	 * @author dengyin
	 * @date 2016-7-6 15:37:00
	 */
	Integer addPriceDeliveryFee(PriceDeliveryFeeEntity entity, boolean isConfirm);

	/**
	 * 
	 * @Description: 修改送货费
	 * @param @return   
	 * @throws
	 * @author dengyin
	 * @date 2016-7-6 15:37:00
	 */
	Integer updatePriceDeliveryFee(PriceDeliveryFeeEntity entity, boolean isConfirm);

	/**
	 * 
	 * @Description: 批量删除送货费
	 * @param @param list   
	 * @return void 
	 * @throws
	 * @author dengyin
	 * @date 2016-7-6 15:37:00
	 */
	int batchDelPriceDeliveryFee(List<PriceDeliveryFeeEntity> entityList);

	
}
