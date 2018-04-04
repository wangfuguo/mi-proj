package com.hoau.miser.module.biz.job.server.job;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hoau.miser.module.biz.job.server.service.IDiscountPrivilegeSendService;
import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;

/**
 * 越发越惠数据同步Job
 * ClassName: DiscountPrivilegeSendJob 
 * @author 286330付于令
 * @date 2016年2月23日
 * @version V1.0
 */
public class DiscountPrivilegeSendJob extends AbstractComonJob {

	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		IDiscountPrivilegeSendService discountPrivilegeSendService = this
				.getBean("discountPrivilegeSendService", IDiscountPrivilegeSendService.class);
		StringBuffer errorInfo = new StringBuffer();
		try {
			discountPrivilegeSendService.sendPrivilege();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		try {
			discountPrivilegeSendService.sendPrivilegeContract();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		try {
			discountPrivilegeSendService.sendPrivilegeDiscount();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		try {
			discountPrivilegeSendService.sendPrivilegeCoupon();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//如果存在错误信息，抛出异常，AbstractComonJob类会捕获该异常并记录到数据库
		if (errorInfo.length() > 0) {
			throw new RuntimeException(errorInfo.toString());
		}
	}

	@Override
	protected JobElementType getJobType() throws JobExecutionException {
		return JobElementType.JOB_BIZ_SEND_PRIVILEGE;
	}

}
