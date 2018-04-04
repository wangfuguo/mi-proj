package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 越发越惠折扣记录
 * ClassName: DiscountPrivilegeTyEntity
 * @author 廖文强
 * @date 2016年06月13日
 * @version V1.0
 */
public class DiscountPrivilegeTyEntity extends BaseEntity {
	private static final long serialVersionUID = 4322857959271764305L;

	private String  customerCode;
	private Date recordMonth;
	private BigDecimal lastLastMonthAmount;
	private BigDecimal lastMonthAmount;
	private BigDecimal lastTwoMonthsAverageAmount;
	private BigDecimal currentMonthAmount;
	private BigDecimal currentDuDiscount;
	private BigDecimal currentDdDiscount;
	private String active;
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public Date getRecordMonth() {
		return recordMonth;
	}
	public void setRecordMonth(Date recordMonth) {
		this.recordMonth = recordMonth;
	}
	public BigDecimal getLastLastMonthAmount() {
		return lastLastMonthAmount;
	}
	public void setLastLastMonthAmount(BigDecimal lastLastMonthAmount) {
		this.lastLastMonthAmount = lastLastMonthAmount;
	}
	public BigDecimal getLastMonthAmount() {
		return lastMonthAmount;
	}
	public void setLastMonthAmount(BigDecimal lastMonthAmount) {
		this.lastMonthAmount = lastMonthAmount;
	}
	public BigDecimal getLastTwoMonthsAverageAmount() {
		return lastTwoMonthsAverageAmount;
	}
	public void setLastTwoMonthsAverageAmount(BigDecimal lastTwoMonthsAverageAmount) {
		this.lastTwoMonthsAverageAmount = lastTwoMonthsAverageAmount;
	}
	public BigDecimal getCurrentMonthAmount() {
		return currentMonthAmount;
	}
	public void setCurrentMonthAmount(BigDecimal currentMonthAmount) {
		this.currentMonthAmount = currentMonthAmount;
	}
	public BigDecimal getCurrentDuDiscount() {
		return currentDuDiscount;
	}
	public void setCurrentDuDiscount(BigDecimal currentDuDiscount) {
		this.currentDuDiscount = currentDuDiscount;
	}
	public BigDecimal getCurrentDdDiscount() {
		return currentDdDiscount;
	}
	public void setCurrentDdDiscount(BigDecimal currentDdDiscount) {
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

}
