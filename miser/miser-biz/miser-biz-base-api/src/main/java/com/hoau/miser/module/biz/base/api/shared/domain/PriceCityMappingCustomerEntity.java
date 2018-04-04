package com.hoau.miser.module.biz.base.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;


/**
 * @author：刘海飞
 * @create：2015年12月15日 下午3:34:04
 * @description：
 */
public class PriceCityMappingCustomerEntity extends BaseEntity {

	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231350438160812L;

    /**行政省份*/
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
    
    /**价格城市code*/
    private String priceCityCode;
    
    /**价格城市名称*/
    private String priceCityName;
    
    /**是否有网点*/
    private String isLocations;
    
    /**出发价格城市*/
    private String startPriceCity;

	/**
	 * 出发价格城市编号
	 */
	private String startPriceCityCode;
    
    /**到达价格城市*/
    private String arrivePriceCity;

	/**
	 * 到达价格城市编号
	 */
	private String arrivalPriceCityCode;
    
    /**备注*/
    private String remarks;
    
    /**最后修改时间*/
    private Date modifyTime;
    
    /**最后修改人*/
    private String modifyUserCode;
    
    /**是否有效*/
    private String active;
    
    /**code*/
    private String code;
    
    private String resourceCode;
    
	//旧的序号
	private String oldSerial;
	
	//错误信息
	private String errorMsg;
    
    
	
    /**城市类型*/
    private String cityType;
    
    private String priceCityScope = "CUSTOMER";
   
    
    private String num;
    
	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
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

	public String getPriceCityCode() {
		return priceCityCode;
	}

	public void setPriceCityCode(String priceCityCode) {
		this.priceCityCode = priceCityCode;
	}


	public String getIsLocations() {
		return isLocations;
	}

	public void setIsLocations(String isLocations) {
		this.isLocations = isLocations;
	}

	public String getStartPriceCity() {
		return startPriceCity;
	}

	public void setStartPriceCity(String startPriceCity) {
		this.startPriceCity = startPriceCity;
	}
	public String getArrivePriceCity() {
		return arrivePriceCity;
	}

	public void setArrivePriceCity(String arrivePriceCity) {
		this.arrivePriceCity = arrivePriceCity;
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

	public String getPriceCityName() {
		return priceCityName;
	}

	public void setPriceCityName(String priceCityName) {
		this.priceCityName = priceCityName;
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

	public String getStartPriceCityCode() {
		return startPriceCityCode;
	}

	public void setStartPriceCityCode(String startPriceCityCode) {
		this.startPriceCityCode = startPriceCityCode;
	}

	public String getArrivalPriceCityCode() {
		return arrivalPriceCityCode;
	}

	public void setArrivalPriceCityCode(String arrivalPriceCityCode) {
		this.arrivalPriceCityCode = arrivalPriceCityCode;
	}

	public String getPriceCityScope() {
		if(priceCityScope == null || priceCityScope.equals("")){
			priceCityScope = "CUSTOMER";
		}
		return priceCityScope;
	}

	public void setPriceCityScope(String priceCityScope) {
		this.priceCityScope = priceCityScope;
	}

	@Override
	public String toString() {
		return "PriceCityMappingEntity{" +
				"cityType='" + cityType + '\'' +
				'}';
	}
	
	/**
	 * add by dengyin 2016-6-3 14:58:02 客户城市管理导出时 是否有网点 没有进行转义
	 * 添加如下字段以进行区分
	 */
	
	private String funcFlag;

	public String getFuncFlag() {
		return funcFlag;
	}

	public void setFuncFlag(String funcFlag) {
		this.funcFlag = funcFlag;
	}
	
	
}
