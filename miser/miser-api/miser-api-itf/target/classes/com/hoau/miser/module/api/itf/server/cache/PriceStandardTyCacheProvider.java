package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.dao.PriceStandardTyDao;
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
public class PriceStandardTyCacheProvider implements ITTLCacheProvider<PriceStandardTyEntity> {

	@Resource
	private PriceStandardTyDao priceStandardTyDao;
	public PriceStandardTyEntity get(String keyStr) {
		CacheKey cacheKey=CacheKey.convertToCacheKey(keyStr);
		PriceStandardTyEntity entity=null;
		if(cacheKey!=null&&cacheKey.length()>=3){
			PriceQueryParam queryParam=new PriceQueryParam();
			queryParam.setTransTypeCode(cacheKey.get(2));
			queryParam.setBillTime(new Date());
			PriceQueryParamInSide priceQueryParamInSide = new PriceQueryParamInSide();
			priceQueryParamInSide.setBaseTyParam(queryParam);
			priceQueryParamInSide.setSendPriceCityCode(cacheKey.get(0));
			priceQueryParamInSide.setArrivePriceCityCode(cacheKey.get(1));
			List<PriceStandardTyEntity> lists = priceStandardTyDao.queryPriceStandardTyByParamInSide(priceQueryParamInSide);
			entity = (lists != null && lists.size() > 0) ? lists.get(0) : null;
		}
		return entity;
	}
}
