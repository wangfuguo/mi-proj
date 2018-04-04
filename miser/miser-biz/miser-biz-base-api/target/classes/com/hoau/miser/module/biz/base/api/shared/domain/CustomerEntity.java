package com.hoau.miser.module.biz.base.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * ClassName: CustomerEntity 
 * @Description: 客户信息实体 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年3月8日
 * @version V1.0   
 */
public class CustomerEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : 序列化ID
	 */
	private static final long serialVersionUID = 799824872267252993L;

	/**
	 * @Fields code : 客户编码
	 */
	private String code;
	
	/**
	 * @Fields name : 客户名称
	 */
	private String name;
	
	/**
	 * @Fields orgCode : 客户所属组织
	 */
	private String orgCode;
	
	/**
	 * @Fields orgName : 客户所属组织名称
	 */
	private String orgName;
	
	/**
	 * @Fields logisticName : 客户所属组织物流代码
	 */
	private String logisticName;
	
	/**
	 * @Fields contractStartTime : 合同开始时间
	 */
	private Date contractStartTime;
	
	/**
	 * @Fields contractEndTime : 合同结束时间
	 */
	private Date contractEndTime;
	
	/**
	 * @Fields pcStartTime : 价格开始时间
	 */
	private Date pcStartTime;
	
	/**
	 * @Fields pcEndTime : 价格结束时间
	 */
	private Date pcEndTime;
	
	/**
	 * @Fields useCustomerPc : 是否启用客户价格
	 */
	private String useCustomerPc;
	
	/**
	 * @Fields useCustomerDiscount : 是否启用客户折扣
	 */
	private String useCustomerDiscount;
	
	/**
	 * @Fields showDiscountPrice : 签收单是否显示运费金额
	 */
	private String showMoneyOnSignBill;
	
	/**
	 * @Fields showDiscountPrice : 易到家产品使用定日达价格
	 */
	private String ydjUseDrd;
	
	/**
	 * @Fields remark : 备注
	 */
	private String remark;
	
	/**
	 * @Fields active : 是否可用
	 */
	private String active;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLogisticName() {
		return logisticName;
	}

	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}

	public Date getContractStartTime() {
		return contractStartTime;
	}

	public void setContractStartTime(Date contractStartTime) {
		this.contractStartTime = contractStartTime;
	}

	public Date getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}

	public Date getPcStartTime() {
		return pcStartTime;
	}

	public void setPcStartTime(Date pcStartTime) {
		this.pcStartTime = pcStartTime;
	}

	public Date getPcEndTime() {
		return pcEndTime;
	}

	public void setPcEndTime(Date pcEndTime) {
		this.pcEndTime = pcEndTime;
	}

	public String getUseCustomerPc() {
		return useCustomerPc;
	}

	public void setUseCustomerPc(String useCustomerPc) {
		this.useCustomerPc = useCustomerPc;
	}

	public String getUseCustomerDiscount() {
		return useCustomerDiscount;
	}

	public void setUseCustomerDiscount(String useCustomerDiscount) {
		this.useCustomerDiscount = useCustomerDiscount;
	}

	public String getShowMoneyOnSignBill() {
		return showMoneyOnSignBill;
	}

	public void setShowMoneyOnSignBill(String showMoneyOnSignBill) {
		this.showMoneyOnSignBill = showMoneyOnSignBill;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getYdjUseDrd() {
		return ydjUseDrd;
	}

	public void setYdjUseDrd(String ydjUseDrd) {
		this.ydjUseDrd = ydjUseDrd;
	}
	
}
