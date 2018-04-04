package com.hoau.miser.module.api.itf.server.constants;

/**
 * 接送货模块相关常量
 * 
 * @author 蒋落琛
 * @date 2016-6-1下午5:48:14
 */
public interface PkpConstants {

	/**
	 * 交货方式(送货方式)
	 */
	/**
	 * 送货方式-客户自提
	 */
	public static final String PICK_UP_SELF = "0";

	/**
	 * 送货方式-送货上门
	 */
	public static final String DELIVERY_HOME = "1";

	/**
	 * 送货方式-送货(大件上楼)
	 */
	public static final String DELIVERY_UPSTAIRS_BIG = "2";

	/**
	 * 送货方式-送货(小件上楼)
	 */
	public static final String DELIVERY_UPSTAIRS_SMALL = "3";

	/**
	 * 客户自送
	 */
	public static final String PICKUP_SELF = "0";

	/**
	 * 上门提货
	 */
	public static final String PICKUP_VISIT = "1";
	
	/**
	 * MRO采购商城订单判断字符串
	 */
	public static final String MRO_TYPE_STR = "MRO";
}
