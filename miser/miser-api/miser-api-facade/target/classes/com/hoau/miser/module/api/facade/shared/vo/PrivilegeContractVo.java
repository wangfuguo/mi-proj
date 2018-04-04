package com.hoau.miser.module.api.facade.shared.vo;

import java.util.List;

public class PrivilegeContractVo {
	
	private String customerNum; // 客户编号
	private String contractStartDate; // 合同开始时间
	private String contractEndDate; // 合同结束时间
	private String yhksdate; // 越发越惠开始时间
	private String yhjsdate; // 越发越惠结束时间
	private String whetherCreditCustomers; // 是否返券
	private String createUser; // 创建人
	private String commitmentProduct; // 承诺产值
	private List<PrivilegeContractDetailVo> detail; // 合同明细
	
	public String getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getYhksdate() {
		return yhksdate;
	}
	public void setYhksdate(String yhksdate) {
		this.yhksdate = yhksdate;
	}
	public String getYhjsdate() {
		return yhjsdate;
	}
	public void setYhjsdate(String yhjsdate) {
		this.yhjsdate = yhjsdate;
	}
	public String getWhetherCreditCustomers() {
		return whetherCreditCustomers;
	}
	public void setWhetherCreditCustomers(String whetherCreditCustomers) {
		this.whetherCreditCustomers = whetherCreditCustomers;
	}
	public List<PrivilegeContractDetailVo> getDetail() {
		return detail;
	}
	public void setDetail(List<PrivilegeContractDetailVo> detail) {
		this.detail = detail;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCommitmentProduct() {
		return commitmentProduct;
	}
	public void setCommitmentProduct(String commitmentProduct) {
		this.commitmentProduct = commitmentProduct;
	}
	
}
