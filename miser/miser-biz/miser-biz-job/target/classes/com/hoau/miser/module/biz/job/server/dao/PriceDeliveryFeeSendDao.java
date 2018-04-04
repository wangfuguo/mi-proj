package com.hoau.miser.module.biz.job.server.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.PriceDeliveryFeeCityTypeEntity;

/**
 * ClassName: PriceDeliveryFeeSendDao 
 * @Description: 送货费 PriceDeliveryFeeCityTypeEntity DAO
 * @Table T_PRICE_DELIVERY_FEE_CITYTYPE
 * @author ZOUYU
 * @version V1.0
 */
@Repository
public interface PriceDeliveryFeeSendDao {

	/**
	 * @Description: MQ sync pms-system data to edi-system
	 * @param entity query conditions
	 * @return List<PriceDeliveryFeeCityTypeEntity> 
	 * @throws
	 * @author zouyu
	 * @date 2016年1月21日
	 */
	public ArrayList<PriceDeliveryFeeCityTypeEntity> queryNeedSendData(DataSendJobQueryFilterEntity entity);
	
	/**
	 * @Description: query MQ send failure data
	 * @param entity query conditions
	 * @throws Exception   
	 * @return List<PriceDeliveryFeeCityTypeEntity> failure data list
	 * @author zouyu
	 * @date 2016年2月25日
	 */
	public ArrayList<PriceDeliveryFeeCityTypeEntity> queryFailureData(DataSendJobFailureEntity entity);
	
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
