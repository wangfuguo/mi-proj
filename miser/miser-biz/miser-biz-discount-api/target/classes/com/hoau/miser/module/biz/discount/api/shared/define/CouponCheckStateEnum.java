package com.hoau.miser.module.biz.discount.api.shared.define;

/**
 * 返券审核状态枚举
 * ClassName: CouponCheckStateEnum 
 * @author 286330付于令
 * @date 2016年1月21日
 * @version V1.0
 */
public enum CouponCheckStateEnum {
	// 不可申请
	NONE_APPLICATION("NONE_APPLICATION", "discount.privilegeCouponCheck.NONE_APPLICATION"),
	// 可申请
	PENDING_APPLICATION("PENDING_APPLICATION", "discount.privilegeCouponCheck.PENDING_APPLICATION"),
	// 待审核
	PENDING_CHECK("PENDING_CHECK", "discount.privilegeCouponCheck.PENDING_CHECK"),
	// 审核通过
	CHECK_SUCCESS("CHECK_SUCCESS", "discount.privilegeCouponCheck.CHECK_SUCCESS"),
	// 审核失败
	CHECK_REJECT("CHECK_REJECT", "discount.privilegeCouponCheck.CHECK_REJECT"),
	// 返券失败
	COUPON_FAIL("COUPON_FAIL", "discount.privilegeCouponCheck.COUPON_FAIL");

	private String value;
	private String message;
	
	private CouponCheckStateEnum(String value, String message) {
		this.value = value;
		this.message = message;
	}
	
	/**
	 * @return the name
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
