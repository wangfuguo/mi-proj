package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.miser.module.api.itf.api.server.IBseCustomerTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.BseCustomerTyEntity;
import com.hoau.miser.module.api.itf.server.cache.BseCustomerTyCache;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BseCustomerTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/8 16:33
 */
@Service
public class BseCustomerTyService implements IBseCustomerTyService {
    @Override
    public BseCustomerTyEntity queryBseCustomerByCustNo(String custNo) {
        if(StringUtil.isEmpty(custNo))return null;
        ICache<String,BseCustomerTyEntity> cache= CacheManager.getInstance().getCache(BseCustomerTyCache.UUID);
        return cache.get(custNo);
    }
}
