package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IDiscountPrivilegeTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountPrivilegeTyEntity;
import com.hoau.miser.module.api.itf.server.cache.DiscountPrivilegeTyCache;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.util.DateUtils;

import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: DiscountPrivilegeTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/13 10:51
 */
@Service
public class DiscountPrivilegeTyService implements IDiscountPrivilegeTyService {

    public DiscountPrivilegeTyEntity queryDiscountCustomerByCustNoAndrecordMonth(String custNo, Date recordMonth) {
        if(StringUtil.isEmpty(custNo)||recordMonth==null){
            return null;
        }
        if(recordMonth==null)recordMonth=new Date();
        ICache<String,DiscountPrivilegeTyEntity> cache= CacheManager.getInstance().getCache(DiscountPrivilegeTyCache.UUID);
        String key= new CacheKey(custNo, DateUtils.convert(recordMonth,DateUtils.DATE_FORMAT)).generateKey();
        return cache.get(key);
    }
}
