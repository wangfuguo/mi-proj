package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.domain.BseCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.OutLineTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.dao.BseCustomerTyDao;
import com.hoau.miser.module.api.itf.server.dao.OutLineTyDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description 外发偏线价格
 * ClassName: OutLineTyCacheProvider
 * @author 廖文强
 * @date 2016年06月13日
 * @version V1.0
 */
@Component
public class OutLineTyCacheProvider implements ITTLCacheProvider<OutLineTyEntity> {

	@Resource
	OutLineTyDao outLineTyDao;

	public OutLineTyEntity get(String keyStr) {
		if (StringUtil.isEmpty(keyStr)) {
			return null;
		}
		return outLineTyDao.queryOutLineByDestCode(keyStr);
	}

}
