package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * @ClassName: CorpPriceCityEntity
 * @Description: 组织出发到达客户价格城市信息
 * @author: 陈宇霖
 * @date: 2016年06月07日19:37:25
 */
public class CustPriceCityEntity extends BaseEntity {

	private static final long serialVersionUID = 7226775261770920651L;
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
	 * 省份名称
	 */
	private String provinceName;

	/**
	 * 城市代码
	 */
	private String cityCode;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 区县编码
	 */
	private String areaCode;

	/**
	 * 区县名称
	 */
	private String areaName;

	/**
	 * 出发价格城市代码
	 */
	private String sendPriceCityCode;

	/**
	 * 出发价格城市名称
	 */
	private String sendPriceCityName;

	/**
	 * 到达价格城市代码
	 */
	private String arrivalPriceCityCode;

	/**
	 * 到达价格城市名称
	 */
	private String arrivalPriceCityName;

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

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getSendPriceCityCode() {
		return sendPriceCityCode;
	}

	public void setSendPriceCityCode(String sendPriceCityCode) {
		this.sendPriceCityCode = sendPriceCityCode;
	}

	public String getSendPriceCityName() {
		return sendPriceCityName;
	}

	public void setSendPriceCityName(String sendPriceCityName) {
		this.sendPriceCityName = sendPriceCityName;
	}

	public String getArrivalPriceCityCode() {
		return arrivalPriceCityCode;
	}

	public void setArrivalPriceCityCode(String arrivalPriceCityCode) {
		this.arrivalPriceCityCode = arrivalPriceCityCode;
	}

	public String getArrivalPriceCityName() {
		return arrivalPriceCityName;
	}

	public void setArrivalPriceCityName(String arrivalPriceCityName) {
		this.arrivalPriceCityName = arrivalPriceCityName;
	}
}
