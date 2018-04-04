package com.hoau.miser.module.biz.job.server.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.jms.Destination;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;

/**
 * 发送MQ基础Service接口
 * ClassName: IBaseSendService 
 * @author 286330付于令
 * @date 2016年2月24日
 * @version V1.0
 */
public interface IBaseSendService {
	/**
	 * @Description: 构建查询Filter
	 * @param jobCode
	 * @return DataSendJobFailureEntity 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	public DataSendJobFailureEntity getFailureQueryFilter(String jobCode);
	public DataSendJobQueryFilterEntity getQueryFilter(String jobCode, Date endTime);
	/**
	 * @Description: 更新本次发送时间
	 * @param jobCode
	 * @param sendTime   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	public void updateCurrentSendTime(String jobCode, Date sendTime);
	/**
	 * @Description: 发送数据
	 * @param entityList
	 * @param destination   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	public <T extends Serializable> void sendData(List<T> entityList, Destination destination);
	public <T extends Serializable> void sendData(List<T> entityList, Destination destination, int maxSendCount);
}
