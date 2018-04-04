package com.hoau.miser.module.sys.job.server.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.sys.job.shared.domain.EmployeeEntity;

/**
 *
 * @author 涂婷婷
 * @date 2015年6月4日
 */
@Repository
public interface EmployeeSyncDao {
	
	/**
	 *根据人员编号查询ID
	 * @author 涂婷婷
	 * @date 2015年6月4日
	 */
	String queryEmployeeByCode(String employeeCode);

	/**
	 *跟新人员信息
	 * @author 涂婷婷
	 * @date 2015年6月4日
	 */
	void updateEmployee(EmployeeEntity employee);

	/**
	 *新增人员
	 * @author 涂婷婷
	 * @date 2015年6月4日
	 */
	void addEmployee(EmployeeEntity employee);
	
	/**
	 * 获取最新版本号
	 * @return
	 * @author 高佳
	 * @date 2015年6月10日
	 * @update 
	 */
	Long getLastVersionNo();

	/**
	 * 查询失效用户信息
	 * @return
	 * @author 高佳
	 * @date 2015年8月21日
	 * @update 
	 */
	List<String> queryInvalidUser();

	/**
	 * 更新无效用户信息
	 * @param map
	 * @author 高佳
	 * @date 2015年8月21日
	 * @update 
	 */
	void updateInvalidUser(Map<String, Object> map);

}
