package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BasePriceTyEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 客户价格
 * @date 2016/6/6 15:12
 */
public class PriceCustConfTyEntity extends BaseEntity {
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

    /** 运费专属折扣 **/
    private String freightOwnDiscountSectCode;
    /** 附加费专属折扣 **/
    private String addFeeOwnDiscountSectCode;

    /**
     * 越发越惠 CHEAPEST:最低折扣
     EVENT:活动折扣
     CUSTOMER:客户折扣**/
    private String discountPriorityType;

    /** 客户价格**/
    private PriceCustTyEntity custTyEntity;

    public String getAddFeeOwnDiscountSectCode() {
        return addFeeOwnDiscountSectCode;
    }

    public void setAddFeeOwnDiscountSectCode(String addFeeOwnDiscountSectCode) {
        this.addFeeOwnDiscountSectCode = addFeeOwnDiscountSectCode;
    }

    public PriceCustTyEntity getCustTyEntity() {
        return custTyEntity;
    }

    public void setCustTyEntity(PriceCustTyEntity custTyEntity) {
        this.custTyEntity = custTyEntity;
    }

    public String getDiscountPriorityType() {
        if(StringUtil.isEmpty(discountPriorityType)){
            discountPriorityType="0";
        }
        return discountPriorityType;
    }

    public void setDiscountPriorityType(String discountPriorityType) {
        this.discountPriorityType = discountPriorityType;
    }

    public String getFreightOwnDiscountSectCode() {
        return freightOwnDiscountSectCode;
    }

    public void setFreightOwnDiscountSectCode(String freightOwnDiscountSectCode) {
        this.freightOwnDiscountSectCode = freightOwnDiscountSectCode;
    }

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


    public int getType() {
        return PCTyConstans.PC_TYPE_CUST;
    }


}
