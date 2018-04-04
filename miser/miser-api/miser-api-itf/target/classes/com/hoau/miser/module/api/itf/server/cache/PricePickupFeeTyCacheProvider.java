package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.PricePickupFeeTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.dao.PricePickupFeeTyDao;
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
public class PricePickupFeeTyCacheProvider implements ITTLCacheProvider<PricePickupFeeTyEntity> {
	@Resource
	private PricePickupFeeTyDao pricePickupFeeTyDao;
	public PricePickupFeeTyEntity get(String keyStr) {
		PricePickupFeeTyEntity entity=null;
		//出发价格城市，运输类型
		CacheKey cacheKey= CacheKey.convertToCacheKey(keyStr);
		if( cacheKey!=null &&cacheKey.length()>=2){
			PriceQueryParamInSide paramInSide=new PriceQueryParamInSide();
			paramInSide.setSendPriceCityCode(cacheKey.get(0));
			PriceQueryParam queryParam=new PriceQueryParam();
			queryParam.setBillTime(new Date());
			queryParam.setTransTypeCode(cacheKey.get(1));
			paramInSide.setBaseTyParam(queryParam);
			List<PricePickupFeeTyEntity> list =pricePickupFeeTyDao.queryPricePickupFeeByParamInSide(paramInSide);
			entity=(list != null && list.size() > 0) ? list.get(0) : null;
		}
		return entity;
	}
}
