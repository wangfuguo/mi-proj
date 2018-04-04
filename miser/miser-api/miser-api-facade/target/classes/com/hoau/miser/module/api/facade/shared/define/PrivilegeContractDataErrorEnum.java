package com.hoau.miser.module.api.facade.shared.define;

/**
 * 越发越惠合同数据错误 枚举
 * ClassName: PrivilegeContractDataErrorEnum 
 * @author 286330付于令
 * @date 2016年3月28日
 * @version V1.0
 */
public enum PrivilegeContractDataErrorEnum {

	CONTRACT_NULL("合同数据为空"),
	CUSTOMER_CODE_NULL("客户编号为空"),
	CONTRACT_START_TIME_NULL("合同开始时间为空"),
	CONTRACT_END_TIME_NULL("合同结束时间为空"),
	CONTRACT_TIME_RANGE_ERROR("合同开始结束时间错误"),
	PRIVILEGE_START_TIME_NULL("优惠开始时间为空"),
	PRIVILEGE_END_TIME_NULL("优惠结束时间为空"),
	PRIVILEGE_TIME_RANGE_ERROR("优惠开始结束时间错误"),
	HAS_COUPON_NULL("是否返券为空"),
	CREATE_USER_NULL("创建人为空"),
	
	CONTRACT_DETAIL_NULL("合同明细数据为空"),
	START_AMOUNT_NULL("明细-发货金额段起为空"),
	END_AMOUNT_NULL("明细-发货金额段止为空"),
	AMOUNT_RANGE_ERROR("明细-发货金额段起段止数据错误"),
	DD_DISCOUNT_RANGE_ERROR("明细-定日达纯运费最低折扣数据错误，取值范围0-1"),
	DU_DISCOUNT_RANGE_ERROR("明细-经济快运纯运费最低折扣数据错误，取值范围0-1"),
	COUPON_SCALE_RANGE_ERROR("明细-最高返券比例数据错误，取值范围0-1");
	
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private PrivilegeContractDataErrorEnum(String msg) {
		this.msg = msg;
	}
}
