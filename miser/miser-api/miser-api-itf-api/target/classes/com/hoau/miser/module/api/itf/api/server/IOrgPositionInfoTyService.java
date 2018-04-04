package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;

/**
 * 组织信息
 * 
 * @author 蒋落琛
 * @date 2016-7-13上午11:10:54
 */
public interface IOrgPositionInfoTyService {

	/**
	 * 根据组织编码查询组织信息
	 * 
	 * @param orgCode
	 * @return
	 * @author 蒋落琛
	 * @date 2016-7-13上午11:10:26
	 * @update
	 */
	public OrgPositionInfoTyEntity queryDistrictByOrgCode(String orgCode);
}
