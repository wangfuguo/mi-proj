package com.hoau.miser.module.api.facade.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class PriceAgingResultEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sendPriceCity;
	private String arrivePriceCity;
	private String fuelFeeSection;
	private String freightSectionCode;
	private String addSectionCode;
	private String sendPriceCityName;
	private String arrivePriceCityName;
	private BigDecimal lowestFee;
	private BigDecimal fuelFee;
	private BigDecimal weightPrice;
	private BigDecimal volumePrice;
	private String pickUpDay;
	private String deliveryDay;
	private String transTypeName;
	private String transTypeCode;
	private BigDecimal freightFeeAmount;
	private String priceEventName;
	private String priceEventCode;
	private BigDecimal weightPriceAfterDiscount;
	private BigDecimal volumnPriceAfterDiscount;
	private BigDecimal freightFeeAmountAfterDiscount;

	public String getSendPriceCity() {
		return sendPriceCity;
	}

	public void setSendPriceCity(String sendPriceCity) {
		this.sendPriceCity = sendPriceCity;
	}

	public String getArrivePriceCity() {
		return arrivePriceCity;
	}

	public void setArrivePriceCity(String arrivePriceCity) {
		this.arrivePriceCity = arrivePriceCity;
	}

	public String getFuelFeeSection() {
		return fuelFeeSection;
	}

	public void setFuelFeeSection(String fuelFeeSection) {
		this.fuelFeeSection = fuelFeeSection;
	}

	public String getFreightSectionCode() {
		return freightSectionCode;
	}

	public void setFreightSectionCode(String freightSectionCode) {
		this.freightSectionCode = freightSectionCode;
	}

	public String getAddSectionCode() {
		return addSectionCode;
	}

	public void setAddSectionCode(String addSectionCode) {
		this.addSectionCode = addSectionCode;
	}

	public String getSendPriceCityName() {
		return sendPriceCityName;
	}

	public void setSendPriceCityName(String sendPriceCityName) {
		this.sendPriceCityName = sendPriceCityName;
	}

	public String getArrivePriceCityName() {
		return arrivePriceCityName;
	}

	public void setArrivePriceCityName(String arrivePriceCityName) {
		this.arrivePriceCityName = arrivePriceCityName;
	}

	public BigDecimal getLowestFee() {
		return lowestFee;
	}

	public void setLowestFee(BigDecimal lowestFee) {
		this.lowestFee = lowestFee;
	}

	public BigDecimal getFuelFee() {
		return fuelFee;
	}

	public void setFuelFee(BigDecimal fuelFee) {
		this.fuelFee = fuelFee;
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

	public String getPickUpDay() {
		return pickUpDay;
	}

	public void setPickUpDay(String pickUpDay) {
		this.pickUpDay = pickUpDay;
	}

	public String getDeliveryDay() {
		return deliveryDay;
	}

	public void setDeliveryDay(String deliveryDay) {
		this.deliveryDay = deliveryDay;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	public String getTransTypeCode() {
		return transTypeCode;
	}

	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}

	public BigDecimal getFreightFeeAmount() {
		return freightFeeAmount;
	}

	public void setFreightFeeAmount(BigDecimal freightFeeAmount) {
		this.freightFeeAmount = freightFeeAmount;
	}

	public String getPriceEventName() {
		return priceEventName;
	}

	public void setPriceEventName(String priceEventName) {
		this.priceEventName = priceEventName;
	}

	public String getPriceEventCode() {
		return priceEventCode;
	}

	public void setPriceEventCode(String priceEventCode) {
		this.priceEventCode = priceEventCode;
	}

	public BigDecimal getWeightPriceAfterDiscount() {
		return weightPriceAfterDiscount;
	}

	public void setWeightPriceAfterDiscount(BigDecimal weightPriceAfterDiscount) {
		this.weightPriceAfterDiscount = weightPriceAfterDiscount;
	}

	public BigDecimal getVolumnPriceAfterDiscount() {
		return volumnPriceAfterDiscount;
	}

	public void setVolumnPriceAfterDiscount(BigDecimal volumnPriceAfterDiscount) {
		this.volumnPriceAfterDiscount = volumnPriceAfterDiscount;
	}

	public BigDecimal getFreightFeeAmountAfterDiscount() {
		return freightFeeAmountAfterDiscount;
	}

	public void setFreightFeeAmountAfterDiscount(
			BigDecimal freightFeeAmountAfterDiscount) {
		this.freightFeeAmountAfterDiscount = freightFeeAmountAfterDiscount;
	}

}
