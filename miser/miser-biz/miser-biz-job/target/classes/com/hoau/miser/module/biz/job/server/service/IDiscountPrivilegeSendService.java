package com.hoau.miser.module.biz.job.server.service;

/**
 * 越发越惠数据同步Service接口
 * ClassName: IDiscountPrivilegeSendService 
 * @author 286330付于令
 * @date 2016年2月23日
 * @version V1.0
 */
public interface IDiscountPrivilegeSendService {
	/**
	 * @Description: 发送越发越惠定义数据
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月23日
	 */
	void sendPrivilege();
	/**
	 * @Description: 发送越发越惠客户合同数据
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月23日
	 */
	void sendPrivilegeContract();
	/**
	 * @Description: 发送越发越惠折扣数据
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	void sendPrivilegeDiscount();
	/**
	 * @Description: 发送越发越惠返券数据
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	void sendPrivilegeCoupon();
}
