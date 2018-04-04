package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IDiscountCustomerTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.DiscountCustomerTyCache;
import com.hoau.miser.module.api.itf.server.dao.DiscountCustomerTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: DiscountCustomerTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: 客户折扣
 * @date 2016/6/12 16:33
 */
@Service
public class DiscountCustomerTyService implements IDiscountCustomerTyService {

    @Resource
    private DiscountCustomerTyDao discountCustomerTyDao;
    public DiscountCustomerTyEntity queryDiscountCustomerByParam(PriceQueryParam baseTyParam) {
        DiscountCustomerTyEntity discountCustomerTyEntity=null;
        if(StringUtil.isEmpty(baseTyParam.getCustNo())){
            return null;
        }
        if(!baseTyParam.isHistory()){
            ICache<String,DiscountCustomerTyEntity> cache= CacheManager.getInstance().getCache(DiscountCustomerTyCache.UUID);
            String keyStr=new CacheKey(baseTyParam.getCustNo(),baseTyParam.getTransTypeCode()).generateKey();
            discountCustomerTyEntity= cache.get(keyStr);
        }else{
            List<DiscountCustomerTyEntity> list=discountCustomerTyDao.queryListByParam(baseTyParam);
            discountCustomerTyEntity=(list!=null&&list.size()>0)?list.get(0):null;
        }
        return discountCustomerTyEntity;
    }
}
