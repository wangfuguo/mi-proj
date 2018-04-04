package com.hoau.miser.module.api.itf.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: PriceEventTyQueryParam
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 可用活动查询
 * @date 16/6/8 09:27
 */
public class PriceEventTyQueryEntity implements Serializable {

	private static final long serialVersionUID = 1234213116222593264L;
	/**
	 * 查询时间
	 */
	private Date queryTime;

	/**
	 * 渠道
	 */
	private String orderChannel;

	/**
	 * 组织类型:SEND发货门店\ARRIVAL到达门店
	 */
	private String orgType;

	/**
	 * 组织编号
	 */
	private String orgCode;

	/**
	 * 出发价卡城市
	 */
	private String sendPriceCity;

	/**
	 * 到达价卡城市
	 */
	private String arrivalPriceCity;

	/**
	 * 客户编号
	 */
	private String customerCode;

	/**
	 * 运输类型
	 */
	private String transportType;

	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getSendPriceCity() {
		return sendPriceCity;
	}

	public void setSendPriceCity(String sendPriceCity) {
		this.sendPriceCity = sendPriceCity;
	}

	public String getArrivalPriceCity() {
		return arrivalPriceCity;
	}

	public void setArrivalPriceCity(String arrivalPriceCity) {
		this.arrivalPriceCity = arrivalPriceCity;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
}
