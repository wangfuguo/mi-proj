package com.hoau.miser.module.sys.base.server.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.sys.base.api.shared.domain.ResourceEntity;
import com.hoau.miser.module.sys.base.server.dao.ResourceDao;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 功能权限缓存数据提供对象
 * @author 高佳
 * @date 2015年6月8日
 */
@Component
public class ResourceMenuCacheProvider implements ITTLCacheProvider<List<ResourceEntity>>{
	@Resource
	private ResourceDao resourceDao;

	@Override
	public List<ResourceEntity> get(String key) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parentCode", key);
		map.put("resActive", MiserConstants.ACTIVE);
		map.put("parentActive", MiserConstants.ACTIVE);
		List<ResourceEntity> resMenus = resourceDao.queryDirectChildResourceByCode(map);
		return resMenus;
	}
}
