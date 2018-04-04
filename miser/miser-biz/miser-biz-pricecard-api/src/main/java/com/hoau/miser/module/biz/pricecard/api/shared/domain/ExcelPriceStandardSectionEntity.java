package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class ExcelPriceStandardSectionEntity extends BaseEntity{
	private static final long serialVersionUID = 7941200643574323884L;
	
	/**
	 * @Fields transTypeCode : 运输类型
	 */
	private String transTypeCode;
	/**
	 * @Fields transTypeName : 运输类型名称
	 */
	private String transTypeName;
	/**
	 * @Fields sendPriceCityName : 出发价格城市名称
	 */
	private String sendPriceCityName;
	/**
	 * @Fields sendPriceCityName : 到达价格城市名称
	 */
	private String arrivePriceCityName;
	/**
	 * @Fields firstWeight : 一段首重
	 */
	private String firstWeight;
	/**
	 * @Fields firstWeightPrice : 一段首重单价
	 */
	private String firstWeightPrice;
	/**
	 * @Fields firstAddWeightPrice : 一段续重单价
	 */
	private String firstAddWeightPrice;
	/**
	 * @Fields secondWeight : 二段首重
	 */
	private String secondWeight;
	/**
	 * @Fields secondWeightPrice : 二段首重单价
	 */
	private String secondWeightPrice;
	/**
	 * @Fields secondAddWeightPrice : 二段续重单价
	 */
	private String secondAddWeightPrice;
	/**
	 * @Fields thirdWeight : 三段首重
	 */
	private String thirdWeight;
	/**
	 * @Fields thirdWeightPrice : 三段首重单价
	 */
	private String thirdWeightPrice;
	/**
	 * @Fields thirdAddWeightPrice : 三段续重单价
	 */
	private String thirdAddWeightPrice;
	/**
	 * @Fields effectiveTime : 生效时间
	 */
	private String effectiveTime;
	/**
	 * @Fields invalidTime : 失效时间
	 */
	private String invalidTime;
	/**
	 * @Fields remark : 备注
	 */
	private String remark;
	/**
	 * @Fields active : 是否可用
	 */
	private String active;
	/**
	 * @Fields state : 当前状态
	 */
	private String state;
	/**
	 * @Fields stateName : 当前状态名称
	 */
	private String stateName;
	/**
	 * @Fields errorMsg : 错误信息
	 */
	private String errorMsg;
	/**
	 * @Fields oldSerial : 旧的序号
	 */
	private String oldSerial;
	
	
	public String getOldSerial() {
		return oldSerial;
	}
	public void setOldSerial(String oldSerial) {
		this.oldSerial = oldSerial;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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
	public String getFirstWeight() {
		return firstWeight;
	}
	public void setFirstWeight(String firstWeight) {
		this.firstWeight = firstWeight;
	}
	public String getFirstWeightPrice() {
		return firstWeightPrice;
	}
	public void setFirstWeightPrice(String firstWeightPrice) {
		this.firstWeightPrice = firstWeightPrice;
	}
	public String getFirstAddWeightPrice() {
		return firstAddWeightPrice;
	}
	public void setFirstAddWeightPrice(String firstAddWeightPrice) {
		this.firstAddWeightPrice = firstAddWeightPrice;
	}
	public String getSecondWeight() {
		return secondWeight;
	}
	public void setSecondWeight(String secondWeight) {
		this.secondWeight = secondWeight;
	}
	public String getSecondWeightPrice() {
		return secondWeightPrice;
	}
	public void setSecondWeightPrice(String secondWeightPrice) {
		this.secondWeightPrice = secondWeightPrice;
	}
	public String getSecondAddWeightPrice() {
		return secondAddWeightPrice;
	}
	public void setSecondAddWeightPrice(String secondAddWeightPrice) {
		this.secondAddWeightPrice = secondAddWeightPrice;
	}
	public String getThirdWeight() {
		return thirdWeight;
	}
	public void setThirdWeight(String thirdWeight) {
		this.thirdWeight = thirdWeight;
	}
	public String getThirdWeightPrice() {
		return thirdWeightPrice;
	}
	public void setThirdWeightPrice(String thirdWeightPrice) {
		this.thirdWeightPrice = thirdWeightPrice;
	}
	public String getThirdAddWeightPrice() {
		return thirdAddWeightPrice;
	}
	public void setThirdAddWeightPrice(String thirdAddWeightPrice) {
		this.thirdAddWeightPrice = thirdAddWeightPrice;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
