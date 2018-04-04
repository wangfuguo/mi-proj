package com.hoau.miser.module.api.facade.shared.define;

/**
 * ClassName: BaseResponseEnum 
 * @author 286330付于令
 * @date 2016年1月25日
 * @version V1.0
 */
public enum BaseResponseEnum {
	
	SUCCESS("1000", "成功"),
	IP_CHECK_FAIL("2001", "验证白名单失败"),
	SIGN_CHECK_FAIL("2002", "签名验证失败"),
	TIMESTAMP_CHECK_FAIL("2003", "时间戳验证失败"),
	PARAMETER_CHECK_FAIL("3001", "参数验证失败"),
	BUSINESS_EXCEPTION("8000", "业务异常"),
	UNKNOW_ERROR("9000", "未知异常"),
	SYSTEM_ERROR("9999", "系统异常"),
	CUSTOMER_CODE("44001","客户编码为空"),	
	TRANS_TYPE_CODE("44002","运输价格不为空，运输类型编码不能为空"),	
	DISCOUNT_ACCODINGPC_TIME("44003","折扣基于标准价格时间为空"),
	DISCOUNT_PRIORITY_TYPE("44004","折扣优先类型为空"),	
	EFFECTIVE_TIME("44005","折扣生效日期为空"),	
	INVALID_TIME("44006","折扣失效日期为空"),	
	CREATE_USER_NAME("44007","同步用户不能为空"),
	PRICE_IS_NUM("45001","价格只能为数字"),
	JSON_ERROR("47001","解析JSON/XML内容错误"),	
	DATA_ERROR("40001","同步数据为空"),
	PRICEAGINGPARAM_CHECK_FAIL("48001","价格查询省市区县名称或者编码为必填项"),
	WEIGHTVOLUME_IS_NUM("48002","重量和体积必须为数字"),
	CUSTOMER_PRICE_ERROR("46001","OA的新失效时间<当前时间 ：不处理"),
	
	//add by dengyin 2016-5-22 14:17:24 OA-PMS
	fregihtSectionHeavyLightFloatBothNull("44008","运费优惠分段与（浮动比例/浮动金额）必须传其中一个");
	//end by dengyin 2016-5-22 14:17:33

	
	
	
	private BaseResponseEnum(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	private String status;
	private String message;

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
