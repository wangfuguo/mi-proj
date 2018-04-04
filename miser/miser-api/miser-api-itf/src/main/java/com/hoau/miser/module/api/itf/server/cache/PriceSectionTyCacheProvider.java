package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.api.itf.server.dao.PriceEventTyDao;
import com.hoau.miser.module.api.itf.server.dao.PriceSectionTyDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 优惠分段缓存
 * ClassName: PriceSectionTyCacheProvider
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0
 */
@Component
public class PriceSectionTyCacheProvider implements ITTLCacheProvider<List<PriceSectionSubEntity>> {

	@Resource
	PriceSectionTyDao priceSectionTyDao;

	public List<PriceSectionSubEntity> get(String sectionCode) {
		if (StringUtil.isEmpty(sectionCode)) {
			return null;
		}
		return priceSectionTyDao.querySectionDetailByCode(sectionCode);
	}

}
