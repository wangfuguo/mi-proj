package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BasePriceTyEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 分段客户价格
 * @date 2016/07/05
 */
public class PriceCustSectionTyEntity extends BaseEntity {

    /**
     * 客户编号
     **/
    private String customerCode;
    /**
     * 客户价格明细
     **/
    private PriceCustSubSectionTyEntity priceCustSubSectionTyEntity;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public PriceCustSubSectionTyEntity getPriceCustSubSectionTyEntity() {
        return priceCustSubSectionTyEntity;
    }

    public void setPriceCustSubSectionTyEntity(PriceCustSubSectionTyEntity priceCustSubSectionTyEntity) {
        this.priceCustSubSectionTyEntity = priceCustSubSectionTyEntity;
    }

    public int getType() {
        return PCTyConstans.PC_TYPE_CUST;
    }


}
