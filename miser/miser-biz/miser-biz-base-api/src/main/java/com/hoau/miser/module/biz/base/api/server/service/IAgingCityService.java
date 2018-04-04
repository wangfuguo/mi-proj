package com.hoau.miser.module.biz.base.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.base.api.shared.domain.AgingCityEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.AgingCityVo;


/**
 * @author 289612
 * @date 2015年12月15日
 * 
 */
public interface IAgingCityService {

	
	List<AgingCityEntity> queryAgingCityByEntity(AgingCityEntity agingCityEntity, int limit,
			int start);

	/**
	 * 根据条件统计用户信息
	 * @param userEntity
	 * @return
	 * @author 高佳
	 * @date 2015年7月23日
	 * @update 
	 */
	Long countAgingCityByEntity(AgingCityEntity agingCityEntity);
	
	
	/**
	 * 新增用户信息
	 * @param userEntity
	 * @author 刘海飞
	 * @date 2015年7月23日
	 * @update 
	 */
	void updateAgingCityEntity(AgingCityEntity entity);
	
	
	/**
	 * @Description: TODO时效城市上传
	 * @param @param file   
	 * @return void 
	 * @throws
	 * @author 刘海飞
	 * @date 2015年12月30日
	 */
	Map<String,Object> cityInprot(String path) throws Exception;
	
	public String createExcelFile(List<AgingCityEntity> list);
	
	public List<AgingCityEntity> excelQueryListByParam(AgingCityVo psv);
	
	/**
	 * @Description: TODO 修改表单查询方法
	 * @param @param entity
	 * @param @return   
	 * @return PriceCityMappingEntity
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月6日
	 */
	AgingCityEntity queryAgingCityInUpdate(AgingCityEntity entity);
	
	/**
	 * @Description: TODO查询出发时效城市列表
	 * @param @param userEntity
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PriceCityMappingEntity>
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月3日
	 */
	List<AgingCityEntity> queryStartAgingCityList(AgingCityEntity userEntity, int limit,
			int start);
	/**
	 * @Description: TODO查询出发时效城市列表条数
	 * @param @param userEntity
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月3日
	 */
	Long queryCountByParam(AgingCityEntity userEntity);
	
	/**
	 * @Description: TODO到达价格城市公共选择器
	 * @param @param userEntity
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PriceCityMappingEntity>
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月6日
	 */
	List<AgingCityEntity> queryArriveAgingCityList(AgingCityEntity userEntity, int limit,
			int start);
	
	/**
	 * @Description: TODO到达时效城市查询条数
	 * @param @param userEntity
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月6日
	 */
	Long queryCountByParamArrive(AgingCityEntity userEntity);
}
