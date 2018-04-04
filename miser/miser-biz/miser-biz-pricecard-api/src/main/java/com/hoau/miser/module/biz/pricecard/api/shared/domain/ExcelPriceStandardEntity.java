package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
/**
 * 
 * @Description: 标准价格实体类(导出时使用)
 * @Author 廖文强
 * @Date 2015年12月15日
 */
public class ExcelPriceStandardEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	//运输类型
	private String transTypeCode;
	//运输类型名称
	private String transTypeName;
	
	//出发价格城市 SEND_PRICE_CITY
	private String sendPriceCityName;
	//到达价格城市 ARRIVE_PRICE_CITY
	private String arrivePriceCityName;
	//出发价格城市 SEND_PRICE_CITY
	private String sendPriceCityCode;
	//到达价格城市 ARRIVE_PRICE_CITY
	private String arrivePriceCityCode;
	//重量单价 WEIGHT_PRICE
	private String weightPrice;
	//体积单价 VOLUME_PRICE
	private String volumePrice;
	//附加费 ADD_FEE
	private String addFee;
	//最低收费 LOWEST_FEE
	private String lowestFee;
	//燃油费分段编号 FUEL_FEE_SECTION
	private String fuelFeeSection;
	//生效时间-EFFECTIVE_TIME
	private String effectiveTime;
	//失效时间-INVALID_TIME
	private String invalidTime;
	//备注 REMARK
	private String remark;
	//是否有效 ACTIVE
	private String active;
	// 当前状态
	private String state;

	//旧的序号
	private String oldSerial;
	
	//错误信息
	private String errorMsg;
	
	private String stateName;
	
	
	public String getStateName() {
		String msg="";
		if(PriceStandardEntity.STATE_1.equals(this.state)){
			msg="已失效";
		}else if(PriceStandardEntity.STATE_2.equals(this.state)){
			msg="生效中";
		}else if(PriceStandardEntity.STATE_3.equals(this.state)){
			msg="待生效";
		}else if(PriceStandardEntity.STATE_4.equals(this.state)){
			msg="已废弃";
		}
		stateName=msg;
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getOldSerial() {
		return oldSerial;
	}
	public void setOldSerial(String oldSerial) {
		this.oldSerial = oldSerial;
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
	
	public String getSendPriceCityCode() {
		return sendPriceCityCode;
	}
	public void setSendPriceCityCode(String sendPriceCityCode) {
		this.sendPriceCityCode = sendPriceCityCode;
	}
	public String getArrivePriceCityCode() {
		return arrivePriceCityCode;
	}
	public void setArrivePriceCityCode(String arrivePriceCityCode) {
		this.arrivePriceCityCode = arrivePriceCityCode;
	}
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
	
	public String getWeightPrice() {
		return weightPrice;
	}
	public void setWeightPrice(String weightPrice) {
		this.weightPrice = weightPrice;
	}
	public String getVolumePrice() {
		return volumePrice;
	}
	public void setVolumePrice(String volumePrice) {
		this.volumePrice = volumePrice;
	}
	public String getAddFee() {
		return addFee;
	}
	public void setAddFee(String addFee) {
		this.addFee = addFee;
	}
	public String getLowestFee() {
		return lowestFee;
	}
	public void setLowestFee(String lowestFee) {
		this.lowestFee = lowestFee;
	}
	public String getFuelFeeSection() {
		return fuelFeeSection;
	}
	public void setFuelFeeSection(String fuelFeeSection) {
		this.fuelFeeSection = fuelFeeSection;
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
