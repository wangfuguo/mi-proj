package com.hoau.miser.module.biz.base.api.server.service;

import com.hoau.miser.module.biz.base.api.shared.domain.OrgEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.OrgInfoEntity;

/**
 * 组织查询Service
 * ClassName: IOrgService 
 * @author 286330付于令
 * @date 2016年1月18日
 * @version V1.0
 */
public interface IOrgService {
	
	OrgEntity queryCurrentUserOrg(OrgEntity entity);
	
	/**
	 * add by dengyin 2016-5-3 11:05:31 价格时效查询需求 添加查询组织机构完整信息
	 * @param entity
	 * @return
	 */
	OrgInfoEntity queryOrgaInfoByEntity(OrgInfoEntity entity);

}
