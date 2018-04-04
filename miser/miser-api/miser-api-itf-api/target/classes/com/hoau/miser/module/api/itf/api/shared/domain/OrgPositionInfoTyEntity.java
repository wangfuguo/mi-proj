package com.hoau.miser.module.api.itf.api.shared.domain;

/**
 * 组织位置信息
 * 
 * @author 蒋落琛
 * @date 2016-7-13上午10:39:13
 */
public class OrgPositionInfoTyEntity {

	/**
	 * 组织编码
	 */
	private String orgCode;

	/**
	 * 组织名称
	 */
	private String orgName;

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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
}