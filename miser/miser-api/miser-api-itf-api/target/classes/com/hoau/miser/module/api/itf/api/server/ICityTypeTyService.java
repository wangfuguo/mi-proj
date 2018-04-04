package com.hoau.miser.module.api.itf.api.server;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.CityTypeEntity;

/**
 * 城市类别service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:50:31
 */
public interface ICityTypeTyService {

	/**
	 * 查询城市类型
	 * 
	 * @param psv
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-16上午9:51:00
	 * @update
	 */
	public List<CityTypeEntity> queryCityTypeByEntity(CityTypeEntity cityTypeEntity);
}
