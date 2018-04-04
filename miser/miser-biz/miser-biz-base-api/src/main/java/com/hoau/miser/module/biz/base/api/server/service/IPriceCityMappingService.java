package com.hoau.miser.module.biz.base.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityMappingVo;

/**
 * ClassName: IPriceCityMappingService
 * @author 刘海飞
 * @date 2016年1月14日
 * @version V1.0
 */

public interface IPriceCityMappingService {

	
	List<PriceCityMappingEntity> queryPriceCityMappingByEntity(PriceCityMappingEntity priceCityMappingEntity, int limit, int start);

	/**
	 * @Description: 根据条件统计价格城市映射总条数
	 * @param priceCityMappingEntity
	 * @return Long
	 * @author 刘海飞
	 * @date 2015年12月22日
	 */
	Long countPriceCityMappingByEntity(PriceCityMappingEntity priceCityMappingEntity);
	
	
	/**
	 * 更新价格城市映射关系
	 * @param priceCityMappingEntity
	 * @author 刘海飞
	 * @date 2015年7月23日
	 * @update 
	 */
	void updatePriceCityMapping(PriceCityMappingEntity priceCityMappingEntity);

	/**
	 * @Description: 导入价格城市映射关系
	 * @param @param file   
	 * @return void 
	 * @throws
	 * @author 刘海飞
	 * @date 2015年12月30日
	 */
	Map<String,Object> priceCityMappingImport(String path) throws Exception;
	/**
	 * @Description: 创建导出文件
	 * @param @param list
	 * @param @return
	 * @return String 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月14日
	 */
	public String createPriceCityMappingExcelFile(List<PriceCityMappingEntity> list);
	
	/**
	 * @Description: 修改时调用此方法查询价格城市映射数据
	 * @param @param entity
	 * @param @return   
	 * @return PriceCityMappingEntity
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月14日
	 */
	PriceCityMappingEntity queryPriceCityMappingInUpdate(PriceCityMappingEntity entity);
}
