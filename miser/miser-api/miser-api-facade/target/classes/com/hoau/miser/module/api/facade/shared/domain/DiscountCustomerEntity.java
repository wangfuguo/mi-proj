package com.hoau.miser.module.api.facade.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class DiscountCustomerEntity extends BaseEntity{
	private static final long serialVersionUID = -6112663223853651804L;

	/**
	 * 1:已失效
	 */
	public static final String STATE_1="1";
	/**
	 * 2:生效中
	 */
	public static final String STATE_2="2";
	/**
	 * 3:待生效
	 */
	public static final String STATE_3="3";
	/**
	 * 4:已废弃
	 */
	public static final String STATE_4="4";
	
	private String customerCode;//客户编号
	
	private String orgCode;//门店编号

    private String transTypeCode;//运输类型编号

    private String discountPriorityType;

    private String freightSectionCode;

    private String pickupSectionCode;

    private String deliverySectionCode;

    private String insuranceRateSectionCode;

    private String insuranceSectionCode;

    private String paperSectionCode;

    private String smsSectionCode;

    private String fuelSectionCode;

    private String collectionRateSectionCode;

    private String collectionSectionCode;

    private Date effectiveTime;

    private Date invalidTime;

    private String remark;

    private String active;

    private String addSectionCode;

    private String defaultShowDiscountValue;
    private String upstairsSectionCode;
    private Date discountAccodingPcTime;
    private BigDecimal heavyFloatPrice;//重货浮动浮动金额
    private BigDecimal lightFloatPrice;//轻货浮动浮动金额
    private String dataOrign;//数据来源
    private String createUserName;//创建人
    private String modifyUserName;//修改人
    //错误信息
  	private String errorMsg;
  	private String state;
  	private BigDecimal floatPercentage;
    
    /**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getTransTypeCode() {
        return transTypeCode;
    }

    public void setTransTypeCode(String transTypeCode) {
        this.transTypeCode = transTypeCode == null ? null : transTypeCode.trim();
    }

    public String getDiscountPriorityType() {
        return discountPriorityType;
    }

    public void setDiscountPriorityType(String discountPriorityType) {
        this.discountPriorityType = discountPriorityType == null ? null : discountPriorityType.trim();
    }

    public String getFreightSectionCode() {
        return freightSectionCode;
    }

    public void setFreightSectionCode(String freightSectionCode) {
        this.freightSectionCode = freightSectionCode == null ? null : freightSectionCode.trim();
    }

    public String getPickupSectionCode() {
        return pickupSectionCode;
    }

    public void setPickupSectionCode(String pickupSectionCode) {
        this.pickupSectionCode = pickupSectionCode == null ? null : pickupSectionCode.trim();
    }

    public String getDeliverySectionCode() {
        return deliverySectionCode;
    }

    public void setDeliverySectionCode(String deliverySectionCode) {
        this.deliverySectionCode = deliverySectionCode == null ? null : deliverySectionCode.trim();
    }

    public String getInsuranceRateSectionCode() {
        return insuranceRateSectionCode;
    }

    public void setInsuranceRateSectionCode(String insuranceRateSectionCode) {
        this.insuranceRateSectionCode = insuranceRateSectionCode == null ? null : insuranceRateSectionCode.trim();
    }

    public String getInsuranceSectionCode() {
        return insuranceSectionCode;
    }

    public void setInsuranceSectionCode(String insuranceSectionCode) {
        this.insuranceSectionCode = insuranceSectionCode == null ? null : insuranceSectionCode.trim();
    }

    public String getPaperSectionCode() {
        return paperSectionCode;
    }

    public void setPaperSectionCode(String paperSectionCode) {
        this.paperSectionCode = paperSectionCode == null ? null : paperSectionCode.trim();
    }

    public String getSmsSectionCode() {
        return smsSectionCode;
    }

    public void setSmsSectionCode(String smsSectionCode) {
        this.smsSectionCode = smsSectionCode == null ? null : smsSectionCode.trim();
    }

    public String getFuelSectionCode() {
        return fuelSectionCode;
    }

    public void setFuelSectionCode(String fuelSectionCode) {
        this.fuelSectionCode = fuelSectionCode == null ? null : fuelSectionCode.trim();
    }

    public String getCollectionRateSectionCode() {
        return collectionRateSectionCode;
    }

    public void setCollectionRateSectionCode(String collectionRateSectionCode) {
        this.collectionRateSectionCode = collectionRateSectionCode == null ? null : collectionRateSectionCode.trim();
    }

    public String getCollectionSectionCode() {
        return collectionSectionCode;
    }

    public void setCollectionSectionCode(String collectionSectionCode) {
        this.collectionSectionCode = collectionSectionCode == null ? null : collectionSectionCode.trim();
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

    public String getAddSectionCode() {
        return addSectionCode;
    }

    public void setAddSectionCode(String addSectionCode) {
        this.addSectionCode = addSectionCode == null ? null : addSectionCode.trim();
    }

    public String getDefaultShowDiscountValue() {
        return defaultShowDiscountValue;
    }

    public void setDefaultShowDiscountValue(String defaultShowDiscountValue) {
        this.defaultShowDiscountValue = defaultShowDiscountValue == null ? null : defaultShowDiscountValue.trim();
    }

	

	/**
	 * @return the upstairsSectionCode
	 */
	public String getUpstairsSectionCode() {
		return upstairsSectionCode;
	}

	/**
	 * @param upstairsSectionCode the upstairsSectionCode to set
	 */
	public void setUpstairsSectionCode(String upstairsSectionCode) {
		this.upstairsSectionCode = upstairsSectionCode;
	}


	/**
	 * @return the discountAccodingPcTime
	 */
	public Date getDiscountAccodingPcTime() {
		return discountAccodingPcTime;
	}

	/**
	 * @param discountAccodingPcTime the discountAccodingPcTime to set
	 */
	public void setDiscountAccodingPcTime(Date discountAccodingPcTime) {
		this.discountAccodingPcTime = discountAccodingPcTime;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @return the modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @param modifyUserName the modifyUserName to set
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public BigDecimal getHeavyFloatPrice() {
		return heavyFloatPrice;
	}

	public void setHeavyFloatPrice(BigDecimal heavyFloatPrice) {
		this.heavyFloatPrice = heavyFloatPrice;
	}

	public BigDecimal getLightFloatPrice() {
		return lightFloatPrice;
	}

	public void setLightFloatPrice(BigDecimal lightFloatPrice) {
		this.lightFloatPrice = lightFloatPrice;
	}

	public String getDataOrign() {
		return dataOrign;
	}

	public void setDataOrign(String dataOrign) {
		this.dataOrign = dataOrign;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getFloatPercentage() {
		return floatPercentage;
	}

	public void setFloatPercentage(BigDecimal floatPercentage) {
		this.floatPercentage = floatPercentage;
	}
	
}