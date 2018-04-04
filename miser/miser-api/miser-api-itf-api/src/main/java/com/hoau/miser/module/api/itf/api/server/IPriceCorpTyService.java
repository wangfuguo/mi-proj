package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: IPriceCorpTyService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 网点价格
 * @date 2016/6/13 17:09
 */
public interface IPriceCorpTyService {
    /**
     *
     * @param baseTyParam
     * @return
     * @Description: 查询网点价格
     * @author 廖文强
     * @date 2016年06月06日
     */
    public PriceCorpTyEntity queryPriceCorpTyByQueryParam(PriceQueryParam baseTyParam);
}
