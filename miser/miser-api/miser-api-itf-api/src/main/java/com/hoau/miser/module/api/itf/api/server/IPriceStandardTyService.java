package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: IPriceStandardTyService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 标准价格
 * @date 2016/6/13 17:07
 */
public interface IPriceStandardTyService {
    /**
     *
     * @param baseTyParam
     * @return
     * @Description: 查询标准价格
     * @author 廖文强
     * @date 2016年06月06日
     */
    public PriceStandardTyEntity queryPriceStandardTyByQueryParam(PriceQueryParam baseTyParam);

}
