package com.hoau.miser.module.biz.base.api.server.service;

import java.util.ArrayList;
import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.CustomerEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.CustomerVo;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;

/**
 * ClassName: ICustomerService 
 * @Description: 客户信息维护 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年3月8日
 * @version V1.0   
 */
public interface ICustomerService {

	/**
	 * @Description: 查询分页客户信息
	 * @param customerVo	入参
	 * @param limit			每页显示条数
	 * @param start			分页起
	 * @return ArrayList<CustomerEntity> 查询结果
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public ArrayList<CustomerEntity> queryCustomers(CustomerVo customerVo, int limit, int start);

	/**
	 * @Description: 查询客户数量
	 * @param customerVo	入参
	 * @return Long 		客户数量
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public Long queryCustomerCount(CustomerVo customerVo);
	
	/**
	 * @Description: 根据客户id查询客户数量
	 * @param customerVo	客户id信息
	 * @return CustomerEntity 	客户信息
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public CustomerEntity queryCustomerById(CustomerVo customerVo);
	
	/**
	 * @Description: 更新客户信息
	 * @param customerVo   
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public void updateCustomer(CustomerVo customerVo);

	/**
	 * @Description: 查询用于excel导出的客户信息
	 * @param customerVo	入参
	 * @return ArrayList<CustomerEntity> 查询结果
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public ArrayList<CustomerEntity> queryExcelCustomers(CustomerVo customerVo) ;

	/**
	 * @Description: 将需要导出的客户信息生成excel文件
	 * @param entity
	 * @return void
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	public ExcelExportResultEntity createCustomerExcelFile(CustomerEntity entity);
}
