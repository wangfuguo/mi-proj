package com.hoau.miser.module.biz.discount.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class PriceEventRouteDiscountEntity extends BaseEntity{

	private static final long serialVersionUID = 4550935608317355569L;
	
	
	private String eventId;
	private String eventCode;
	private String eventName;
	private String sendPriceCityCode;
	private String arrivalPriceCityCode;
	private String transTypeCode;
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getSendPriceCityCode() {
		return sendPriceCityCode;
	}
	public void setSendPriceCityCode(String sendPriceCityCode) {
		this.sendPriceCityCode = sendPriceCityCode;
	}
	public String getArrivalPriceCityCode() {
		return arrivalPriceCityCode;
	}
	public void setArrivalPriceCityCode(String arrivalPriceCityCode) {
		this.arrivalPriceCityCode = arrivalPriceCityCode;
	}
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	
	
	
	
}
