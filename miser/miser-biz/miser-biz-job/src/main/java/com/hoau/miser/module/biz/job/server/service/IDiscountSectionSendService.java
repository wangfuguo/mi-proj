package com.hoau.miser.module.biz.job.server.service;

public interface IDiscountSectionSendService {

	/**
	 * @Description: 发送优惠分段
	 * @param    
	 * @return void 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月22日
	 */
	public void sendDiscountSection();
	
	/**
	 * @Description: 根据code查询优惠分段并发送
	 * @param @param code   
	 * @return void 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年2月26日
	 */
	public void sendDiscountSectionByCode(String code);
}
