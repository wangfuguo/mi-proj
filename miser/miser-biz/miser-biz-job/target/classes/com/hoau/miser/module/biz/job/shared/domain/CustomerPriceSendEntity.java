package com.hoau.miser.module.biz.job.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;

/**
 * 客户价格
 * ClassName: CustomerPriceSendEntity 
 * @author 廖文强
 * @date 2016年1月27日
 * @version V1.0
 */
public class CustomerPriceSendEntity {

	
	
	//type 'C'
	//cityMapType 对照类型：1）当C为价格城市类型；
	//transportTime 运输时间（小时）
	
	/**
	 * 运输类型
	 */
	private String transportType;
	private String id;
	private String standardId;// 客户编号
	private String arriveCityName;// a)对照类型为C时为目的城市编号
	private Double startPrice;// 最低收费
	private Double weightPrice;// 重量单价
	private Double volumePrice;// 体积单价
	private Double extraChargePrice;// 附加费
	private Double lightDiscount;// 轻货折扣
	private Double heavyDiscount;// 重货折扣
	private Date effectiveDate;// 生效日期
	private Date invalidDate;// 失效日期
	private String fuelSubchargeId;// 燃油分段 暂为null
	private Integer recStatus; //最终删除标示（Y-有效；N-逻辑删除）
	private String sectionId;// 优惠分段 运费
	private String discountPriorityType;//折扣基于类型，CHEAPEST:最低折扣 (跟活动融合) CUSTOMER:客户折扣）
	private String sendCity;//出发价格城市
	private String active;//数据是否有效
	private String remark;//备注
	private String customertype;//客户类别
	
	
	
	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
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
	public String getSendCity() {
		return sendCity;
	}
	public void setSendCity(String sendCity) {
		this.sendCity = sendCity;
	}
	public String getDiscountPriorityType() {
		if(StringUtil.isEmpty(this.discountPriorityType)){//默认要融合
			this.discountPriorityType="CHEAPEST";
		}
		return discountPriorityType;
	}
	public void setDiscountPriorityType(String discountPriorityType) {
		this.discountPriorityType = discountPriorityType;
	}
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
	public String getStandardId() {
		return standardId;
	}
	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	public String getArriveCityName() {
		return arriveCityName;
	}
	public void setArriveCityName(String arriveCityName) {
		this.arriveCityName = arriveCityName;
	}
	public Double getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}
	public Double getWeightPrice() {
		return weightPrice;
	}
	public void setWeightPrice(Double weightPrice) {
		this.weightPrice = weightPrice;
	}
	public Double getVolumePrice() {
		return volumePrice;
	}
	public void setVolumePrice(Double volumePrice) {
		this.volumePrice = volumePrice;
	}
	public Double getExtraChargePrice() {
		return extraChargePrice;
	}
	public void setExtraChargePrice(Double extraChargePrice) {
		this.extraChargePrice = extraChargePrice;
	}
	public Double getLightDiscount() {
		return lightDiscount;
	}
	public void setLightDiscount(Double lightDiscount) {
		this.lightDiscount = lightDiscount;
	}
	public Double getHeavyDiscount() {
		return heavyDiscount;
	}
	public void setHeavyDiscount(Double heavyDiscount) {
		this.heavyDiscount = heavyDiscount;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getInvalidDate() {
		return invalidDate;
	}
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}
	public String getFuelSubchargeId() {
		return fuelSubchargeId;
	}
	public void setFuelSubchargeId(String fuelSubchargeId) {
		this.fuelSubchargeId = fuelSubchargeId;
	}
	public Integer getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(Integer recStatus) {
		this.recStatus = recStatus;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	
	
	
}
