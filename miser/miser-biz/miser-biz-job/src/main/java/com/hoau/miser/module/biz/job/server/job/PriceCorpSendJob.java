package com.hoau.miser.module.biz.job.server.job;


import com.hoau.miser.module.biz.job.server.service.IDiscountCorpSendService;
import com.hoau.miser.module.biz.job.server.service.IOuterBranchPriceSendService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.hoau.miser.module.biz.job.server.service.IPriceCorpSendService;
import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;

/**
 * 
 * ClassName: PriceCorpSendJob 
 * @Description: 网点价格\网点折扣\偏线外发价格发送
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月25日
 * @version V1.0
 */
public class PriceCorpSendJob extends AbstractComonJob {

	
	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		StringBuffer errorInfo = new StringBuffer();
		//网点价格
		try {
			IPriceCorpSendService priceCorpSendService=getBean("priceCorpSendService", IPriceCorpSendService.class);
			priceCorpSendService.sendPriceCorp();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//网点折扣
		try {
			IDiscountCorpSendService discountCorpSendService=getBean("discountCorpSendService", IDiscountCorpSendService.class);
			discountCorpSendService.sendDiscountCorp();
		} catch (Exception e) {
			errorInfo.append(ExceptionUtils.getFullStackTrace(e));
		}
		//偏线外发价格
		try {
			IOuterBranchPriceSendService outerBranchPriceSendService = getBean("outerBranchPriceSendService", IOuterBranchPriceSendService.class);
			outerBranchPriceSendService.SendOuterBranchPrice();
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
		return JobElementType.JOB_BIZ_SEND_CORP_PRICE;
	}

}
