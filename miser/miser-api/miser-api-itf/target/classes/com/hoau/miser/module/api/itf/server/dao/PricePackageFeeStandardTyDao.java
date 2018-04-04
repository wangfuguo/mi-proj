package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.itf.api.shared.domain.PricePackageFeeStandardEntity;

/**
 * 标准包装费Dao
 *
 * @author 蒋落琛
 * @date 2016-6-15下午5:26:33
 */
@Repository
public interface PricePackageFeeStandardTyDao {

	/**
	 * 查询标准包装费集合
	 * 
	 * @param params
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-16上午10:45:58
	 * @update
	 */
	List<PricePackageFeeStandardEntity> queryListByParam(
			PricePackageFeeStandardEntity params);

}
