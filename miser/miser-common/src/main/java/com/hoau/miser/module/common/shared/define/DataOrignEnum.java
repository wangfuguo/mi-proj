package com.hoau.miser.module.common.shared.define;

/**
 * 数据来源枚举值定义
 * @author dengyin
 *
 */
public enum DataOrignEnum {
	
	PMS("pms","价格管理系统"),
	OA("oa","办公系统");
	
	//数据来源编码
	private String code;
	
	//数据来源描述
	private String name;
	
	private DataOrignEnum(String code,String name){
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
