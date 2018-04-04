package com.hoau.miser.module.biz.extrafee.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 增值费 ClassName: PriceAddValueFee
 * 
 * @author 王茂
 * @date 2015年12月30日
 * @version V1.0
 */
public class ExtrafeeAddValueFeeEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -5040034235497904351L;

	private String code; // 增值费项目编码
	private String codeName; // 增值费项目名称
	private String transTypeCode;//运输类型
	private String transTypeName;//运输类型名称
	private String calculationType; // 计算类型
	private Double weightPrice; // 重货单价
	private Double lightPrice; // 轻货单价
	private Double lowestMoney; // 最低收费
	private Double lockType; // 是否锁定
	private Date effectiveTime; // 生效时间
	private Date invalidTime; // 失效时间
	private String remark;
	private String active;
	private String state;
	private String isAlert;
	
	/** 
	 * 导出数据时使用
	 * 
	 */
	private String lockTypeName;
	
	public String getLockTypeName() {
		return lockTypeName;
	}
	public void setLockTypeName(String lockTypeName) {
		this.lockTypeName = lockTypeName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	public String getCalculationType() {
		return calculationType;
	}
	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}
	public Double getWeightPrice() {
		return weightPrice;
	}
	public void setWeightPrice(Double weightPrice) {
		this.weightPrice = weightPrice;
	}
	public Double getLightPrice() {
		return lightPrice;
	}
	public void setLightPrice(Double lightPrice) {
		this.lightPrice = lightPrice;
	}
	public Double getLowestMoney() {
		return lowestMoney;
	}
	public void setLowestMoney(Double lowestMoney) {
		this.lowestMoney = lowestMoney;
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
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

}
