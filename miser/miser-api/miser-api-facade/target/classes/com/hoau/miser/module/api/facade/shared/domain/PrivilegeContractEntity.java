package com.hoau.miser.module.api.facade.shared.domain;

import java.util.Date;
import java.util.List;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 越发越惠客户合同实体类
 * ClassName: PrivilegeContractEntity 
 * @author 286330付于令
 * @date 2016年1月12日
 * @version V1.0
 */
public class PrivilegeContractEntity extends BaseEntity {
	private static final long serialVersionUID = 5871756016947575927L;
	
	/* 数据库映射字段 */
	private String contractCode;
	private String customerCode; // 客户编号
	private String customerName; // 客户名称
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
	private Double commitmentProduct; // 承诺产值
	private List<PrivilegeContractDetailEntity> detail; // 合同明细
	   
	/* 自定义字段 */
	private Date contractStartAndEndTime;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public Date getContractStartAndEndTime() {
		return contractStartAndEndTime;
	}
	public void setContractStartAndEndTime(Date contractStartAndEndTime) {
		this.contractStartAndEndTime = contractStartAndEndTime;
	}
	public Double getDuMinFreightDiscount() {
		return duMinFreightDiscount;
	}
	public void setDuMinFreightDiscount(Double duMinFreightDiscount) {
		this.duMinFreightDiscount = duMinFreightDiscount;
	}
	public Double getDdMinFreightDiscount() {
		return ddMinFreightDiscount;
	}
	public void setDdMinFreightDiscount(Double ddMinFreightDiscount) {
		this.ddMinFreightDiscount = ddMinFreightDiscount;
	}
	public String getSeaDivision() {
		return seaDivision;
	}
	public void setSeaDivision(String seaDivision) {
		this.seaDivision = seaDivision;
	}
	public String getSeaBigregion() {
		return seaBigregion;
	}
	public void setSeaBigregion(String seaBigregion) {
		this.seaBigregion = seaBigregion;
	}
	public String getSeaRoadarea() {
		return seaRoadarea;
	}
	public void setSeaRoadarea(String seaRoadarea) {
		this.seaRoadarea = seaRoadarea;
	}
	public String getSeaSalesdepartment() {
		return seaSalesdepartment;
	}
	public void setSeaSalesdepartment(String seaSalesdepartment) {
		this.seaSalesdepartment = seaSalesdepartment;
	}
	public List<PrivilegeContractDetailEntity> getDetail() {
		return detail;
	}
	public void setDetail(List<PrivilegeContractDetailEntity> detail) {
		this.detail = detail;
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
	 * @return the commitmentProduct
	 */
	public Double getCommitmentProduct() {
		return commitmentProduct;
	}
	/**
	 * @param commitmentProduct the commitmentProduct to set
	 */
	public void setCommitmentProduct(Double commitmentProduct) {
		this.commitmentProduct = commitmentProduct;
	}
	
}
