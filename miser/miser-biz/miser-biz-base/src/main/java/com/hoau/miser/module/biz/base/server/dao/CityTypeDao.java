package com.hoau.miser.module.biz.base.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.base.api.shared.domain.CityTypeEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryValueEntity;

@Repository
public interface CityTypeDao {
	
	//查询城市类型 带分页
	List<CityTypeEntity> queryCityTypeByEntity(CityTypeEntity cityTypeEntity,
			RowBounds rowBounds);
	//更新查询操作
	CityTypeEntity queryCityTypeInUpdate(CityTypeEntity entity);
	//统计城市条数
	Long countCityTypeByEntity(CityTypeEntity cityTypeEntity);
//	修改城市类别
	Long updateActiveBySSQ(CityTypeEntity cityTypeEntity);
//	新增一条数据在城市类别表
	void insertCityType(CityTypeEntity cityTypeEntity);
//	查询省市区
	CityTypeEntity queryBySSQ(CityTypeEntity cityTypeEntity);
//	城市类别code和name
	List<DataDictionaryValueEntity> queryCityTypeCode();
}
