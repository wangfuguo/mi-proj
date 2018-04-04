package com.hoau.miser.module.biz.job.server.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.ExtrafeeAddValueFeeItemsEntity;

/**
 * ClassName: ExtrafeeAddValueFeeItemsSendDao 
 * @Description: 增值服务费项目定义 ExtrafeeAddValueFeeItemsEntity DAO
 * @Table T_PRICE_ADDVALUE_FEE_ITEMS
 * @author ZOUYU
 * @version V1.0
 */
@Repository
public interface ExtrafeeAddValueFeeItemsSendDao {

	/**
	 * @Description: MQ sync pms-system data to edi-system
	 * @param entity query conditions
	 * @return List<ExtrafeeAddValueFeeItemsEntity> 
	 * @throws
	 * @author ZOUYU
	 * @date 2016.1.21
	 */
	public ArrayList<ExtrafeeAddValueFeeItemsEntity> queryNeedSendData(DataSendJobQueryFilterEntity entity);
	
	/**
	 * @Description: query MQ send failure data
	 * @param entity query conditions
	 * @throws Exception   
	 * @return List<ExtrafeeAddValueFeeItemsEntity> failure data list
	 * @author ZOUYU
	 * @date 2016.2.25
	 */
	public ArrayList<ExtrafeeAddValueFeeItemsEntity> queryFailureData(DataSendJobFailureEntity entity);
	
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
