package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;

/**
 * 优惠分段
 * 
 * @author 蒋落琛
 * @date 2016-6-6下午3:05:03
 */
@Repository
public interface PriceSectionTyDao {

	/**
	 * 查询优惠分段明细列表
	 * 
	 * @param sectionCode
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-6下午3:05:58
	 * @update
	 */
	public List<PriceSectionSubEntity> querySectionDetailByCode(String sectionCode);

}
