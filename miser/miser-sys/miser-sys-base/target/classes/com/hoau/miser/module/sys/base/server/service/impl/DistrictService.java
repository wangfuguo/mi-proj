package com.hoau.miser.module.sys.base.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.miser.module.sys.base.api.server.service.IDistrictService;
import com.hoau.miser.module.sys.base.api.shared.domain.DistrictEntity;
import com.hoau.miser.module.sys.base.server.cache.DistrictCache;
import com.hoau.miser.module.sys.base.server.cache.DistrictCacheProvider;
import com.hoau.miser.module.sys.base.server.dao.DistrictDao;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：高佳
 * @create：2015年6月3日 下午2:51:00
 * @description：省市区service实现类
 */
@Service
public class DistrictService implements IDistrictService{
	@Resource
	private DistrictDao districtDao;
	
	/**
	 * 获取缓存实例
	 * @return
	 * @author 高佳
	 * @date 2015年6月3日
	 * @update 
	 */
	@SuppressWarnings("unchecked")
	private ICache<String, List<DistrictEntity>> getCache(){
		return CacheManager.getInstance().getCache(DistrictCache.DISTRICT_UUID);
	}

	@Override
	public List<DistrictEntity> queryAllProvince() {
		//从缓存中获取省份信息
		List<DistrictEntity> provinces = getCache().get(DistrictCacheProvider.KEY_PROVINCES);
		return provinces;
	}
	
	public List<DistrictEntity> queryDistrictByEntity(DistrictEntity district){
		return districtDao.queryDistrictByEntity(district);
	}

	@Override
	public List<DistrictEntity> queryDistrictByEntity(DistrictEntity district,
			int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return districtDao.queryDistrictByEntity(district,rowBounds);
	}

	@Override
	public List<DistrictEntity> queryAllNation(int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		DistrictEntity district = new DistrictEntity();
		district.setDistrictType("COUNTRY");
		return districtDao.queryDistrictByEntity(district,rowBounds);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DistrictEntity> queryDistrictInfoEntityInfoByParentCode(
			String parentDistrictCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parentDistrictCode", parentDistrictCode);
		map.put("parentActive", MiserConstants.ACTIVE);
		map.put("childActive", MiserConstants.ACTIVE);
		List<DistrictEntity> district = districtDao.queryDistrictInfoByParentCode(map);
		return district;
	}






	@Override
	public List<DistrictEntity> queryDistrictInfoByName(String districtName) {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("districtName", districtName);
		map.put("active", MiserConstants.ACTIVE);
		return districtDao.queryDistrictInfoByName(map);
	}
	
	@Override
	public DistrictEntity queryDistrictInfoByCode (String distictCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("distictCode", distictCode);
		map.put("active", MiserConstants.ACTIVE);
		return districtDao.queryDistrictInfoByCode(map);
	}

	@Override
	public Long queryDistrictByCode(String code) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("active", MiserConstants.ACTIVE);
		return districtDao.queryDistrictByCode(map);
	}
	
}
