package com.hoau.miser.module.biz.discount.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
/**
 * 
 * ClassName: PrivilegeDiscountEntity 
 * @Description: TODO(越发越惠返券记录) 
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月19日
 * @version V1.0
 */
public class PrivilegeCouponEntity extends BaseEntity {

	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6772636564807670699L;
	private String  customerId;
	private String  customerContractId;
	private String  contractCode;
	private Date recordMonth;
	private String hasCoupon;
	private String couponScale;
	private String couponAmount;
	private String couponState;
	private String couponAcceptorId;
	private Date checkTime;
	private String shouldPayAmountSum;
	private String checkRemark;
	private String couponRemark;
	private Date couponTime;
	private String active;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerContractId() {
		return customerContractId;
	}
	public void setCustomerContractId(String customerContractId) {
		this.customerContractId = customerContractId;
	}
	public Date getRecordMonth() {
		return recordMonth;
	}
	public void setRecordMonth(Date recordMonth) {
		this.recordMonth = recordMonth;
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
	public String getCouponAcceptorId() {
		return couponAcceptorId;
	}
	public void setCouponAcceptorId(String couponAcceptorId) {
		this.couponAcceptorId = couponAcceptorId;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getShouldPayAmountSum() {
		return shouldPayAmountSum;
	}
	public void setShouldPayAmountSum(String shouldPayAmountSum) {
		this.shouldPayAmountSum = shouldPayAmountSum;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public String getCouponRemark() {
		return couponRemark;
	}
	public void setCouponRemark(String couponRemark) {
		this.couponRemark = couponRemark;
	}
	public Date getCouponTime() {
		return couponTime;
	}
	public void setCouponTime(Date couponTime) {
		this.couponTime = couponTime;
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
