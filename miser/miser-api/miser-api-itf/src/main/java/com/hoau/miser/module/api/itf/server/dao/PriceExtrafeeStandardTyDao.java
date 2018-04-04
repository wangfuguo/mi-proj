package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceExtrafeeStandardEntity;

/**
 * 标准附加费
 *
 * @author 蒋落琛
 * @date 2016-6-16上午8:43:58
 */
@Repository
public interface PriceExtrafeeStandardTyDao {

	/**
	 * 
	 * @Description: 查询标准附加费列表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceExtrafeeStandardEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public List<PriceExtrafeeStandardEntity> queryListByParam(PriceExtrafeeStandardEntity psv);
	
}
