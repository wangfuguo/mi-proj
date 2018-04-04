package com.hoau.miser.module.api.itf.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IPriceExtrafeeStandardTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceExtrafeeStandardEntity;
import com.hoau.miser.module.api.itf.server.cache.PriceExtrafeeStandardCache;
import com.hoau.miser.module.api.itf.server.dao.PriceExtrafeeStandardTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;

/**
 * 标准附加费service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午11:18:43
 */
@Service
public class PriceExtrafeeStandardTyService implements IPriceExtrafeeStandardTyService {

	@Resource
	private PriceExtrafeeStandardTyDao priceExtrafeeStandardTyDao;

	@Override
	public List<PriceExtrafeeStandardEntity> queryListByParam(PriceExtrafeeStandardEntity psv) {
		if (psv == null || StringUtil.isEmpty(psv.getTransTypeCode())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
		}
		if(StringUtil.isEmpty(psv.getType())){
			throw new ChargeException(ChargeException.SURCHARGEPRICE_EXTRAFEETYPE_ISNULL_EXCEPTION);
		}
		List<PriceExtrafeeStandardEntity> list = null;
		if (!psv.isHistory()) {
			@SuppressWarnings("unchecked")
			ICache<String, List<PriceExtrafeeStandardEntity>> cache = CacheManager
					.getInstance().getCache(PriceExtrafeeStandardCache.UUID);
			String keyStr = new CacheKey(psv.getTransTypeCode(), psv.getType()).generateKey();
			list = cache.get(keyStr);
		} else {
			list = priceExtrafeeStandardTyDao.queryListByParam(psv);
		}
		return list;
	}
}
