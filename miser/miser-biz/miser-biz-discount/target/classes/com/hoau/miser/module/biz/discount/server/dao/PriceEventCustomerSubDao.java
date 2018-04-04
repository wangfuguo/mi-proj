package com.hoau.miser.module.biz.discount.server.dao;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventCustomerSubEntity;

/**
 * 优惠活动客户明细
 * ClassName: PriceEventCustomerSubDao 
 * @author 廖文强
 * @date 2016年1月13日
 * @version V1.0
 */
@Repository
public interface PriceEventCustomerSubDao {

	
	
	/**
	 * 
	 * @Description: 插入优惠活动客户明细
	 * @param @param entity   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月13日
	 */
	public void insertPriceEventCustomerSub(PriceEventCustomerSubEntity entity);
	
	/**
	 * 
	 * @Description: 修改优惠活动客户明细
	 * @param @param entity   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月13日
	 */
	public void updatePriceEventCustomerSub(PriceEventCustomerSubEntity entity);
}
