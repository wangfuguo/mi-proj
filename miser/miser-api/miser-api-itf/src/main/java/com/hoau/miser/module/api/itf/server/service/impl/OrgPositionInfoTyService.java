package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgBasicInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.server.cache.OrgBasicInfoTyCache;

/**
 * 组织信息SERVICE
 *
 * @author 蒋落琛
 * @date 2016-7-13上午11:13:40
 */
@Service
public class OrgPositionInfoTyService implements IOrgPositionInfoTyService {

	public OrgPositionInfoTyEntity queryDistrictByOrgCode(String orgCode) {
		OrgPositionInfoTyEntity orgPositionInfoTyEntity = null;
		if (StringUtil.isEmpty(orgCode)) {
			throw new ChargeException("组织编码不能为空！");
		}
		@SuppressWarnings("unchecked")
		ICache<String, OrgBasicInfoTyEntity> cache = CacheManager.getInstance()
				.getCache(OrgBasicInfoTyCache.CACHE_UUID);
		OrgBasicInfoTyEntity entity = cache.get(orgCode);
		if(entity != null){
			orgPositionInfoTyEntity = new OrgPositionInfoTyEntity();
			orgPositionInfoTyEntity.setOrgName(entity.getCode());
			orgPositionInfoTyEntity.setOrgName(entity.getName());
			orgPositionInfoTyEntity.setLogisticCode(entity.getLogisticCode());
			orgPositionInfoTyEntity.setProvinceCode(entity.getProvinceCode());
			orgPositionInfoTyEntity.setCityCode(entity.getCityCode());
			orgPositionInfoTyEntity.setCountyCode(entity.getCountyCode());
			orgPositionInfoTyEntity.setProvinceName(entity.getProvinceName());
			orgPositionInfoTyEntity.setCityName(entity.getCityName());
			orgPositionInfoTyEntity.setCountyName(entity.getCountyName());
		}
		return orgPositionInfoTyEntity;
	}
}
