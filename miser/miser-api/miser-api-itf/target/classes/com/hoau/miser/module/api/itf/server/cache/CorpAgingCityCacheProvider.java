package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpAgingCityService;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import com.hoau.miser.module.api.itf.server.dao.CorpAgingCityDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description 运输类型缓存
 * ClassName: CorpAgingCityCacheProvider
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class CorpAgingCityCacheProvider implements ITTLCacheProvider<CorpAgingCityEntity> {

	@Resource
	CorpAgingCityDao corpAgingCityDao;

	public CorpAgingCityEntity get(String orgCode) {
		if (StringUtil.isEmpty(orgCode)) {
			return null;
		}
		return corpAgingCityDao.queryAgingCityByOrgCode(orgCode);
	}

}
