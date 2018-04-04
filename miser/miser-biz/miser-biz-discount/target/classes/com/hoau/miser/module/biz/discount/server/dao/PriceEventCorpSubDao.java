package com.hoau.miser.module.biz.discount.server.dao;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventCorpSubEntity;

/**
 * 优惠门店dao
 * ClassName: PriceEventCorpSubDao 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
@Repository
public interface PriceEventCorpSubDao {

	
	
	/**
	 * 
	 * @Description: 插入优惠折扣门店
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void insertPriceEventCorpSub(PriceEventCorpSubEntity entity);
	
	/**
	 * 
	 * @Description: 修改优惠折扣门店
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void updatePriceEventCorpSub(PriceEventCorpSubEntity entity);
}
