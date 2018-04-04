package com.hoau.miser.module.biz.job.server.service.impl;

import javax.annotation.Resource;

import com.hoau.miser.module.util.define.MiserConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.miser.module.biz.job.server.dao.DataSendJobTimeDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;

/**
 * ClassName: DataSendJobTimeService 
 * @Description: 数据发送任务发送时间操作实现 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月25日
 * @version V1.0   
 */
@Service
public class DataSendJobTimeService implements IDataSendJobTimeService {

	@Resource
	private DataSendJobTimeDao dataSendJobTimeDao;
	
	@Override
	@Transactional
	public void saveLastSendTime(DataSendJobTimeEntity entity) {
		//更新上次的记录无效
		dataSendJobTimeDao.updateLastSendTime(entity);
		//插入新的有效的最后发送时间
		dataSendJobTimeDao.insertLastSendTime(entity);
	}

	/**
	 * @param jobCode 同步任务编码
	 * @return DataSendJobTimeEntity 上次同步时间参数
	 * @Description: 获取任务上次同步时间
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	@Override
	public DataSendJobTimeEntity getLastSendTime(String jobCode) {
		DataSendJobTimeEntity entity = new DataSendJobTimeEntity();
		entity.setJobCode(jobCode);
		entity.setActive(MiserConstants.ACTIVE);
		return getLastSendTime(entity);
	}

	@Override
	public DataSendJobTimeEntity getLastSendTime(DataSendJobTimeEntity entity) {
		return dataSendJobTimeDao.getLastSendTime(entity);
	}

	public DataSendJobTimeDao getDataSendJobTimeDao() {
		return dataSendJobTimeDao;
	}

	public void setDataSendJobTimeDao(DataSendJobTimeDao dataSendJobTimeDao) {
		this.dataSendJobTimeDao = dataSendJobTimeDao;
	}

}
