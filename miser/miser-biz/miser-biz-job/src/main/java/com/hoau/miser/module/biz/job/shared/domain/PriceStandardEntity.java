package com.hoau.miser.module.biz.job.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
/**
 * 
 * ClassName: PriceStandardEntity 
 * @Description: 价格实体
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月21日
 * @version V1.0
 */
public class PriceStandardEntity extends BaseEntity{
	
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -2121366982392973367L;
	/**
	 * 1:已失效
	 */
	public static final String STATE_1="1";
	/**
	 * 2:生效中
	 */
	public static final String STATE_2="2";
	/**
	 * 3:待生效
	 */
	public static final String STATE_3="3";
	/**
	 * 4:已废弃
	 */
	public static final String STATE_4="4";

	//运输类型
	private String transTypeCode;
	//运输类型名称
	private String transTypeName;
	//出发价格城市 SEND_PRICE_CITY
	private PriceCitySendEntity sendPriceCity;
	//到达价格城市 ARRIVE_PRICE_CITY
	private PriceCitySendEntity arrivePriceCity;
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
	//运费分段
	private String freightSectionCode;
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
	private String spceId;
	private String spceCode;
	private String spceName;
	private String apceId;
	private String apceCode;
	private String apceName;
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
	public String getSpceId() {
		return spceId;
	}
	public void setSpceId(String spceId) {
		this.spceId = spceId;
	}
	public String getSpceCode() {
		return spceCode;
	}
	public void setSpceCode(String spceCode) {
		this.spceCode = spceCode;
	}
	public String getSpceName() {
		return spceName;
	}
	public void setSpceName(String spceName) {
		this.spceName = spceName;
	}
	public String getApceId() {
		return apceId;
	}
	public void setApceId(String apceId) {
		this.apceId = apceId;
	}
	public String getApceCode() {
		return apceCode;
	}
	public void setApceCode(String apceCode) {
		this.apceCode = apceCode;
	}
	public String getApceName() {
		return apceName;
	}
	public void setApceName(String apceName) {
		this.apceName = apceName;
	}
	public String getFreightSectionCode() {
		return freightSectionCode;
	}
	public void setFreightSectionCode(String freightSectionCode) {
		this.freightSectionCode = freightSectionCode;
	}
	public PriceCitySendEntity getSendPriceCity() {
		return sendPriceCity;
	}
	public void setSendPriceCity(PriceCitySendEntity sendPriceCity) {
		this.sendPriceCity = sendPriceCity;
	}
	public PriceCitySendEntity getArrivePriceCity() {
		return arrivePriceCity;
	}
	public void setArrivePriceCity(PriceCitySendEntity arrivePriceCity) {
		this.arrivePriceCity = arrivePriceCity;
	}
	
	
}
