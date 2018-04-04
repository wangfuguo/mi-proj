package com.hoau.miser.module.api.itf.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: TimeQueryParam
 * @Package com.hoau.miser.module.api.itf.api.shared.vo
 * @Description: 时效查询入参
 * @date 16/6/22 10:23
 */
public class TimeQueryParam implements Serializable {

	private static final long serialVersionUID = 5322997808719693053L;
	/**
	 * 查询类型:0按组织进行查询\1按行政区域查询\2按时效城市查询
	 */
	private Integer queryType;

	/**
	 * 起运地组织编码
	 */
	private String sendOrgCode;

	/**
	 * 目的地组织编码
	 */
	private String arrivalOrgCode;

	/**
	 * 起运地区县编码
	 */
	private String sendDistrictCode;

	/**
	 * 目的地区县编码
	 */
	private String arrivalDistrictCode;

	/**
	 * 出发时效城市
	 */
	private String sendTimeCityCode;

	/**
	 * 目的地时效城市
	 */
	private String arrivalTimeCityCode;

	/**
	 * 运输类型
	 */
	private String transportType;

	/**
	 * 送货方式:0客户自提\其他送货
	 */
	private Integer deliveryType;

	/**
	 * 是否查询历史时效
	 */
	private boolean isHistory;

	/**
	 * 出发时间,查询历史时必须,否则后台默认应用服务器时间
	 */
	private Date sendTime;

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public String getSendOrgCode() {
		return sendOrgCode;
	}

	public void setSendOrgCode(String sendOrgCode) {
		this.sendOrgCode = sendOrgCode;
	}

	public String getArrivalOrgCode() {
		return arrivalOrgCode;
	}

	public void setArrivalOrgCode(String arrivalOrgCode) {
		this.arrivalOrgCode = arrivalOrgCode;
	}

	public String getSendDistrictCode() {
		return sendDistrictCode;
	}

	public void setSendDistrictCode(String sendDistrictCode) {
		this.sendDistrictCode = sendDistrictCode;
	}

	public String getArrivalDistrictCode() {
		return arrivalDistrictCode;
	}

	public void setArrivalDistrictCode(String arrivalDistrictCode) {
		this.arrivalDistrictCode = arrivalDistrictCode;
	}

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

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public boolean isHistory() {
		return isHistory;
	}

	public void setHistory(boolean history) {
		isHistory = history;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
}
