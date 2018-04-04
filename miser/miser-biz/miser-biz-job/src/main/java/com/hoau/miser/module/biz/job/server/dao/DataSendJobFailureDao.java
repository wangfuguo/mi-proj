package com.hoau.miser.module.biz.job.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;

/**
 * ClassName: DataSendJobFailureDao 
 * @Description: 发送数据失败数据库操作类
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月25日
 * @version V1.0   
 */
@Repository
public interface DataSendJobFailureDao {

	/**
	 * @Description: 保存失败数据
	 * @param entity   需要保存的数据
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public void saveFailureData(DataSendJobFailureEntity entity);
	
	/**
	 * @Description: 获取发送失败的数据
	 * @param entity	查询条件
	 * @return List<DataSendJobTimeEntity>	发送失败的数据 
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
	 * @param entity   需要更新的数据
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月24日
	 */
	public void updateSendFailTimes(DataSendJobFailureEntity entity);
}
