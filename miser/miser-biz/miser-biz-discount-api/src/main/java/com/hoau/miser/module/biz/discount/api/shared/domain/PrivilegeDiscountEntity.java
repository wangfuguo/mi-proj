package com.hoau.miser.module.biz.discount.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
/**
 * 
 * ClassName: PrivilegeDiscountEntity 
 * @Description: TODO(越发越惠折扣记录) 
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月19日
 * @version V1.0
 */
public class PrivilegeDiscountEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 4322857959271764305L;
	private String  customerId;
	private String  customerContractId;
	private String contractCode; // 合同编号
	private Date recordMonth;
	private String lastLastMonthAmount;
	private String lastMonthAmount;
	private String lastTwoMonthsAverageAmount;
	private String currentMonthAmount;
	private String currentDuDiscount;
	private String currentDdDiscount;
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
	public String getActive() {
		return active;
	}
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
