package com.hoau.miser.module.biz.job.server.service;

import java.util.List;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;

/**
 * ClassName: IDataSendJobFailureService 
 * @Description: 保存发送数据失败数据的接口
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月25日
 * @version V1.0   
 */
public interface IDataSendJobFailureService {

	/**
	 * @Description: 保存发送失败记录
	 * @param  entity  发送失败的记录 
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public void saveFailureData(DataSendJobFailureEntity entity);
	
	/**
	 * @Description: 获取发送失败的记录
	 * @param entity	获取的条件对象
	 * @return List<DataSendJobFailureEntity> 	发送失败的数据集合
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public List<DataSendJobFailureEntity> getFailureDatas(DataSendJobFailureEntity entity);
	
	/**
	 * @Description: 更新发送成功的数据
	 * @param entity   发送成功的数据
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public void updateSendedData(DataSendJobFailureEntity entity);
	
	/**
	 * @Description: 更新发送失败次数
	 * @param entity	需要更新的数据   
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月24日
	 */
	public void updateSendFailTimes(DataSendJobFailureEntity entity);
}
