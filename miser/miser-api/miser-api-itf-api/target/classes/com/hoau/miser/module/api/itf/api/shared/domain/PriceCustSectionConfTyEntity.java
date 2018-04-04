package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BasePriceTyEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 分段客户价格
 * @date 2016/07/05
 */
public class PriceCustSectionConfTyEntity extends BaseEntity {
    /**
     * 是否启用客户价格
     **/
    private boolean isUseCustomerPc;
    /**
     * 是否启用客户折扣
     **/
    private boolean isUseCustomerDiscount;
    /**
     * 是否显示打折后的价格
     **/
    private boolean isShowDiscountPrice;

    /** 客户价格**/
    private PriceCustSectionTyEntity custSectionTyEntity;


    public boolean isUseCustomerPc() {
        return isUseCustomerPc;
    }

    public void setUseCustomerPc(boolean useCustomerPc) {
        isUseCustomerPc = useCustomerPc;
    }

    public boolean isUseCustomerDiscount() {
        return isUseCustomerDiscount;
    }

    public void setUseCustomerDiscount(boolean useCustomerDiscount) {
        isUseCustomerDiscount = useCustomerDiscount;
    }

    public boolean isShowDiscountPrice() {
        return isShowDiscountPrice;
    }

    public void setShowDiscountPrice(boolean showDiscountPrice) {
        isShowDiscountPrice = showDiscountPrice;
    }

    public PriceCustSectionTyEntity getCustSectionTyEntity() {
        return custSectionTyEntity;
    }

    public void setCustSectionTyEntity(PriceCustSectionTyEntity custSectionTyEntity) {
        this.custSectionTyEntity = custSectionTyEntity;
    }
}
