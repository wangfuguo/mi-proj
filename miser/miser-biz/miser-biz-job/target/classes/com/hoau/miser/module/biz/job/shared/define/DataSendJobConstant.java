package com.hoau.miser.module.biz.job.shared.define;

/**
 * ClassName: SendDataJobConstant
 * 
 * @Description: 发送数据相关基础常量
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月25日
 * @version V1.0
 */
public class DataSendJobConstant {

	/**
	 * @Fields MAX_FAIL_RETRY_TIMES : 最大失败重发次数
	 */
	public static final int MAX_FAIL_RETRY_TIMES = 3;

	/**
	 * @Fields JOB_CODE_PRICE_CITY : 价格城市JOB编码
	 */
	public static final String JOB_CODE_PRICE_CITY = "PRICE_CITY";

	/**
	 * @Fields JOB_CODE_DISCOUNT_SECTION : 优惠分段JOB编码
	 */
	public static final String JOB_CODE_DISCOUNT_SECTION = "DISCOUNT_SECTION";

	/**
	 * @Fields JOB_CODE_EXTRA : 附加费JOB编码
	 */
	public static final String JOB_CODE_EXTRA = "EXTRA";

	/**
	 * @Fields JOB_CUSTOMER_CODE_EXTRA : 客户附加费JOB编码
	 */
	public static final String JOB_CUSTOMER_CODE_EXTRA = "CUSTOMER_EXTRA";

	/**
	 * @Fields JOB_CODE_DISCOUNT : 客户折扣JOB编码
	 */
	public static final String JOB_CUSTOMER_CODE_DISCOUNT = "CUSTOMER_DISCOUNT";

	/**
	 * @Fields JOB_CODE_PRICE_CARD : 价格JOB编码
	 */
	public static final String JOB_CODE_PRICE_CARD = "PRICE_CARD";
	/**
	 * @Fields JOB_CODE_PRICE_CARD : 网点折扣JOB编码
	 */
	public static final String JOB_CODE_DISCOUNT_CORP = "DISCOUNT_CORP";
	/**
	 * @Fields JOB_CODE_PRICE_CARD : 网点价格JOB编码
	 */
	public static final String JOB_CODE_PRICE_CORP = "PRICE_CORP";
	/**
	 * @Fields CUSTOMER_PRICE_CARD : 客户价格JOB编码
	 */
	public static final String JOB_CODE_CUSTOMER_PRICE_CARD = "CUSTOMER_PRICE_CARD";
	
	/**
	 * @Fields JOB_CODE_CUSTOMER_PRICE_CARD_DISCOUNT : 客户价格（无价格）折扣JOB编码
	 */
	public static final String JOB_CODE_CUSTOMER_PRICE_CARD_DISCOUNT = "CUSTOMER_PRICE_CARD_DISCOUNT";
	/**
	 * @Fields JOB_CODE_PRIVILEGE : 越发越惠定义JOB编码
	 */
	public static final String JOB_CODE_PRIVILEGE = "PRIVILEGE";
	/**
	 * @Fields JOB_CODE_PRIVILEGE : 越发越惠合同JOB编码
	 */
	public static final String JOB_CODE_PRIVILEGE_CONTRACT = "PRIVILEGE_CONTRACT";
	/**
	 * @Fields JOB_CODE_PRIVILEGE : 越发越惠折扣JOB编码
	 */
	public static final String JOB_CODE_PRIVILEGE_DISCOUNT = "PRIVILEGE_DISCOUNT";
	/**
	 * @Fields JOB_CODE_PRIVILEGE : 越发越惠返券JOB编码
	 */
	public static final String JOB_CODE_PRIVILEGE_COUPON = "PRIVILEGE_COUPON";
	/**
	 * @Fields JOB_CODE_PRICE_CARD : 标准价格JOB编码
	 */
	public static final String JOB_CODE_PRICE_STANDARD = "PRICE_STANDARD";
	/**
	 * @Fields JOB_CODE_PRICE_CARD : 标准价格JOB编码
	 */
	public static final String JOB_CODE_PRICE_STANDARD_EVENT = "PRICE_STANDARD_EVENT";
	
	/**
	 * @Fields JOB_CODE_PRICE_PACKAGE_FEE : 包装费JOB编码
	 */
	public static final String JOB_CODE_PRICE_PACKAGE_FEE = "PRICE_PACKAGE_FEE";
	
	/**
	 * @Fields JOB_CODE_PRICE_PACKAGE_FEE_PC : 价格城市包装费JOB编码
	 */
	public static final String JOB_CODE_PRICE_PACKAGE_FEE_PC = "PRICE_PACKAGE_FEE_PC";
	
	/**
	 * @Fields JOB_CODE_OUTER_BRANCH_PRICE: 偏线外发配置
	 */
	public static final String JOB_CODE_OUTER_BRANCH_PRICE = "OUTER_BRANCH_PRICE";

	/**
	 * @Fields JOB_CODE_PRICE_DELIVERY_FEE : 送货费JOB编码
	 */
	public static final String JOB_CODE_PRICE_DELIVERY_FEE = "PRICE_DELIVERY_FEE";
	/**
	 * @Fields JOB_CODE_PRICE_PICK_UP_FEE : 提货费JOB编码
	 */
	public static final String JOB_CODE_PRICE_PICK_UP_FEE = "PRICE_PICK_UP_FEE";
	/**
	 *  @Fields JOB_BIZ_SEND_PRICE_CITY_MAPPING : 价格城市映射关系
	*/
	public static final String JOB_BIZ_SEND_PRICE_CITY_MAPPING = "PRICE_CITY_MAPPING";
	/**
	 * @Fields JOB_BIZ_SEND_CUST_PRICE_CITY_MAPPING : 客户价格城市映射关系
	 */
	public static final String JOB_BIZ_SEND_CUST_PRICE_CITY_MAPPING = "CUST_PRICE_CITY_MAPPING";
	/**
	 * @Fields JOB_CODE_EXTRAFEE_ADD_VALUE_FEE : 增值服务费JOB编码
	 */
	public static final String JOB_CODE_EXTRAFEE_ADD_VALUE_FEE = "EXTRAFEE_ADD_VALUE_FEE";
	/**
	 * @Fields JOB_CODE_EXTRAFEE_ADD_VALUE_FEE_ITEMS : 增值服务费项目定义JOB编码
	 */
	public static final String JOB_CODE_EXTRAFEE_ADD_VALUE_FEE_ITEMS = "EXTRAFEE_ADD_VALUE_FEE_ITEMS";

	/**
	 * @Fields JOB_BIZ_SEND_CUSTOMER_BASIC_INFO : 客户配置信息JOB编码
	 */
	public static final String JOB_BIZ_SEND_CUSTOMER_BASIC_INFO = "JOB_BIZ_SEND_CUSTOMER_BASIC_INFO";

	/**
	 * @Fields JOB_BIZ_SEND_CUSTOMER_LOCK_STATUS : 客户价格锁定状态JOB编码
	 */
	public static final String JOB_BIZ_SEND_CUSTOMER_LOCK_STATUS = "JOB_BIZ_SEND_CUSTOMER_LOCK_STATUS";

	/**
	 * @Fields JOB_BIZ_SEND_EVENT : 活动信息发送
	 */
	public static final String JOB_BIZ_SEND_EVENT = "JOB_BIZ_SEND_EVENT";

	/**
	 * @Fields JOB_BIZ_SEND_DELIVERY_CITYLEVEL_MAPPING : 行政区域映射送货城市等级
	 */
	public static final String JOB_BIZ_SEND_DELIVERY_CITYLEVEL_MAPPING = "JOB_BIZ_SEND_DELIVERY_CITYLEVEL_MAPPING";

	/**
	 * @Description: 根据code获取名称
	 * @param code	code
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	public static String getNameByCode(String code) {
		if (JOB_CODE_PRICE_CITY.equals(code)) {
			return "价格城市";
		}
		if (JOB_CODE_DISCOUNT_SECTION.equals(code)) {
			return "优惠分段";
		}
		if (JOB_CODE_EXTRA.equals(code)) {
			return "附加费";
		}
		if (JOB_CUSTOMER_CODE_EXTRA.equals(code)) {
			return "客户附加费";
		}
		if (JOB_CUSTOMER_CODE_DISCOUNT.equals(code)) {
			return "客户折扣";
		}
		if (JOB_CODE_PRICE_CARD.equals(code)) {
			return "价格";
		}
		if (JOB_CODE_PRICE_CORP.equals(code)) {
			return "网点价格城市";
		}
		if (JOB_CODE_CUSTOMER_PRICE_CARD.equals(code)) {
			return "客户价格";
		}
		if (JOB_CODE_PRIVILEGE.equals(code)) {
			return "越发越惠定义";
		}
		if (JOB_CODE_PRIVILEGE_CONTRACT.equals(code)) {
			return "越发越惠合同";
		}
		if (JOB_CODE_PRIVILEGE_DISCOUNT.equals(code)) {
			return "越发越惠折扣";
		}
		if (JOB_CODE_PRIVILEGE_COUPON.equals(code)) {
			return "越发越惠返券";
		}
		if (JOB_CODE_PRICE_STANDARD.equals(code)) {
			return "标准价格";
		}
		if (JOB_CODE_PRICE_STANDARD_EVENT.equals(code)) {
			return "标准价格活动同步";
		}
		if (JOB_BIZ_SEND_PRICE_CITY_MAPPING.equals(code)) {
			return "价格城市映射关系同步";
		}
		if (JOB_BIZ_SEND_CUST_PRICE_CITY_MAPPING.equals(code)) {
			return "客户价格城市映射关系同步";
		}
		if (JOB_CODE_PRICE_DELIVERY_FEE.equals(code)) {
			return "送货费";
		}
		if (JOB_CODE_DISCOUNT_CORP.equals(code)) {
			return "网点折扣";
		}
		if (JOB_CODE_PRICE_PICK_UP_FEE.equals(code)) {
			return "提货费";
		}
		if (JOB_CODE_PRICE_PACKAGE_FEE.equals(code)) {
			return "包装费";
		}
		if (JOB_CODE_PRICE_PACKAGE_FEE_PC.equals(code)) {
			return "价格包装费";
		}
		
		if (JOB_CODE_EXTRAFEE_ADD_VALUE_FEE.equals(code)) {
			return "增值服务费";
		}
		if (JOB_CODE_EXTRAFEE_ADD_VALUE_FEE_ITEMS.equals(code)) {
			return "增值服务费项目定义";
		}
		if (JOB_BIZ_SEND_CUSTOMER_BASIC_INFO.equals(code)) {
			return "客户配置信息";
		}
		if (JOB_BIZ_SEND_CUSTOMER_LOCK_STATUS.equals(code)) {
			return "客户价格锁定状态";
		}
		if (JOB_BIZ_SEND_EVENT.equals(code)) {
			return "活动";
		}
		if (JOB_BIZ_SEND_DELIVERY_CITYLEVEL_MAPPING.equals(code)) {
			return "送货城市等级";
		}
		return code;
	}

	/**
	 * 
	 * @Description: 将pms运输类型代码转换为同步mq需要的运输类型代码
	 * @param @param transTypeCode
	 * @param @return
	 * @return String
	 * @throws
	 * @author 赵金义
	 * @date 2016年3月4日
	 */
	public static String transTypeCodeConvert(String transTypeCode) {
		if (("10000000000000000001").equals(transTypeCode)) {
			return "D";
		} else if ("20000000000000000001".equals(transTypeCode)) {
			return "Z";
		} else if ("30000000000000000001".equals(transTypeCode)) {
			return "P";
		} else {
			return "";
		}
	}
}
