package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 折扣信息（客户折扣、网点折扣）
 * @author Leslie
 *
 */
public class DisctionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对应的折扣唯一标识（折扣主键）
	 */
	private Integer id;
	/**
	 * 折扣分段编号
	 */
	private String sectionId;
	/**
	 * N-网点折扣，C-客户折扣
	 */
	private String type;
	/**
	 * 当为网点折扣，则表示网点编号；当为客户折扣，则表示为客户编号
	 */
	private String standardNo;
	/**
	 * 运输类型：(D-定日达、P-普通零担、Z-整车)
	 */
	private String transportType;
	/**
	 * 显示折扣后价格Y-表示显示；N-不显示（属于客户折扣）
	 */
	private String isDisctionPrice;
	/**
	 * 折扣生效日期
	 */
	private Date effectiveDate;
	/**
	 * 折扣失效日期
	 */
	private Date invalidDate;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除状态0正常
	 */
	private Integer recStatus;

	public Integer getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(Integer recStatus) {
		this.recStatus = recStatus;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the sectionId
	 */
	public String getSectionId() {
		return sectionId;
	}

	/**
	 * @param sectionId the sectionId to set
	 */
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the standardNo
	 */
	public String getStandardNo() {
		return standardNo;
	}

	/**
	 * @param standardNo the standardNo to set
	 */
	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo;
	}

	/**
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * @param transportType the transportType to set
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * @return the isDisctionPrice
	 */
	public String getIsDisctionPrice() {
		return isDisctionPrice;
	}

	/**
	 * @param isDisctionPrice the isDisctionPrice to set
	 */
	public void setIsDisctionPrice(String isDisctionPrice) {
		this.isDisctionPrice = isDisctionPrice;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the invalidDate
	 */
	public Date getInvalidDate() {
		return invalidDate;
	}

	/**
	 * @param invalidDate the invalidDate to set
	 */
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "运输类型:"+this.getTransportType()+" StantardNo:"+this.getStandardNo();
	}
}
