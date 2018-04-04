package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 越发越惠折扣记录
 * ClassName: PrivilegeDiscountEntity 
 * @author 286330付于令
 * @date 2016年2月25日
 * @version V1.0
 */
public class PrivilegeDiscountInfo implements Serializable {
	private static final long serialVersionUID = 4322857959271764305L;
	
	private String  customerCode;
	private Date recordMonth;
	private Double lastLastMonthAmount;
	private Double lastMonthAmount;
	private Double lastTwoMonthsAverageAmount;
	private Double currentMonthAmount;
	private Double currentDuDiscount;
	private Double currentDdDiscount;
	private String active;

	public Date getRecordMonth() {
		return recordMonth;
	}
	public void setRecordMonth(Date recordMonth) {
		this.recordMonth = recordMonth;
	}
	public Double getLastLastMonthAmount() {
		return lastLastMonthAmount;
	}
	public void setLastLastMonthAmount(Double lastLastMonthAmount) {
		this.lastLastMonthAmount = lastLastMonthAmount;
	}
	public Double getLastMonthAmount() {
		return lastMonthAmount;
	}
	public void setLastMonthAmount(Double lastMonthAmount) {
		this.lastMonthAmount = lastMonthAmount;
	}
	public Double getLastTwoMonthsAverageAmount() {
		return lastTwoMonthsAverageAmount;
	}
	public void setLastTwoMonthsAverageAmount(Double lastTwoMonthsAverageAmount) {
		this.lastTwoMonthsAverageAmount = lastTwoMonthsAverageAmount;
	}
	public Double getCurrentMonthAmount() {
		return currentMonthAmount;
	}
	public void setCurrentMonthAmount(Double currentMonthAmount) {
		this.currentMonthAmount = currentMonthAmount;
	}
	public Double getCurrentDuDiscount() {
		return currentDuDiscount;
	}
	public void setCurrentDuDiscount(Double currentDuDiscount) {
		this.currentDuDiscount = currentDuDiscount;
	}
	public Double getCurrentDdDiscount() {
		return currentDdDiscount;
	}
	public void setCurrentDdDiscount(Double currentDdDiscount) {
		this.currentDdDiscount = currentDdDiscount;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
