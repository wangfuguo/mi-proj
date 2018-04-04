package com.hoau.miser.module.sys.base.server.cache;

import com.hoau.hbdp.framework.cache.DefaultStrongRedisCache;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryEntity;


/**
 * @author：高佳
 * @create：2015年6月15日 上午10:48:39
 * @description：数据字典缓存
 */
public class DataDictionaryCache extends DefaultStrongRedisCache<String, DataDictionaryEntity>{
	public final static String DATA_DICTIONARY_UUID = DataDictionaryCache.class.getName();
	public String getUUID() {
		return DATA_DICTIONARY_UUID;
	}
}
