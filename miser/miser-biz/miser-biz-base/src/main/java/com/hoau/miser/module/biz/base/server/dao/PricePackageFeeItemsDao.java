package com.hoau.miser.module.biz.base.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.base.api.shared.domain.PricePackageFeeItemsEntity;

@Repository
public interface PricePackageFeeItemsDao {

	/**
	 * 
	 * @Description: 根据参数查询包装费列表信息
	 * @param @param ppfItemsEntity
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PricePackageFeeItemsEntity> 
	 * @throws
	 * @author 292078
	 * @date 2015年12月22日
	 */
	List<PricePackageFeeItemsEntity> queryPricePackageFeeItemsByParam(PricePackageFeeItemsEntity ppfItemsEntity, RowBounds rowBounds);
	
	/**
	 * 
	 * @Description: 根据参数统计包装费列表信息数量
	 * @param @param ppfItemsEntity
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 292078
	 * @date 2015年12月22日
	 */
	Long queryCountByParam(PricePackageFeeItemsEntity ppfItemsEntity);
	
	/**
	 * 
	 * @Description: 根据Entity修改包装费
	 * @param @param ppfItemsEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月22日
	 */
	void updatePricePackageFeeItemsByEntity(PricePackageFeeItemsEntity ppfItemsEntity);
	/**
	 * 
	 * @Description: 根据Entity删除包装费
	 * @param @param ppfItemsEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月22日
	 */
	void deletePricePackageFeeItemsByEntity(PricePackageFeeItemsEntity ppfItemsEntity);
	/**
	 * 
	 * @Description: 根据Entity增加包装费
	 * @param @param ppfItemsEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月22日
	 */
	void addPricePackageFeeItemsByEntity(PricePackageFeeItemsEntity ppfItemsEntity);
	
	
	
}
