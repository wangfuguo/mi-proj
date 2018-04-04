package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.itf.api.shared.domain.PricePackageFeePcEntity;

/**
 * 标准价格城市包装费dao
 * 
 * @author 蒋落琛
 * @date 2016-6-15下午5:44:55
 */
@Repository
public interface PricePackageFeePcTyDao {

	/**
	 * 查询标准包装费集合
	 * 
	 * @param params
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-15下午5:44:44
	 * @update
	 */
	List<PricePackageFeePcEntity> queryListByParam(
			PricePackageFeePcEntity params);

}
