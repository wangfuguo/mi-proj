package com.hoau.miser.module.biz.job.server.dao;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;

/**
 * ClassName: DataSendJobTimeDao 
 * @Description: 数据发送任务发送时间数据库操作类 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月25日
 * @version V1.0   
 */

@Repository
public interface DataSendJobTimeDao {

	/**
	 * @Description: 首次插入上次发送时间
	 * @param entity   上次发送时间数据 
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public void insertLastSendTime(DataSendJobTimeEntity entity);
	
	/**
	 * @Description: 更新上次发送时间
	 * @param entity   上次发送时间数据 
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public void updateLastSendTime(DataSendJobTimeEntity entity);
	
	/**
	 * @Description: 获取上次发送时间
	 * @param entity	查询条件
	 * @return DataSendJobTimeEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public DataSendJobTimeEntity getLastSendTime(DataSendJobTimeEntity entity);
	
	
}
