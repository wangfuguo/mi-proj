package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;

import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BasePriceTyEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 客户价格
 * @date 2016/6/6 15:12
 */
public class PriceCustTyEntity extends BaseEntity {

    /**
     * 客户编号
     **/
    private String customerCode;
    /**
     * 客户价格明细
     **/
    private PriceCustSubTyEntity priceCustSubTyEntity;
    /**
     * 客户价格类型 Y：新客户，N：旧客户
     **/
    private String customerType;




    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public PriceCustSubTyEntity getPriceCustSubTyEntity() {
        return priceCustSubTyEntity;
    }

    public void setPriceCustSubTyEntity(PriceCustSubTyEntity priceCustSubTyEntity) {
        this.priceCustSubTyEntity = priceCustSubTyEntity;
    }

    public int getType() {
        return PCTyConstans.PC_TYPE_CUST;
    }


}
