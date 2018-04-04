package com.hoau.miser.module.api.itf.api.shared.domain;


import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;

import java.math.BigDecimal;
import java.util.Date;

public class DiscountCustomerTyEntity extends BaseEntity{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 88395161071452710L;

	private String customerCode;

	private String orgCode;

    private String transTypeCode;

    private String discountPriorityType;

    private String freightSectionCode;
   // private String heavyFreightSectionCode;

    private String lightFreightSectionCode;

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
    private BigDecimal floatPercentage;//浮动百分比

    //扩展信息
    private String roadArea;
    private String bigRegion;
    private String division;
    private String orgCodeName;
    private String discountPriorityName;
    private String customerName;
    private String transTypeName;
    private String freightSectionName;
    private String pickupSectionName;
    private String deliverySectionName;
    private String insuranceRateSectionName;
    private String insuranceSectionName;
    private String paperSectionName;
    private String smsSectionName;
    private String fuelSectionName;
    private String collectionRateSectionName;
    private String collectionSectionName;
    private String addSectionName;
    private String state;
    private Date nowDate;
    private String upstairsSectionName;
    private String createUserName;//创建人
    private String modifyUserName;//修改人
    //错误信息
  	private String errorMsg;

  	private String logistCode;//物流代码

  	public static final String FEE_TYPE_FREIGHT = "FREIGHT";
	public static final String FEE_TYPE_EXTRA_FEE = "EXTRA_FEE";
	public static final String FEE_TYPE_FUEL = "FUEL";
	public static final String FEE_TYPE_PICKUP = "PICKUP";
	public static final String FEE_TYPE_DELIVERY = "DELIVERY";
	public static final String FEE_TYPE_UPSTAIR = "UPSTAIR";
	public static final String FEE_TYPE_INSURANCE_RATE = "INSURANCE_RATE";
	public static final String FEE_TYPE_INSURANCE = "INSURANCE";
	public static final String FEE_TYPE_PAPER = "PAPER";
	public static final String FEE_TYPE_SMS = "SMS";
	public static final String FEE_TYPE_COLLECTION_RATE = "COLLECTION_RATE";
	public static final String FEE_TYPE_COLLECTION = "COLLECTION";

  	/**
  	 *  根据费用类型获取此费用类型的优惠分段
  	 *
  	 * @param feeType
  	 * @return
  	 * @author 蒋落琛
  	 * @date 2016-6-8下午7:04:55
  	 * @update
  	 */
	public String getSectionByType(String feeType)
	{
		if (FEE_TYPE_FREIGHT.equals(feeType))
		{
			return getFreightSectionCode();
		}
		else if (FEE_TYPE_EXTRA_FEE.equals(feeType)) {
			return getAddSectionCode();
		}
		else if (FEE_TYPE_FUEL.equals(feeType)) {
			return getFuelSectionCode();
		}
		else if (FEE_TYPE_PICKUP.equals(feeType)) {
			return getPickupSectionCode();
		}
		else if (FEE_TYPE_DELIVERY.equals(feeType)) {
			return getDeliverySectionCode();
		}
		else if (FEE_TYPE_UPSTAIR.equals(feeType)) {
			return getUpstairsSectionCode();
		}
		else if (FEE_TYPE_INSURANCE_RATE.equals(feeType)) {
			return getInsuranceRateSectionCode();
		}
		else if (FEE_TYPE_INSURANCE.equals(feeType)) {
			return getInsuranceSectionCode();
		}
		else if (FEE_TYPE_PAPER.equals(feeType)) {
			return getPaperSectionCode();
		}
		else if (FEE_TYPE_SMS.equals(feeType)) {
			return getSmsSectionCode();
		}
		else if (FEE_TYPE_COLLECTION_RATE.equals(feeType)) {
			return getCollectionRateSectionCode();
		}
		else if (FEE_TYPE_COLLECTION.equals(feeType)) {
			return getCollectionSectionCode();
		}
		else {
			return getFreightSectionCode();
		}
	}

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
		if(StringUtil.isEmpty(discountPriorityType))discountPriorityType="0";
        return discountPriorityType;
    }

    public void setDiscountPriorityType(String discountPriorityType) {
        this.discountPriorityType = discountPriorityType == null ? null : discountPriorityType.trim();
    }

    public String getFreightSectionCode() {
		return freightSectionCode;
	}

	public void setFreightSectionCode(String freightSectionCode) {
		this.freightSectionCode = freightSectionCode;
	}

	public String getLightFreightSectionCode() {
		return lightFreightSectionCode;
	}

	public void setLightFreightSectionCode(String lightFreightSectionCode) {
		this.lightFreightSectionCode = lightFreightSectionCode;
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
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @return the transTypeName
	 */
	public String getTransTypeName() {
		return transTypeName;
	}

	/**
	 * @return the freightSectionName
	 */
	public String getFreightSectionName() {
		return freightSectionName;
	}

	/**
	 * @return the pickupSectionName
	 */
	public String getPickupSectionName() {
		return pickupSectionName;
	}

	/**
	 * @return the deliverySectionName
	 */
	public String getDeliverySectionName() {
		return deliverySectionName;
	}

	/**
	 * @return the insuranceRateSectionName
	 */
	public String getInsuranceRateSectionName() {
		return insuranceRateSectionName;
	}

	/**
	 * @return the insuranceSectionName
	 */
	public String getInsuranceSectionName() {
		return insuranceSectionName;
	}

	/**
	 * @return the paperSectionName
	 */
	public String getPaperSectionName() {
		return paperSectionName;
	}

	/**
	 * @return the smsSectionName
	 */
	public String getSmsSectionName() {
		return smsSectionName;
	}

	/**
	 * @return the fuelSectionName
	 */
	public String getFuelSectionName() {
		return fuelSectionName;
	}

	/**
	 * @return the collectionRateSectionName
	 */
	public String getCollectionRateSectionName() {
		return collectionRateSectionName;
	}

	/**
	 * @return the collectionSectionName
	 */
	public String getCollectionSectionName() {
		return collectionSectionName;
	}

	/**
	 * @return the addSectionName
	 */
	public String getAddSectionName() {
		return addSectionName;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the nowDate
	 */
	public Date getNowDate() {
		return nowDate;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @param transTypeName the transTypeName to set
	 */
	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	/**
	 * @param freightSectionName the freightSectionName to set
	 */
	public void setFreightSectionName(String freightSectionName) {
		this.freightSectionName = freightSectionName;
	}

	/**
	 * @param pickupSectionName the pickupSectionName to set
	 */
	public void setPickupSectionName(String pickupSectionName) {
		this.pickupSectionName = pickupSectionName;
	}

	/**
	 * @param deliverySectionName the deliverySectionName to set
	 */
	public void setDeliverySectionName(String deliverySectionName) {
		this.deliverySectionName = deliverySectionName;
	}

	/**
	 * @param insuranceRateSectionName the insuranceRateSectionName to set
	 */
	public void setInsuranceRateSectionName(String insuranceRateSectionName) {
		this.insuranceRateSectionName = insuranceRateSectionName;
	}

	/**
	 * @param insuranceSectionName the insuranceSectionName to set
	 */
	public void setInsuranceSectionName(String insuranceSectionName) {
		this.insuranceSectionName = insuranceSectionName;
	}

	/**
	 * @param paperSectionName the paperSectionName to set
	 */
	public void setPaperSectionName(String paperSectionName) {
		this.paperSectionName = paperSectionName;
	}

	/**
	 * @param smsSectionName the smsSectionName to set
	 */
	public void setSmsSectionName(String smsSectionName) {
		this.smsSectionName = smsSectionName;
	}

	/**
	 * @param fuelSectionName the fuelSectionName to set
	 */
	public void setFuelSectionName(String fuelSectionName) {
		this.fuelSectionName = fuelSectionName;
	}

	/**
	 * @param collectionRateSectionName the collectionRateSectionName to set
	 */
	public void setCollectionRateSectionName(String collectionRateSectionName) {
		this.collectionRateSectionName = collectionRateSectionName;
	}

	/**
	 * @param collectionSectionName the collectionSectionName to set
	 */
	public void setCollectionSectionName(String collectionSectionName) {
		this.collectionSectionName = collectionSectionName;
	}

	/**
	 * @param addSectionName the addSectionName to set
	 */
	public void setAddSectionName(String addSectionName) {
		this.addSectionName = addSectionName;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param nowDate the nowDate to set
	 */
	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}



	/**
	 * @return the discountPriorityName
	 */
	public String getDiscountPriorityName() {
		return discountPriorityName;
	}

	/**
	 * @param discountPriorityName the discountPriorityName to set
	 */
	public void setDiscountPriorityName(String discountPriorityName) {
		this.discountPriorityName = discountPriorityName;
	}

	/**
	 * @return the roadArea
	 */
	public String getRoadArea() {
		return roadArea;
	}

	/**
	 * @return the bigRegion
	 */
	public String getBigRegion() {
		return bigRegion;
	}

	/**
	 * @return the division
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * @param roadArea the roadArea to set
	 */
	public void setRoadArea(String roadArea) {
		this.roadArea = roadArea;
	}

	/**
	 * @param bigRegion the bigRegion to set
	 */
	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	/**
	 * @param division the division to set
	 */
	public void setDivision(String division) {
		this.division = division;
	}

	/**
	 * @return the orgCodeName
	 */
	public String getOrgCodeName() {
		return orgCodeName;
	}

	/**
	 * @param orgCodeName the orgCodeName to set
	 */
	public void setOrgCodeName(String orgCodeName) {
		this.orgCodeName = orgCodeName;
	}

	/**
	 * @return the upstairsSectionCode
	 */
	public String getUpstairsSectionCode() {
		return upstairsSectionCode;
	}

	/**
	 * @return the upstairsSectionName
	 */
	public String getUpstairsSectionName() {
		return upstairsSectionName;
	}

	/**
	 * @param upstairsSectionCode the upstairsSectionCode to set
	 */
	public void setUpstairsSectionCode(String upstairsSectionCode) {
		this.upstairsSectionCode = upstairsSectionCode;
	}

	/**
	 * @param upstairsSectionName the upstairsSectionName to set
	 */
	public void setUpstairsSectionName(String upstairsSectionName) {
		this.upstairsSectionName = upstairsSectionName;
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

	public BigDecimal getFloatPercentage() {
		return floatPercentage;
	}

	public void setFloatPercentage(BigDecimal floatPercentage) {
		this.floatPercentage = floatPercentage;
	}

	public String getLogistCode() {
		return logistCode;
	}

	public void setLogistCode(String logistCode) {
		this.logistCode = logistCode;
	}
}