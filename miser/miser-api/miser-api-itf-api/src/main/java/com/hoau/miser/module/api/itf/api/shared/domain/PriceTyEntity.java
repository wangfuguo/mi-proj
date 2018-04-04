package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 价格实体
 * @date 2016/6/6 14:45
 */
public class PriceTyEntity extends BaseEntity  {
    /** 价卡类型 1：标准，2：客户价格，3：网点价格 **/
    private int type;
    /** 运输类型**/
    private String transType;

    /** 出发价格城市**/
    /** 到达价格城市**/

    /** 起运地 物流编码**/
    private String originCode;
    /** 目的地 物流编码**/
    private String destCode;
    /** 重量单价**/
    private BigDecimal weightPrice;
    /** 体积单价**/
    private BigDecimal volumePrice;
    /** 最低收费**/
    private BigDecimal lowestFee;
    /** 附加费**/
    private BigDecimal addFee;

    /**重货折扣 **/
    private BigDecimal weightDiscount;
    /** 轻货折扣 **/
    private BigDecimal volumeDiscount;
    /** 运费折扣 **/
    private String  freightDiscountSectCode;
    /** 运费专属折扣 **/
    private String freightOwnDiscountSectCode;

    public String getFreightDiscountSectCode() {
        return freightDiscountSectCode;
    }

    public void setFreightDiscountSectCode(String freightDiscountSectCode) {
        this.freightDiscountSectCode = freightDiscountSectCode;
    }

    public String getFreightOwnDiscountSectCode() {
        return freightOwnDiscountSectCode;
    }

    public void setFreightOwnDiscountSectCode(String freightOwnDiscountSectCode) {
        this.freightOwnDiscountSectCode = freightOwnDiscountSectCode;
    }

    public BigDecimal getWeightDiscount() {
        return weightDiscount;
    }

    public void setWeightDiscount(BigDecimal weightDiscount) {
        this.weightDiscount = weightDiscount;
    }

    public BigDecimal getVolumeDiscount() {
        return volumeDiscount;
    }

    public void setVolumeDiscount(BigDecimal volumeDiscount) {
        this.volumeDiscount = volumeDiscount;
    }



    public BigDecimal getAddFee() {
        return addFee;
    }

    public void setAddFee(BigDecimal addFee) {
        this.addFee = addFee;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public BigDecimal getWeightPrice() {
        return weightPrice;
    }

    public void setWeightPrice(BigDecimal weightPrice) {
        this.weightPrice = weightPrice;
    }

    public BigDecimal getVolumePrice() {
        return volumePrice;
    }

    public void setVolumePrice(BigDecimal volumePrice) {
        this.volumePrice = volumePrice;
    }

    public BigDecimal getLowestFee() {
        return lowestFee;
    }

    public void setLowestFee(BigDecimal lowestFee) {
        this.lowestFee = lowestFee;
    }
}
