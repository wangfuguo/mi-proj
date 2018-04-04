package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: IDiscountTyService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 折扣Service
 * @date 2016/6/17 14:45
 */
public interface IDiscountTyService {

    /**
     * 拿到越发越惠最小折扣(经济快运，定日达 取最小值)
     * @Param  [custNo, billTime]
     * @Return java.math.BigDecimal
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/17 14:48
     * @Version v1
     */
    public  BigDecimal getDiscountPriValueMin(String custNo,Date billTime);
    /**
     * 得到客户的专属折扣运费优惠分段编号
     * @Param  [baseTyParam]
     * @Return java.lang.String
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/17 15:38
     * @Version v1
     */
    public String getCustFreightOwnDiscountSectCode(PriceQueryParam baseTyParam);
    /**
     * 得到客户的专属折扣运费优惠分段编号
     * @Param  [baseTyParam]
     * @Return java.lang.String
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/17 15:38
     * @Version v1
     */
    public String getCustAddFeeOwnDiscountSectCode(PriceQueryParam baseTyParam);
    /**
     * 得到网点的专属折扣运费优惠分段编号
     * @Param  [baseTyParam]
     * @Return java.lang.String
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/17 15:38
     * @Version v1
     */
    public String getCorpFreightOwnDiscountSectCode(PriceQueryParam baseTyParam);

    /**
     * 得到最小的折扣值
     *
     * @param selectCode    优惠分段编号
     * @param accodingItem  分段依据
     * @param accodingValue 依据值
     * @return
     * @author  陈宇霖
     * @date 2016年07月28日10:33:08
     */
    public BigDecimal getMinDiscount(String selectCode, String accodingItem, BigDecimal accodingValue);
}
