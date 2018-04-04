package com.hoau.miser.module.api.itf.api.server;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.ExtrafeeAddValueFeeEntity;

/**
 * 特服费service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:50:31
 */
public interface IExtrafeeAddValueFeeTyService {

	/**
	 * 根据条件查询特服费
	 * 
	 * @param psv
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-16上午9:51:00
	 * @update
	 */
	public List<ExtrafeeAddValueFeeEntity> queryListByParam(
			ExtrafeeAddValueFeeEntity psv);
}
