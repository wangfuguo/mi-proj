package com.hoau.miser.module.api.facade.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class PriceSectionSubSyncEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6438915102841889782L;
	private String sectionId;	//分段项目主键
	private Double startValue;	//段起
	private Double endValue;	//段止
	private String sectionAccodingItem;//分段依据：根据哪一项的值来确定分段	WEIGHT:重量	VOLUMN:体积	PIECE:件数	PACKAGE:票	INSURANCE:保价费COLLECTION:代收货款
	private String priceType;	//费用类型	RATE:单价	MONEY:金额
	private Double price;		//费用
	private String calcAlone;	//是否单独进行结算	Y:单独结算,只计算此分段中的费用	N:向下继续寻找分段，直至找到单独结算或没有分段为止，然后累加各个分段内计算的费用
	private Double minPrice;	//最低收费
	private Double maxPrice;	//最高收费
	private String remark;		//备注
	private String active;		//是否可用


	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public Double getStartValue() {
		return startValue;
	}

	public void setStartValue(Double startValue) {
		this.startValue = startValue;
	}

	public Double getEndValue() {
		return endValue;
	}

	public void setEndValue(Double endValue) {
		this.endValue = endValue;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCalcAlone() {
		return calcAlone;
	}

	public void setCalcAlone(String calcAlone) {
		this.calcAlone = calcAlone;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
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

	public String getSectionAccodingItem() {
		return sectionAccodingItem;
	}

	public void setSectionAccodingItem(String sectionAccodingItem) {
		this.sectionAccodingItem = sectionAccodingItem;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PriceSectionSubSyncEntity) {
			PriceSectionSubSyncEntity entity = (PriceSectionSubSyncEntity) obj;
			if (((startValue == null && entity.getStartValue() == null) || (startValue != null && startValue.equals(entity.getStartValue()))) 
					&& ((endValue == null && entity.getEndValue() == null) || (endValue != null && endValue.equals(entity.getEndValue())))
					&& ((sectionAccodingItem == null && entity.getSectionAccodingItem() == null) || (sectionAccodingItem != null && sectionAccodingItem.equals(entity.getSectionAccodingItem())))
					&& ((priceType == null && entity.getPriceType() == null) || (priceType != null && priceType.equals(entity.getPriceType())))
					&& ((price == null && entity.getPrice() == null) || (price != null && price.equals(entity.getPrice())))
					&& ((calcAlone == null && entity.getCalcAlone() == null) || (calcAlone != null && calcAlone.equals(entity.getCalcAlone())))
					&& ((minPrice == null && entity.getMinPrice() == null) || (minPrice != null && minPrice.equals(entity.getMinPrice())))
					&& ((maxPrice == null && entity.getMaxPrice() == null) || (maxPrice != null && maxPrice.equals(entity.getMaxPrice())))
					&& ((active == null && entity.getActive() == null) || (active != null && active.equals(entity.getActive())))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return new StringBuffer().append(startValue).append(endValue).append(sectionAccodingItem).append(priceType)
				.append(price).append(calcAlone).append(minPrice).append(maxPrice).append(active).toString().hashCode();
	}

	@Override
	public String toString() {
		return "PriceSectionSubEntity [sectionId=" + sectionId
				+ ", startValue=" + startValue + ", endValue=" + endValue
				+ ", sectionAccodingItem=" + sectionAccodingItem
				+ ", priceType=" + priceType + ", price=" + price
				+ ", calcAlone=" + calcAlone + ", minPrice=" + minPrice
				+ ", maxPrice=" + maxPrice + ", remark=" + remark + ", active="
				+ active + "]";
	}

}
