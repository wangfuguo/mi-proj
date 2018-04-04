package com.hoau.miser.module.api.itf.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IPricePackageFeeStandardTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PricePackageFeeStandardEntity;
import com.hoau.miser.module.api.itf.server.cache.PricePackageFeeStandardCache;
import com.hoau.miser.module.api.itf.server.dao.PricePackageFeeStandardTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;

/**
 *  标准包装费service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午11:18:43
 */
@Service
public class PricePackageFeeStandardTyService implements IPricePackageFeeStandardTyService {

	@Resource
	PricePackageFeeStandardTyDao pricePackageFeeStandardTyDao;

	@Override
	public List<PricePackageFeeStandardEntity> queryListByParam(
			PricePackageFeeStandardEntity psv) {
		if (psv == null || StringUtil.isEmpty(psv.getTransTypeCode())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
		}
		List<PricePackageFeeStandardEntity> list = null;
		if (!psv.isHistory()) {
			@SuppressWarnings("unchecked")
			ICache<String, List<PricePackageFeeStandardEntity>> cache = CacheManager
					.getInstance().getCache(PricePackageFeeStandardCache.UUID);
			if(StringUtil.isEmpty(psv.getCode())){
				String keyStr = new CacheKey(psv.getTransTypeCode()).generateKey();
				list = cache.get(keyStr);
			} else {
				String keyStr = new CacheKey(psv.getTransTypeCode(), psv.getCode()).generateKey();
				list = cache.get(keyStr);
			}
		} else {
			list = pricePackageFeeStandardTyDao.queryListByParam(psv);
		}
		return list;
	}
}
