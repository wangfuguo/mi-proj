package com.hoau.miser.module.biz.discount.api.shared.exception;

public class MessageType{
	
	
	public static final String ADD_SUCCESS = "bse.baseinfo.addSuccess";
	public static final String UPDATE_SUCCESS = "bse.baseinfo.updateSuccess";
	public static final String DELETE_SUCCESS = "bse.baseinfo.deleteSuccess";
	public static final String ERROR_ISEXIST = "miser.base.isExist";
	public static final String CORPSTANDARD_ISCONFIRM = "discount.discountCorp.isConfirm";
	public static final String CUSTOMERSTANDARD_ISCONFIRM = "discount.discountCustomer.isConfirm";
	public static final String ERROR_ISALERT = "miser.base.isAlert";
	public static final String TIME_ISCONFIRM = "discount.timeConfirm";
	public static final String NOWTIME_ISCONFIRM = "discount.nowtimeConfirm";
	public static final String ERROR_INVALID = "miser.base.invalid";
	public static final String CORP_VERIFICATION = "discount.discountCorp.verification";
	public static final String CUSTOMERS_VERIFICATION = "discount.discountCustomer.verification";

	/** 活动状态已经发送变化 */
	public static final String PRICEEVENT_STATUS_CHANGED = "discount.priceEvent.statusChanged";
	
	/**存在重复时间*/
	public static final String REPEAT_TIME_ZONE = "discount.discountCustomer.repeatTimeZone";
}
