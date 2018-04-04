package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.itf.api.shared.domain.CityTypeEntity;

@Repository
public interface CityTypeTyDao {
	
	/**
	 * 查询城市类型
	 * 
	 * @param cityTypeEntity
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-28下午6:51:09
	 * @update
	 */
	List<CityTypeEntity> queryCityTypeByEntity(CityTypeEntity cityTypeEntity);
}
