package com.hoau.miser.module.biz.extrafee.server.service;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeCityTypeEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceDeliveryFeeCityTypeVo;

/**
 * 送货费Service
 * ClassName: IPriceDeliveryFeeCityTypeService 
 * @author 廖文强
 * @date 2016年1月4日
 * @version V1.0
 */
public interface IPriceDeliveryFeeCityTypeService {

	/**
	 * 
	 * @Description: 根据参数查询送货费集合
	 * @param @param priceDeliveryFeeCityTypeVo
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PriceDeliveryFeeCityTypeEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	List<PriceDeliveryFeeCityTypeEntity> queryListByParam(
			PriceDeliveryFeeCityTypeVo priceDeliveryFeeCityTypeVo, int limit,
			int start);
	
	/**
	 * 
	 * @Description: 根据参数统计送货费条数
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
	 * @Description: 根据id拿到送货费
	 * @param @param id
	 * @param @return   
	 * @return PriceDeliveryFeeCityTypeEntity 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	PriceDeliveryFeeCityTypeEntity queryPriceDeliveryFeeCityTypeById(String id);

	/**
	 * 
	 * @Description: 增加送货费
	 * @param @param pdfctEntity
	 * @param @param IsConfirm
	 * @param @return   0：待确认；1：确认
	 * @return Integer 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	Integer addPriceDeliveryFeeCityType(
			PriceDeliveryFeeCityTypeEntity pdfctEntity, boolean IsConfirm);

	/**
	 * 
	 * @Description: 修改送货费
	 * @param @param pdfctEntity
	 * @param @param IsConfirm
	 * @param @return   
	 * @return Integer 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	Integer updatePriceDeliveryFeeCityType(
			PriceDeliveryFeeCityTypeEntity pdfctEntity, boolean IsConfirm);

	/**
	 * 
	 * @Description: 批量删除送货费
	 * @param @param list   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	int bathDelPriceDeliveryFeeCityType(
			List<PriceDeliveryFeeCityTypeEntity> list);

}
