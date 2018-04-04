package com.hoau.miser.module.biz.job.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hoau.miser.module.biz.job.server.service.ICustomerInsertService;
import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;

/**
 * ClassName: CustomerInsertJob 
 * @Description: 从CRM同步客户基础数据
 * @author 275636
 * @date 2016年1月25日
 * @version V1.0   
 */
public class CustomerInsertJob extends AbstractComonJob {

	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		ICustomerInsertService iCustomerInsertService=getBean("customerInsertService", ICustomerInsertService.class);		
		iCustomerInsertService.insertCustomerData();
	}

	@Override
	protected JobElementType getJobType() throws JobExecutionException {
		return JobElementType.JOB_BIZ_SEND_CUSTOMER;
	}
	

}
