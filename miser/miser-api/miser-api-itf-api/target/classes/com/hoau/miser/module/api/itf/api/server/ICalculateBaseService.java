package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.define.WaybillCalculateType;

/**
 * 开单计算费用接口
 * 
 * @author 蒋落琛
 * @date 2016-6-1下午4:20:11
 */
public interface ICalculateBaseService<K, V> {

	/**
	 * 获取开单计算费用类型
	 * 
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-1下午4:20:19
	 * @update
	 */
	WaybillCalculateType getCalculateType();

	/**
	 * 计算费用
	 * 
	 * @param obj
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-1下午4:20:25
	 * @update
	 */
	V calculate(K obj);
	
	/**
	 * 计算标准费用
	 * 
	 * @param obj
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-28上午11:02:17
	 * @update
	 */
	V calculateStandard(K obj);
}
