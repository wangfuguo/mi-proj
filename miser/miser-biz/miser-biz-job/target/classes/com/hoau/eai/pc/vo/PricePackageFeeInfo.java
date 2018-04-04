package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
/**
 * 包装费定义
 * ClassName: PricePackageFeeInfo 
 * @author 刘海飞
 * @date 2016年2月22日
 * @version V1.0
 */
public class PricePackageFeeInfo implements Serializable {

	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -5767994681960636016L;

	/**
	 * PACKAGE_CODE 包装项目编码
	 */
	private String code;

	/**
	 * 包装项目名称
	 */
	private String name;
	/**
	 * CALCULATION_TYPE 金额计算方式
	 */
	private String calculationType;
	
	/**
	 * MONEY 单价
	 */
	private Double money;
	
	/**
	 * MIN_MONEY 最低价格
	 */
	private Double minMoney;
	
	/**
	 * LOCK_TYPE 锁定方式 
	 * 0 不锁定 1 向上锁定 2 向下锁定 3 完全锁定
	 */
	private String lockType;
	
	/**
	 *  REMARK 备注
	 */
	private String remark;
	
	/**
	 * 运输类型：(D-定日达、P-普通零担、Z-整车)
	 */
	private String transportType;
	
	/**
	 * 是否有效 0无效  1有效
	 */
	private String sfyx;
	
	/**
	 * 系数
	 */
	private Double rate; 

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

	public String getCalculationType() {
		return calculationType;
	}

	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}

	public String getLockType() {
		return lockType;
	}

	public void setLockType(String lockType) {
		this.lockType = lockType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getSfyx() {
		return sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	
}

