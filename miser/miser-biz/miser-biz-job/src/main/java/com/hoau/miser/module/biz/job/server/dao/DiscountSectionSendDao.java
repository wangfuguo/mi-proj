package com.hoau.miser.module.biz.job.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DiscountSectionSendEntity;

/**
 * ClassName: DiscountSestionDao 
 * @author 刘海飞
 * @date 2016年1月22日
 * @version V1.0
 */
@Repository
public interface DiscountSectionSendDao {

	/**
	 * @Description: 查询本次需要发送的数据
	 * @param @param entity
	 * @param @return   
	 * @return List<DiscountSectionSendEntity> 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月26日
	 */
	public ArrayList<DiscountSectionSendEntity> queryListByParam(DataSendJobQueryFilterEntity entity);
	
	
	/**
	 * @Description: 查询上次发送失败的数据
	 * @param @param entity
	 * @param @return   
	 * @return ArrayList<PriceCitySendEntity> 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月26日
	 */
	public ArrayList<DiscountSectionSendEntity> queryFailureData(DataSendJobFailureEntity entity);

	/**
	 * @Description: 根据code值查询优惠分段信息
	 * @param @param code
	 * @param @return   
	 * @return DiscountSectionSendEntity 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年2月26日
	 */
	public DiscountSectionSendEntity queryDataByCode(String code);
}
