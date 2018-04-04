package com.hoau.miser.module.api.itf.server.constants;

/**
 * PMS相关常量
 * 
 * @author 蒋落琛
 * @date 2016-6-1下午5:48:14
 */
public interface PmsConstants {

	/********************上楼方式数据字典*****************/
	/**
	 * 大件上楼数据字典值
	 */
	public static final String UPSTAIRS_TYPE_BIG = "BIG";
	/**
	 * 大件上楼数据字典值
	 */
	public static final String UPSTAIRS_TYPE_TM = "TM";
	/**
	 * 小件上楼
	 */
	public static final String UPSTAIRS_TYPE_LITTLE = "LITTLE";
	
	/**
	 * 附加费科目-送货费数据字典值
	 */
	public static final String ADDITIONAL_TYPE_DELIVERY = "DELIVERY";
	
	/**
	 * 附加费科目-信息费
	 */
	public static final String ADDITIONAL_TYPE_SMS = "SMS";
	
	/**
	 * 附加费科目-保价最低收费
	 */
	public static final String ADDITIONAL_TYPE_INSURANCE = "COLLECTION_RATE";

	/**
	 * 附加费科目-保价费率
	 */
	public static final String ADDITIONAL_TYPE_INSURANCE_RATE = "INSURANCE";
	
	/**
	 * 附加费科目-提货费
	 */
	public static final String ADDITIONAL_TYPE_PICKUP = "PICKUP";
	
	/**
	 * 附加费科目-工本费
	 */
	public static final String ADDITIONAL_TYPE_PAPER = "PAPER";
	
	/********************价格状态数据字典*****************/
	/**
	 * 有效
	 */
	public static final String PRICE_SATUS_EFFECTIVE = "EFFECTIVE";
	
	/********************活动状态数据字典*****************/
	/**
	 * 有效
	 */
	public static final String ACTIVITY_STATE_EFFECTIVE = "2";
	
	/********************费用分段明细分段依据*****************/
	/**
	 * 体积
	 */
	public static final String SECTION_ACCODING_ITEM_VOLUMN = "VOLUMN";
	/**
	 * 重量
	 */
	public static final String SECTION_ACCODING_ITEM_WEIGHT = "WEIGHT";
	/**
	 * 保价费
	 */
	public static final String SECTION_ACCODING_ITEM_INSURANCE = "INSURANCE";
	/**
	 * 票
	 */
	public static final String SECTION_ACCODING_ITEM_PACKAGE = "PACKAGE";
	/**
	 * 件数
	 */
	public static final String SECTION_ACCODING_ITEM_PIECE = "PIECE";
	/**
	 * 代收货款
	 */
	public static final String SECTION_ACCODING_ITEM_COLLECTION = "COLLECTION";
	
	/************************费用分段明细费用类型********************
	/**
	 * 折扣
	 */
	public static final String PRICE_TYPE_RATE = "RATE";
	/**
	 * 金额
	 */
	public static final String PRICE_TYPE_MONEY = "MONEY";
	
	
	/*********************数据是否有效**********************/
	public static final String Y = "Y";
	public static final String N = "N";
	
	/*********************价卡城市类型**********************/
	public static final String PRICE_CITY_SEND = "SEND";
	public static final String PRICE_CITY_ARRIVAL = "ARRIVAL";
}
