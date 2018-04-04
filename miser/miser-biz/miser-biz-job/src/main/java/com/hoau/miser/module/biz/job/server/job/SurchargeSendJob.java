package com.hoau.miser.module.biz.job.server.job;

import com.hoau.miser.module.biz.job.server.service.*;
import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * ClassName: SurchargeSendJob
 * @Description: 发送所有增值服务费JOB
 * @author ZOUYU
 * @date 2016年1月15日
 * @version V1.0
 */
public class SurchargeSendJob extends AbstractComonJob {
	
	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		StringBuffer errorInfo = new StringBuffer();
		//提货费发送
		try {
			IPricePickUpFeeSendService pricePickUpFeeSendService = getBean("pricePickUpFeeSendService", IPricePickUpFeeSendService.class);
			pricePickUpFeeSendService.sendPricePickUpFee();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//送货费发送
		try {
			IPriceDeliveryFeeSendService priceDeliveryFeeSendService = getBean("priceDeliveryFeeSendService", IPriceDeliveryFeeSendService.class);
			priceDeliveryFeeSendService.sendPriceDeliveryFee();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//包装费发送
		try {
			IPricePackageFeeService ipricePackageFeeService=getBean("pricePackageFeeSendService", IPricePackageFeeService.class);
			ipricePackageFeeService.sendPackageFee();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//价格城市包装费发送
		try {
			IPricePackageFeePcService ipricePackageFeePcService=getBean("pricePackageFeePcSendService", IPricePackageFeePcService.class);
			ipricePackageFeePcService.sendPackageFeePc();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//标准附加费发送
		try {
			IPriceExtrafeeStandardSendService priceExtrafeeStandardSendService=getBean("priceExtrafeeStandardSendService", IPriceExtrafeeStandardSendService.class);
			priceExtrafeeStandardSendService.sendPriceExtrafeeStandard();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//特服费项目发送
		try {
			IExtrafeeAddValueFeeItemsSendService extrafeeAddValueFeeItemsSendService = getBean("extrafeeAddValueFeeItemsSendService", IExtrafeeAddValueFeeItemsSendService.class);
			extrafeeAddValueFeeItemsSendService.sendExtrafeeAddValueFeeItems();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//特服费发送
		try {
			IExtrafeeAddValueFeeSendService extrafeeAddValueFeeSendService = getBean("extrafeeAddValueFeeSendService", IExtrafeeAddValueFeeSendService.class);
			extrafeeAddValueFeeSendService.sendExtrafeeAddValueFee();
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
		return JobElementType.JOB_BIZ_SEND_SURCHARGE_INFO;
	}

}
