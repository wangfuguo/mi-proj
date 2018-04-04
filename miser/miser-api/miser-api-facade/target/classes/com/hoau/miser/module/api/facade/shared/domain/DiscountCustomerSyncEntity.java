package com.hoau.miser.module.api.facade.shared.domain;

import java.math.BigDecimal;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class DiscountCustomerSyncEntity extends BaseEntity{

	private static final long serialVersionUID = 6636331308004070748L;
	private String customerCode;
	private String transTypeCode;
	private String discountAccodingPcTime;
	private BigDecimal heavyFloatPrice;
	private BigDecimal lightFloatPrice;
	private String discountPriorityType;
	private String freightSectionPrice;
	private String pickupSectionPrice;
	private String deliverySectionPrice;
	private String insuranceRateSectionPrice;
	private String insuranceSectionPrice;
	private String paperSectionPrice;
	private String smsSectionPrice;
	private String fuelSectionPrice;
	private String addSectionPrice;
	private String collectionRateSectionPrice;
	private String collectionSectionPrice;
	private String upstairsSectionPrice;
	private String effectiveTime;
	private String invalidTime;
	private String remark;
	private String createUserName;
	private BigDecimal floatPercentage;
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	public String getDiscountAccodingPcTime() {
		return discountAccodingPcTime;
	}
	public void setDiscountAccodingPcTime(String discountAccodingPcTime) {
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
	public String getDiscountPriorityType() {
		return discountPriorityType;
	}
	public void setDiscountPriorityType(String discountPriorityType) {
		this.discountPriorityType = discountPriorityType;
	}
	public String getFreightSectionPrice() {
		return freightSectionPrice;
	}
	public void setFreightSectionPrice(String freightSectionPrice) {
		this.freightSectionPrice = freightSectionPrice;
	}
	public String getPickupSectionPrice() {
		return pickupSectionPrice;
	}
	public void setPickupSectionPrice(String pickupSectionPrice) {
		this.pickupSectionPrice = pickupSectionPrice;
	}
	public String getDeliverySectionPrice() {
		return deliverySectionPrice;
	}
	public void setDeliverySectionPrice(String deliverySectionPrice) {
		this.deliverySectionPrice = deliverySectionPrice;
	}
	public String getInsuranceRateSectionPrice() {
		return insuranceRateSectionPrice;
	}
	public void setInsuranceRateSectionPrice(String insuranceRateSectionPrice) {
		this.insuranceRateSectionPrice = insuranceRateSectionPrice;
	}
	public String getInsuranceSectionPrice() {
		return insuranceSectionPrice;
	}
	public void setInsuranceSectionPrice(String insuranceSectionPrice) {
		this.insuranceSectionPrice = insuranceSectionPrice;
	}
	public String getPaperSectionPrice() {
		return paperSectionPrice;
	}
	public void setPaperSectionPrice(String paperSectionPrice) {
		this.paperSectionPrice = paperSectionPrice;
	}
	public String getSmsSectionPrice() {
		return smsSectionPrice;
	}
	public void setSmsSectionPrice(String smsSectionPrice) {
		this.smsSectionPrice = smsSectionPrice;
	}
	public String getFuelSectionPrice() {
		return fuelSectionPrice;
	}
	public void setFuelSectionPrice(String fuelSectionPrice) {
		this.fuelSectionPrice = fuelSectionPrice;
	}
	public String getAddSectionPrice() {
		return addSectionPrice;
	}
	public void setAddSectionPrice(String addSectionPrice) {
		this.addSectionPrice = addSectionPrice;
	}
	public String getCollectionRateSectionPrice() {
		return collectionRateSectionPrice;
	}
	public void setCollectionRateSectionPrice(String collectionRateSectionPrice) {
		this.collectionRateSectionPrice = collectionRateSectionPrice;
	}
	public String getCollectionSectionPrice() {
		return collectionSectionPrice;
	}
	public void setCollectionSectionPrice(String collectionSectionPrice) {
		this.collectionSectionPrice = collectionSectionPrice;
	}
	public String getUpstairsSectionPrice() {
		return upstairsSectionPrice;
	}
	public void setUpstairsSectionPrice(String upstairsSectionPrice) {
		this.upstairsSectionPrice = upstairsSectionPrice;
	}
	public String getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public String getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public BigDecimal getFloatPercentage() {
		return floatPercentage;
	}
	public void setFloatPercentage(BigDecimal floatPercentage) {
		this.floatPercentage = floatPercentage;
	}

}
