/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceCollectDeliveryFeeEntity;

/**
 * @author dengyin
 *
 */
public interface IPriceCollectDeliveryFeeService {

	public List<PriceCollectDeliveryFeeEntity> queryListByEntity(PriceCollectDeliveryFeeEntity entity,int start,int limit);
	
	public List<PriceCollectDeliveryFeeEntity> queryListByEntity2(PriceCollectDeliveryFeeEntity entity,int start,int limit);
	
	public Long queryCountByEntity(PriceCollectDeliveryFeeEntity entity);
	
	public Integer add(PriceCollectDeliveryFeeEntity entity,boolean isConfirmAdd);
	
	public Integer update(PriceCollectDeliveryFeeEntity entity,boolean isConfirmAdd);
	
	public void delete(String selectedIdStr);

	public String createExcelFile(List<PriceCollectDeliveryFeeEntity> entityList);

	public Map<String, Object> excelImport(String uploadPath);
	
}
