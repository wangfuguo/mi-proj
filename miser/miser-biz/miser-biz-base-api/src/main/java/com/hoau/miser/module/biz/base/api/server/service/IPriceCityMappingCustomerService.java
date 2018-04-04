package com.hoau.miser.module.biz.base.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingCustomerEntity;

/**
 * ClassName: IPriceCityCustomerService
 * @author 李玮琰
 * @date 2016年3月17日
 * @version V1.0
 */

public interface IPriceCityMappingCustomerService {

	
	List<PriceCityMappingCustomerEntity> queryPriceCityCustomerByEntity(PriceCityMappingCustomerEntity priceCityCustomerEntity, int limit, int start);

	/**
	 * @Description: 根据条件统计价格城市映射总条数
	 * @param priceCityCustomerEntity
	 * @return Long
	 * @author 李玮琰
	 * @date 2016年3月17日
	 */
	Long countPriceCityCustomerByEntity(PriceCityMappingCustomerEntity priceCityCustomerEntity);
	
	
	/**
	 * 更新价格城市映射关系
	 * @param priceCityCustomerEntity
	 * @author 李玮琰
	 * @date 2016年3月17日
	 * @update 
	 */
	void updatePriceCityCustomer(PriceCityMappingCustomerEntity priceCityCustomerEntity);

	/**
	 * @Description: 导入价格城市映射关系
	 * @param @param file   
	 * @return void 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年3月17日
	 */
	Map<String,Object> priceCityCustomerImport(String path) throws Exception;
	/**
	 * @Description: 创建导出文件
	 * @param @param list
	 * @param @return
	 * @return String 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年3月17日
	 */
	public String createPriceCityCustomerExcelFile(List<PriceCityMappingCustomerEntity> list);
	
	/**
	 * @Description: 修改时调用此方法查询价格城市映射数据
	 * @param @param entity
	 * @param @return   
	 * @return PriceCityCustomerEntity
	 * @throws
	 * @author 李玮琰
	 * @date 2016年3月17日
	 */
	PriceCityMappingCustomerEntity queryPriceCityCustomerInUpdate(PriceCityMappingCustomerEntity entity);
}
