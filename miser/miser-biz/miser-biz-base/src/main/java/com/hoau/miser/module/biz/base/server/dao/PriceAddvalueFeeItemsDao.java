package com.hoau.miser.module.biz.base.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceAddvalueFeeItemsEntity;

/**
 * 特服费dao
 * ClassName: PriceAddvalueFeeItemsDao 
 * @author 292078
 * @date 2015年12月23日
 * @version V1.0
 */
@Repository
public interface PriceAddvalueFeeItemsDao {

	/**
	 * 
	 * @Description: 根据条件查询特服费集合
	 * @param @param pafItemsEntity
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceAddvalueFeeItemsEntity> 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	List<PriceAddvalueFeeItemsEntity> queryPriceAddvalueFeeItemsByParam(PriceAddvalueFeeItemsEntity pafItemsEntity, RowBounds rowBounds);

	/**
	 * 
	 * @Description: 根据条件统计特服费信息条数
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
	 * @Description: 修改特服费
	 * @param @param pafItemsEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	void updatePriceAddvalueFeeItemsByEntity(PriceAddvalueFeeItemsEntity pafItemsEntity);

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
	 * @Description: 删除特服费
	 * @param @param pafItemsEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	void deletePriceAddvalueFeeItemsByEntity(PriceAddvalueFeeItemsEntity pafItemsEntity);
	
	public List<PriceAddvalueFeeItemsEntity> queryPriceAddvalueFeeItems(PriceAddvalueFeeItemsEntity pafItemsEntity);

}
