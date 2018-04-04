package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

import java.io.File;
import java.util.Date;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: PriceQueryResultVo
 * @Package com.hoau.miser.module.api.itf.api.shared.vo
 * @Description: 网点时效城市相关信息查询接口返回
 * @date 2016年06月07日17:52:38
 */
public class CorpAgingCityEntity extends BaseEntity {

	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231350438160812L;

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
	 * 出发时效城市代码
	 */
	private String sendAgingCityCode;

	/**
	 * 出发时效城市名称
	 */
	private String sendAgingCityName;

	/**
	 * 到达时效城市代码
	 */
	private String arrivalAgingCityCode;

	/**
	 * 到达时效城市名称
	 */
	private String arrivalAgingCityName;

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

	public String getSendAgingCityCode() {
		return sendAgingCityCode;
	}

	public void setSendAgingCityCode(String sendAgingCityCode) {
		this.sendAgingCityCode = sendAgingCityCode;
	}

	public String getSendAgingCityName() {
		return sendAgingCityName;
	}

	public void setSendAgingCityName(String sendAgingCityName) {
		this.sendAgingCityName = sendAgingCityName;
	}

	public String getArrivalAgingCityCode() {
		return arrivalAgingCityCode;
	}

	public void setArrivalAgingCityCode(String arrivalAgingCityCode) {
		this.arrivalAgingCityCode = arrivalAgingCityCode;
	}

	public String getArrivalAgingCityName() {
		return arrivalAgingCityName;
	}

	public void setArrivalAgingCityName(String arrivalAgingCityName) {
		this.arrivalAgingCityName = arrivalAgingCityName;
	}
}
