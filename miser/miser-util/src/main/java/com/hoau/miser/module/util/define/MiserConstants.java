package com.hoau.miser.module.util.define;

import java.util.Date;

/**
 * @author：高佳
 * @create：2015年6月3日 上午10:48:08
 * @description：
 */
public class MiserConstants {
	/**
	 * 可用
	 */
	public static final String ACTIVE ="Y";
	/**
	 * 不可用
	 */
	public static final String INACTIVE ="N";
	/**
	 * 作废
	 */
	public static final String INVALID ="Y";
	/**
	 * 不作废
	 */
	public static final String ININVALID ="N";
	
	
	/**
	 * 是
	 */
	public static final String YES ="Y";
	/**
	 * 否
	 */
	public static final String NO ="N";
	
	public static final String KEY_CURRENT_DEPTCODE = "BUTTERFLY_KEY_CURRENT_DEPTCODE";
	public static final String FRAMEWORK_KEY_EMPCODE = "FRAMEWORK_KEY_EMPCODE";
	public static final String KEY_CURRENT_DEPTNAME = "BUTTERFLY_KEY_CURRENT_DEPTNAME";
	
	/**
	 * 前台树根节点id
	 */
	public static final String TREE_ROOT_ID = "root";
	
	/**
	 * web权限资源树根节点code
	 */
	public static final String RESOURCE_TREE_WEB_ROOT_CODE = "1";
	
	/**
	 * @Fields MQ_UPDATE_DATE_USER : 发送数据时需要修改数据的用户名称
	 */
	public static final String MQ_UPDATE_DATE_USER = "SYSTEM_MQ";
	
	/**
	 * @Fields DEFAULT_MAX_DATE : 系统默认最大时间 2999/12/31 23:59:59
	 */
	public static final Date DEFAULT_MAX_DATE = new Date(32503651199999l);
	
	/**
	 * @Fields DEFAULT_MAX_MONEY : 优惠分段默认的最大值
	 */
	public static final int DEFAULT_MAX_VALUE = 99999999;
	
}
