package com.hoau.miser.module.biz.job.server.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.CustomerEntity;
import com.hoau.miser.module.biz.job.shared.domain.CustomerExtVo;

/**
 * ClassName: CustomerJobMapper 
 * @Description: 客户数据相关操作
 * @author 275636
 * @date 2016年1月25日
 * @version V1.0   
 */
@Repository
public interface CustomerJobMapper {
	
	/**
	 * @Description: 获取客户修改或新增的最大时间
	 * @author 275636
	 * @date 2016年1月25日
	 */
	public CustomerExtVo queryMaxCustomerDate();
	
	/**
	 * @Description: 获取客户信息是否存在
	 * @author 275636
	 * @date 2016年1月25日
	 */
	public List<Map<String,Object>> getCustomerIDs(List<CustomerEntity> list);

	/**
	 * @Description: 获取客户信息新增
	 * @author 275636
	 * @date 2016年1月25日
	 */
	public void addCustomerEntity(List<CustomerEntity> customerAdd);

	/**
	 * @Description: 获取客户信息修改
	 * @author 275636
	 * @date 2016年1月25日
	 */
	public void updateCustomerEntity(List<CustomerEntity> customerUpdate);

	/**
	 * @Description: 根据迪辰客户编号获取客户信息是否存在
	 * @author 275636
	 * @date 2016年1月25日
	 */
	public List<Map<String,Object>> getCustomerCodes(List<CustomerEntity> list);
	
}
