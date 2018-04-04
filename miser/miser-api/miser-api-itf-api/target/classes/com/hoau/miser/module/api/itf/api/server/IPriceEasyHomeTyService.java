package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.*;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceEasyHomeQueryResult;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: IPriceService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 易到家价格Service
 * @date 2016/07/05
 */
public interface IPriceEasyHomeTyService {

    /**
     * 得到价格
     * @Param  [baseTyParam]
     * @Return PriceEasyHomeQueryResult
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/12 14:55
     * @Version v1
     */
    public PriceEasyHomeQueryResult getPriceSectionQueryResultByParam(PriceQueryParam baseTyParam);

    /**
     * 得到标准价格信息
     * @Param  [baseTyParam]
     * @Return com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardSectionTyEntity
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/16 9:57
     * @Version v1
     */
    public PriceStandardSectionTyEntity getPriceStandardSectionByParam(PriceQueryParam baseTyParam);

    /**
     * 得到网点价格
     * @Param  [baseTyParam]
     * @Return com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpSectionTyEntity
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/16 10:12
     * @Version v1
     */
    public PriceCorpSectionTyEntity getPriceCorpSectionByParam(PriceQueryParam baseTyParam);

    /**
     * 得到客户价格
     * @Param  [baseTyParam]
     * @Return com.hoau.miser.module.api.itf.api.shared.domain.PriceCustSectionConfTyEntity
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/16 14:16
     * @Version v1
     */
    public PriceCustSectionConfTyEntity getPriceCustSectionConfByParam(PriceQueryParam baseTyParam);

}
