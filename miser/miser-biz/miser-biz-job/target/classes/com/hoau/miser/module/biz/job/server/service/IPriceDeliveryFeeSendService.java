package com.hoau.miser.module.biz.job.server.service;

/**
 * sync PMS PriceDeliveryFee to EDI interface
 * @author zouyu
 */
public interface IPriceDeliveryFeeSendService {

	/**
	 * @Description: sync PMS PriceDeliveryFee to EDI
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年2月25日
	 */
	public void sendPriceDeliveryFee();
}
