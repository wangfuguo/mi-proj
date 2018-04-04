package com.hoau.miser.module.biz.job.server.job;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.hoau.miser.module.biz.job.server.service.IPriceStandSendService;
import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;

/**
 * 
 * ClassName: PriceCardSendJob 
 * @Description: 标准价格MQ
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月25日
 * @version V1.0
 */
public class PriceStandSendJob extends AbstractComonJob {
	
	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		IPriceStandSendService priceStandSendService=getBean("priceStandSendService", IPriceStandSendService.class);	
		priceStandSendService.sendPriceCard();
	}

	@Override
	protected JobElementType getJobType() throws JobExecutionException {
		return JobElementType.JOB_BIZ_SEND_STANDARD_PRICECARD;
	}
	
}
