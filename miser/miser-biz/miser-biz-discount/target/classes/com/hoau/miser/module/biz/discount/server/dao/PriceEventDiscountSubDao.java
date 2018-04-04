package com.hoau.miser.module.biz.discount.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventRouteDiscountEntity;

/**
 * 优惠活动折扣明细dao
 * ClassName: PriceEventDiscountSubDao 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
@Repository
public interface PriceEventDiscountSubDao {

	/**
	 * 
	 * @Description: 插入优惠活动折扣明细
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void insertPriceEventDiscountSub(PriceEventDiscountSubEntity entity);
	
	/**
	 * 
	 * @Description: 修改优惠活动折扣明细
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void updatePriceEventDiscountSub(PriceEventDiscountSubEntity entity);

	/**
	 * 
	 * @Description: 根据父id查询优惠内容
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author275636
	 * @date 2016年2月26日
	 */
	public List<PriceEventDiscountSubEntity> selectPEDSubListByParam(
			PriceEventDiscountSubEntity priceEventDiscountSubEntity,
			RowBounds rowBounds);
	
	/**
	 * add by dengyin 2016-5-2 17：01：23  价格时效查询
	 * @param entity
	 * @return
	 */
	public List<PriceEventRouteDiscountEntity> queryEventRouteDiscount(PriceEventRouteDiscountEntity entity);
}
