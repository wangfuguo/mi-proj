package com.hoau.miser.module.sys.base.server.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.sys.base.api.shared.domain.RoleEntity;
import com.hoau.miser.module.sys.base.server.dao.RoleDao;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：高佳
 * @create：2015年6月12日 下午1:53:56
 * @description：角色缓存数据提供者
 */
@Component
public class RoleCacheProvider implements ITTLCacheProvider<RoleEntity>{
	@Resource
	private RoleDao roleDao;
	@Override
	public RoleEntity get(String code) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("roleActive", MiserConstants.ACTIVE);
		map.put("resActive", MiserConstants.ACTIVE);
		map.put("rrActive", MiserConstants.ACTIVE);
		return roleDao.queryRoleResourceByCode(map);
	}

}
