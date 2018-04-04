package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.PricePickupFeeTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: IPricePickupFeeTyService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 提货费（又叫两套价格）
 * @date 2016/6/6 17:23
 */
public interface IPricePickupFeeTyService {

   /**
     *
     * @param baseTyParam
     * @return
     * @Description: 查询提货费
     * @author 廖文强
     * @date 2016年06月06日
     */
    public PricePickupFeeTyEntity queryPricePickupFeeByQueryParam(PriceQueryParam baseTyParam);
}
