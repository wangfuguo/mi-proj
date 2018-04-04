package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventTyQueryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventEntity;

/**
 * 活动DAO
 * 
 * @author 蒋落琛
 * @date 2016-6-7上午8:42:35
 */
@Repository
public interface PriceEventTyDao {

	/**
	 * 根据条件查询不带明细的活动信息
	 * @param queryEntity
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日10:45:34
	 */
	public List<PriceEventEntity> queryEventsWithoutDetail(PriceEventTyQueryEntity queryEntity);

	/**
	 * 根据条件查询带明细的活动信息
	 * @param queryEntity
	 * @return
	 * @author 蒋落琛
	 * @date 2016年06月08日10:48:05
	 */
	public List<PriceEventEntity> queryEventsWithDetail(PriceEventTyQueryEntity queryEntity);

	/**
	 * 根据活动编号查询活动折扣明细
	 * @param eventCode
	 * @param transportType
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日10:47:19
	 */
	public List<PriceEventDiscountSubEntity> queryEventDetails(@Param("eventCode") String eventCode,
															   @Param("transportType") String transportType);

}
