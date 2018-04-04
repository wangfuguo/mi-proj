package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: PriceQueryResultVo
 * @Package com.hoau.miser.module.api.itf.api.shared.vo
 * @Description: 省市区县时效城市相关信息查询接口返回
 * @date 2016年06月07日17:52:38
 */
public class DistrictAgingCityRequestEntity extends BaseEntity {

	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231350438160812L;

	/**
	 * 省份编码
	 */
	private String provinceCode;

	/**
	 * 城市代码
	 */
	private String cityCode;

	/**
	 * 区县编码
	 */
	private String areaCode;

	/**
	 * 时效城市类型:SEND出发时效城市、ARRIVAL到达时效城市
	 */
	private String agingCityType;


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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAgingCityType() {
		return agingCityType;
	}

	public void setAgingCityType(String agingCityType) {
		this.agingCityType = agingCityType;
	}
}
