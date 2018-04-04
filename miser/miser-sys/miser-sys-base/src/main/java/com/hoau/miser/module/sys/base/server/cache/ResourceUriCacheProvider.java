package com.hoau.miser.module.sys.base.server.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.hbdp.framework.entity.IFunction;
import com.hoau.hbdp.framework.server.context.AppContext;
import com.hoau.miser.module.sys.base.api.shared.domain.ResourceEntity;
import com.hoau.miser.module.sys.base.server.dao.ResourceDao;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：高佳
 * @create：2015年6月10日 下午4:46:20
 * @description：资源权限uri缓存数据提供者
 */
@Component
public class ResourceUriCacheProvider implements ITTLCacheProvider<IFunction>{
	@Resource
	private ResourceDao resourceDao;
	@Override
	public IFunction get(String uri) {
		if(uri.equalsIgnoreCase("WEB")){
			return queryResourceRoot("WEB");
		}
		String contextPath = AppContext.getAppContext().getContextPath();
		String[] uris = new String[3];
		int index = uri.indexOf("?");
		if(uri.indexOf("?")!=-1){
			uri = uri.substring(0, index);
		}
		uris[0] = uri;
		uris[1] = contextPath + uri;
		StringBuilder sb = new StringBuilder();
		sb.append(contextPath);
		sb.append("/#!");
		sb.append(uri.substring(1));
		uris[2] = sb.toString();
		List<ResourceEntity> resources  = this.queryResourceBatchByUri(uris);
		if (resources != null && resources.size() > 0) {
			return resources.get(0);
		} else {
			return null;
		}
	}
	
	private ResourceEntity queryResourceRoot(String type){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("active", MiserConstants.ACTIVE);
		map.put("belongSystemType", type);
		return resourceDao.queryResourceRoot(map);
	}
	
	private List<ResourceEntity> queryResourceBatchByUri(String[] uris){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uris", uris);
		map.put("sign", "?");
		map.put("resActive", MiserConstants.ACTIVE);
		map.put("parentActive", MiserConstants.ACTIVE);
		return resourceDao.queryResourceBatchByUri(map);
	}
}
