package com.hoau.miser.module.biz.job.server.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.biz.job.server.service.IBaseSendService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 发送MQ基础Service实现
 * ClassName: BaseSendService 
 * @author 286330付于令
 * @date 2016年2月24日
 * @version V1.0
 */
@Service
public class BaseSendService implements IBaseSendService {
	private static int MAX_SEND_COUNT = 100; // 每次发送MQ的最大条数
	@Resource
	private IDataSendJobTimeService dataSendJobTimeService;
	@Resource
	private IDataSendJobFailureService dataSendJobFailureService;
	@Resource
	private IActiveMQProducerService activeMQProducerService;
	/**
	 * @Description: 构建查询Filter
	 * @param jobCode
	 * @return DataSendJobFailureEntity 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	public DataSendJobFailureEntity getFailureQueryFilter(String jobCode) {
		DataSendJobFailureEntity failureFilterEntity = new DataSendJobFailureEntity();
		failureFilterEntity.setActive(MiserConstants.ACTIVE);
		failureFilterEntity.setType(jobCode);
		return failureFilterEntity;
	}
	/**
	 * @Description: 构建查询Filter
	 * @param jobCode
	 * @param endTime
	 * @return DataSendJobQueryFilterEntity 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	public DataSendJobQueryFilterEntity getQueryFilter(String jobCode, Date endTime) {
		// 查询上次发送的时间
		DataSendJobTimeEntity sendJobTimeEntity = dataSendJobTimeService
				.getLastSendTime(jobCode);
		// 构建查询Filter
		DataSendJobQueryFilterEntity queryFilterEntity = new DataSendJobQueryFilterEntity();
		if(sendJobTimeEntity != null) {
			queryFilterEntity.setStartTime(sendJobTimeEntity.getLastSendTime());
		}
		queryFilterEntity.setEndTime(endTime);
		return queryFilterEntity;
	}
	/**
	 * @Description: 更新本次发送时间
	 * @param jobCode
	 * @param sendTime   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	public void updateCurrentSendTime(String jobCode, Date sendTime) {
		DataSendJobTimeEntity currentSendTimeEntity = new DataSendJobTimeEntity();
		currentSendTimeEntity.setId(UUIDUtil.getUUID());
		currentSendTimeEntity.setJobCode(jobCode);
		currentSendTimeEntity.setJobName(DataSendJobConstant.getNameByCode(jobCode));
		currentSendTimeEntity.setLastSendTime(sendTime);
		currentSendTimeEntity.setActive(MiserConstants.ACTIVE);
		currentSendTimeEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
		Date date = new Date();
		currentSendTimeEntity.setCreateDate(date);
		currentSendTimeEntity.setModifyDate(date);
		currentSendTimeEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
		dataSendJobTimeService.saveLastSendTime(currentSendTimeEntity);
	}
	/**
	 * @Description: 发送数据
	 * @param entityList
	 * @param destination
	 * @param destination   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	public <T extends Serializable> void sendData(List<T> entityList, Destination destination) {
		sendData(entityList, destination, MAX_SEND_COUNT);
	}
	public <T extends Serializable> void sendData(List<T> entityList, Destination destination, int maxSendCount) {
		if(entityList.isEmpty()) return;
		int size = entityList.size();
		ArrayList<T> entityArrayList = new ArrayList<T>(maxSendCount);
		for(int i=0; i<size; i++) {
			T t = entityList.get(i);
			entityArrayList.add(t);
			if(i == (size-1) || (i+1) % maxSendCount == 0) {
				// 发送MQ
				activeMQProducerService.postObjectToQueue(destination, entityArrayList);
				entityArrayList.clear();
			}
		}
	}
	
}
