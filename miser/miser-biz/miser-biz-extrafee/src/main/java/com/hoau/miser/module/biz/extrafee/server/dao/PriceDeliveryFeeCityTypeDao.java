package com.hoau.miser.module.biz.extrafee.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeCityTypeEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceDeliveryFeeCityTypeVo;

/**
 * 送货费Dao
 * ClassName: PriceDeliveryFeeCityTypeDao 
 * @author 廖文强
 * @date 2016年1月4日
 * @version V1.0
 */
public interface PriceDeliveryFeeCityTypeDao {

	/**
	 * 
	 * @Description: 送货费集合查询
	 * @param @param priceDeliveryFeeCityTypeVo
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceDeliveryFeeCityTypeEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	List<PriceDeliveryFeeCityTypeEntity> queryListByParam(
			PriceDeliveryFeeCityTypeVo priceDeliveryFeeCityTypeVo,
			RowBounds rowBounds);

	/**
	 * 
	 * @Description: 送货费统计
	 * @param @param priceDeliveryFeeCityTypeVo
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	Long queryCountByParam(PriceDeliveryFeeCityTypeVo priceDeliveryFeeCityTypeVo);

	/**
	 * 
	 * @Description: 修改送货费
	 * @param @param entity   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	void updatePriceDeliveryFeeCityType(
			PriceDeliveryFeeCityTypeEntity entity);

	/**
	 * 
	 * @Description: 新增送货费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	void addPriceDeliveryFeeCityType(PriceDeliveryFeeCityTypeEntity pse);

}
