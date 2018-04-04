package com.hoau.miser.module.sys.base.server.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.sys.base.api.shared.domain.ResourceEntity;
import com.hoau.miser.module.sys.base.server.dao.ResourceDao;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：高佳
 * @create：2015年6月8日 下午7:16:18
 * @description：
 */
@Component
public class ResourceCodeCacheProvider implements ITTLCacheProvider<ResourceEntity> {
	@Resource
	private ResourceDao resourceDao;
	@Override
	public ResourceEntity get(String key) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", key);
		map.put("resActive", MiserConstants.ACTIVE);
		map.put("parentActive", MiserConstants.ACTIVE);
		return resourceDao.queryResourceByCode(map);
	}

}
