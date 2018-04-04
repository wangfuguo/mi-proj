package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceEventTyQueryParam;

import java.util.List;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: IPriceEventTyService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 活动查询
 * @date 16/6/8 10:33
 */
public interface IPriceEventTyService {

	/**
	 * 根据条件查询所有可用的活动,不含活动明细
	 * @param queryParam
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日10:37:26
	 */
	public List<PriceEventEntity> queryPriceEventsWithoutDetailByCondition(PriceEventTyQueryParam queryParam);

	/**
	 * 根据活动编号查询活动明细
	 * @param eventCode
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日10:39:19
	 */
	public List<PriceEventDiscountSubEntity> queryPriceEventDetails(String eventCode);

	/**
	 * 根据活动编号及运输类型查询一条活动折扣信息
	 * @param eventCode
	 * @param transportType
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日14:22:14
	 */
	public PriceEventDiscountSubEntity queryPriceEventDetail(String eventCode, String transportType);

	/**
	 * 根据条件带明细的活动
	 * @param queryParam
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日10:40:15
	 */
	public List<PriceEventEntity> queryPriceEventWithDetailByCondition(PriceEventTyQueryParam queryParam);

	/**
	 * 查询活动分段明细
	 * @param queryParam	 查询条件
	 * @param chargeType	 费用类型
	 * @return
	 * @author 陈宇霖
	 * @date 2016年07月28日10:18:56
	 */
	public List<List<PriceSectionSubEntity>> getEventSection(PriceEventTyQueryParam queryParam, String chargeType);

}
