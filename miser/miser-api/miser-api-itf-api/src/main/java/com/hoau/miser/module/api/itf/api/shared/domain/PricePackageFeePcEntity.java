package com.hoau.miser.module.api.itf.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 价格城市包装费
 *
 * @author 蒋落琛
 * @date 2016-6-15下午5:43:41
 */
public class PricePackageFeePcEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/**编号 CODE */
    private String code;
    
    /**名称 NAME*/
    private String name;
	
	/**
	 * PRICE_CITY 价格城市
	 */
	private String priceCity;
	/**
	 * 价格城市名称
	 */
	private String priceCityName;
	/**
	 * 运输类型
	 */
	private String transTypeCode;
	/**
	 * 运输类型
	 */
	private String transTypeName;
	
	/**
	 * CALCULATION_TYPE 金额计算方式
	 */
	private String calculationType;
	
	/**
	 * MONEY 单价
	 */
	private Double money;
	
	/**
	 * RATE 系数
	 */
	private Double rate;
	
	/**
	 * MIN_MONEY 最低价格
	 */
	private Double minMoney;
	
	/**
	 * MAX_MONEY2 最高收费
	 */
	private Double maxMoney2;
	
	/**
	 * LOCK_TYPE 锁定方式 
	 * 0 不锁定 1 向上锁定 2 向下锁定 3 完全锁定
	 */
	private String lockType;
	
	/**
	 *  REMARK 备注
	 */
	private String remark;
	
	//生效时间-EFFECTIVE_TIME
	private Date effectiveTime;
	
	/**
	 * 失效时间
	 */
	private Date invalidTime;
	
	/**
	 * 是否有效
	 */
	private String active;

	/** 开单时间 **/
    private Date billTime;
    /** 是否历史**/
    private boolean isHistory;
	
	public String getTransTypeCode() {
		return transTypeCode;
	}

	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	public String getPriceCity() {
		return priceCity;
	}

	public void setPriceCity(String priceCity) {
		this.priceCity = priceCity;
	}

	
	public String getPriceCityName() {
		return priceCityName;
	}

	public void setPriceCityName(String priceCityName) {
		this.priceCityName = priceCityName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCalculationType() {
		return calculationType;
	}

	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}

	public Double getMaxMoney2() {
		return maxMoney2;
	}

	public void setMaxMoney2(Double maxMoney2) {
		this.maxMoney2 = maxMoney2;
	}

	public String getLockType() {
		return lockType;
	}

	public void setLockType(String lockType) {
		this.lockType = lockType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public boolean isHistory() {
		return isHistory;
	}

	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}
}

