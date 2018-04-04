package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.math.BigDecimal;


public class ExcelTimeStandVo {

    private String transTypeName;

    private String sendTimeCityName;
    
    private String arriveTimeCityName;

    private String  minNotifyDay;

    private String maxNotifyDay;

    private String pickupPromDay;

    private String deliveryPromDay;

    private String effectiveTime;

    private String invalidTime;

    private String remark;
    
    private String oldSerial;
    
    private String errorMess;

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	public String getSendTimeCityName() {
		return sendTimeCityName;
	}

	public void setSendTimeCityName(String sendTimeCityName) {
		this.sendTimeCityName = sendTimeCityName;
	}

	public String getArriveTimeCityName() {
		return arriveTimeCityName;
	}

	public void setArriveTimeCityName(String arriveTimeCityName) {
		this.arriveTimeCityName = arriveTimeCityName;
	}

	public String getMinNotifyDay() {
		return minNotifyDay;
	}

	public void setMinNotifyDay(String minNotifyDay) {
		this.minNotifyDay = minNotifyDay;
	}

	public String getMaxNotifyDay() {
		return maxNotifyDay;
	}

	public void setMaxNotifyDay(String maxNotifyDay) {
		this.maxNotifyDay = maxNotifyDay;
	}

	public String getPickupPromDay() {
		return pickupPromDay;
	}

	public void setPickupPromDay(String pickupPromDay) {
		this.pickupPromDay = pickupPromDay;
	}

	public String getDeliveryPromDay() {
		return deliveryPromDay;
	}

	public void setDeliveryPromDay(String deliveryPromDay) {
		this.deliveryPromDay = deliveryPromDay;
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
	
	public String getOldSerial() {
		return oldSerial;
	}

	public void setOldSerial(String oldSerial) {
		this.oldSerial = oldSerial;
	}

	public String getErrorMess() {
		return errorMess;
	}

	public void setErrorMess(String errorMess) {
		this.errorMess = errorMess;
	}
}
