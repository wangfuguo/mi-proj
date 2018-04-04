package com.hoau.miser.module.api.itf.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICityTypeTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CityTypeEntity;
import com.hoau.miser.module.api.itf.server.cache.CityTypeTyCache;
import com.hoau.miser.module.api.itf.server.dao.CityTypeTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;

/**
 *  城市类别service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午11:18:43
 */
@Service
public class CityTypeTyService implements ICityTypeTyService {

	@Resource
	CityTypeTyDao cityTypeTyDao;

	@Override
	public List<CityTypeEntity> queryCityTypeByEntity(
			CityTypeEntity cityTypeEntity) {
		if (cityTypeEntity == null || StringUtil.isEmpty(cityTypeEntity.getProvinceCode())) {
			throw new ChargeException(ChargeException.CHARGE_CITYTYPE_PROVINCECODE_ISNULL_EXCEPTION);
		}
		if (StringUtil.isEmpty(cityTypeEntity.getCityCode())) {
			throw new ChargeException(ChargeException.CHARGE_CITYTYPE_CITYCODE_ISNULL_EXCEPTION);
		}
		if (StringUtil.isEmpty(cityTypeEntity.getAreaCode())) {
			throw new ChargeException(ChargeException.CHARGE_CITYTYPE_AREACODE_ISNULL_EXCEPTION);
		}
		@SuppressWarnings("unchecked")
		ICache<String, List<CityTypeEntity>> cache = CacheManager
				.getInstance().getCache(CityTypeTyCache.UUID);
		String keyStr = new CacheKey(cityTypeEntity.getProvinceCode(), cityTypeEntity.getCityCode(), cityTypeEntity.getAreaCode()).generateKey();
		List<CityTypeEntity> list = cache.get(keyStr);
		return list;
	}
}
