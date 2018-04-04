package com.hoau.miser.module.biz.base.api.shared.vo;

public class ExcelPriceCityVo {
	private String cityName;
	private String cityType;
	private String remark;
	private String oldSerial;
	private String errorMsg;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOldSerial() {
		return oldSerial;
	}

	public void setOldSerial(String oldSerial) {
		this.oldSerial = oldSerial;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
