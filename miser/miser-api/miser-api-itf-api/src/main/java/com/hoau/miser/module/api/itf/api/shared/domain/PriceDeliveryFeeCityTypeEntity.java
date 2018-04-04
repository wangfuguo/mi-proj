package com.hoau.miser.module.api.itf.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class PriceDeliveryFeeCityTypeEntity extends BaseEntity {
	
	/**
	 * 1:已失效
	 */
	public static final String STATE_1="PASSED";
	/**
	 * 2:生效中
	 */
	public static final String STATE_2="EFFECTIVE";
	/**
	 * 3:待生效
	 */
	public static final String STATE_3="WAIT";
	/**
	 * 4:已废弃
	 */
	public static final String STATE_4="DELETED";

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * CITY_TYPE 城市类型
	 */
	private String cityType;
	
	/**
	 * 城市类型name
	 */
	private String cityTypeName;

	/**
	 * SECTION_ITEM_CODE 送货费分段编码
	 */
	private String sectionItemCode;
	
	/**
	 * 送货分段名称
	 */
	private String sectionItemName;

	// 生效时间-EFFECTIVE_TIME
	private Date effectiveTime;
	// 失效时间-INVALID_TIME
	private Date invalidTime;
	// 备注 REMARK
	private String remark;
	// 是否有效 ACTIVE
	private String active;
	// 当前状态
	private String state;
	/**
	 * 运输类型
	 */
	private String transTypeCode;
	/**
	 * 运输类型
	 */
	private String transTypeName;
	
	/** 开单时间 **/
    private Date billTime;
    /** 是否历史**/
    private boolean isHistory;
	
	public String getTransTypeCode() {
		return transTypeCode;
	}

	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}
	public String getCityType() {
		return cityType;
	}
	public void setCityType(String cityType) {
		this.cityType = cityType;
	}
	public String getSectionItemCode() {
		return sectionItemCode;
	}
	public void setSectionItemCode(String sectionItemCode) {
		this.sectionItemCode = sectionItemCode;
	}
	public Date getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public Date getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCityTypeName() {
		return cityTypeName;
	}
	public void setCityTypeName(String cityTypeName) {
		this.cityTypeName = cityTypeName;
	}
	public String getSectionItemName() {
		return sectionItemName;
	}
	public void setSectionItemName(String sectionItemName) {
		this.sectionItemName = sectionItemName;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public boolean isHistory() {
		return isHistory;
	}

	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}
}
