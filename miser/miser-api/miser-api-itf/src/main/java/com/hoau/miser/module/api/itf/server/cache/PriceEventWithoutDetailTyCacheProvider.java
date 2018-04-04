package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventTyQueryEntity;
import com.hoau.miser.module.api.itf.server.dao.CorpAgingCityDao;
import com.hoau.miser.module.api.itf.server.dao.PriceEventTyDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 运输类型缓存
 * ClassName: PriceEventWithoutDetailTyCacheProvider
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class PriceEventWithoutDetailTyCacheProvider implements ITTLCacheProvider<List<PriceEventEntity>> {

	@Resource
	PriceEventTyDao priceEventTyDao;

	public List<PriceEventEntity> get(String param) {
		if (StringUtil.isEmpty(param)) {
			return null;
		}
		CacheKey keys = CacheKey.convertToCacheKey(param);
		if (keys == null || keys.length() != 6) {
			return null;
		}
		PriceEventTyQueryEntity queryEntity = new PriceEventTyQueryEntity();
		queryEntity.setOrgCode(keys.get(0));
		queryEntity.setOrgType(keys.get(1));
		queryEntity.setOrgCode(keys.get(2));
		queryEntity.setSendPriceCity(keys.get(3));
		queryEntity.setArrivalPriceCity(keys.get(4));
		queryEntity.setCustomerCode(keys.get(5));
		return priceEventTyDao.queryEventsWithoutDetail(queryEntity);
	}

}
