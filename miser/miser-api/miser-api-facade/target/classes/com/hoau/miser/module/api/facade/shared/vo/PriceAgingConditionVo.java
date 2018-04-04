package com.hoau.miser.module.api.facade.shared.vo;

import java.math.BigDecimal;

public class PriceAgingConditionVo {

	private String sendProvinceName;
	private String sendCityName;
	private String sendAreaName;
	private String arriveProvinceName;
	private String arriveCityName;
	private String arriveAreaName;
	private String sendProvinceCode;
	private String sendCityCode;
	private String sendAreaCode;
	private String arriveProvinceCode;
	private String arriveCityCode;
	private String arriveAreaCode;
	private String transTypeName;
	private BigDecimal weight;
	private BigDecimal volume;
	private String channelCode;
	private String sign;

	public String getSendProvinceName() {
		return sendProvinceName;
	}

	public void setSendProvinceName(String sendProvinceName) {
		this.sendProvinceName = sendProvinceName;
	}

	public String getSendCityName() {
		return sendCityName;
	}

	public void setSendCityName(String sendCityName) {
		this.sendCityName = sendCityName;
	}

	public String getSendAreaName() {
		return sendAreaName;
	}

	public void setSendAreaName(String sendAreaName) {
		this.sendAreaName = sendAreaName;
	}

	public String getArriveProvinceName() {
		return arriveProvinceName;
	}

	public void setArriveProvinceName(String arriveProvinceName) {
		this.arriveProvinceName = arriveProvinceName;
	}

	public String getArriveCityName() {
		return arriveCityName;
	}

	public void setArriveCityName(String arriveCityName) {
		this.arriveCityName = arriveCityName;
	}

	public String getArriveAreaName() {
		return arriveAreaName;
	}

	public void setArriveAreaName(String arriveAreaName) {
		this.arriveAreaName = arriveAreaName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSendProvinceCode() {
		return sendProvinceCode;
	}

	public void setSendProvinceCode(String sendProvinceCode) {
		this.sendProvinceCode = sendProvinceCode;
	}

	public String getSendCityCode() {
		return sendCityCode;
	}

	public void setSendCityCode(String sendCityCode) {
		this.sendCityCode = sendCityCode;
	}

	public String getSendAreaCode() {
		return sendAreaCode;
	}

	public void setSendAreaCode(String sendAreaCode) {
		this.sendAreaCode = sendAreaCode;
	}

	public String getArriveProvinceCode() {
		return arriveProvinceCode;
	}

	public void setArriveProvinceCode(String arriveProvinceCode) {
		this.arriveProvinceCode = arriveProvinceCode;
	}

	public String getArriveCityCode() {
		return arriveCityCode;
	}

	public void setArriveCityCode(String arriveCityCode) {
		this.arriveCityCode = arriveCityCode;
	}

	public String getArriveAreaCode() {
		return arriveAreaCode;
	}

	public void setArriveAreaCode(String arriveAreaCode) {
		this.arriveAreaCode = arriveAreaCode;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

}
