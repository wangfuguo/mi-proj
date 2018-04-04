package com.hoau.miser.module.biz.job.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 联系人信息实体
 *
 * @author 蒋落琛
 * @date 2015-5-19
 */
public class ContactEntity extends BaseEntity{

	private static final long serialVersionUID = 2038402988719124122L;
	
	/**
	 * 客户UUID
	 */
	private String accountId;
	
	/**
	 * 联系人姓名
	 */
	private String contactName;
	
	/**
	 * 区号
	 */
	private String districtNumber;
	
	/**
	 * 座机
	 */
	private String telephone;
	
	/**
	 * 电子邮件
	 */
	private String eMailAddress;
	
	/**
	 * 手机
	 */
	private String cellphone;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 是否默认
	 */
	private String isDefault;
	
	/**
	 * 职位
	 */
	private String job;
	
	/**
	 *负责人
	 */
	private String manageEmpCode;
	
	/**
	 * 来源
	 */
	private String source;
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDistrictNumber() {
		return districtNumber;
	}

	public void setDistrictNumber(String districtNumber) {
		this.districtNumber = districtNumber;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String geteMailAddress() {
		return eMailAddress;
	}

	public void seteMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getManageEmpCode() {
		return manageEmpCode;
	}

	public void setManageEmpCode(String manageEmpCode) {
		this.manageEmpCode = manageEmpCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
}
