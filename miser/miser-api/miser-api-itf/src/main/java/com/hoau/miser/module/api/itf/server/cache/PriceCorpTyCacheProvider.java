package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.dao.PriceCorpTyDao;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description: 提货费缓存
 * @author 廖文强
 * @date 2016年06月07日
 * @version V1.0
 */
@Component
public class PriceCorpTyCacheProvider implements ITTLCacheProvider<PriceCorpTyEntity> {

	@Resource
	private PriceCorpTyDao priceCorpTyDao;

	public PriceCorpTyEntity get(String param) {
		// 起运地，到达价格城市，运输类型
		CacheKey key = CacheKey.convertToCacheKey(param);
		if(key==null&&key.length()!=3) {
			return null;
		}
		PriceQueryParam baseTyParam=new PriceQueryParam();
		baseTyParam.setOriginCode(key.get(0));
		baseTyParam.setBillTime(new Date());
		baseTyParam.setTransTypeCode(key.get(2));
		PriceQueryParamInSide priceQueryParamInSide = new PriceQueryParamInSide();
		priceQueryParamInSide.setBaseTyParam(baseTyParam);
		priceQueryParamInSide.setArrivePriceCityCode(key.get(1));
		List<PriceCorpTyEntity> list = priceCorpTyDao.queryPriceCorpTyByParamInSide(priceQueryParamInSide);
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}
}
