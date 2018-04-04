package com.hoau.miser.module.biz.job.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 越发越惠返券记录
 * ClassName: PrivilegeCouponEntity 
 * @author 286330付于令
 * @date 2016年2月25日
 * @version V1.0
 */
public class PrivilegeCouponEntity extends BaseEntity {
	private static final long serialVersionUID = -6772636564807670699L;
	
	private String customerCode;
	private PrivilegeCouponAcceptorEntity acceptor;
	private Date recordMonth;
	private String hasCoupon;
	private Double couponScale;
	private Double couponAmount;
	private String couponState;
	private Date checkTime;
	private Double shouldPayAmountSum;
	private String checkRemark;
	private String couponRemark;
	private Date couponTime;
	private String active;
	
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
	public Double getCouponScale() {
		return couponScale;
	}
	public void setCouponScale(Double couponScale) {
		this.couponScale = couponScale;
	}
	public Double getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}
	public String getCouponState() {
		return couponState;
	}
	public void setCouponState(String couponState) {
		this.couponState = couponState;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Double getShouldPayAmountSum() {
		return shouldPayAmountSum;
	}
	public void setShouldPayAmountSum(Double shouldPayAmountSum) {
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
	 * @return the acceptor
	 */
	public PrivilegeCouponAcceptorEntity getAcceptor() {
		return acceptor;
	}
	/**
	 * @param acceptor the acceptor to set
	 */
	public void setAcceptor(PrivilegeCouponAcceptorEntity acceptor) {
		this.acceptor = acceptor;
	}
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

}
