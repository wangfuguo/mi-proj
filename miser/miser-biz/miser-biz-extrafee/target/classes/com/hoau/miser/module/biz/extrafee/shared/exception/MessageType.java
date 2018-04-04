package com.hoau.miser.module.biz.extrafee.shared.exception;


public class MessageType{
	
	
	public static final String ADD_SUCCESS = "biz.base.addSuccess";
	public static final String UPDATE_SUCCESS = "biz.base.updateSuccess";
	public static final String DELETE_SUCCESS = "biz.base.deleteSuccess";
	public static final String ERROR_ISEXIST="biz.base.error.isExist";
	public static final String SAVE_SUCCESS = "bse.baseinfo.saveSuccess";
	
	/**
	 * 状态发生变更
	 */
	public static final String STATE_CHANGE = "miser.base.stateChange";
	public static final String DELIVERY_ISCONFIRM = "extrafee.deliverry.isConfirm";
	public static final String DELIVERY_DOWN_SYSDATE = "extrafee.deliverry.downSysdate";
	public static final String PRICE_PICK_UP_DO_NOT_MODIFY = "extrafee.pickUp.notModify";
	public static final String PRICE_UPSTAIRS_CHECK_SUCCESS = "extrafee.upstairs.check.success";
	public static final String PRICE_UPSTAIRS_EXISTS_WAIT = "extrafee.upstairs.exists.wait";
	public static final String ERROR_ISALERT = "miser.base.isAlert";
	public static final String ERROR_INVALID = "miser.base.invalid";
	public static final String PRICEDELIVERYFEECITYTYPE_ISCONFIRM = "extrafee.priceDeliveryFeeCityType.isConfirm";
	
	//add by dengyin 2016-7-7 14:04:18 配送费管理
	public static final String PRICEDELIVERYFEE_ISCONFIRM = "extrafee.priceDeliveryFee.isConfirm";
	
	//add by dengyin 2016-7-10 14:32:50 代收货款手续费管理
	public static final String COLLECT_DELIVERY_ISCONFIRM = "extrafee.priceCollectDeliveryFee.isConfirm";

}
