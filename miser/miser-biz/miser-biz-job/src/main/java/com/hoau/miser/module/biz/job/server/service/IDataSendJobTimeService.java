package com.hoau.miser.module.biz.job.server.service;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;

/**
 * ClassName: IDataSendJobTimeService 
 * @Description: 数据发送任务发送时间操作接口 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月25日
 * @version V1.0   
 */
public interface IDataSendJobTimeService {

	/**
	 * @Description: 保存任务上次同步时间
	 * @param entity   任务时间
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public void saveLastSendTime(DataSendJobTimeEntity entity);
	
	/**
	 * @Description: 获取任务上次同步时间
	 * @param entity	获取时间参数
	 * @return DataSendJobTimeEntity 上次同步时间参数
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public DataSendJobTimeEntity getLastSendTime(DataSendJobTimeEntity entity);

	/**
	 * @Description: 获取任务上次同步时间
	 * @param jobCode	同步任务编码
	 * @return DataSendJobTimeEntity 上次同步时间参数
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public DataSendJobTimeEntity getLastSendTime(String jobCode);
	
}
