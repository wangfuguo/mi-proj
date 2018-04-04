package com.hoau.miser.module.api.itf.api.server;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.PricePackageFeePcEntity;

/**
 * 标准价格城市包装费service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:50:31
 */
public interface IPricePackageFeePcTyService {

	/**
	 * 根据条件查询特标准附加费
	 * 
	 * @param psv
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-16上午9:51:00
	 * @update
	 */
	public List<PricePackageFeePcEntity> queryListByParam(PricePackageFeePcEntity params);
}
