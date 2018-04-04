package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 组织信息
 * 
 * @author 蒋落琛
 * @date 2016-7-13上午10:39:13
 */
public class OrgBasicInfoTyEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 组织编码
	 */
	private String code;

	/**
	 * 组织名称
	 */
	private String name;

	/**
	 * 物流代码
	 */
	private String logisticCode;

	/**
	 * 省份编码
	 */
	private String provinceCode;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 区县编码
	 */
	private String countyCode;

	/**
	 * 省份名称
	 */
	private String provinceName;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 区县名称
	 */
	private String countyName;

	/**
	 * 详细地址
	 */
	private String addressDetail;

	/**
	 * 是否可用
	 */
	private String active;

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

	public String getLogisticCode() {
		return logisticCode;
	}

	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
}