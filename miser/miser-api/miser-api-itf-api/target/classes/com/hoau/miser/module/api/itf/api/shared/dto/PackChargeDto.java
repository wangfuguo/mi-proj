package com.hoau.miser.module.api.itf.api.shared.dto;

import java.math.BigDecimal;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.WaybillCalculateBaseEntity;

/**
 * 包装费信息返回值
 * 
 * @author 蒋落琛
 * @date 2016-6-8下午6:27:35
 */
public class PackChargeDto extends WaybillCalculateBaseEntity {

	private static final long serialVersionUID = 6800625487448258833L;

	/**
	 * 服务项目编号
	 */
	private String itemCode;

	/**
	 * 服务项目名称
	 */
	private String itemName;

	/**
	 * 计算方式
	 */
	private String calcuMethod;
	
	/**
	 * 系数
	 */
	private BigDecimal coefficient;

	/**
	 * 单价
	 */
	private BigDecimal unitPrice;

	/**
	 * 最低收费
	 */
	private BigDecimal lowestPrice;

	/**
	 * 锁定方式
	 */
	private Integer lockType;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public Integer getLockType() {
		return lockType;
	}

	public void setLockType(Integer lockType) {
		this.lockType = lockType;
	}

	public BigDecimal getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(BigDecimal coefficient) {
		this.coefficient = coefficient;
	}

	public String getCalcuMethod() {
		return calcuMethod;
	}

	public void setCalcuMethod(String calcuMethod) {
		this.calcuMethod = calcuMethod;
	}
}
