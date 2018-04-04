package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
import  com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
/**
 * 
 * @Description: 标准价格实体类
 * @Author 廖文强
 * @Date 2015年12月15日
 */
public class PriceStandardEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 1:已失效
	 */
	public static final String STATE_1="PASSED";
	/**
	 * 2:生效中
	 */
	public static final String STATE_2="EFFECTIVE";
	/**
	 * 3:待生效
	 */
	public static final String STATE_3="WAIT";
	/**
	 * 4:已废弃
	 */
	public static final String STATE_4="DELETED";

	//运输类型
	private String transTypeCode;
	//运输类型名称
	private String transTypeName;
	
	//出发价格城市 SEND_PRICE_CITY
	private PriceCityEntity sendPriceCity;
	//到达价格城市 ARRIVE_PRICE_CITY
	private PriceCityEntity arrivePriceCity;
	
	//重量单价 WEIGHT_PRICE
	private Double weightPrice;
	//体积单价 VOLUME_PRICE
	private Double volumePrice;
	//附加费 ADD_FEE
	private Double addFee;
	//最低收费 LOWEST_FEE
	private Double lowestFee;
	//燃油费分段编号 FUEL_FEE_SECTION
	private String fuelFeeSection;
	private String fuelFeeSectionName;
	//生效时间-EFFECTIVE_TIME
	private Date effectiveTime;
	//失效时间-INVALID_TIME
	private Date invalidTime;
	//备注 REMARK
	private String remark;
	//是否有效 ACTIVE
	private String active;
	// 当前状态
	private String state;

	public String getState() {
		// 待生效、已生效、失效
		return state;
	}
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	public String getTransTypeName() {
		return transTypeName;
	}
	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}
	public PriceCityEntity getSendPriceCity() {
		return sendPriceCity;
	}
	public void setSendPriceCity(PriceCityEntity sendPriceCity) {
		this.sendPriceCity = sendPriceCity;
	}
	public PriceCityEntity getArrivePriceCity() {
		return arrivePriceCity;
	}
	public void setArrivePriceCity(PriceCityEntity arrivePriceCity) {
		this.arrivePriceCity = arrivePriceCity;
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
	public Double getAddFee() {
		return addFee;
	}
	public void setAddFee(Double addFee) {
		this.addFee = addFee;
	}
	public Double getLowestFee() {
		return lowestFee;
	}
	public void setLowestFee(Double lowestFee) {
		this.lowestFee = lowestFee;
	}
	public String getFuelFeeSection() {
		return fuelFeeSection;
	}
	public void setFuelFeeSection(String fuelFeeSection) {
		this.fuelFeeSection = fuelFeeSection;
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
		this.remark = remark;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFuelFeeSectionName() {
		return fuelFeeSectionName;
	}
	public void setFuelFeeSectionName(String fuelFeeSectionName) {
		this.fuelFeeSectionName = fuelFeeSectionName;
	}
	
	
}
