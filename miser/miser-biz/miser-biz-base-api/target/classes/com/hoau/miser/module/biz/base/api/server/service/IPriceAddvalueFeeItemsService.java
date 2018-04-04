package com.hoau.miser.module.biz.base.api.server.service;

import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceAddvalueFeeItemsEntity;

public interface IPriceAddvalueFeeItemsService {

	/**
	 * 
	 * @Description: 根据参数查询特服费集合
	 * @param @param pafItemsEntity
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PriceAddvalueFeeItemsEntity> 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	List<PriceAddvalueFeeItemsEntity> queryPriceAddvalueFeeItemsByParam(PriceAddvalueFeeItemsEntity pafItemsEntity, int limit, int start);

	/**
	 * 
	 * @Description: 根据条件统计特服费条数
	 * @param @param pafItemsEntity
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	Long queryCountByParam(PriceAddvalueFeeItemsEntity pafItemsEntity);

	/**
	 * 
	 * @Description: 修改特服费:作废旧的信息，增加一条新的信息
	 * @param @param pafItemsEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	void updatePriceAddvalueFeeItemsByEntity(PriceAddvalueFeeItemsEntity pafItemsEntity);

	/**
	 * 
	 * @Description: 删除特服费
	 * @param @param pafItemsEntity  active:是否删除 
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	void deletePriceAddvalueFeeItemsByEntity(PriceAddvalueFeeItemsEntity pafItemsEntity,String active);

	/**
	 * 
	 * @Description: 增加特服费
	 * @param @param pafItemsEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	void addPriceAddvalueFeeItemsByEntity(PriceAddvalueFeeItemsEntity pafItemsEntity);

	/**
	 * 
	 * @Description: 根据id查询特服费
	 * @param @param id
	 * @param @return   
	 * @return PriceAddvalueFeeItemsEntity 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	PriceAddvalueFeeItemsEntity queryPriceAddvalueFeeItemsById(String id);
	
	public List<PriceAddvalueFeeItemsEntity> queryPriceAddvalueFeeItems(PriceAddvalueFeeItemsEntity pafItemsEntity);

}
