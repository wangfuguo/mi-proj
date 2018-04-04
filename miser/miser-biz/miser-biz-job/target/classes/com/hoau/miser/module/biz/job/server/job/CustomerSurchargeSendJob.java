package com.hoau.miser.module.biz.job.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hoau.miser.module.biz.job.server.service.ICustomerExtraFeeSendService;
import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;

/**
 *  
 * ClassName: CustomerPriceSendJob 
 * @author 275636
 * @date 2016年1月26日
 * @version V1.0
 * 客户附加费job
 */
public class CustomerSurchargeSendJob extends AbstractComonJob{

	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		ICustomerExtraFeeSendService ICustomerExtraFeeSendService =getBean("customerExtraFeeSendService", ICustomerExtraFeeSendService.class);
		ICustomerExtraFeeSendService.sendCustomerSurcharge();
	}

	@Override
	protected JobElementType getJobType() throws JobExecutionException {
		return JobElementType.JOB_BIZ_SEND_CUSTOMER_SURCHARGE;
	}

}
