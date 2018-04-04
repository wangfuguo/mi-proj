package com.hoau.miser.module.biz.job.server.service;

/**
 * sync PMS ExtrafeeAddValueFee to EDI interface
 * @author zouyu
 */
public interface IExtrafeeAddValueFeeSendService {

	/**
	 * @Description: sync PMS ExtrafeeAddValueFee to EDI
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年2月25日
	 */
	public void sendExtrafeeAddValueFee();
}
