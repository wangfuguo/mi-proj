package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 越发越惠客户合同实体类
 * ClassName: PrivilegeContractEntity 
 * @author 286330付于令
 * @date 2016年1月12日
 * @version V1.0
 */
public class PrivilegeContractInfo implements Serializable {
	private static final long serialVersionUID = 1931255496984730892L;
	/* 数据库映射字段 */
	private String customerCode; // 客户编号
	private String customerName; // 客户名称
	private String contactName; // 联系人
	private String contactPhone; // 联系电话
	private Date contractStartTime; // 合同开始时间
	private Date contractEndTime; // 合同结束时间
	private Date privilegeStartTime; // 越发越惠开始时间
	private Date privilegeEndTime; // 越发越惠结束时间
	private String state; // 越发越惠当前状态
	private String hasCoupon; // 是否返券
	private String remark; // 备注
	private String active;  // 是否可用
	private List<PrivilegeContractDetailInfo> detail; // 明细
	   
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
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public Date getContractStartTime() {
		return contractStartTime;
	}
	public void setContractStartTime(Date contractStartTime) {
		this.contractStartTime = contractStartTime;
	}
	public Date getContractEndTime() {
		return contractEndTime;
	}
	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}
	public Date getPrivilegeStartTime() {
		return privilegeStartTime;
	}
	public void setPrivilegeStartTime(Date privilegeStartTime) {
		this.privilegeStartTime = privilegeStartTime;
	}
	public Date getPrivilegeEndTime() {
		return privilegeEndTime;
	}
	public void setPrivilegeEndTime(Date privilegeEndTime) {
		this.privilegeEndTime = privilegeEndTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getHasCoupon() {
		return hasCoupon;
	}
	public void setHasCoupon(String hasCoupon) {
		this.hasCoupon = hasCoupon;
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
	public List<PrivilegeContractDetailInfo> getDetail() {
		return detail;
	}
	public void setDetail(List<PrivilegeContractDetailInfo> detail) {
		this.detail = detail;
	}
	
}
