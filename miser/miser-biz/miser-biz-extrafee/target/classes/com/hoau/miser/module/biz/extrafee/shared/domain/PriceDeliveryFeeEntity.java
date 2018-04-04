/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 配送费管理
 * @author dengyin
 *
 */
public class PriceDeliveryFeeEntity implements Serializable{
	
	private static final long serialVersionUID = 1823969528176446597L;
	
	private String id;

	//城市类型编码
	private String cityType;
	
	//城市类型名称
	private String cityTypeName;

	//配送费分段编码
	private String sectionItemCode;
	
	//配送费分段名称
	private String sectionItemName;

	// 生效时间
	private Date effectiveTime;
	
	// 失效时间
	private Date invalidTime;
	
	// 备注 
	private String remark;
	
	// 是否有效 
	private String active;
	
	// 当前状态
	private String state;
	
	//运输类型
	private String transTypeCode;
	
	//运输类型名称
	private String transTypeName;

	private String createUserCode;
	private String createUserName;
	private String modifyUserCode;
	private String modifyUserName;
	private Date createTime;
	private Date modifyTime;
	
	
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getCityTypeName() {
		return cityTypeName;
	}

	public void setCityTypeName(String cityTypeName) {
		this.cityTypeName = cityTypeName;
	}

	public String getSectionItemCode() {
		return sectionItemCode;
	}

	public void setSectionItemCode(String sectionItemCode) {
		this.sectionItemCode = sectionItemCode;
	}

	public String getSectionItemName() {
		return sectionItemName;
	}

	public void setSectionItemName(String sectionItemName) {
		this.sectionItemName = sectionItemName;
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

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
