package com.hoau.miser.module.biz.base.api.shared.vo;

import java.util.ArrayList;

import com.hoau.miser.module.biz.base.api.shared.domain.CustomerEntity;

/**
 * ClassName: CustomerVo 
 * @Description: 客户维护界面前后台交互数据类 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年3月8日
 * @version V1.0   
 */
public class CustomerVo {

	/**
	 * @Fields queryParam : 入参查询条件
	 */
	private CustomerEntity queryParam;
	
	/**
	 * @Fields resultCustomerEntity : 单条记录查询返回
	 */
	private CustomerEntity customerEntity;
	
	/**
	 * @Fields resultCustomerEntities : 批量查询结果返回
	 */
	private ArrayList<CustomerEntity> customerEntities;

	public CustomerEntity getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(CustomerEntity queryParam) {
		this.queryParam = queryParam;
	}

	public CustomerEntity getCustomerEntity() {
		return customerEntity;
	}

	public void setCustomerEntity(CustomerEntity customerEntity) {
		this.customerEntity = customerEntity;
	}

	public ArrayList<CustomerEntity> getCustomerEntities() {
		return customerEntities;
	}

	public void setCustomerEntities(ArrayList<CustomerEntity> customerEntities) {
		this.customerEntities = customerEntities;
	}

}
