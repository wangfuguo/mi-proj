package com.hoau.miser.module.sys.job.server.service;


/**
 * @author：高佳
 * @create：2015年6月1日 下午6:28:22
 * @description：
 */
public interface IEmployeeSyncService {

	/**
	 * 员工信息同步
	 * @author 高佳
	 * @date 2015年6月1日
	 * @update 
	 */
	void employeeSync();
	
	/**
	 * 更新失效用户信息
	 * @author 高佳
	 * @date 2015年8月21日
	 * @update 
	 */
	void updateInvalidUser();

}
