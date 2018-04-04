package com.hoau.miser.module.api.itf.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 优惠分段
 * 
 * @author 蒋落琛
 * @date 2016-6-6下午3:18:54
 */
public class PriceSectionEntity extends BaseEntity {

	private static final long serialVersionUID = -1955806360398516352L;
	
	private String code; // 分段项目编号
	private String shortCode; // 简码
	private String name; // 分段项目名称
	/*
	 * suit_product tableAttr
	 */
	private String suitProduct; // 适用产品
	private String suitProductName; // 适用产品名称
	private String sectionedItem;// 适用的类型： FREIGHT:运费 FUEL:燃油费 DELIVERY:送货费
									// PICK:提货费
	private String remark; // 备注
	private String active; // 是否可用
	private String suitoa; // 是否适用OA

	/**
	 * 导出有用字段
	 */
	private String sectionAccodingItem;
	private String startValue;
	private String calcAlone;
	private String endValue;
	private String priceType;
	private String price;
	private String minPrice;
	private String maxPrice;
	private String sub_remark;
	private Date modifyDate;
	private String modifyUser;
	/**
	 * @Fields subEntities : 费用分段明细
	 * @author yulin.chen@newhoau.com.cn
	 */
	private List<PriceSectionSubEntity> subEntities;

	/**
	 * @Fields reuniteSource : 融合分段的来源
	 */
	private String reuniteSource;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuitProduct() {
		return suitProduct;
	}

	public void setSuitProduct(String suitProduct) {
		this.suitProduct = suitProduct;
	}

	public String getSectionedItem() {
		return sectionedItem;
	}

	public void setSectionedItem(String sectionedItem) {
		this.sectionedItem = sectionedItem;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getSuitProductName() {
		return suitProductName;
	}

	public void setSuitProductName(String suitProductName) {
		this.suitProductName = suitProductName;
	}

	public List<PriceSectionSubEntity> getSubEntities() {
		return subEntities;
	}

	public void setSubEntities(List<PriceSectionSubEntity> subEntities) {
		this.subEntities = subEntities;
	}

	public String getReuniteSource() {
		return reuniteSource;
	}

	public void setReuniteSource(String reuniteSource) {
		this.reuniteSource = reuniteSource;
	}

	public String getSectionAccodingItem() {
		return sectionAccodingItem;
	}

	public void setSectionAccodingItem(String sectionAccodingItem) {
		this.sectionAccodingItem = sectionAccodingItem;
	}

	public String getStartValue() {
		return startValue;
	}

	public void setStartValue(String startValue) {
		this.startValue = startValue;
	}

	public String getCalcAlone() {
		return calcAlone;
	}

	public void setCalcAlone(String calcAlone) {
		this.calcAlone = calcAlone;
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

	public String getSub_remark() {
		return sub_remark;
	}

	public void setSub_remark(String sub_remark) {
		this.sub_remark = sub_remark;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getSuitoa() {
		return suitoa;
	}

	public void setSuitoa(String suitoa) {
		this.suitoa = suitoa;
	}

	@Override
	public String toString() {
		return "PriceSectionEntity [code=" + code + ", shortCode=" + shortCode
				+ ", name=" + name + ", suitProduct=" + suitProduct
				+ ", suitProductName=" + suitProductName + ", sectionedItem="
				+ sectionedItem + ", remark=" + remark + ", active=" + active
				+ ", subEntities=" + subEntities + ", reuniteSource="
				+ reuniteSource + "]";
	}

}
