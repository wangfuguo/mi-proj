package com.hoau.miser.module.api.itf.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hoau.miser.module.api.itf.api.server.ICalculateBaseService;

/**
 * 直营开单计算费用抽象类
 * 
 * @author 蒋落琛
 * @date 2016-6-1下午4:44:01
 */
public abstract class AbstractCalculateBaseService<K, V> implements
		ICalculateBaseService<K, V> {

	/**
	 * 日志类
	 */
	private static final Log logger = LogFactory
			.getLog(AbstractCalculateBaseService.class);

	/**
	 * 上下文 用于绑定参数
	 */
	private static ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};

	private boolean onlyCalculate = false;

	public K beforeCalculate(K obj) {
		logger.info(this.getCalculateType().getName() + "开始");
		logger.info("输入参数：" + obj);
		return obj;
	}

	public V afterCalculate(V obj) {
		logger.info("输出参数：" + obj);
		logger.info(this.getCalculateType().getName() + "结束");
		return obj;
	}

	/**
	 * 计算费用
	 */
	@Override
	public V calculate(K obj) {

		obj = beforeCalculate(obj);
		// 计算结算费用
		V v = calculateCharge(obj);
		v = afterCalculate(v);

		return v;
	}
	
	/**
	 * 计算标准费用
	 */
	@Override
	public V calculateStandard(K obj) {

		obj = beforeCalculate(obj);
		// 计算结算费用
		V v = calculateStandardCharge(obj);
		v = afterCalculate(v);

		return v;
	}

	public abstract V calculateCharge(K obj);
	
	public abstract V calculateStandardCharge(K obj);

	public boolean isOnlyCalculate() {
		return onlyCalculate;
	}

	public void setOnlyCalculate(boolean onlyCalculate) {
		this.onlyCalculate = onlyCalculate;
	}

	public Map<String, Object> getCurrentContext() {
		return context.get();
	}

	public void setCurrentContext(String key, Object value) {
		context.get().put(key, value);
	}

	public void remove() {
		context.remove();
	}
}
