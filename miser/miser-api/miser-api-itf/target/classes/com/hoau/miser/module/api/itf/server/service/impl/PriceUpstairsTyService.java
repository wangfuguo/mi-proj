package com.hoau.miser.module.api.itf.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.hoau.miser.module.api.itf.server.cache.PriceUpstairsTyCache;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IPriceUpstairsTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceUpstairsEntity;
import com.hoau.miser.module.api.itf.server.cache.PriceSectionTyCache;
import com.hoau.miser.module.api.itf.server.dao.PriceSectionTyDao;
import com.hoau.miser.module.api.itf.server.dao.PriceUpstairsTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.util.CollectionUtils;

/**
 *  上楼费service
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午11:18:43
 */
@Service
public class PriceUpstairsTyService implements IPriceUpstairsTyService {

	@Resource
	PriceUpstairsTyDao priceUpstairsTyDao;

	@Resource
	PriceSectionTyDao priceSectionTyDao;


	@Override
	public PriceUpstairsEntity queryUpstairsByParam(
			PriceUpstairsEntity psv) {
		if (psv == null || StringUtil.isEmpty(psv.getTransportType())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
		}
		if (StringUtil.isEmpty(psv.getType())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_UPSTAIRSTYPE_ISNULL_EXCEPTION);
		}
		List<PriceUpstairsEntity> list = null;
		if (!psv.isHistory()) {
			@SuppressWarnings("unchecked")
			ICache<String, List<PriceUpstairsEntity>> cache = CacheManager
					.getInstance().getCache(PriceUpstairsTyCache.UUID);
			if (cache != null) {
				String keyStr = new CacheKey(psv.getTransportType(), psv.getType()).generateKey();
				list = cache.get(keyStr);
			} else {
				psv.setBillTime(new Date());
				list = priceUpstairsTyDao.queryUpstairsPrices(psv);
			}
		} else {
			list = priceUpstairsTyDao.queryUpstairsPrices(psv);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		//设置优惠分段的明细信息
		ICache<String, List<PriceSectionSubEntity>> cache = CacheManager.getInstance().getCache(PriceSectionTyCache.UUID);
		PriceUpstairsEntity upstairs = list.get(0);
		if (!StringUtil.isEmpty(upstairs.getSectionItemCode())) {
			if (cache != null) {
				upstairs.setSectionSubEntities(cache.get(upstairs.getSectionItemCode()));
			} else {
				upstairs.setSectionSubEntities(priceSectionTyDao.querySectionDetailByCode(upstairs.getSectionItemCode()));
			}
		}
		return upstairs;
	}
}
