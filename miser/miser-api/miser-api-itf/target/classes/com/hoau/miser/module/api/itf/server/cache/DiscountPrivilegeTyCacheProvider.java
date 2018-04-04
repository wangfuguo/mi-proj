package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountPrivilegeTyEntity;
import com.hoau.miser.module.api.itf.server.dao.DiscountPrivilegeTyDao;
import com.hoau.miser.module.util.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 越发越惠折扣
 * ClassName: DiscountPrivilegeTyCacheProvider
 * @author 廖文强
 * @date 2016年06月08日
 * @version V1.0
 */
@Component
public class DiscountPrivilegeTyCacheProvider implements ITTLCacheProvider<DiscountPrivilegeTyEntity> {

	@Resource
	DiscountPrivilegeTyDao discountPrivilegeTyDao;

	public DiscountPrivilegeTyEntity get(String keyStr) {
		DiscountPrivilegeTyEntity entity=null;
		if (StringUtil.isEmpty(keyStr)) {
			return null;
		}
		CacheKey cacheKey=CacheKey.convertToCacheKey(keyStr);
		if(cacheKey!=null&&cacheKey.length()>=2){
			DiscountPrivilegeTyEntity param=new DiscountPrivilegeTyEntity();
			param.setCustomerCode(cacheKey.get(0));
			param.setRecordMonth(DateUtils.convert(cacheKey.get(1)));
			List<DiscountPrivilegeTyEntity> list=discountPrivilegeTyDao.queryDiscountCustomerByParam(param);
			entity=(list!=null&&list.size()>0)?list.get(0):null;
		}
		return entity;
	}

}
