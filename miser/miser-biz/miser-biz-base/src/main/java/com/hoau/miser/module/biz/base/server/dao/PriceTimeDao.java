/**
 * 
 */
package com.hoau.miser.module.biz.base.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceTimeEntity;

/**
 * @author dengyin
 *
 */
@Repository
public interface PriceTimeDao {
	
	/**
	 * 分页查询
	 * @param priceTimeEntity
	 * @param rowBounds
	 * @return
	 */
	List<PriceTimeEntity> queryPriceTimeEntityListByEntity(PriceTimeEntity priceTimeEntity,RowBounds rowBounds);
	
	/**
	 * 统计数量
	 */
	long countOfPriceTimeEntityListByEntity(PriceTimeEntity priceTimeEntity);

	
	/**
	 * @Description: 价格时效需求涉及优惠分段明细查询
	 * @param @param entity
	 * @param @return   
	 * @return List<PriceSectionEntity> 
	 * @throws
	 * @author dengyin
	 * @date 2016-4-26 13:48:44
	 */
	public List<PriceSectionEntity> querySectionSubListBySectionCodeStr(Map<String, String> paramMap);

}
