package com.hoau.miser.module.api.itf.api.server;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.PricePackageFeeStandardEntity;

/**
 * 标准包装费service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:50:31
 */
public interface IPricePackageFeeStandardTyService {

	/**
	 * 根据条件查询标准包装费
	 * 
	 * @param psv
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-16上午9:51:00
	 * @update
	 */
	public List<PricePackageFeeStandardEntity> queryListByParam(
			PricePackageFeeStandardEntity params);
}
