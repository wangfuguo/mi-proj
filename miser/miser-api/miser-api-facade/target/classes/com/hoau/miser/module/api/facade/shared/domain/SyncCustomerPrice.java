package com.hoau.miser.module.api.facade.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class SyncCustomerPrice extends BaseEntity{
	private static final long serialVersionUID = 7429603350579686153L;
	private String customerCode;//客户编号
    private String transTypeCode;//运输类型编号
    private BigDecimal heavyFloatPrice;//重货浮动浮动金额
    private BigDecimal lightFloatPrice;//轻货浮动浮动金额
    private BigDecimal floatPercentage;//浮动百分比
    private Date effectiveTime;//生效时间
    private Date invalidTime;//失效时间
    private String userCode;
    
    private String freightSectionPrice;//运费分段金额
    
	public String getFreightSectionPrice() {
		return freightSectionPrice;
	}
	public void setFreightSectionPrice(String freightSectionPrice) {
		this.freightSectionPrice = freightSectionPrice;
	}
	
	
	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	public BigDecimal getHeavyFloatPrice() {
		return heavyFloatPrice;
	}
	public void setHeavyFloatPrice(BigDecimal heavyFloatPrice) {
		this.heavyFloatPrice = heavyFloatPrice;
	}
	public BigDecimal getLightFloatPrice() {
		return lightFloatPrice;
	}
	public void setLightFloatPrice(BigDecimal lightFloatPrice) {
		this.lightFloatPrice = lightFloatPrice;
	}
	public BigDecimal getFloatPercentage() {
		return floatPercentage;
	}
	public void setFloatPercentage(BigDecimal floatPercentage) {
		this.floatPercentage = floatPercentage;
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
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	
    
}
