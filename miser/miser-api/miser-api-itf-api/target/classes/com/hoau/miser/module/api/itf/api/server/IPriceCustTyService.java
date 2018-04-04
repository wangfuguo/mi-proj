package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceCustTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: IPriceCustTyService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 客户价格
 * @date 2016/6/13 17:06
 */
public interface IPriceCustTyService {
    /**
     *
     * @param baseTyParam
     * @return
     * @Description: 查询客户价格
     * @author 廖文强
     * @date 2016年06月06日
     */
    public PriceCustTyEntity queryPriceCustTyByQueryParam(PriceQueryParam baseTyParam);
}
