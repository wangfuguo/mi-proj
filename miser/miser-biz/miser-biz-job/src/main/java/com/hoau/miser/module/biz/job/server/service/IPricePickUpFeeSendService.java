package com.hoau.miser.module.biz.job.server.service;

/**
 * sync PMS PricePickUpFee to EDI interface
 * @author zouyu
 */
public interface IPricePickUpFeeSendService {

	/**
	 * @Description: sync PMS PricePickUpFee to EDI
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年2月25日
	 */
	public void sendPricePickUpFee();
}
