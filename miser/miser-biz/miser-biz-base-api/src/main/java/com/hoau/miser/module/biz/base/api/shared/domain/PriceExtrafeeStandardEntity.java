/**
 * 
 */
package com.hoau.miser.module.biz.base.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 因产生 maven 的循环依赖 这里参照之前的定义复制一份
 * @author dengyin
 *
 */
public class PriceExtrafeeStandardEntity extends BaseEntity{

	private static final long serialVersionUID = -4784549644718766659L;
	
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
	
	private double lowestFee; //最低费用
	private double heightestFee; //最高费用
	
	
	
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
	public double getLowestFee() {
		return lowestFee;
	}
	public void setLowestFee(double lowestFee) {
		this.lowestFee = lowestFee;
	}
	public double getHeightestFee() {
		return heightestFee;
	}
	public void setHeightestFee(double heightestFee) {
		this.heightestFee = heightestFee;
	}

}
