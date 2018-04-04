package com.hoau.miser.module.biz.base.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * @ClassName: PriceCityEntity
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author: 279716
 * @date: 2016/3/9 17:23
 */
public class PriceCityEntity extends BaseEntity {

	/**
	 * 城市编号
	 */
	private String code;

	/**
	 * 城市名称
	 */
	private String name;

	/**
	 * 城市类型
	 */
	private String type;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否作废
	 */
	private String invalid;

	/**
	 * 价格城市范围， STANDARD 适用于标准价格的价格城市, CUSOTMER 适用于老客户价格的价格城市
	 */
	private String priceCityScope;

	public String getPriceCityScope() {
		return priceCityScope;
	}

	public void setPriceCityScope(String priceCityScope) {
		this.priceCityScope = priceCityScope;
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

	public String getInvalid() {
		return invalid;
	}

	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}

}
