package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
/**
 * 价格城市包装费定义
 * ClassName: PricePackageFeeInfo 
 * @author 刘海飞
 * @date 2016年2月22日
 * @version V1.0
 */
public class PricePackageFeePcInfo implements Serializable {

	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -5767994681960636016L;

	/**
	 * 价格城市编号
	 */
	private String priceCity;
	/**
	 * PACKAGE_CODE 包装项目编码
	 */
	private String code;
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
	 * 是否有效 0无效 1有效
	 */
	private String sfyx;
	/**
	 * 操作时间
	 */
	private Date recordDate;
	/**
	 * 运输类型：(D-定日达、P-普通零担、Z-整车)
	 */
	
	/**
	 * rate :系数
	 */
	private String rate;
	
	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	private String transportType;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getPriceCity() {
		return priceCity;
	}

	public void setPriceCity(String priceCity) {
		this.priceCity = priceCity;
	}

	public String getSfyx() {
		return sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	
	
}

