package com.hoau.miser.module.biz.discount.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * ClassName: PrivilegeStateQueryVo 
 * @Description: TODO(越发越惠状态查询VO) 
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月15日
 * @version V1.0
 */
public class PrivilegeStateQueryVo implements Serializable {


	private static final long serialVersionUID = -3104909347986538527L;
	private String htid;
	private String contractCode;
	private String customerCode;
	private String customerName;
	private String contactName;
	private String contactPhone;
	private Date contractStartTime;
	private Date contractEndTime;
	private Date privilegeStartTime;
	private Date privilegeEndTime;
	private String tpdId;
	private Date recordMonth;
	private String lastLastMonthAmount;
	private String lastMonthAmount;
	private String lastTwoMonthsAverageAmount;
	private String currentMonthAmount;
	private String currentDuDiscount;
	private String currentDdDiscount;
	private String tpcId;
	private String hasCoupon;
	private String couponScale;
	private String couponAmount;
	private String shouldPayAmountSum;
	private String couponState;
	private String checkRemark;
	private Date checkTime;
	private String couponRemark;
	private Date couponTime;
	private String seaSalesdepartment;
	private String seaRoadarea;
	private String seaBigregion;
	private String seaDivision;
	
	public String getHtid() {
		return htid;
	}
	public void setHtid(String htid) {
		this.htid = htid;
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
	public String getTpdId() {
		return tpdId;
	}
	public void setTpdId(String tpdId) {
		this.tpdId = tpdId;
	}
	public String getLastLastMonthAmount() {
		return lastLastMonthAmount;
	}
	public void setLastLastMonthAmount(String lastLastMonthAmount) {
		this.lastLastMonthAmount = lastLastMonthAmount;
	}
	public String getLastMonthAmount() {
		return lastMonthAmount;
	}
	public void setLastMonthAmount(String lastMonthAmount) {
		this.lastMonthAmount = lastMonthAmount;
	}
	public String getLastTwoMonthsAverageAmount() {
		return lastTwoMonthsAverageAmount;
	}
	public void setLastTwoMonthsAverageAmount(String lastTwoMonthsAverageAmount) {
		this.lastTwoMonthsAverageAmount = lastTwoMonthsAverageAmount;
	}
	public String getCurrentMonthAmount() {
		return currentMonthAmount;
	}
	public void setCurrentMonthAmount(String currentMonthAmount) {
		this.currentMonthAmount = currentMonthAmount;
	}
	public String getCurrentDuDiscount() {
		return currentDuDiscount;
	}
	public void setCurrentDuDiscount(String currentDuDiscount) {
		this.currentDuDiscount = currentDuDiscount;
	}
	public String getCurrentDdDiscount() {
		return currentDdDiscount;
	}
	public void setCurrentDdDiscount(String currentDdDiscount) {
		this.currentDdDiscount = currentDdDiscount;
	}
	public String getTpcId() {
		return tpcId;
	}
	public void setTpcId(String tpcId) {
		this.tpcId = tpcId;
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
	public String getShouldPayAmountSum() {
		return shouldPayAmountSum;
	}
	public void setShouldPayAmountSum(String shouldPayAmountSum) {
		this.shouldPayAmountSum = shouldPayAmountSum;
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
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
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
	/**
	 * @return the contractStartTime
	 */
	public Date getContractStartTime() {
		return contractStartTime;
	}
	/**
	 * @param contractStartTime the contractStartTime to set
	 */
	public void setContractStartTime(Date contractStartTime) {
		this.contractStartTime = contractStartTime;
	}
	/**
	 * @return the contractEndTime
	 */
	public Date getContractEndTime() {
		return contractEndTime;
	}
	/**
	 * @param contractEndTime the contractEndTime to set
	 */
	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}
	
	
}
