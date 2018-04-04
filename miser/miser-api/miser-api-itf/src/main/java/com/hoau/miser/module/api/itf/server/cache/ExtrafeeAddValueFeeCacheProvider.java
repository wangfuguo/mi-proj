package com.hoau.miser.module.api.itf.server.cache;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.ExtrafeeAddValueFeeEntity;
import com.hoau.miser.module.api.itf.server.dao.ExtrafeeAddValueFeeTyDao;

/**
 * 特服费缓存
 * 
 * @author 蒋落琛
 * @date 2016-6-16上午9:52:58
 */
@Component
public class ExtrafeeAddValueFeeCacheProvider implements
		ITTLCacheProvider<List<ExtrafeeAddValueFeeEntity>> {

	@Resource
	ExtrafeeAddValueFeeTyDao extrafeeAddValueFeeTyDao;

	public List<ExtrafeeAddValueFeeEntity> get(String keyStr) {
		List<ExtrafeeAddValueFeeEntity> list = null;
		CacheKey cacheKey = CacheKey.convertToCacheKey(keyStr);
		if (cacheKey != null && cacheKey.length() > 0) {
			ExtrafeeAddValueFeeEntity psv = new ExtrafeeAddValueFeeEntity();
			if(cacheKey.length() == 1){
				psv.setTransTypeCode(cacheKey.get(0));
			} else if(cacheKey.length() == 2){
				psv.setTransTypeCode(cacheKey.get(0));
				psv.setCode(cacheKey.get(1));
			}
			// 缓存只查当前时间的数据
			psv.setBillTime(new Date());
			list = extrafeeAddValueFeeTyDao
					.queryListByParam(psv);
		}
		return list;
	}

}
