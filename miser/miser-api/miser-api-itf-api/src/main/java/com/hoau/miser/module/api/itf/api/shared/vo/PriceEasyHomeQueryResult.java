package com.hoau.miser.module.api.itf.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceQueryResult
 * @Package com.hoau.miser.module.api.itf.api.shared.vo
 * @Description: 易到家运单查询返回参数
 * @date 16/07/05
 */
public class PriceEasyHomeQueryResult implements Serializable {

	private static final long serialVersionUID = -3085577725459137558L;
	/**
	 * 标准价格下首重
	 */
	private BigDecimal standardWeight;

	/**
	 * 标准价卡下的首重单价
	 */
	private BigDecimal standardWeightPrice;

	/**
	 * 标准价卡下的续重单价
	 */
	private BigDecimal standardAddPrice;

	/**
	 * 网点价卡下的首重
	 */
	private BigDecimal corpWeight;
	/**
	 * 网点价卡下的首重单价
	 */
	private BigDecimal corpWeightPrice;

	/**
	 * 网点价卡下的续重单价
	 */
	private BigDecimal corpAddPrice;

	/**
	 * 客户价卡下的首重
	 */
	private BigDecimal customerWeight;
	/**
	 * 客户价卡下的首重单价
	 */
	private BigDecimal customerWeightPrice;

	/**
	 * 客户价卡下的续重单价
	 */
	private BigDecimal customerAddPrice;

	/**
	 * 首重
	 */
	private BigDecimal weight;
	/**
	 * 最小首重单价
	 */
	private BigDecimal minWeightPrice;

	/**
	 * 最小续重单价
	 */
	private BigDecimal minAddPrice;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public BigDecimal getStandardWeight() {
		return standardWeight;
	}

	public void setStandardWeight(BigDecimal standardWeight) {
		this.standardWeight = standardWeight;
	}

	public BigDecimal getStandardWeightPrice() {
		return standardWeightPrice;
	}

	public void setStandardWeightPrice(BigDecimal standardWeightPrice) {
		this.standardWeightPrice = standardWeightPrice;
	}

	public BigDecimal getStandardAddPrice() {
		return standardAddPrice;
	}

	public void setStandardAddPrice(BigDecimal standardAddPrice) {
		this.standardAddPrice = standardAddPrice;
	}

	public BigDecimal getCorpWeight() {
		return corpWeight;
	}

	public void setCorpWeight(BigDecimal corpWeight) {
		this.corpWeight = corpWeight;
	}

	public BigDecimal getCorpWeightPrice() {
		return corpWeightPrice;
	}

	public void setCorpWeightPrice(BigDecimal corpWeightPrice) {
		this.corpWeightPrice = corpWeightPrice;
	}

	public BigDecimal getCorpAddPrice() {
		return corpAddPrice;
	}

	public void setCorpAddPrice(BigDecimal corpAddPrice) {
		this.corpAddPrice = corpAddPrice;
	}

	public BigDecimal getCustomerWeight() {
		return customerWeight;
	}

	public void setCustomerWeight(BigDecimal customerWeight) {
		this.customerWeight = customerWeight;
	}

	public BigDecimal getCustomerWeightPrice() {
		return customerWeightPrice;
	}

	public void setCustomerWeightPrice(BigDecimal customerWeightPrice) {
		this.customerWeightPrice = customerWeightPrice;
	}

	public BigDecimal getCustomerAddPrice() {
		return customerAddPrice;
	}

	public void setCustomerAddPrice(BigDecimal customerAddPrice) {
		this.customerAddPrice = customerAddPrice;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getMinWeightPrice() {
		return minWeightPrice;
	}

	public void setMinWeightPrice(BigDecimal minWeightPrice) {
		this.minWeightPrice = minWeightPrice;
	}

	public BigDecimal getMinAddPrice() {
		return minAddPrice;
	}

	public void setMinAddPrice(BigDecimal minAddPrice) {
		this.minAddPrice = minAddPrice;
	}
}
