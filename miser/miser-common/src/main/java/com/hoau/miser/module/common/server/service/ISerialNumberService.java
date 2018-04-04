package com.hoau.miser.module.common.server.service;

import java.util.Map;

import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.common.shared.domain.SerialNumberRuleEntity;

/**
 * @author：高佳
 * @create：2015年8月19日 上午10:07:13
 * @description：序号service接口
 */
public interface ISerialNumberService {
	
	/**
	 * 
	 * @Description: TODO 生成序列使用了锁
	 * @param @param serialNumberRule
	 * @param @param params
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author Liwy
	 * @date 2016年8月3日
	 */
	String generateSerialNumber(SerialNumberRuleEnum serialNumberRule,String... params);
	
	/**
	 * 
	 * @Description: TODO 生序列开始，这里无锁
	 * @param @param serialNumberRule
	 * @param @param params
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月19日
	 */
	SerialNumberRuleEntity generateBeginSerialNumber(SerialNumberRuleEnum serialNumberRule,String... params);
	
	/**
	 * 
	 * @Description: TODO 生序列结束，无锁。
	 * @param @param snre
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月19日
	 */
	void generateEndSerialNumber(SerialNumberRuleEntity serialNumberRuleEntity);
	
	/**
	 * 
	 * @Description: TODO 根据当前序列号生成一个新的序列
	 * @param @param serialNumberRuleEntity
	 * @param @param params
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月19日
	 */
	String generateSerialNumber(SerialNumberRuleEntity serialNumberRuleEntity,String[] params);
	
	
}
