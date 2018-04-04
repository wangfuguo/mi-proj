package com.hoau.miser.module.biz.base.api.shared.domain;

import java.io.File;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;


/**
 * @author：刘海飞
 * @create：2015年12月15日 下午3:34:04
 * @description：
 */
public class AgingCityEntity extends BaseEntity {

	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231350438160812L;


    
    /**省份名称*/
    private String provinceName;
    /**行政城市名称*/
    private String cityName;
    /**行政区县名称*/
    private String areaName;
    /**出发时效城市*/
    private String startAgingCity;
    /**出发时效城市Code*/
    private String startAgingCityCode;
    /**到达时效城市*/
    private String arriveAgingCity;
    /**到达时效城市Code*/
    private String arriveAgingCityCode;
    /**备注*/
    private String remarks;

    /**行政省份*/
    private String provinceCode;
    /**行政城市*/
    private String cityCode;
    /**行政区县*/
    private String areaCode;
    
   
    
    /**时效城市code*/
    private String agingCityCode;
    
    /**时效城市名称*/
    private String agingCityName;
    
    /**是否有网点*/
    private String isLocations;
    
  
    
  
    
    
    
    /**最后修改时间*/
    private Date modifyTime;
    
    /**最后修改人*/
    private String modifyUserCode;
    
    /**是否有效*/
    private String active;
    
    /**code*/
    private String code;
    
    private String resourceCode;
    
    /**城市类型*/
    private String cityType;
    
    private String num;
    
    /**文件名*/
    private File file;
    
	//旧的序号
	private String oldSerial;
	
	//错误信息
	private String errorMsg;
    
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getIsLocations() {
		return isLocations;
	}

	public void setIsLocations(String isLocations) {
		this.isLocations = isLocations;
	}

	public String getStartAgingCity() {
		return startAgingCity;
	}

	public void setStartAgingCity(String startAgingCity) {
		this.startAgingCity = startAgingCity;
	}

	public String getArriveAgingCity() {
		return arriveAgingCity;
	}

	public void setArriveAgingCity(String arriveAgingCity) {
		this.arriveAgingCity = arriveAgingCity;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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


	public String getAgingCityCode() {
		return agingCityCode;
	}

	public void setAgingCityCode(String agingCityCode) {
		this.agingCityCode = agingCityCode;
	}

	public String getAgingCityName() {
		return agingCityName;
	}

	public void setAgingCityName(String agingCityName) {
		this.agingCityName = agingCityName;
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
	
	public String getStartAgingCityCode() {
		return startAgingCityCode;
	}

	public void setStartAgingCityCode(String startAgingCityCode) {
		this.startAgingCityCode = startAgingCityCode;
	}

	public String getArriveAgingCityCode() {
		return arriveAgingCityCode;
	}

	public void setArriveAgingCityCode(String arriveAgingCityCode) {
		this.arriveAgingCityCode = arriveAgingCityCode;
	}
	
	public AgingCityEntity copy(){
		AgingCityEntity ace = new AgingCityEntity();
		
		ace.setActive(getActive());
		ace.setAgingCityCode(getAgingCityCode());
		ace.setAgingCityName(getAgingCityName());
		ace.setAreaCode(getAreaCode());
		ace.setAreaName(getAreaName());
		ace.setArriveAgingCity(getArriveAgingCity());
		ace.setArriveAgingCityCode(getArriveAgingCityCode());
		
		ace.setCityCode(getCityCode());
		ace.setCityName(getCityName());
		ace.setCode(getCode());
		ace.setCreateDate(getCreateDate());
		ace.setCreateUser(getCreateUser());
		ace.setCityType(getCityType());
		
		ace.setErrorMsg(getErrorMsg());
		ace.setFile(getFile());
		ace.setId(getId());
		ace.setIsLocations(getIsLocations());
		ace.setModifyDate(getModifyDate());
		ace.setModifyTime(getModifyTime());
		ace.setModifyUser(getModifyUser());
		ace.setModifyUserCode(getModifyUserCode());
		ace.setNum(getNum());
		ace.setOldSerial(getOldSerial());
		ace.setProvinceCode(getProvinceCode());
		ace.setProvinceName(getProvinceName());
		ace.setRemarks(getRemarks());
		ace.setResourceCode(getResourceCode());
		ace.setStartAgingCity(getStartAgingCity());
		ace.setStartAgingCityCode(getStartAgingCityCode());
		return ace;
	}
    
}
