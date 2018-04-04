package com.hoau.miser.module.biz.base.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.base.api.shared.domain.AgingCityEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingEntity;

/**
 * @author 289612
 * @date 2015年12月15日
 */
@Repository
public interface AgingCityDao {



	/**
	 * @description: 
	 * @date 2015年12月15日
	 * @author 289612
	 * @return List<AgingCityEntity>
	 * @param 
	 */
	List<AgingCityEntity> queryAgingCityByEntity(AgingCityEntity agingCityEntity,
			RowBounds rowBounds);

	
	/**
	 * @description: 
	 * @date 2015年12月15日
	 * @author 289612
	 * @return Long
	 * @param 
	 */
	Long countAgingCityByEntity(AgingCityEntity agignCityEntity);

	/**
	 * @description: 增加时效城市
	 * @date 2015年12月22日
	 * @author 289612
	 * @return void
	 * @param
	 */
	void addAgingCity(AgingCityEntity agignCityEntity);
	
	/**
	 * @Description: TODO 增加时效城市映射
	 * @param @param agignCityEntity   
	 * @return void 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月14日
	 */
	void addAgingCityMapping(AgingCityEntity agignCityEntity);
	
	
	/**
	 * @Description: TODO 根据时效城市名称去查询是已经有这个出发时效城市
	 * @param @param map
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 刘海飞
	 * @date 2015年12月30日
	 */
	String queryAgingCityByStartAgingName(Map<String, Object> map);
	
	/**
	 * 
	 * @Description: TODO 查询出所有的时效城市
	 * @param @param map
	 * @param @return   
	 * @return List<Map<String,String>> 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月18日
	 */
	List<Map<String,String>> queryAgingCity(Map<String,Object> map);
	
	/**
	 * 
	 * @Description: TODO 查询省市区
	 * @param @param type
	 * @param @return   
	 * @return List<Map<String,String>> 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月18日
	 */
	List<Map<String,String>> queryCity(@Param("type") String type);
	
	/**
	 * 
	 * @Description: TODO 获取当前时间
	 * @param @return   
	 * @return Date 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月18日
	 */
	Date searchCurrentDateTime();
	
	
	/**
	 * 
	 * @Description: TODO 批量插入时效城市
	 * @param @param list
	 * @param @return   
	 * @return long 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月18日
	 */
	long batchInsertTimeCity(List<AgingCityEntity> list);
	
	/**
	 * 
	 * @Description: TODO 批量插入时效城市映射
	 * @param @param list
	 * @param @return   
	 * @return long 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月18日
	 */
	long batchInsertTimeMapping(List<PriceCityMappingEntity> list);
	
	/**
	 * 
	 * @Description: TODO 作废时效城市映射
	 * @param @param pcme
	 * @param @return   
	 * @return long 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月18日
	 */
	long deleteMapping(PriceCityMappingEntity list);
	
	/**
	 * @Description: TODO 查询 改行政区域是否已在映射表中存在
	 * @param @param entity
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 刘海飞
	 * @date 2015年12月31日
	 */
	Long queryAgingCityMappingByEntity(AgingCityEntity entity);

	/**
	 * @Description: TODO删除映射表 --》批量导入
	 * @param @param entity   
	 * @return void 
	 * @throws
	 * @author 刘海飞
	 * @date 2015年12月31日
	 */
	void deleteAgingCityMappingByEntity(AgingCityEntity entity);
	
	
	
	/**
	 * @Description: TODO 根据省市区查询其对应的code值
	 * @param @param entity
	 * @param @return   
	 * @return AgingCityEntity 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月1日
	 */
	AgingCityEntity queryCodeByEntity(AgingCityEntity entity);
	
	
	/**
	 * @Description: TODO导出查询list
	 * @param @param psv
	 * @param @return   
	 * @return List<AgingCityEntity> 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月5日
	 */
	public List<AgingCityEntity> excelQueryListByParam(AgingCityEntity entity);
	
	/**
	 * @Description: TODO 修改窗口查询方法
	 * @param @param entity
	 * @param @return   
	 * @return AgingCityEntity 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月6日
	 */
	AgingCityEntity queryAgingCityInUpdate(AgingCityEntity entity);
	
	/**
	 * @Description: TODO 出发时效城市列表
	 * @param @param userEntity
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PriceCityMappingEntity>
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月3日
	 */
	 List<AgingCityEntity> queryStartAgingCityList(
			 AgingCityEntity userEntity,RowBounds rowBounds);
	 
	 /**
	  * @Description: TODO出发时效城市条数
	  * @param @param userEntity
	  * @param @return   
	  * @return Long 
	  * @throws
	  * @author 刘海飞
	  * @date 2016年1月3日
	  */
	 Long queryCountByParam(AgingCityEntity userEntity);
	 
	 /**
		 * @Description: TODO到达时效城市公共下拉框
		 * @param @param userEntity
		 * @param @param rowBounds
		 * @param @return   
		 * @return List<PriceCityMappingEntity>
		 * @throws
		 * @author 刘海飞
		 * @date 2016年1月6日
		 */
	List<AgingCityEntity> queryArriveAgingCityList(
			AgingCityEntity userEntity,RowBounds rowBounds);
	
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
