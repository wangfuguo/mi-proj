package com.hoau.miser.module.sys.base.server.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.sys.base.api.shared.domain.SalesDepartmentEntity;
import com.hoau.miser.module.sys.base.server.dao.OrgAdministrativeInfoDao;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author 张贞献
 * @date 2015-7-24
 * @description：
 */
@Component
public class SalesDepartmentCacheProvider implements ITTLCacheProvider<SalesDepartmentEntity> {
	@Resource
	private OrgAdministrativeInfoDao orgAdministrativeInfoDao;
	@Override
	public SalesDepartmentEntity get(String key) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", key);
		map.put("active",MiserConstants.ACTIVE);
		return orgAdministrativeInfoDao.querySalesDepartmentEntityByCode(map);
	}

}
