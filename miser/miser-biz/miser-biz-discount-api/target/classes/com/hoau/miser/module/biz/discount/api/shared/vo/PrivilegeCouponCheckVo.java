package com.hoau.miser.module.biz.discount.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 越发越惠返券审核
 * ClassName: PrivilegeCouponCheckVo 
 * @author 付于令
 * @date 2016年01月18日
 * @version V1.0
 */
public class PrivilegeCouponCheckVo implements Serializable {
	private static final long serialVersionUID = -4456286024126547645L;
	/* 合同字段 */
	private String id; // 合同ID
	private String contractCode; // 合同CODE
	private String customerCode; // 客户编号
	private String customerName; // 客户名称
	private String contactName; // 联系人
	private String contactPhone; // 联系人手机
	private Date privilegeStartTime; // 越发越惠开始时间
	private Date privilegeEndTime; // 越发越惠结束时间
	private String active; // 是否可用
	/* 折扣字段 */
	private Date recordMonth; // 执行年月
	private String currentMonthAmount; // 当月产值
	/* 返券字段 */
	private String couponId; // 返券ID
	private String hasCoupon; // 是否返券
	private String couponScale; // 返券比例
	private String couponAmount; // 返券值
	private String couponState; // 返券状态
	private String checkRemark; // 审核说明
	/* 组织字段 */
	private String seaSalesdepartment; // 所属门店
	private String seaRoadarea; // 所属路区
	private String seaBigregion; // 所属大区
	private String seaDivision; // 所属事业部
	/* 返券人字段 */
	private String acceptorName; // 收券人姓名
	private String relationshipWithCustomer; // 与客户关系
	private String acceptorPhone; // 收券人手机
	private String identityCardNo; // 收券人身份证号
	private String creditCardNo; // 收券人银行账号
	private String bankName; // 收券人开户行
	private String subbranchName; // 开户支行
	private String alipayAccount; // 支付宝账号
	
	/* 其他字段 */
	private String checkState; // 审核状态
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Date getRecordMonth() {
		return recordMonth;
	}
	public void setRecordMonth(Date recordMonth) {
		this.recordMonth = recordMonth;
	}
	public String getCurrentMonthAmount() {
		return currentMonthAmount;
	}
	public void setCurrentMonthAmount(String currentMonthAmount) {
		this.currentMonthAmount = currentMonthAmount;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getHasCoupon() {
		return hasCoupon;
	}
	public void setHasCoupon(String hasCoupon) {
		this.hasCoupon = hasCoupon;
	}
	public String getCouponScale() {
		return couponScale;
	}
	public void setCouponScale(String couponScale) {
		this.couponScale = couponScale;
	}
	public String getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}
	public String getCouponState() {
		return couponState;
	}
	public void setCouponState(String couponState) {
		this.couponState = couponState;
	}
	public String getSeaSalesdepartment() {
		return seaSalesdepartment;
	}
	public void setSeaSalesdepartment(String seaSalesdepartment) {
		this.seaSalesdepartment = seaSalesdepartment;
	}
	public String getSeaRoadarea() {
		return seaRoadarea;
	}
	public void setSeaRoadarea(String seaRoadarea) {
		this.seaRoadarea = seaRoadarea;
	}
	public String getSeaBigregion() {
		return seaBigregion;
	}
	public void setSeaBigregion(String seaBigregion) {
		this.seaBigregion = seaBigregion;
	}
	public String getSeaDivision() {
		return seaDivision;
	}
	public void setSeaDivision(String seaDivision) {
		this.seaDivision = seaDivision;
	}
	public String getAcceptorName() {
		return acceptorName;
	}
	public void setAcceptorName(String acceptorName) {
		this.acceptorName = acceptorName;
	}
	public String getRelationshipWithCustomer() {
		return relationshipWithCustomer;
	}
	public void setRelationshipWithCustomer(String relationshipWithCustomer) {
		this.relationshipWithCustomer = relationshipWithCustomer;
	}
	public String getAcceptorPhone() {
		return acceptorPhone;
	}
	public void setAcceptorPhone(String acceptorPhone) {
		this.acceptorPhone = acceptorPhone;
	}
	public String getCreditCardNo() {
		return creditCardNo;
	}
	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}
	public String getIdentityCardNo() {
		return identityCardNo;
	}
	public void setIdentityCardNo(String identityCardNo) {
		this.identityCardNo = identityCardNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getSubbranchName() {
		return subbranchName;
	}
	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	/**
	 * @return the checkRemark
	 */
	public String getCheckRemark() {
		return checkRemark;
	}
	/**
	 * @param checkRemark the checkRemark to set
	 */
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	/**
	 * @return the alipayAccount
	 */
	public String getAlipayAccount() {
		return alipayAccount;
	}
	/**
	 * @param alipayAccount the alipayAccount to set
	 */
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return the contractCode
	 */
	public String getContractCode() {
		return contractCode;
	}
	/**
	 * @param contractCode the contractCode to set
	 */
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
}
