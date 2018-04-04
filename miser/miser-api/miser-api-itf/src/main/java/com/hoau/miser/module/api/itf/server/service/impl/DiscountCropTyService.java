package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IDiscountCorpTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCorpTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.DiscountCorpTyCache;
import com.hoau.miser.module.api.itf.server.cache.DiscountCustomerTyCache;
import com.hoau.miser.module.api.itf.server.dao.DiscountCorpTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: DiscountCropTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: 网点折扣
 * @date 2016/6/12 16:33
 */
@Service
public class DiscountCropTyService implements IDiscountCorpTyService {

    @Resource
    private DiscountCorpTyDao discountCropTyDao;
    @Resource
    private ICorpPriceCityService corpPriceCityService;
    public DiscountCorpTyEntity queryDisCorpByParam(PriceQueryParam baseTyParam) {
        DiscountCorpTyEntity entity=null;
        if(StringUtil.isEmpty(baseTyParam.getOriginCode())){
            return null;
        }
        if (StringUtil.isEmpty(baseTyParam.getTransTypeCode())) {
            return null;
        }
        if(!baseTyParam.isHistory()){
            ICache<String,DiscountCorpTyEntity> cache= CacheManager.getInstance().getCache(DiscountCorpTyCache.UUID);
            String keyStr=new CacheKey(baseTyParam.getOriginCode(),baseTyParam.getTransTypeCode()).generateKey();
            entity= cache.get(keyStr);
        }else{
            List<DiscountCorpTyEntity> list=discountCropTyDao.queryListByParam(baseTyParam);
            entity=(list!=null&&list.size()>0)?list.get(0):null;
        }
        return entity;
    }
}
