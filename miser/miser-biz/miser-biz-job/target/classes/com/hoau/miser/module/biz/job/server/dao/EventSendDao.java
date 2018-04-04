package com.hoau.miser.module.biz.job.server.dao;

import com.hoau.miser.module.biz.job.shared.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * ClassName: EventSendDao
 * @Description: 活动发送相关数据操作
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月15日
 * @version V1.0   
 */
@Repository
public interface EventSendDao {
	
	/**
	 * @Description: 查询上次发送失败的数据
	 * @param entity
	 * @return ArrayList<PriceEventSendEntity>
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public ArrayList<PriceEventSendEntity> queryFailureData(DataSendJobFailureEntity entity);
	
	/**
	 * @Description: 查询本次需要发送的数据
	 * @param @param entity
	 * @return ArrayList<PriceEventSendEntity>
	 * @throws
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public ArrayList<PriceEventSendEntity> queryNeedSendData(DataSendJobQueryFilterEntity entity);

	/**
	 * @Description: 根据活动主表查询渠道范围
	 * @param entity
	 * @return
     */
	public ArrayList<PriceEventOrderchannelSubSendEntity> queryEventOrderChannelCondition(PriceEventSendEntity entity);

	/**
	 * @Description: 根据活动主表查询线路范围
	 * @param entity
	 * @return
	 */
	public ArrayList<PriceEventRouteSubSendEntity> queryEventRouteCondition(PriceEventSendEntity entity);

	/**
	 * @Description: 根据活动主表查询门店范围
	 * @param entity
	 * @return
	 */
	public ArrayList<PriceEventCorpSubSendEntity> queryEventCorpCondition(PriceEventSendEntity entity);

	/**
	 * @Description: 根据活动主表查询客户范围
	 * @param entity
	 * @return
	 */
	public ArrayList<PriceEventCustomerSubSendEntity> queryEventCustomerCondition(PriceEventSendEntity entity);

	/**
	 * @Description: 根据活动主表查询活动明细
	 * @param entity
	 * @return
	 */
	public ArrayList<PriceEventDiscountSubSendEntity> queryEventDiscount(PriceEventSendEntity entity);
	
	/**
	 * @Description: 更新失败表中重复记录(与新记录重复的)
	 * @param entity   
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月1日
	 */
	public void updateFailureRepeatData(DataSendJobFailureEntity entity);
}
