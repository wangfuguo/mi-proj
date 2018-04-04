package com.hoau.miser.module.biz.base.server.dao;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingEntity;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

/**
 * ClassName: PriceCityDao 
 * @author 刘海飞
 * @date 2016年1月14日
 * @version V1.0
 */
@Repository
public interface PriceCityMappingDao {



	/**
	 * @Description: 查询价格城市 带分页
	 * @param @param userEntity
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceCityMappingEntity>
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月14日
	 */
	List<PriceCityMappingEntity> queryPriceCityByEntity(PriceCityMappingEntity userEntity,
														RowBounds rowBounds);

	/**
	 * @Description: 统计价格城市条数
	 * @param @param userEntity
	 * @param @return   
	 * @return Long
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月14日
	 */
	Long countPriceCityByEntity(PriceCityMappingEntity userEntity);

	/**
	 * @Description: 增加价格城市映射
	 * @param @param agignCityEntity   
	 * @return void
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月14日
	 */
	void addPriceCityMapping(PriceCityMappingEntity agignCityEntity);

	/**
	 * @Description: 查询 改行政区域是否已在映射表中存在
	 * @param @param entity
	 * @param @return   
	 * @return Long
	 * @throws
	 * @author 刘海飞
	 * @date 2015年12月31日
	 */
	Long queryPriceCityMappingByEntity(PriceCityMappingEntity entity);

	/**
	 * @Description: 根据省市区查询其对应的code值
	 * @param @param entity
	 * @param @return   
	 * @return AgingCityEntity
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月1日
	 */
	PriceCityMappingEntity queryCodeByEntity(PriceCityMappingEntity entity);

    /**
     * 删除价格城市映射关系
     * @param entity
     */
	void deletePriceCityMappingByEntity(PriceCityMappingEntity entity);

    /**
     * 根据省市区县查询出发到达价格城市映射关系
     * @param entity
     * @return
     */
	PriceCityMappingEntity queryPriceCityInUpdate(PriceCityMappingEntity entity);
}
