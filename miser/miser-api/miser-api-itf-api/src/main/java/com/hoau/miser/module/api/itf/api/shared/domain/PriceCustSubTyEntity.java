package com.hoau.miser.module.api.itf.api.shared.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceCustSubTyEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 客户价格明细
 * @date 2016/6/6 16:10
 */
public class PriceCustSubTyEntity  extends PriceBaseTyEntity {

    /** 主表主键 **/
    private String parentId;
    /**重货折扣 **/
    private BigDecimal weightDiscount;
    /** 轻货折扣 **/
    private BigDecimal volumeDiscount;
    /** 运费分段编号 **/
    private String  freightSectionCode;

    public String getFreightSectionCode() {
        return freightSectionCode;
    }

    public void setFreightSectionCode(String freightSectionCode) {
        this.freightSectionCode = freightSectionCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
}
