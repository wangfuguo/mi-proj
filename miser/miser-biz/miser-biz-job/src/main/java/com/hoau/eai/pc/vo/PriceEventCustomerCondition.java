package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;

public class PriceEventCustomerCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	//活动编码 EVENT_CODE
	private String eventCode;
	//客户编号 CUSTOMER_CODE
	private String customerCode;
	//客户名称 CUSTOMER_NAME
	private String customerName;
	//备注 REMARK
	private String remark;
	//是否有效 ACTIVE
	private String active;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 修改日期
	 */
	private Date modifyDate;

	/**
	 * 修改人
	 */
	private String modifyUser;

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
