package com.hoau.miser.module.biz.base.server.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.base.api.shared.domain.CustomerEntity;

/**
 * ClassName: CustomerDao 
 * @Description: 客户信息数据库操作类 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年3月8日
 * @version V1.0   
 */
@Repository
public interface CustomerDao {

	/**
	 * @Description: 查询客户列表
	 * @param customerEntity
	 * @param bounds
	 * @return ArrayList<CustomerEntity> 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public ArrayList<CustomerEntity> queryCustomers(CustomerEntity customerEntity, RowBounds bounds);
	
	/**
	 * @Description: 查询客户条数
	 * @param customerEntity
	 * @return Long 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public Long queryCustomerCount(CustomerEntity customerEntity);
	
	/**
	 * @Description: 根据客户id查询客户信息
	 * @param customerEntity
	 * @return CustomerEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public CustomerEntity queryCustomerById(CustomerEntity customerEntity);
	
	/**
	 * @Description: 更新客户信息
	 * @param customerEntity   
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public void updateCustomer(CustomerEntity customerEntity);
	/**
	 * @Description: 查询用于excel导出的客户列表
	 * @param customerEntity
	 * @return ArrayList<CustomerEntity>
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public ArrayList<CustomerEntity> queryExcelCustomers(CustomerEntity customerEntity);

}
