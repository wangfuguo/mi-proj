package com.hoau.miser.module.biz.job.server.job;

import com.hoau.miser.module.biz.job.server.service.*;
import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * ClassName: BasicConfigSendJob
 * @Description: 基础配置信息同步JOB
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月15日
 * @version V1.0   
 */
public class BasicConfigSendJob extends AbstractComonJob {

	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		StringBuffer errorInfo = new StringBuffer();
		/********** 价格城市相关基础信息 ***********/
		//价格城市信息发送
		try {
			IPriceCitySendService priceCitySendService = getBean("priceCitySendService", IPriceCitySendService.class);
			priceCitySendService.sendPriceCity();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//标准价格城市映射关系发送
		try {
			IPriceCityMappingSendService priceCityMappingSendService=getBean("priceCityMappingSendService", IPriceCityMappingSendService.class);
			priceCityMappingSendService.send();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//客户价格城市映射关系发送
		try {
			ICustPriceCityMappingSendService custPriceCityMappingSendService=getBean("custPriceCityMappingSendService", ICustPriceCityMappingSendService.class);
			custPriceCityMappingSendService.send();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		/********* end of 价格城市相关基础信息 ************/

		/**********  客户相关基础信息 ***********/
		//客户价格配置信息发送
		try {
			ICustomerConfigSendService customerConfigSendService = getBean("customerConfigSendService", ICustomerConfigSendService.class);
			customerConfigSendService.sendCustomerConfig();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//客户价格锁定状态发送
		try {
			ICustomerLockStatusSendService customerLockStatusSendService = getBean("customerLockStatusSendService", ICustomerLockStatusSendService.class);
			customerLockStatusSendService.sendCustomerLockStatus();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		/********** end of 客户相关基础信息 ***********/

		/********** 优惠分段信息 ***********/
		//优惠分段
		try {
			IDiscountSectionSendService discountSectionSendService = getBean("discountSectionSendService", IDiscountSectionSendService.class);
			discountSectionSendService.sendDiscountSection();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		/********** end of 优惠分段信息 ***********/
		/********** 行政区域映射送货城市类型信息 ***********/

		/********** end of 行政区域映射送货城市类型信息 ***********/
		//如果存在错误信息，抛出异常，AbstractComonJob类会捕获该异常并记录到数据库
		if (errorInfo.length() > 0) {
			throw new RuntimeException(errorInfo.toString());
		}
	}

	@Override
	protected JobElementType getJobType() throws JobExecutionException {
		return JobElementType.JOB_BIZ_SEND_BASIC_INFO;
	}
	
}
