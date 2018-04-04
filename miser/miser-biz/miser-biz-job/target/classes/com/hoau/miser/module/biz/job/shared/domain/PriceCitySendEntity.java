package com.hoau.miser.module.biz.job.shared.domain;

/**
 * ClassName: PriceCitySendEntity 
 * @Description: 价格城市发送处理前查询结果对象
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月15日
 * @version V1.0   
 */
public class PriceCitySendEntity {

	/**
	 * @Fields id : 主键
	 */
	private String id;
	
	/**
	 * @Fields code : 价格城市编码
	 */
	private String code;
	
	/**
	 * @Fields name : 价格城市名称
	 */
	private String name;
	
	/**
	 * @Fields type : 价格城市类型:SEND出发 ARRIVAL到达
	 */
	private String type;

	/**
	 * 价格城市使用范围：standard标准价格 customer客户价格
	 */
	private String priceCityScope;
	
	/**
	 * @Fields remark : 备注
	 */
	private String remark;
	
	/**
	 * @Fields active : 是否有效 
	 */
	private String active;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPriceCityScope() {
		return priceCityScope;
	}

	public void setPriceCityScope(String priceCityScope) {
		this.priceCityScope = priceCityScope;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	
	
}
