package com.hoau.miser.module.sys.base.server.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.IBatchCacheProvider;
import com.hoau.miser.module.sys.base.api.shared.domain.DistrictEntity;
import com.hoau.miser.module.sys.base.server.dao.DistrictDao;

/**
 * @author：高佳
 * @create：2015年6月3日 下午2:55:10
 * @description：行政区域缓存数据provider
 */
@Component
public class DistrictCacheProvider implements IBatchCacheProvider<String, Object> {
	public final static String KEY_PROVINCES = "provinces";
	@Resource
	private DistrictDao districtDao;
	
	@Override
	public Date getLastModifyTime() {
		Long version = districtDao.getLastUpdateTime();
		if(version == null){
			version = 0l;
		}
		return new Date(version);
	}

	@Override
	public Map<String, Object> get() {
		Map<String,Object> map = new HashMap<String, Object>();
		DistrictEntity district = new DistrictEntity();
		district.setDistrictType("PROVINCE");
		List<DistrictEntity> provinces = districtDao.queryDistrictByEntity(district);
		map.put(KEY_PROVINCES, provinces);
		return map;
	}

}
