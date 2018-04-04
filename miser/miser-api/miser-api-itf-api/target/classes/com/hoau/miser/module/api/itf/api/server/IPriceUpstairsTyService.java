package com.hoau.miser.module.api.itf.api.server;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceUpstairsEntity;

/**
 * 上楼费service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:50:31
 */
public interface IPriceUpstairsTyService {

	/**
	 * 根据条件查询上楼费
	 * 
	 * @param params
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-16上午9:51:00
	 * @update
	 */
	public PriceUpstairsEntity queryUpstairsByParam(PriceUpstairsEntity params);
}
