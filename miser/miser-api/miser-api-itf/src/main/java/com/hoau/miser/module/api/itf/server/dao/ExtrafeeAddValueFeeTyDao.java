package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.itf.api.shared.domain.ExtrafeeAddValueFeeEntity;

/**
 * 特服费Dao
 * 
 * @author 蒋落琛
 * @date 2016-6-15下午3:19:00
 */
@Repository
public interface ExtrafeeAddValueFeeTyDao {

	/**
	 * 查询增值费列表
	 * 
	 * @param psv
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-15下午3:18:51
	 * @update
	 */
	public List<ExtrafeeAddValueFeeEntity> queryListByParam(
			ExtrafeeAddValueFeeEntity psv);
}
