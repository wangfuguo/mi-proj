package com.hoau.miser.module.sys.base.server.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.IBatchCacheProvider;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryEntity;
import com.hoau.miser.module.sys.base.server.dao.DataDictionaryValueDao;

/**
 * @author：高佳
 * @create：2015年6月15日 上午10:48:39
 * @description：数据字典缓存数据提供者
 */
@Component
public class DataDictionaryCacheProvider implements IBatchCacheProvider<String, DataDictionaryEntity> {
	@Resource
	private DataDictionaryValueDao dataDictionaryValueDao;
	@Override
	public Date getLastModifyTime() {
		Long version = dataDictionaryValueDao.queryLastVersionNo();
		if(version == null){
			version = 0l;
		}
		return new Date(version);
	}

	@Override
	public Map<String, DataDictionaryEntity> get() {
		Map<String, DataDictionaryEntity> map = new HashMap<String, DataDictionaryEntity>();
		List<DataDictionaryEntity> dataDictionarys = dataDictionaryValueDao.queryDataForCache();
		Long version = dataDictionaryValueDao.queryLastVersionNo();
		if(version == null){
			version = 0l;
		}
		//数据字典版本号
		DataDictionaryEntity d = new DataDictionaryEntity();
		d.setVersionNo(version);
		map.put("version", d);
		for (DataDictionaryEntity dataDictionary : dataDictionarys) {
			map.put(dataDictionary.getTermsCode(), dataDictionary);
		}
		return map;
	}

}
