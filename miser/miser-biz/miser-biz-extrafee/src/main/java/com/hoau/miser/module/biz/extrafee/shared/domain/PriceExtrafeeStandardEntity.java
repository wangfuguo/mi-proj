package com.hoau.miser.module.biz.extrafee.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 标准附加费 ClassName: PriceExtrafeeStandardEntity
 * 
 * @author 王茂
 * @date 2015年12月31日
 * @version V1.0
 */
public class PriceExtrafeeStandardEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 7070787358144436662L;
	private String transTypeCode;//运输类型
	private String transTypeName;//运输类型名称
	private String type; // 附加费类别
	private Double money; // 金额/费率
	private Double lockType; // 是否锁定
	private Date effectiveTime; // 生效时间
	private Date invalidTime; // 失效时间
	private String remark;
	private String active;
	private String state;
	private String isAlert;
	
	private String lockTypeName;
	
	public String getLockTypeName() {
		return lockTypeName;
	}
	public void setLockTypeName(String lockTypeName) {
		this.lockTypeName = lockTypeName;
	}
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getLockType() {
		return lockType;
	}
	public void setLockType(Double lockType) {
		this.lockType = lockType;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsAlert() {
		return isAlert;
	}
	public void setIsAlert(String isAlert) {
		this.isAlert = isAlert;
	}
	public String getTransTypeName() {
		return transTypeName;
	}
	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

}
