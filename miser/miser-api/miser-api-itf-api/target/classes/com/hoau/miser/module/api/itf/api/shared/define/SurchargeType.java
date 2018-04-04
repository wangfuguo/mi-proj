package com.hoau.miser.module.api.itf.api.shared.define;

/**
 * @author：高佳
 * @create：2015年9月21日 下午3:59:01
 * @description：附加费类型
 */
public enum SurchargeType {
	NOMINAL_FEE("0","工本费"),
	PICK_UP_CHARGE("1","提货费"),
	DELIVERY_CHARGE("2","送货费"),
	PACKAGING_CHARGE("3","打包费"),
	INSURANCE_RATE("4","保价率"),
	INSURANCE_FEE("5","保价费"),
	INFORMATION_FEE("6","信息费"),
	;
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;

	private SurchargeType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
