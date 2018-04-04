package com.hoau.miser.module.common.shared.define;

/**
 * 运输类型
 * ClassName: TransportType 
 * @author 李玮琰
 * @date 2016年4月11日
 * @version V1.0
 */
public enum TransportType {

	SEND("SEND","出发价格城市"),
	ARRIVAL("ARRIVAL","到达价格城市");
	
	//数据来源编码
	private String code;
	
	//数据来源描述
	private String name;
	
	private TransportType(String code,String name){
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
