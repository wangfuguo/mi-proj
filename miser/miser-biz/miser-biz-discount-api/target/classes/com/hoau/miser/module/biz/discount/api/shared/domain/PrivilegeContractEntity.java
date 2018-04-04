package com.hoau.miser.module.biz.discount.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 越发越惠客户合同实体类
 * ClassName: PrivilegeContractEntity 
 * @author 286330付于令
 * @date 2016年1月12日
 * @version V1.0
 */
public class PrivilegeContractEntity extends BaseEntity {
	
	/* 数据库映射字段 */
	private String customerCode; // 客户编号
	private String customerName; // 客户名称
	private String contractCode; // 合同编号
	private String contactName; // 联系人
	private String contactPhone; // 联系电话
	private Date contractStartTime; // 合同开始时间
	private Date contractEndTime; // 合同结束时间
	private Date privilegeStartTime; // 越发越惠开始时间
	private Date privilegeEndTime; // 越发越惠结束时间
	private String state; // 越发越惠当前状态
	private String hasCoupon; // 是否返券
	private String remark; // 备注
	private String active;  // 是否可用
	private Double commitmentProduct;//承诺产值

	/* 自定义字段 */
	private Date privilegeStartAndEndTime; // 有效时间
	private Double ddMinFreightDiscount; // 定日达纯运费最低折扣
	private Double duMinFreightDiscount; // 经济快运纯运费最低折扣
	private String seaDivision; // 事业部
	private String seaBigregion; // 大区
	private String seaRoadarea; // 路区
	private String seaSalesdepartment; // 网点
	   
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
	public Date getPrivilegeStartTime() {
		return privilegeStartTime;
	}
	public void setPrivilegeStartTime(Date privilegeStartTime) {
		this.privilegeStartTime = privilegeStartTime;
	}
	public Date getPrivilegeEndTime() {
		return privilegeEndTime;
	}
	public void setPrivilegeEndTime(Date privilegeEndTime) {
		this.privilegeEndTime = privilegeEndTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getHasCoupon() {
		return hasCoupon;
	}
	public void setHasCoupon(String hasCoupon) {
		this.hasCoupon = hasCoupon;
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
	/**
	 * @return the duMinFreightDiscount
	 */
	public Double getDuMinFreightDiscount() {
		return duMinFreightDiscount;
	}
	/**
	 * @param duMinFreightDiscount the duMinFreightDiscount to set
	 */
	public void setDuMinFreightDiscount(Double duMinFreightDiscount) {
		this.duMinFreightDiscount = duMinFreightDiscount;
	}
	/**
	 * @return the ddMinFreightDiscount
	 */
	public Double getDdMinFreightDiscount() {
		return ddMinFreightDiscount;
	}
	/**
	 * @param ddMinFreightDiscount the ddMinFreightDiscount to set
	 */
	public void setDdMinFreightDiscount(Double ddMinFreightDiscount) {
		this.ddMinFreightDiscount = ddMinFreightDiscount;
	}
	/**
	 * @return the seaDivision
	 */
	public String getSeaDivision() {
		return seaDivision;
	}
	/**
	 * @param seaDivision the seaDivision to set
	 */
	public void setSeaDivision(String seaDivision) {
		this.seaDivision = seaDivision;
	}
	/**
	 * @return the seaBigregion
	 */
	public String getSeaBigregion() {
		return seaBigregion;
	}
	/**
	 * @param seaBigregion the seaBigregion to set
	 */
	public void setSeaBigregion(String seaBigregion) {
		this.seaBigregion = seaBigregion;
	}
	/**
	 * @return the seaRoadarea
	 */
	public String getSeaRoadarea() {
		return seaRoadarea;
	}
	/**
	 * @param seaRoadarea the seaRoadarea to set
	 */
	public void setSeaRoadarea(String seaRoadarea) {
		this.seaRoadarea = seaRoadarea;
	}
	/**
	 * @return the seaSalesdepartment
	 */
	public String getSeaSalesdepartment() {
		return seaSalesdepartment;
	}
	/**
	 * @param seaSalesdepartment the seaSalesdepartment to set
	 */
	public void setSeaSalesdepartment(String seaSalesdepartment) {
		this.seaSalesdepartment = seaSalesdepartment;
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
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the privilegeStartAndEndTime
	 */
	public Date getPrivilegeStartAndEndTime() {
		return privilegeStartAndEndTime;
	}
	/**
	 * @param privilegeStartAndEndTime the privilegeStartAndEndTime to set
	 */
	public void setPrivilegeStartAndEndTime(Date privilegeStartAndEndTime) {
		this.privilegeStartAndEndTime = privilegeStartAndEndTime;
	}
	public Double getCommitmentProduct() {
		return commitmentProduct;
	}
	public void setCommitmentProduct(Double commitmentProduct) {
		this.commitmentProduct = commitmentProduct;
	}
	
}
