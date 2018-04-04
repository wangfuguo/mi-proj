package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 易入户客户价格导出excel使用实体
 * ClassName: ExcelPriceCustomerSectionEntity 
 * @author 何羽
 * @date 2016年5月9日
 * @version V2.0
 */
public class ExcelPriceCustomerSectionEntity extends BaseEntity {
	
	private static final long serialVersionUID = -763064974861753756L;
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
	//一段首重
    private BigDecimal firstWeight;
    
    //一段首重单价
    private BigDecimal firstWeightPrice;
    
    //一段续重单价
    private BigDecimal firstAddWeightPrice;

    //二段首重
    private BigDecimal secondWeight;
    
    //二段首重单价
    private BigDecimal secondWeightPrice;
    
    //二段续重单价
    private BigDecimal secondAddWeightPrice;
    
    //三段首重
    private BigDecimal thirdWeight;
    
    //三段首重单价
    private BigDecimal thirdWeightPrice;
    
    //三段续重单价
    private BigDecimal thirdAddWeightPrice;
    
	// 生效时间
	private Date effectiveTime;
	// 失效时间
	private Date invalidTime;
	// 备注
	private String remark;
	// 状态
	private String state;
	//运费优惠分段名字
    private String freightSectionCodeName;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public BigDecimal getFirstWeight() {
		return firstWeight;
	}
	public void setFirstWeight(BigDecimal firstWeight) {
		this.firstWeight = firstWeight;
	}
	public BigDecimal getFirstWeightPrice() {
		return firstWeightPrice;
	}
	public void setFirstWeightPrice(BigDecimal firstWeightPrice) {
		this.firstWeightPrice = firstWeightPrice;
	}
	public BigDecimal getFirstAddWeightPrice() {
		return firstAddWeightPrice;
	}
	public void setFirstAddWeightPrice(BigDecimal firstAddWeightPrice) {
		this.firstAddWeightPrice = firstAddWeightPrice;
	}
	public BigDecimal getSecondWeight() {
		return secondWeight;
	}
	public void setSecondWeight(BigDecimal secondWeight) {
		this.secondWeight = secondWeight;
	}
	public BigDecimal getSecondWeightPrice() {
		return secondWeightPrice;
	}
	public void setSecondWeightPrice(BigDecimal secondWeightPrice) {
		this.secondWeightPrice = secondWeightPrice;
	}
	public BigDecimal getSecondAddWeightPrice() {
		return secondAddWeightPrice;
	}
	public void setSecondAddWeightPrice(BigDecimal secondAddWeightPrice) {
		this.secondAddWeightPrice = secondAddWeightPrice;
	}
	public BigDecimal getThirdWeight() {
		return thirdWeight;
	}
	public void setThirdWeight(BigDecimal thirdWeight) {
		this.thirdWeight = thirdWeight;
	}
	public BigDecimal getThirdWeightPrice() {
		return thirdWeightPrice;
	}
	public void setThirdWeightPrice(BigDecimal thirdWeightPrice) {
		this.thirdWeightPrice = thirdWeightPrice;
	}
	public BigDecimal getThirdAddWeightPrice() {
		return thirdAddWeightPrice;
	}
	public void setThirdAddWeightPrice(BigDecimal thirdAddWeightPrice) {
		this.thirdAddWeightPrice = thirdAddWeightPrice;
	}
	public String getFreightSectionCodeName() {
		return freightSectionCodeName;
	}
	public void setFreightSectionCodeName(String freightSectionCodeName) {
		this.freightSectionCodeName = freightSectionCodeName;
	}
	
}
