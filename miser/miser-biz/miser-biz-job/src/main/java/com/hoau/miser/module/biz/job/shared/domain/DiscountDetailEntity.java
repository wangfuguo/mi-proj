package com.hoau.miser.module.biz.job.shared.domain;

public class DiscountDetailEntity {
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 分段项目编号
	 */
	private String sectionCode;
	/**
	 * 分段依据：根据哪一项的值来确定分段
		WEIGHT:重量
		VOLUMN:体积
		PIECE:件数
		PACKAGE:票
		INSURANCE:保价费
		COLLECTION:代收货款
	 */
	private String sectionAccdoingItem;
	/**
	 * 段起
	 */
	private String startValue;
	/**
	 * 段止
	 */
	private String endValue;
	/**
	 * 费用类型
		PRICE:单价
		DISCOUNT:折扣
		MONEY:金额
	 */
	private String priceType;
	/**
	 * 费用
	 */
	private String price;
	/**
	 * 是否阶梯计算 
	 */
	private String calcAlone;
	/**
	 * 最低费用
	 */
	private String minPrice;
	/**
	 * 最高费用
	 */
	private String maxPrice;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	public String getSectionAccdoingItem() {
		return sectionAccdoingItem;
	}
	public void setSectionAccdoingItem(String sectionAccdoingItem) {
		this.sectionAccdoingItem = sectionAccdoingItem;
	}
	public String getStartValue() {
		return startValue;
	}
	public void setStartValue(String startValue) {
		this.startValue = startValue;
	}
	public String getEndValue() {
		return endValue;
	}
	public void setEndValue(String endValue) {
		this.endValue = endValue;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCalcAlone() {
		return calcAlone;
	}
	public void setCalcAlone(String calcAlone) {
		this.calcAlone = calcAlone;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
