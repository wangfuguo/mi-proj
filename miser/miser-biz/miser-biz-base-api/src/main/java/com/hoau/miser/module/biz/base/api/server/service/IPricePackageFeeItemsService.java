package com.hoau.miser.module.biz.base.api.server.service;

import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.PricePackageFeeItemsEntity;

/**
 * 包装费Service
 * ClassName: IPricePackageFeeItemsService 
 * @author 292078
 * @date 2015年12月22日
 * @version V1.0
 */
public interface IPricePackageFeeItemsService {

	/**
	 * 
	 * @Description: 根据参数查询包装费列表信息
	 * @param @param ppfItemsEntity
	 * @param @return   
	 * @return List<PricePackageFeeItemsEntity> 
	 * @throws
	 * @author 292078
	 * @date 2015年12月22日
	 */
	List<PricePackageFeeItemsEntity> queryPricePackageFeeItemsByParam(PricePackageFeeItemsEntity ppfItemsEntity,int limit,
			int start);
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
	 * @Description: 根据Entity修改包装费:作废旧的信息，生成新的信息
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
	void deletePricePackageFeeItemsByEntity(PricePackageFeeItemsEntity ppfItemsEntity,String active);
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
	/**
	 * 
	 * @Description: 根据id拿到包装费
	 * @param @param id
	 * @param @return   
	 * @return PricePackageFeeItemsEntity 
	 * @throws
	 * @author 292078
	 * @date 2015年12月22日
	 */
	PricePackageFeeItemsEntity queryPricePackageFeeItemsById(String id);

}
