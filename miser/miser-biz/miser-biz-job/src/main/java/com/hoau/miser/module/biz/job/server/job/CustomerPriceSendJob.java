package com.hoau.miser.module.biz.job.server.job;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hoau.miser.module.biz.job.server.service.ICustomerPriceSendService;
import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;

/**
 *  
 * ClassName: CustomerPriceSendJob 
 * @author 廖文强
 * @date 2016年1月26日
 * @version V1.0
 */
public class CustomerPriceSendJob extends AbstractComonJob {

	@Resource
	private ICustomerPriceSendService customerPriceSendService;
	
	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		customerPriceSendService=getBean("customerPriceSendService", ICustomerPriceSendService.class);
		customerPriceSendService.sendPrice();
	}

	@Override
	protected JobElementType getJobType() throws JobExecutionException {
		return JobElementType.JOB_BIZ_SEND_CUSTOMER_PRICECARD;
	}
	

}
