package com.hoau.miser.module.api.itf.server.cache;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.DefaultTTLRedisCache;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgBasicInfoTyEntity;

/**
 * 组织信息缓存
 * 
 * @author 蒋落琛
 * @date 2016-7-13上午10:45:40
 */
@Component
public class OrgBasicInfoTyCache extends
		DefaultTTLRedisCache<OrgBasicInfoTyEntity> {

	public static final String CACHE_UUID = "OrgBasicInfoTyCache";

	@Override
	public String getUUID() {
		return CACHE_UUID;
	}

}
