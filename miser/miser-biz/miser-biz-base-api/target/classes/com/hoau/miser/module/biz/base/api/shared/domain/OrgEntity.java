package com.hoau.miser.module.biz.base.api.shared.domain;

/**
 * ORG查询
 * ClassName: CustomerSearchConditionEntity 
 * @author 286330付于令
 * @date 2016年1月18日
 * @version V1.0
 */
public class OrgEntity {
	
	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 事业部编码
	 */
	private String divisionCode;

	/**
	 * 事业部名称
	 */
	private String divisionName;

	/**
	 * 大区编码
	 */
	private String bigRegionCode;

	/**
	 * 大区名称
	 */
	private String bigRegionName;

	/**
	 * 路区编码
	 */
	private String roadAreaCode;

	/**
	 * 路区名称
	 */
	private String roadAreaName;

	/**
	 * 门店编码
	 */
	private String salesDepartmentCode;

	/**
	 * 门店名称
	 */
	private String salesDepartmentName;
	
	/**
	 * 是否事业部
	 */
	private String isDivision;

	/**
	 * 是否大区
	 */
	private String isBigRegion;

	/**
	 * 是否路区
	 */
	private String isRoadArea;

	/**
	 * 是否门店
	 */
	private String isSalesDepartment;

	public String getIsDivision() {
		return isDivision;
	}

	public void setIsDivision(String isDivision) {
		this.isDivision = isDivision;
	}

	public String getIsBigRegion() {
		return isBigRegion;
	}

	public void setIsBigRegion(String isBigRegion) {
		this.isBigRegion = isBigRegion;
	}

	public String getIsRoadArea() {
		return isRoadArea;
	}

	public void setIsRoadArea(String isRoadArea) {
		this.isRoadArea = isRoadArea;
	}

	public String getIsSalesDepartment() {
		return isSalesDepartment;
	}

	public void setIsSalesDepartment(String isSalesDepartment) {
		this.isSalesDepartment = isSalesDepartment;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getBigRegionCode() {
		return bigRegionCode;
	}

	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}

	public String getBigRegionName() {
		return bigRegionName;
	}

	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}

	public String getRoadAreaCode() {
		return roadAreaCode;
	}

	public void setRoadAreaCode(String roadAreaCode) {
		this.roadAreaCode = roadAreaCode;
	}

	public String getRoadAreaName() {
		return roadAreaName;
	}

	public void setRoadAreaName(String roadAreaName) {
		this.roadAreaName = roadAreaName;
	}

	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}

	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
	}

	public String getSalesDepartmentName() {
		return salesDepartmentName;
	}

	public void setSalesDepartmentName(String salesDepartmentName) {
		this.salesDepartmentName = salesDepartmentName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
