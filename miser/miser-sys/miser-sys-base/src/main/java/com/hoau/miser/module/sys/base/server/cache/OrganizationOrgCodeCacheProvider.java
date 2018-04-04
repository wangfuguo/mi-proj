package com.hoau.miser.module.sys.base.server.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.sys.base.api.shared.domain.OrgAdministrativeInfoEntity;
import com.hoau.miser.module.sys.base.server.dao.OrgAdministrativeInfoDao;
import com.hoau.miser.module.util.define.MiserConstants;


/**
 * @author：高佳
 * @create：2015年6月12日 下午1:00:09
 * @description：组织编码缓存数据提供者
 */
@Component
public class OrganizationOrgCodeCacheProvider implements ITTLCacheProvider<OrgAdministrativeInfoEntity>{
	@Resource
	private OrgAdministrativeInfoDao orgAdministrativeInfoDao;
	@Override
	public OrgAdministrativeInfoEntity get(String code) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("active",MiserConstants.ACTIVE);
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoDao.queryOrgAdministrativeInfoByCode(map);
		return org;
	}

}
