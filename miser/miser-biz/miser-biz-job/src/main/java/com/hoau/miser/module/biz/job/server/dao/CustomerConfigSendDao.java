package com.hoau.miser.module.biz.job.server.dao;

import com.hoau.miser.module.biz.job.shared.domain.CustomerConfig;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * ClassName: CustomerConfigSendDao
 * @Description: 客户配置信息发送相关数据操作
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月15日
 * @version V1.0   
 */
@Repository
public interface CustomerConfigSendDao {
	
	/**
	 * @Description: 查询上次发送失败的数据
	 * @param @return   
	 * @return ArrayList<PriceCitySendEntity> 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public ArrayList<CustomerConfig> queryFailureData(DataSendJobFailureEntity entity);
	
	/**
	 * @Description: 查询本次需要发送的数据
	 * @param @param entity
	 * @param @return   
	 * @return ArrayList<PriceCitySendEntity> 
	 * @throws
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public ArrayList<CustomerConfig> queryNeedSendData(DataSendJobQueryFilterEntity entity);
	
	/**
	 * @Description: 更新失败表中重复记录(与新记录重复的)
	 * @param entity   
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月1日
	 */
	public void updateFailureRepeatData(DataSendJobFailureEntity entity);
}
