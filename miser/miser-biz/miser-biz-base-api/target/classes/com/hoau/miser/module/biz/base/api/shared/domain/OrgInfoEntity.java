/**
 * 
 */
package com.hoau.miser.module.biz.base.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * T_BSE_ORG 补充完整字段实体定义
 * @author dengyin
 *
 */
public class OrgInfoEntity extends BaseEntity{
 
	private static final long serialVersionUID = 7850988566974452090L;

	
	private String id;
	private String code;
	private String name;
	private String parentCode;
	private String parentName;
	private int nature;
	private String logistCode;
	private String managerCode;
	private String managerName;
	private String province;
	private String provinceCode;
	private String city;
	private String cityCode;
	private String county;
	private String countyCode;
	private String areaCode;
	private String phone;
	private String fax;
	private String addressDetail;
	private int lng;
	private int lat;
	private String isDivision;
	private String divisionCode;
	private String isBigRegion;
	private String bigRegionCode;
	private String isTransferCenter;
	private String isRoadArea;
	private String isFleet;
	private String isPlatform;
	private String isSalesDepartment;
	private String active;
	private String versionNo;
	private Date createTime;
	private String createUserCode;
	private Date modifyTime;
	private String modifyUserCode;
	private Date beginTime;
	private Date endTime;
	private String pinyin;
	private String simplePinyin;
	private String notes;
	private String isBigRegionFinance;
	private String isDivisionFinance;
	private String isFinance;
	private String isFranchise;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public int getNature() {
		return nature;
	}
	public void setNature(int nature) {
		this.nature = nature;
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
	public int getLng() {
		return lng;
	}
	public void setLng(int lng) {
		this.lng = lng;
	}
	public int getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public String getIsDivision() {
		return isDivision;
	}
	public void setIsDivision(String isDivision) {
		this.isDivision = isDivision;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getIsBigRegion() {
		return isBigRegion;
	}
	public void setIsBigRegion(String isBigRegion) {
		this.isBigRegion = isBigRegion;
	}
	public String getBigRegionCode() {
		return bigRegionCode;
	}
	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}
	public String getIsTransferCenter() {
		return isTransferCenter;
	}
	public void setIsTransferCenter(String isTransferCenter) {
		this.isTransferCenter = isTransferCenter;
	}
	public String getIsRoadArea() {
		return isRoadArea;
	}
	public void setIsRoadArea(String isRoadArea) {
		this.isRoadArea = isRoadArea;
	}
	public String getIsFleet() {
		return isFleet;
	}
	public void setIsFleet(String isFleet) {
		this.isFleet = isFleet;
	}
	public String getIsPlatform() {
		return isPlatform;
	}
	public void setIsPlatform(String isPlatform) {
		this.isPlatform = isPlatform;
	}
	public String getIsSalesDepartment() {
		return isSalesDepartment;
	}
	public void setIsSalesDepartment(String isSalesDepartment) {
		this.isSalesDepartment = isSalesDepartment;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
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
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getSimplePinyin() {
		return simplePinyin;
	}
	public void setSimplePinyin(String simplePinyin) {
		this.simplePinyin = simplePinyin;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getIsBigRegionFinance() {
		return isBigRegionFinance;
	}
	public void setIsBigRegionFinance(String isBigRegionFinance) {
		this.isBigRegionFinance = isBigRegionFinance;
	}
	public String getIsDivisionFinance() {
		return isDivisionFinance;
	}
	public void setIsDivisionFinance(String isDivisionFinance) {
		this.isDivisionFinance = isDivisionFinance;
	}
	public String getIsFinance() {
		return isFinance;
	}
	public void setIsFinance(String isFinance) {
		this.isFinance = isFinance;
	}
	public String getIsFranchise() {
		return isFranchise;
	}
	public void setIsFranchise(String isFranchise) {
		this.isFranchise = isFranchise;
	}

	
	
}
