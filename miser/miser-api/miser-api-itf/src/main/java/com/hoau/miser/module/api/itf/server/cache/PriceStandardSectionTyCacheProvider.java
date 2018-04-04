package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardSectionTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.dao.PriceStandardSectionTyDao;
import com.hoau.miser.module.api.itf.server.dao.PriceStandardTyDao;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;
import com.hoau.miser.module.api.itf.server.param.PriceSectionQueryParamInSide;
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
public class PriceStandardSectionTyCacheProvider implements ITTLCacheProvider<PriceStandardSectionTyEntity> {

	@Resource
	private PriceStandardSectionTyDao priceStandardSectionTyDao;
	public PriceStandardSectionTyEntity get(String keyStr) {
		CacheKey cacheKey=CacheKey.convertToCacheKey(keyStr);
		PriceStandardSectionTyEntity entity=null;
		if(cacheKey!=null&&cacheKey.length()>=3){
			PriceQueryParam queryParam=new PriceQueryParam();
			queryParam.setTransTypeCode(cacheKey.get(2));
			queryParam.setBillTime(new Date());
			PriceSectionQueryParamInSide paramInSide = new PriceSectionQueryParamInSide();
			paramInSide.setBaseTyParam(queryParam);
			paramInSide.setSendPriceCityCode(cacheKey.get(0));
			paramInSide.setArrivePriceCityCode(cacheKey.get(1));
			entity = priceStandardSectionTyDao.queryByPriceSectionQueryParamInSide(paramInSide);
		}
		return entity;
	}
}
