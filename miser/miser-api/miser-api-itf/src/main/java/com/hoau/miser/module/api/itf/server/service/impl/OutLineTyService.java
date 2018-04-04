package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.miser.module.api.itf.server.dao.OutLineTyDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IOutLineTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.CacheKey;
import com.hoau.miser.module.api.itf.api.shared.domain.OutLineTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.cache.OutLineTyCache;

import javax.annotation.Resource;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: OutLineTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/13 14:45
 */
@Service
public class OutLineTyService implements IOutLineTyService {

    @Resource
    OutLineTyDao outLineTyDao;

    @Override
    public OutLineTyEntity queryOutLineByEntity(String destOrgCode) {
        ICache<String,OutLineTyEntity> cache = CacheManager.getInstance().getCache(OutLineTyCache.UUID);
        return cache.get(destOrgCode);
    }
}
