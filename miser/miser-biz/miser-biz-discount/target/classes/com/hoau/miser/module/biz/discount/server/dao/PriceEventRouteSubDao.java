package com.hoau.miser.module.biz.discount.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventRouteSubEntity;

/**
 * 优惠活动线路明细dao
 * ClassName: PriceEventRouteSubDao 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
@Repository
public interface PriceEventRouteSubDao {

	
	/**
	 * 
	 * @Description: 插入优惠活动线路明细
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void insertPriceEventRouteSub(PriceEventRouteSubEntity entity);
	
	/**
	 * 
	 * @Description: 修改优惠活动线路明细
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void updatePriceEventRouteSub(PriceEventRouteSubEntity entity);

	/**
	 * 
	 * @Description: 根据父id查询线路明细
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 275636
	 * @date 2016年3月1日
	 */
	public List<PriceEventRouteSubEntity> selectLineSubListByParam(
			PriceEventRouteSubEntity eventRouteSubEntity, RowBounds rowBounds);
}
