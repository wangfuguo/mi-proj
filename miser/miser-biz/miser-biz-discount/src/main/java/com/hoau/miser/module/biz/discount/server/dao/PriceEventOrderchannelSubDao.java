package com.hoau.miser.module.biz.discount.server.dao;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventOrderchannelSubEntity;

/**
 * 优惠活动订单渠道明细dao
 * ClassName: PriceEventOrderchannelSubDao 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
@Repository
public interface PriceEventOrderchannelSubDao {

	/**
	 * 
	 * @Description: 插入优惠活动订单渠道明细
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void insertPriceEventOrderchannelSub(PriceEventOrderchannelSubEntity entity);
	
	/**
	 * 
	 * @Description: 修改优惠活动订单渠道明细
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void updatePriceEventOrderchannelSub(PriceEventOrderchannelSubEntity entity);
}
