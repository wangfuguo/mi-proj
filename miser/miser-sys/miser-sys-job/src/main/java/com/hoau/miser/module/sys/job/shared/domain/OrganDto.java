package com.hoau.miser.module.sys.job.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * @author：李旭锋
 * @create：2015年6月3日 下午3:06:31
 * @description：
 */
public class OrganDto extends BaseEntity{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String deptCode; //部门编码

	private String deptName;//部门名称

	private String parentDeptCode;//上级部门编码

	private String parentDeptName;//上级部门名称
	/**
	 * 所属大区编码
	 */
	private String bigRegionCode;
	/**
	 * 所属事业部编码
	 */
	private String divisionCode;

	private String logistCode;//物流代码

	private String managerCode;//负责人编号

	private String managerName;//负责人姓名

	private String province;//省份

	private String provinceCode;//省份编码

	private String city;//城市

	private String cityCode;//城市编码

	private String county;//区县

	private String countyCode;//区县编码

	private String areaCode;//区号(座机区号)

	private String phone;//电话

	private String fax;//传真

	private String addressDetail;//

	private Double lng;//经度

	private Double lat;//纬度

	private String active;//是否激活

	private long versionNo;//版本号
	
	private int deptNature;//部门性质
	
	private String isSpecifiedTime; //是否提供定日达
	
	private String isShipment; //是否可发货
	
	private String isDelivery; //是否可送货
	
	private String isPickUp; //是否可接货

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getParentDeptCode() {
		return parentDeptCode;
	}

	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}

	public String getParentDeptName() {
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}

	public String getLogistCode() {
		return logistCode;
	}

	public void setLogistCode(String logistCode) {
		this.logistCode = logistCode;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public int getDeptNature() {
		return deptNature;
	}

	public void setDeptNature(int deptNature) {
		this.deptNature = deptNature;
	}

	public String getIsSpecifiedTime() {
		return isSpecifiedTime;
	}

	public void setIsSpecifiedTime(String isSpecifiedTime) {
		this.isSpecifiedTime = isSpecifiedTime;
	}

	public String getIsShipment() {
		return isShipment;
	}

	public void setIsShipment(String isShipment) {
		this.isShipment = isShipment;
	}

	public String getIsDelivery() {
		return isDelivery;
	}

	public void setIsDelivery(String isDelivery) {
		this.isDelivery = isDelivery;
	}



	public String getIsPickUp() {
		return isPickUp;
	}

	public void setIsPickUp(String isPickUp) {
		this.isPickUp = isPickUp;
	}

	public String getBigRegionCode() {
		return bigRegionCode;
	}

	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	
}
