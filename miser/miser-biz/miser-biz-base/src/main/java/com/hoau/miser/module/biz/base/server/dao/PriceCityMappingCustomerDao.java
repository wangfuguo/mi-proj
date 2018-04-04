package com.hoau.miser.module.biz.base.server.dao;

import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingCustomerEntity;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

/**
 * ClassName: PriceCityDao 
 * @author 李玮琰
 * @date 2016年1月14日
 * @version V1.0
 */
@Repository
public interface PriceCityMappingCustomerDao {



	/**
	 * @Description: 查询价格城市 带分页
	 * @param @param userEntity
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceCityMappingCustomerEntity>
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月14日
	 */
	List<PriceCityMappingCustomerEntity> queryPriceCityByEntity(PriceCityMappingCustomerEntity userEntity,
														RowBounds rowBounds);

	/**
	 * @Description: 统计价格城市条数
	 * @param @param userEntity
	 * @param @return   
	 * @return Long
	 * @throws
	 * @author 李玮琰
	 * @date 2016年3月17日
	 */
	Long countPriceCityByEntity(PriceCityMappingCustomerEntity userEntity);

	/**
	 * @Description: 增加价格城市映射
	 * @param @param agignCityEntity   
	 * @return void
	 * @throws
	 * @author 李玮琰
	 * @date 2016年3月17日
	 */
	void addPriceCityMappingCustomer(PriceCityMappingCustomerEntity agignCityEntity);

	/**
	 * @Description: 查询 改行政区域是否已在映射表中存在
	 * @param @param entity
	 * @param @return   
	 * @return Long
	 * @throws
	 * @author 李玮琰
	 * @date 2016年3月17日
	 */
	Long queryPriceCityMappingCustomerByEntity(PriceCityMappingCustomerEntity entity);

	/**
	 * @Description: 根据省市区查询其对应的code值
	 * @param @param entity
	 * @param @return   
	 * @return AgingCityEntity
	 * @throws
	 * @author 李玮琰
	 * @date 2016年3月17日
	 */
	PriceCityMappingCustomerEntity queryCodeByEntity(PriceCityMappingCustomerEntity entity);

    /**
     * 删除价格城市映射关系
     * @param entity
     */
	void deletePriceCityMappingCustomerByEntity(PriceCityMappingCustomerEntity entity);

    /**
     * 根据省市区县查询出发到达价格城市映射关系
     * @param entity
     * @return
     */
	PriceCityMappingCustomerEntity queryPriceCityInUpdate(PriceCityMappingCustomerEntity entity);
}
