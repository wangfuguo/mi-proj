package com.hoau.eai.pc.vo;

import java.io.Serializable;

/**
 * 折扣分段detail信息
 * @author Leslie
 *
 */
public class DiscountDetailInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 轻重类型Z 重货 Q 轻货 E 金额
	 */
	private String lightWeightType;
	/**
	 * 段起
	 */
	private Double beginSection;
	/**
	 * 段止
	 */
	private Double endSection;
	/**
	 * 段折扣
	 */
	private Double sectionDiscount;
	/**
	 * 折扣金额
	 */
	private Double discountAmount;
	/**
	 * 段内单价
	 */
	private Double sectionUnitPrice;
	/**
	 * 段内扣减基数
	 */
	private Double reduceRadix;
	/**
	 * 燃油服务费
	 */
	private Double fuelFees;
	public Double getFuelFees() {
		return fuelFees;
	}
	public void setFuelFees(Double fuelFees) {
		this.fuelFees = fuelFees;
	}
	/**
	 * @return the lightWeightType
	 */
	public String getLightWeightType() {
		return lightWeightType;
	}
	/**
	 * @param lightWeightType the lightWeightType to set
	 */
	public void setLightWeightType(String lightWeightType) {
		this.lightWeightType = lightWeightType;
	}
	/**
	 * @return the beginSection
	 */
	public Double getBeginSection() {
		return beginSection;
	}
	/**
	 * @param beginSection the beginSection to set
	 */
	public void setBeginSection(Double beginSection) {
		this.beginSection = beginSection;
	}
	/**
	 * @return the endSection
	 */
	public Double getEndSection() {
		return endSection;
	}
	/**
	 * @param endSection the endSection to set
	 */
	public void setEndSection(Double endSection) {
		this.endSection = endSection;
	}
	/**
	 * @return the sectionDiscount
	 */
	public Double getSectionDiscount() {
		return sectionDiscount;
	}
	/**
	 * @param sectionDiscount the sectionDiscount to set
	 */
	public void setSectionDiscount(Double sectionDiscount) {
		this.sectionDiscount = sectionDiscount;
	}
	/**
	 * @return the discountAmount
	 */
	public Double getDiscountAmount() {
		return discountAmount;
	}
	/**
	 * @param discountAmount the discountAmount to set
	 */
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	/**
	 * @return the sectionUnitPrice
	 */
	public Double getSectionUnitPrice() {
		return sectionUnitPrice;
	}
	/**
	 * @param sectionUnitPrice the sectionUnitPrice to set
	 */
	public void setSectionUnitPrice(Double sectionUnitPrice) {
		this.sectionUnitPrice = sectionUnitPrice;
	}
	/**
	 * @return the reduceRadix
	 */
	public Double getReduceRadix() {
		return reduceRadix;
	}
	/**
	 * @param reduceRadix the reduceRadix to set
	 */
	public void setReduceRadix(Double reduceRadix) {
		this.reduceRadix = reduceRadix;
	}

}
