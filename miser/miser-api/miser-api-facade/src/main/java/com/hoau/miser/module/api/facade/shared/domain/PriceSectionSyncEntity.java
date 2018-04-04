package com.hoau.miser.module.api.facade.shared.domain;

import java.util.List;

import com.hoau.hbdp.framework.entity.BaseEntity;


public class PriceSectionSyncEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -1955806360398516352L;
	private String code; 		//分段项目编号
	private String shortCode;  //简码
	private String name; 		//分段项目名称	
	/*
	 * suit_product tableAttr
	 */
	private String suitProduct; //适用产品
	private String suitProductName; //适用产品名称
	private String sectionedItem;//适用的类型：	FREIGHT:运费 FUEL:燃油费	DELIVERY:送货费	PICK:提货费
	private String remark;		//备注
	private String active;		//是否可用
	private String price;
	private String priceType;
	private String sectionAccodingItem;
	private String suitoa;
	/**
	 * @Fields subEntities : 费用分段明细
	 * @author yulin.chen@newhoau.com.cn
	 */
	private List<PriceSectionSubSyncEntity> subEntities;
	
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

	public List<PriceSectionSubSyncEntity> getSubEntities() {
		return subEntities;
	}

	public void setSubEntities(List<PriceSectionSubSyncEntity> subEntities) {
		this.subEntities = subEntities;
	}

	public String getReuniteSource() {
		return reuniteSource;
	}

	public void setReuniteSource(String reuniteSource) {
		this.reuniteSource = reuniteSource;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getSectionAccodingItem() {
		return sectionAccodingItem;
	}

	public void setSectionAccodingItem(String sectionAccodingItem) {
		this.sectionAccodingItem = sectionAccodingItem;
	}

	public String getSuitoa() {
		return suitoa;
	}

	public void setSuitoa(String suitoa) {
		this.suitoa = suitoa;
	}
}
