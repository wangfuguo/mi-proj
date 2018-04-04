package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventTyQueryEntity;
import com.hoau.miser.module.api.itf.server.dao.PriceEventTyDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 运输类型缓存
 * ClassName: PriceEventDiscountDetailTyCacheProvider
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class PriceEventDiscountDetailTyCacheProvider implements ITTLCacheProvider<List<PriceEventDiscountSubEntity>> {

	@Resource
	PriceEventTyDao priceEventTyDao;

	public List<PriceEventDiscountSubEntity> get(String param) {
		if (StringUtil.isEmpty(param)) {
			return null;
		}
		CacheKey keys = CacheKey.convertToCacheKey(param);
		if (keys == null || keys.length() == 0 || keys.length() > 2) {
			return null;
		}
		String eventCode = keys.get(0);
		String transportType = keys.length() == 2 ? keys.get(1) : "";
		return priceEventTyDao.queryEventDetails(eventCode, transportType);
	}

}
