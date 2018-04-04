package com.hoau.miser.module.api.itf.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: TimeQueryResult
 * @Package com.hoau.miser.module.api.itf.api.shared.vo
 * @Description: 时效查询出参
 * @date 16/6/22 10:24
 */
public class TimeQueryResult implements Serializable {

	private static final long serialVersionUID = -5445562065014555875L;
	/**
	 * 出发时效城市
	 */
	private String sendTimeCityCode;

	/**
	 * 到达时效城市
	 */
	private String arrivalTimeCityCode;

	/**
	 * 运输类型
	 */
	private String transportType;

	/**
	 * 自提最小通知天数
	 */
	private Integer minDay;

	/**
	 * 自提最大通知天数
	 */
	private Integer maxDay;

	/**
	 * 送货通知天数
	 */
	private Integer deliveryDay;

	/**
	 * 最小预计到达时间
	 */
	private Date minExpectArrivalTime;

	/**
	 * 最大预计到达时间
	 */
	private Date maxExpectArrivalTime;

	public String getSendTimeCityCode() {
		return sendTimeCityCode;
	}

	public void setSendTimeCityCode(String sendTimeCityCode) {
		this.sendTimeCityCode = sendTimeCityCode;
	}

	public String getArrivalTimeCityCode() {
		return arrivalTimeCityCode;
	}

	public void setArrivalTimeCityCode(String arrivalTimeCityCode) {
		this.arrivalTimeCityCode = arrivalTimeCityCode;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public Integer getMinDay() {
		return minDay;
	}

	public void setMinDay(Integer minDay) {
		this.minDay = minDay;
	}

	public Integer getMaxDay() {
		return maxDay;
	}

	public void setMaxDay(Integer maxDay) {
		this.maxDay = maxDay;
	}

	public Integer getDeliveryDay() {
		return deliveryDay;
	}

	public void setDeliveryDay(Integer deliveryDay) {
		this.deliveryDay = deliveryDay;
	}

	public Date getMinExpectArrivalTime() {
		return minExpectArrivalTime;
	}

	public void setMinExpectArrivalTime(Date minExpectArrivalTime) {
		this.minExpectArrivalTime = minExpectArrivalTime;
	}

	public Date getMaxExpectArrivalTime() {
		return maxExpectArrivalTime;
	}

	public void setMaxExpectArrivalTime(Date maxExpectArrivalTime) {
		this.maxExpectArrivalTime = maxExpectArrivalTime;
	}
}
