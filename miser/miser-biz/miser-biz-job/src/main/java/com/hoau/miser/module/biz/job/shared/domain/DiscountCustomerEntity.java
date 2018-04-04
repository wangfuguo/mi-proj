package com.hoau.miser.module.biz.job.shared.domain;


import java.math.BigDecimal;
import java.util.Date;

public class DiscountCustomerEntity{
	
	private String id;

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
    
    private Double subcharge;
    private String fuelSubcharge;
    private Double pickFees;
    private Double deliverFees;
    private Double insuranceRate;
    private Double lowestInsuranceFees;
    private Double payableServiceRate;
    private Double flatCost;
    private Double informationFees;
    //发送给dc的运输类型
    private String transportType;
    private String state;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
    public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public Double getSubcharge() {
		return subcharge;
	}

	public void setSubcharge(Double subcharge) {
		this.subcharge = subcharge;
	}

	public String getFuelSubcharge() {
		return fuelSubcharge;
	}

	public void setFuelSubcharge(String fuelSubcharge) {
		this.fuelSubcharge = fuelSubcharge;
	}

	public Double getPickFees() {
		return pickFees;
	}

	public void setPickFees(Double pickFees) {
		this.pickFees = pickFees;
	}

	public Double getDeliverFees() {
		return deliverFees;
	}

	public void setDeliverFees(Double deliverFees) {
		this.deliverFees = deliverFees;
	}

	public Double getInsuranceRate() {
		return insuranceRate;
	}

	public void setInsuranceRate(Double insuranceRate) {
		this.insuranceRate = insuranceRate;
	}

	public Double getLowestInsuranceFees() {
		return lowestInsuranceFees;
	}

	public void setLowestInsuranceFees(Double lowestInsuranceFees) {
		this.lowestInsuranceFees = lowestInsuranceFees;
	}

	public Double getPayableServiceRate() {
		return payableServiceRate;
	}

	public void setPayableServiceRate(Double payableServiceRate) {
		this.payableServiceRate = payableServiceRate;
	}

	public Double getFlatCost() {
		return flatCost;
	}

	public void setFlatCost(Double flatCost) {
		this.flatCost = flatCost;
	}

	public Double getInformationFees() {
		return informationFees;
	}

	public void setInformationFees(Double informationFees) {
		this.informationFees = informationFees;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}