package com.hoau.miser.module.biz.base.server.dao;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.base.api.shared.domain.OrgEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.OrgInfoEntity;

/**
 * 组织DAO
 * ClassName: OrgDao 
 * @author 286330付于令
 * @date 2016年1月18日
 * @version V1.0
 */
@Repository
public interface OrgDao {
	OrgEntity queryOrgByUserName(OrgEntity entity);
	
	/**
	 * add by dengyin 2016-5-3 11:05:31 价格时效查询需求 添加查询组织机构完整信息
	 * @param entity
	 * @return
	 */
	OrgInfoEntity queryOrgaInfoByEntity(OrgInfoEntity entity);
}
