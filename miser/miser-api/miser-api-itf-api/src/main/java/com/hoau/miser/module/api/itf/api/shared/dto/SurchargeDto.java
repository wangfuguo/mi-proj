package com.hoau.miser.module.api.itf.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hoau.miser.module.api.itf.api.shared.define.SurchargeType;
import com.hoau.miser.module.api.itf.api.shared.domain.WaybillCalculateBaseEntity;

/**
 * 增值费信息返回值DTO
 *
 * @author 蒋落琛
 * @date 2016-6-2下午3:30:33
 */
public class SurchargeDto  extends WaybillCalculateBaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 附加费类型
	 */
	private SurchargeType surchargeType;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 标准金额
	 */
	private BigDecimal standardAmount;
	
	/**
	 * 锁定方式
	 */
	private Integer lockType;
	
	/**
	 * 最小金额
	 */
	private BigDecimal minAmount;
	
	/**
	 * 最大金额
	 */
	private BigDecimal maxAmount;

	/**
	 * 是否拥有配置项
     */
	private boolean isHaveConfig;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getLockType() {
		return lockType;
	}

	public void setLockType(Integer lockType) {
		this.lockType = lockType;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public SurchargeType getSurchargeType() {
		return surchargeType;
	}

	public void setSurchargeType(SurchargeType surchargeType) {
		this.surchargeType = surchargeType;
	}

	public BigDecimal getStandardAmount() {
		return standardAmount;
	}

	public void setStandardAmount(BigDecimal standardAmount) {
		this.standardAmount = standardAmount;
	}

	public boolean isHaveConfig() {
		return isHaveConfig;
	}

	public void setHaveConfig(boolean haveConfig) {
		isHaveConfig = haveConfig;
	}
}
