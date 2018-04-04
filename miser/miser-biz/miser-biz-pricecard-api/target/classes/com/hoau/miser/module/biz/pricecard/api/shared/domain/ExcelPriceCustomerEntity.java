package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 客户价格导出excel使用实体
 * ClassName: ExcelPriceCustomerEntity 
 * @author 赵金义
 * @date 2016年1月19日
 * @version V1.0
 */
public class ExcelPriceCustomerEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	// 事业部
	private String divisionName;
	//大区
	private String bigRegionName;
	// 路区
	private String roadAreaName;
	// 门店
	private String orgName;
	
	//物流代码
	private String logistCode;
	
	// 客户编码
	private String customerCode;
	// 客户名称
	private String customerName;
	// 出发价格城市
	private String sendCityName;
	// 到达价格城市
	private String arriveCityName;
	// 运输类型
	private String transTypeName;
	// 重量单价
	private BigDecimal weightPrice;
	// 体积单价
	private BigDecimal volumePrice;
	// 附加费用
	private BigDecimal addFee;
	// 最低收费
	private BigDecimal lowestFee;
	// 燃油费分段
	private String fuelFeeSectionName;
	// 生效时间
	private Date effectiveTime;
	// 失效时间
	private Date invalidTime;
	// 备注
	private String remark;
	// 状态
	private String state;
	// 运费分段
	private String freightFeeSectionName;
	// 重货折扣
	private BigDecimal weightDiscount;
	// 轻货折扣
	private BigDecimal volumeDiscount;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFreightFeeSectionName() {
		return freightFeeSectionName;
	}
	public void setFreightFeeSectionName(String freightFeeSectionName) {
		this.freightFeeSectionName = freightFeeSectionName;
	}
	public BigDecimal getWeightDiscount() {
		return weightDiscount;
	}
	public void setWeightDiscount(BigDecimal weightDiscount) {
		this.weightDiscount = weightDiscount;
	}
	public BigDecimal getVolumeDiscount() {
		return volumeDiscount;
	}
	public void setVolumeDiscount(BigDecimal volumeDiscount) {
		this.volumeDiscount = volumeDiscount;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getBigRegionName() {
		return bigRegionName;
	}
	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}
	public String getRoadAreaName() {
		return roadAreaName;
	}
	public void setRoadAreaName(String roadAreaName) {
		this.roadAreaName = roadAreaName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getSendCityName() {
		return sendCityName;
	}
	public void setSendCityName(String sendCityName) {
		this.sendCityName = sendCityName;
	}
	public String getArriveCityName() {
		return arriveCityName;
	}
	public void setArriveCityName(String arriveCityName) {
		this.arriveCityName = arriveCityName;
	}
	public String getTransTypeName() {
		return transTypeName;
	}
	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}
	public BigDecimal getWeightPrice() {
		return weightPrice;
	}
	public void setWeightPrice(BigDecimal weightPrice) {
		this.weightPrice = weightPrice;
	}
	public BigDecimal getVolumePrice() {
		return volumePrice;
	}
	public void setVolumePrice(BigDecimal volumePrice) {
		this.volumePrice = volumePrice;
	}
	public BigDecimal getAddFee() {
		return addFee;
	}
	public void setAddFee(BigDecimal addFee) {
		this.addFee = addFee;
	}
	public BigDecimal getLowestFee() {
		return lowestFee;
	}
	public void setLowestFee(BigDecimal lowestFee) {
		this.lowestFee = lowestFee;
	}
	public String getFuelFeeSectionName() {
		return fuelFeeSectionName;
	}
	public void setFuelFeeSectionName(String fuelFeeSectionName) {
		this.fuelFeeSectionName = fuelFeeSectionName;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLogistCode() {
		return logistCode;
	}
	public void setLogistCode(String logistCode) {
		this.logistCode = logistCode;
	}
	
}
