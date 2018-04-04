package com.hoau.miser.module.sys.base.api.server.service;

import java.util.List;

import com.hoau.miser.module.sys.base.api.shared.domain.CommonEmployeeEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.EmployeeSearchConditionEntity;


/**
 * @author：李旭锋
 * @create：2015年7月22日 上午10:19:06
 * @description：
 */
public interface ICommonEmployeeService {
	
	/**
	 * 根据条件查询员工信息
	 * @param employeeSearchConditionEntity
	 * @return
	 * @author 李旭锋
	 * @date 2015年7月22日
	 * @update 
	 */
	List<CommonEmployeeEntity> queryEmployeeByParam(EmployeeSearchConditionEntity employeeSearchConditionEntity);

	/**
	 * 根据条件查询员工信息总数
	 * @param employeeSearchConditionEntity
	 * @return
	 * @author 李旭锋
	 * @date 2015年7月22日
	 * @update 
	 */
	Long queryCountEmployeeByParam(EmployeeSearchConditionEntity employeeSearchConditionEntity);
}
