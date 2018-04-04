package com.hoau.miser.module.api.itf.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hoau.miser.module.api.itf.api.shared.domain.WaybillCalculateBaseEntity;

/**
 * 特服费计算结果
 *
 * @author 蒋落琛
 * @date 2016-6-15下午2:41:39
 */
public class SpecialServerCalculateResultDto extends WaybillCalculateBaseEntity
		implements Serializable {

	private static final long serialVersionUID = -2956938408347346040L;

	/**
	 * 服务类型代码
	 */
	private Integer itemCode;

	/**
	 * 服务类型名称
	 */
	private String itemName;

	/**
	 * 锁定方式
	 */
	private Integer lockType;

	/**
	 * 单位名称
	 */
	private String unitName;

	/**
	 * 数量
	 */
	private BigDecimal unitValue;

	/**
	 * 费用名称
	 */
	private String priceName;

	/**
	 * 费用
	 */
	private BigDecimal priceValue;
	
	/**
	 * 标准金额
	 */
	private BigDecimal standardAmount;

	/**
	 * 数量是否可编辑
	 */
	private boolean isEdit;

	/**
	 * 是否拥有配置项
	 */
	private boolean isHaveConfig;

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public Integer getItemCode() {
		return itemCode;
	}

	public void setItemCode(Integer itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getLockType() {
		return lockType;
	}

	public void setLockType(Integer lockType) {
		this.lockType = lockType;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(BigDecimal unitValue) {
		this.unitValue = unitValue;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public BigDecimal getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(BigDecimal priceValue) {
		this.priceValue = priceValue;
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
