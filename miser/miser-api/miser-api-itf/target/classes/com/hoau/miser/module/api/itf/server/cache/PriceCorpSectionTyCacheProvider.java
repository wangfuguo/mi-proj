package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpSectionTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.dao.PriceCorpSectionTyDao;
import com.hoau.miser.module.api.itf.server.param.PriceSectionQueryParamInSide;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description: 提货费缓存
 * @author 廖文强
 * @date 2016年06月07日
 * @version V1.0
 */
@Component
public class PriceCorpSectionTyCacheProvider implements ITTLCacheProvider<PriceCorpSectionTyEntity> {

	@Resource
	private PriceCorpSectionTyDao priceCorpSectionTyDao;

	public PriceCorpSectionTyEntity get(String param) {
		// 起运地，到达价格城市，运输类型
		CacheKey key = CacheKey.convertToCacheKey(param);
		if(key==null&&key.length()!=3) {
			return null;
		}
		PriceQueryParam baseTyParam=new PriceQueryParam();
		baseTyParam.setOriginCode(key.get(0));
		baseTyParam.setBillTime(new Date());
		baseTyParam.setTransTypeCode(key.get(2));
		PriceSectionQueryParamInSide paramInSide = new PriceSectionQueryParamInSide();
		paramInSide.setBaseTyParam(baseTyParam);
		paramInSide.setArrivePriceCityCode(key.get(1));
		return priceCorpSectionTyDao.queryPriceSectionQueryParamInSide(paramInSide);
	}
}
