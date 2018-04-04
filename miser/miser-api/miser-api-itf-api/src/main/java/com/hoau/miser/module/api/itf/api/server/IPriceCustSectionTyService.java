package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceCustSectionTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: IPriceCustSectionTyService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 分段客户价格
 * @date 2017/07/05
 */
public interface IPriceCustSectionTyService {
    /**
     *
     * @param baseTyParam
     * @return
     * @Description: 查询客户价格
     * @author 廖文强
     * @date 2017/07/05
     */
    public PriceCustSectionTyEntity queryPriceCustSectionTyByQueryParam(PriceQueryParam baseTyParam);
}
