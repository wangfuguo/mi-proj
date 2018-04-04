package com.hoau.miser.module.biz.job.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hoau.miser.module.biz.job.server.service.ICustomerDiscountSendService;
import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;

/**
 *  
 * ClassName: CustomerPriceSendJob 
 * @author 275636
 * @date 2016年1月26日
 * @version V1.0
 * 客户折扣job
 */
public class CustomerDiscountSendJob extends AbstractComonJob {

	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		ICustomerDiscountSendService iCustomerDiscountSendService =getBean("customerDiscountSendService", ICustomerDiscountSendService.class);		
		iCustomerDiscountSendService.sendCustomerDiscount();
	}

	@Override
	protected JobElementType getJobType() throws JobExecutionException {
		return JobElementType.JOB_BIZ_SEND_CUSTOMER_DISCOUNT;
	}

}
