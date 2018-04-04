package com.hoau.miser.module.biz.job.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.miser.module.biz.job.server.dao.DataSendJobFailureDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;

/**
 * ClassName: DataSendJobFailureService 
 * @Description: 发送数据失败的实现 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月25日
 * @version V1.0   
 */
@Service
public class DataSendJobFailureService implements IDataSendJobFailureService {

	/**
	 * @Fields dataSendJobFailureDao : 数据操作对象
	 */
	@Resource
	private DataSendJobFailureDao dataSendJobFailureDao;
	
	@Override
	@Transactional
	public void saveFailureData(DataSendJobFailureEntity entity) {
		dataSendJobFailureDao.saveFailureData(entity);
	}

	@Override
	public List<DataSendJobFailureEntity> getFailureDatas(
			DataSendJobFailureEntity entity) {
		return dataSendJobFailureDao.getFailureDatas(entity);
	}

	@Override
	@Transactional
	public void updateSendedData(DataSendJobFailureEntity entity) {
		dataSendJobFailureDao.updateSendedData(entity);
	}

	@Override
	@Transactional
	public void updateSendFailTimes(DataSendJobFailureEntity entity) {
		dataSendJobFailureDao.updateSendFailTimes(entity);
	}

	public DataSendJobFailureDao getDataSendJobFailureDao() {
		return dataSendJobFailureDao;
	}

	public void setDataSendJobFailureDao(DataSendJobFailureDao dataSendJobFailureDao) {
		this.dataSendJobFailureDao = dataSendJobFailureDao;
	}

}
