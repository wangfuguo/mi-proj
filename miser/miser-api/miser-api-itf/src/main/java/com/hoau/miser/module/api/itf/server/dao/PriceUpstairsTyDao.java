package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceUpstairsEntity;

/**
 * 上楼费DAO
 * 
 * @author 蒋落琛
 * @date 2016-6-2上午9:52:12
 */
@Repository
public interface PriceUpstairsTyDao {

	/**
	 * 上楼费界面查询
	 * 
	 * @param entity
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-2上午9:52:08
	 * @update
	 */
	public List<PriceUpstairsEntity> queryUpstairsPrices(
			PriceUpstairsEntity entity);
}