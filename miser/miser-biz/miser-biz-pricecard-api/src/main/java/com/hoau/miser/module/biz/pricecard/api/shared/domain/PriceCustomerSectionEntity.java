package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 易入户客户价格实体类
 * ClassName: PriceCustomerSectionEntity 
 * @author 何羽
 * @date 2016年5月3日
 * @version V2.0   
 */
public class PriceCustomerSectionEntity implements Serializable {

	private static final long serialVersionUID = -2092018265489206332L;

	//id
    private String id;
    
    //扩展 所属事业部编码 
    private String divisionCode;
    
    //扩展 所属事业部名称
    private String divisionName;
    
    //扩展 所属大区编码
    private String bigRegionCode;
    
    //扩展 所属大区名称
    private String bigRegionName;
    
    //扩展 所属路区编码
    private String roadAreaCode;
    
    //扩展 所属路区名称
    private String roadAreaName;
    
    //扩展 所属门店编码
    private String saleDepartCode;
    
    //扩展 所属门店名称
    private String saleDepartName;
 
    //客户编码
    private String customerCode;
    
    //客户名称
    private String customerName;
    
    //生效时间，客户合同开始时间，生效时间到了之后不允许修改
    private Date effectiveTime;
    
    //失效时间，客户合同结束时间，失效时间过了之后不允许修改
    private Date invalidTime;
    
    //备注
    private String remark;	
    
    //创建时间
    private Date createTime;
    
    //创建人
    private String createUserCode;
    
    //扩展 创建人名称
    private String createUserName;
    
    //更新时间
    private Date modifyTime;
    
    //更新人
    private String modifyUserCode;	
    
    //扩展 修改人名称
    private String modifyUserName;
    
    //是否可用
    private String active;

    //扩展 状态 
    private String status;
    
    //add by dengyin 2016-2-22 14:34:26 添加 数据来源
    
    private String dataOrign;
    
    private String statusName;

	/**
	 * Add by Chenyl @ 20160322 物流代码
	 */
	private String logisticName;
	
	//add by dengyin 2016-3-24 15:38:31 添加物流代码
	private String logistCode;
    
	public String getDataOrign() {
		return dataOrign;
	}

	public void setDataOrign(String dataOrign) {
		this.dataOrign = dataOrign;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode == null ? null : customerCode.trim();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getBigRegionCode() {
		return bigRegionCode;
	}

	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}

	public String getBigRegionName() {
		return bigRegionName;
	}

	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}

	public String getRoadAreaCode() {
		return roadAreaCode;
	}

	public void setRoadAreaCode(String roadAreaCode) {
		this.roadAreaCode = roadAreaCode;
	}

	public String getRoadAreaName() {
		return roadAreaName;
	}

	public void setRoadAreaName(String roadAreaName) {
		this.roadAreaName = roadAreaName;
	}

	public String getSaleDepartCode() {
		return saleDepartCode;
	}

	public void setSaleDepartCode(String saleDepartCode) {
		this.saleDepartCode = saleDepartCode;
	}

	public String getSaleDepartName() {
		return saleDepartName;
	}

	public void setSaleDepartName(String saleDepartName) {
		this.saleDepartName = saleDepartName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if(status.equals("EFFECTIVE")){
			this.statusName = "生效中";
		}else if(status.equals("DELETED")){
			this.statusName = "已作废";
		}else if(status.equals("PASSED")){
			this.statusName = "已失效";
		}else if(status.equals("WAIT")){
			this.statusName = "待生效";
		}
		this.status = status;
	}

	public String getLogisticName() {
		return logisticName;
	}

	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}
	
	public String getLogistCode() {
		return logistCode;
	}

	public void setLogistCode(String logistCode) {
		this.logistCode = logistCode;
	}	
}
