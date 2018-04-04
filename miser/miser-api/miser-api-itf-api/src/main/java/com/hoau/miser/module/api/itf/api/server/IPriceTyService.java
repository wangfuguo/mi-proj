package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.*;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryResult;

import java.math.BigDecimal;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: IPriceService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 价格Service
 * @date 2016/6/6 16:28
 */
public interface IPriceTyService {

    /**
     * 得到价格
     * @Param  [baseTyParam]
     * @Return PriceQueryResult
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/12 14:55
     * @Version v1
     */
    public PriceQueryResult getPriceQueryResultByParam(PriceQueryParam baseTyParam);

    /**
     * 得到标准价格信息
     * @Param  [baseTyParam]
     * @Return com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardTyEntity
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/16 9:57
     * @Version v1
     */
    public PriceStandardTyEntity getPriceStandardByParam(PriceQueryParam baseTyParam);

    /**
     * 得到网点价格
     * @Param  [baseTyParam]
     * @Return com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpTyEntity
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/16 10:12
     * @Version v1
     */
    public PriceCorpTyEntity getPriceCorpByParam(PriceQueryParam baseTyParam);

    /**
     * 得到客户价格
     * @Param  [baseTyParam]
     * @Return com.hoau.miser.module.api.itf.api.shared.domain.PriceCustConfTyEntity
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/16 14:16
     * @Version v1
     */
    public PriceCustConfTyEntity getPriceCustConfByParam(PriceQueryParam baseTyParam);

}
