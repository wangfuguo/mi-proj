package com.hoau.miser.module.common.shared.define;


/**
 * job业务类型
 * @author 张贞献
 * @date 2015年7月13日
 */
public enum JobElementType {
	/******************job业务类型枚举***************************/
	/**BSE JOB开始**/
	JOB_SYS_MDM_DATA_SYNC("JOB_SYS_MDM_DATA_SYNC", "MDM信息同步"),

	JOB_BIZ_SEND_CUSTOMER("JOB_BIZ_SEND_CUSTOMER", "客户信息同步"),

	JOB_BIZ_SEND_BASIC_INFO("JOB_BIZ_SEND_BASIC_INFO", "基础配置发送"),
	JOB_BIZ_SEND_EVENT("JOB_BIZ_SEND_EVENT", "活动信息发送"),
	JOB_BIZ_SEND_SURCHARGE_INFO("JOB_BIZ_SEND_SURCHARGE_INFO", "增值信息发送"),
	JOB_BIZ_SEND_PRIVILEGE("JOB_BIZ_SEND_PRIVILEGE", "越发越惠发送"),
	JOB_BIZ_SEND_CORP_PRICE("JOB_BIZ_SEND_CORP_PRICE", "网点价格发送"),

	JOB_BIZ_SEND_STANDARD_PRICECARD("JOB_BIZ_SEND_STANDARD_PRICECARD", "标准价格发送"),
	JOB_BIZ_SEND_CUSTOMER_PRICECARD("JOB_BIZ_SEND_CUSTOMER_PRICECARD", "客户价格发送"),
	JOB_BIZ_SEND_CUSTOMER_DISCOUNT("JOB_BIZ_SEND_CUSTOMER_DISCOUNT", "客户折扣发送"),
	JOB_BIZ_SEND_CUSTOMER_SURCHARGE("JOB_BIZ_SEND_CUSTOMER_SURCHARGE", "客户附加费发送");
	/**BSE JOB结束**/
	/********************************************************/
	
	public final static String SUCCESS = "Y"; 
	public final static String FAILUTR = "N"; 
	public final static String USER = "BUTTERFLY";
	/**
	 * job任务编码（JOB_业务模块_业务功能）
	 */
	private String code;
	/**
	 * job任务名称
	 */
	private String name;

	private JobElementType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
