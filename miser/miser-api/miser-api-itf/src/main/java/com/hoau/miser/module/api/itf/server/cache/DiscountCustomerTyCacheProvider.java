package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.dao.DiscountCustomerTyDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description 客户折扣
 * ClassName: DiscountCustomerTyCacheProvider
 * @author 廖文强
 * @date 2016年06月12日
 * @version V1.0
 */
@Component
public class DiscountCustomerTyCacheProvider implements ITTLCacheProvider<DiscountCustomerTyEntity> {

	@Resource
	DiscountCustomerTyDao discountCustomerTyDao;

	public DiscountCustomerTyEntity get(String keyStr) {
		DiscountCustomerTyEntity entity=null;
		// 客户编号，运输类型
		CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
		if (cacheKey != null && cacheKey.length() >= 2) {
			PriceQueryParam queryEnity=new PriceQueryParam();
			queryEnity.setBillTime(new Date());
			queryEnity.setCustNo(cacheKey.get(0));
			queryEnity.setTransTypeCode(cacheKey.get(1));
			List<DiscountCustomerTyEntity> list=discountCustomerTyDao.queryListByParam(queryEnity);
			entity=(list!=null&&list.size()>0)?list.get(0):null;
		}
		return entity;
	}

}
