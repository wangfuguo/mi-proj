package com.hoau.miser.module.biz.job.server.job;

import com.hoau.miser.module.biz.job.server.service.IEventSendService;
import com.hoau.miser.module.biz.job.server.service.IPriceCitySendService;
import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * ClassName: EventSendJob
 * @Description: 活动信息发送JOB
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月15日
 * @version V1.0   
 */
public class EventSendJob extends AbstractComonJob {

	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		IEventSendService eventSendService = getBean("eventSendService", IEventSendService.class);
		eventSendService.sendEvent();
	}

	@Override
	protected JobElementType getJobType() throws JobExecutionException {
		return JobElementType.JOB_BIZ_SEND_EVENT;
	}
	
}
