package com.hoau.miser.module.api.itf.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: PriceEventTyQueryParam
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 可用活动查询
 * @date 16/6/8 09:27
 */
public class PriceEventTyQueryParam implements Serializable {

	private static final long serialVersionUID = 1234213116222593264L;

	/**
	 * 是否查询历史活动
	 */
	private boolean isHistory;

	/**
	 * 查询时间
	 */
	private Date queryTime;

	/**
	 * 渠道
	 */
	private String orderChannel;

	/**
	 * 出发组织
	 */
	private String sendOrgCode;

	/**
	 * 到达组织
	 */
	private String arrivalOrgCode;

	/**
	 * 客户编号
	 */
	private String customerCode;

	/**
	 * 运输类型
	 */
	private String transportType;
	
	/**
     * 起运地公司省市区信息
     */
    private OrgPositionInfoTyEntity originPositionInfo;
    
    /**
     * 目的地公司省市区信息
     */
    private OrgPositionInfoTyEntity destPositionInfo;

	public boolean isHistory() {
		return isHistory;
	}

	public void setHistory(boolean history) {
		isHistory = history;
	}

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

	public OrgPositionInfoTyEntity getOriginPositionInfo() {
		return originPositionInfo;
	}

	public void setOriginPositionInfo(OrgPositionInfoTyEntity originPositionInfo) {
		this.originPositionInfo = originPositionInfo;
	}

	public OrgPositionInfoTyEntity getDestPositionInfo() {
		return destPositionInfo;
	}

	public void setDestPositionInfo(OrgPositionInfoTyEntity destPositionInfo) {
		this.destPositionInfo = destPositionInfo;
	}
}
