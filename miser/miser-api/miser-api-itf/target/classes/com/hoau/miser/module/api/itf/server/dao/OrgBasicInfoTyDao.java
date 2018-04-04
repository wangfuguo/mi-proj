package com.hoau.miser.module.api.itf.server.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.itf.api.shared.domain.OrgBasicInfoTyEntity;

/**
 * 组织信息dao
 * 
 * @author 蒋落琛
 * @date 2016-7-13上午10:39:54
 */
@Repository
public interface OrgBasicInfoTyDao {

	/**
	 * 根据组织编码查询组织
	 * 
	 * @param orgCode
	 * @return
	 * @author 蒋落琛
	 * @date 2016-7-13上午10:40:01
	 * @update
	 */
	List<OrgBasicInfoTyEntity> queryOrgBasicInfoByCode(String orgCode);
}
