/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceDeliveryFeeVo;

/**
 * 配送费管理
 * @author dengyin
 *
 */
@Repository
public interface PriceDeliveryFeeDao {

	/**
	 * 
	 * @Description: 配送费集合查询
	 * @param @param PriceDeliveryFeeVo
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceDeliveryFeeEntity> 
	 * @throws
	 * @author dengyin
	 * @date 2016-7-6 15:18:23
	 */
	List<PriceDeliveryFeeEntity> queryListByParam(
			PriceDeliveryFeeVo priceDeliveryFeeVo,
			RowBounds rowBounds);

	/**
	 * 
	 * @Description: 配送费统计
	 * @param @param PriceDeliveryFeeVo
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author dengyin
	 * @date 2016-7-6 15:18:23
	 */
	Long queryCountByParam(PriceDeliveryFeeVo priceDeliveryFeeVo);

	/**
	 * 
	 * @Description: 修改配送费
	 * @param @param entity   
	 * @return void 
	 * @throws
	 * @author dengyin
	 * @date 2016-7-6 15:18:23
	 */
	void updatePriceDeliveryFee(
			PriceDeliveryFeeEntity entity);

	/**
	 * 
	 * @Description: 新增配送费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author dengyin
	 * @date 2016-7-6 15:18:23
	 */
	void addPriceDeliveryFee(PriceDeliveryFeeEntity entity);

	
}
