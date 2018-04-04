package com.hoau.miser.module.api.itf.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IPricePackageFeePcTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PricePackageFeePcEntity;
import com.hoau.miser.module.api.itf.server.cache.PricePackageFeePcCache;
import com.hoau.miser.module.api.itf.server.dao.PricePackageFeePcTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;

/**
 * 标准价格城市包装费service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午11:18:43
 */
@Service
public class PricePackageFeePcTyService implements IPricePackageFeePcTyService {

	@Resource
	private PricePackageFeePcTyDao pricePackageFeePcTyDao;

	@Override
	public List<PricePackageFeePcEntity> queryListByParam(PricePackageFeePcEntity psv) {
		if (psv == null || StringUtil.isEmpty(psv.getTransTypeCode())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
		}
		if(StringUtil.isEmpty(psv.getPriceCity())){
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PRICECITY_ISNULL_EXCEPTION);
		}
		List<PricePackageFeePcEntity> list = null;
		if (!psv.isHistory()) {
			@SuppressWarnings("unchecked")
			ICache<String, List<PricePackageFeePcEntity>> cache = CacheManager
					.getInstance().getCache(PricePackageFeePcCache.UUID);
			if(StringUtil.isEmpty(psv.getCode())){
				String keyStr = new CacheKey(psv.getTransTypeCode(), psv.getPriceCity()).generateKey();
				list = cache.get(keyStr);
			} else {
				String keyStr = new CacheKey(psv.getTransTypeCode(), psv.getPriceCity(), psv.getCode()).generateKey();
				list = cache.get(keyStr);
			}
		} else {
			list = pricePackageFeePcTyDao.queryListByParam(psv);
		}
		return list;
	}
}
