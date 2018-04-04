package com.hoau.miser.module.biz.job.shared.domain;

import java.util.List;

/**
 * 客户信息RESTFUL提供给外部系统接口返回VO
 *
 * @author 275636
 * @date 2016-1-22
 */
public class CustomerExtVo{

	/**
	 * 开始时间
	 */
	private String createDate;
	
	/**
	 * 结束时间
	 */
	private String modifyDate;
	
	/**
	 * 客户信息集合
	 */
	private List<CustomerEntity> customerEntityList;
	
	
	public List<CustomerEntity> getCustomerEntityList() {
		return customerEntityList;
	}

	public void setCustomerEntityList(List<CustomerEntity> customerEntityList) {
		this.customerEntityList = customerEntityList;
	}
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
}
