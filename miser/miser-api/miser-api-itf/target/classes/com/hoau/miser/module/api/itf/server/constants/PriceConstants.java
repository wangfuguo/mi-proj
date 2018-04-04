package com.hoau.miser.module.api.itf.server.constants;

import java.math.BigDecimal;

/**
 * 价卡相关常量类
 * 
 * @author 蒋落琛
 * @date 2016-6-1下午5:50:42
 */
public class PriceConstants {

	/** 价卡类型常量开始 **/
	/**
	 * 网点价卡类型
	 */
	public static final String PRICE_TYPE_ORG = "ORG";

	/**
	 * 标准价卡类型
	 */
	public static final String PRICE_TYPE_STANDARD = "STANDARD";
	/** 价卡类型常量结束 **/

	/** 重量体积折扣类型常量开始 **/

	/**
	 * 重量体积折扣类型轻货折扣
	 */
	public static final String WEIGHT_VOLUME_DISCOUNT_TYPE_LIGHT = "Q";

	/**
	 * 重量体积折扣类型重货折扣
	 */
	public static final String WEIGHT_VOLUME_DISCOUNT_TYPE_HEAVY = "Z";

	/** 重量体积折扣类型常量结束 **/

	/**
	 * 燃油费
	 */
	public static final BigDecimal FUEL_CHARGE = new BigDecimal(4.00);

	/**
	 * 信息费
	 */
	public static final BigDecimal MESSAGE_CHARGE = new BigDecimal(1.00);

	/**
	 * 工本费
	 */
	public static final BigDecimal ISSUING_CHARGE = new BigDecimal(2.00);

	/**
	 * 保价率
	 */
	public static final BigDecimal INSURED_RATE = new BigDecimal(4.00);

	/**
	 * 保价费
	 */
	public static final BigDecimal INSURED_CHARGE = new BigDecimal(6.00);

	/** 送货上楼计价方式常量开始 **/
	/**
	 * 计价方式-重量
	 */
	public static final String CALCULATE_TYPE_WEIGHT = "0";

	/**
	 * 计价方式-体积
	 */
	public static final String CALCULAT_TYPE_VOLUME = "1";

	/**
	 * 计价方式-票数
	 */
	public static final String CALCULAT_TYPE_VOTE = "2";

	/** 送货上楼计价方式常量结束 **/

	/** 包装费计价方式常量开始 **/
	/**
	 * 按包装体积
	 */
	public static final String CALCULATION_TYPE_VOLUMN = "VOLUMN";
	/**
	 * 按包装个数
	 */
	public static final String CALCULATION_NUMBER_NUMBER = "NUMBER";
	/** 包装费计价方式常量结束 **/

	/***********************价格计算类型********************/
	/**
	 * 定日达
	 */
	public static final String USE_PRICE_TYPE_DRD = "1";
	/**
	 * 易安装
	 */
	public static final String USE_PRICE_EASYHOME = "2";
}
