package com.hoau.miser.module.api.itf.server.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgBasicInfoTyEntity;
import com.hoau.miser.module.api.itf.server.dao.OrgBasicInfoTyDao;

/**
 * 组织信息缓存
 * 
 * @author 蒋落琛
 * @date 2016-7-13上午10:46:36
 */
@Component
public class OrgBasicInfoTyCacheProvider implements
		ITTLCacheProvider<OrgBasicInfoTyEntity> {
	@Resource
	OrgBasicInfoTyDao orgBasicInfoTyDao;

	public OrgBasicInfoTyEntity get(String param) {
		List<OrgBasicInfoTyEntity> orgList = orgBasicInfoTyDao
				.queryOrgBasicInfoByCode(param);
		if (orgList != null && orgList.size() > 0) {
			return orgList.get(0);
		}
		return null;
	}

}
