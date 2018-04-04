package com.hoau.miser.module.biz.job.server.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.ExtrafeeAddValueFeeEntity;

/**
 * ClassName: ExtrafeeAddValueFeeSendDao 
 * @Description: 增值服务费 ExtrafeeAddValueFeeEntity DAO
 * @Table T_PRICE_PICKUP_FEE_PC, T_BSE_TRANS_TYPE
 * @author ZOUYU
 * @version V1.0
 */
@Repository
public interface ExtrafeeAddValueFeeSendDao {

	/**
	 * @Description: MQ sync pms-system data to edi-system
	 * @param entity query conditions
	 * @return List<ExtrafeeAddValueFeeEntity> 
	 * @throws
	 * @author ZOUYU
	 * @date 2016.1.21
	 */
	public ArrayList<ExtrafeeAddValueFeeEntity> queryNeedSendData(DataSendJobQueryFilterEntity entity);
	
	/**
	 * @Description: query MQ send failure data
	 * @param entity query conditions
	 * @throws Exception   
	 * @return List<ExtrafeeAddValueFeeEntity> failure data list
	 * @author ZOUYU
	 * @date 2016.2.25
	 */
	public ArrayList<ExtrafeeAddValueFeeEntity> queryFailureData(DataSendJobFailureEntity entity);
	
	/**
	 * @Description: update repeat MQ send failure data
	 * @param entity query conditions
	 * @throws Exception   
	 * @return void
	 * @author zouyu
	 * @date 2016年2月25日
	 */
	public void updateFailureRepeatData(DataSendJobFailureEntity entity);
	
}
