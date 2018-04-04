package com.hoau.miser.module.biz.job.server.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hoau.eai.pc.vo.PriceCityMappingInfo;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
@Repository
public interface CustPriceCityMappingSendDao {

	/**
	 * 失败信息去重
	 * @Description: TODO描述该方法是做什么的
	 * @param @param queryEnity   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年2月28日
	 */
	public void failureInfoUniq(DataSendJobFailureEntity queryEnity);
	/**
	 * 查询失败信息
	 * @Description: TODO描述该方法是做什么的
	 * @param @param failuerEntity
	 * @param @return   
	 * @return ArrayList<PriceCityMappingInfo> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年2月28日
	 */
	public ArrayList<PriceCityMappingInfo> queryFailureData(DataSendJobFailureEntity failuerEntity);
	/**
	 * 查询本次要发送的信息
	 * @Description: TODO描述该方法是做什么的
	 * @param @param queryEnity
	 * @param @return   
	 * @return ArrayList<PriceCityMappingInfo> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年2月28日
	 */
	public ArrayList<PriceCityMappingInfo> queryNeedSendData(DataSendJobQueryFilterEntity queryEnity);
}
