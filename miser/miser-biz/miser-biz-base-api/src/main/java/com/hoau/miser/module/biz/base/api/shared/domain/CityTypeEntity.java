package com.hoau.miser.module.biz.base.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;


public class CityTypeEntity extends BaseEntity{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -8343605400765536636L;
	//行政省会
	private String provinceCode;
	 /**省份名称*/
    private String provinceName;
    
    /**行政城市*/
    private String cityCode;
    
    /**行政城市名称*/
    private String cityName;
    
    /**行政区县*/
    private String areaCode;
    
    /**行政区县名称*/
    private String areaName;
	//城市类型
	private String cityType;
	//备注
	private  String remark;
	//创建时间
	private Date createTime;
	//创建人
	private String createUserCode;
	//更新时间
	private Date modifyTime;
	//更新人
	private String modifyUserCode;
	//是否可用
	private String active;
	
	
	
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "CityTypeEntity [provinceCode=" + provinceCode + ", provinceName=" + provinceName + ", cityCode="
				+ cityCode + ", cityName=" + cityName + ", areaCode=" + areaCode + ", areaName=" + areaName
				+ ", cityType=" + cityType + ", remark=" + remark + ", createTime=" + createTime + ", createUserCode="
				+ createUserCode + ", modifyTime=" + modifyTime + ", modifyUserCode=" + modifyUserCode + ", active="
				+ active + "]";
	}
	
	
}
