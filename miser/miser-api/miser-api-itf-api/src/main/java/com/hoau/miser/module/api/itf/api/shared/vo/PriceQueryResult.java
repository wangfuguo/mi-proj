package com.hoau.miser.module.api.itf.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: PriceQueryResult
 * @Package com.hoau.miser.module.api.itf.api.shared.vo
 * @Description: 运单查询返回参数
 * @date 16/6/8 16:06
 */
public class PriceQueryResult implements Serializable {

	private static final long serialVersionUID = -3085577725459137558L;

	/**
	 * 使用的价格类型：1、定日达  2、易到家
	 */
	private String usePriceType;

	/**
	 * 标准价卡下的重量单价
	 */
	private BigDecimal standardWeightPrice;

	/**
	 * 标准价卡下的体积单价
	 */
	private BigDecimal standardVolumePrice;

	/**
	 * 网点价卡下的重量单价
	 */
	private BigDecimal corpWeightPrice;

	/**
	 * 网点价卡下的体积单价
	 */
	private BigDecimal corpVolumePrice;

	/**
	 * 客户价卡下的重量单价
	 */
	private BigDecimal customerWeightPrice;

	/**
	 * 客户价卡下的体积单价
	 */
	private BigDecimal customerVolumePrice;

	/**
	 * 界面显示的重量单价
	 */
	private BigDecimal showWeightPrice;

	/**
	 * 界面显示的体积单价
	 */
	private BigDecimal showVolumePrice;

	/**
	 * 重量折扣
	 */
	private BigDecimal weightDiscount;

	/**
	 * 体积折扣
	 */
	private BigDecimal volumeDiscount;

	/**
	 * 最小重量单价
	 */
	private BigDecimal minWeightPrice;

	/**
	 * 最小体积单价
	 */
	private BigDecimal minVolumePrice;

	/**
	 * 活动编码:如果使用的折扣是某个活动产生的,则返回此活动的编码,如果不是因为活动产生的,则返回空
	 */
	private String eventCode;

	/**
	 * 活动名称
	 */
	private String eventName;

	/**
	 * 最小运费
	 */
	private BigDecimal minFreightCharge;

	/**
	 * 易到家的价格信息
	 */
	private PriceEasyHomeQueryResult priceEasyHomeQueryResult;

	public BigDecimal getStandardWeightPrice() {
		return standardWeightPrice;
	}

	public void setStandardWeightPrice(BigDecimal standardWeightPrice) {
		this.standardWeightPrice = standardWeightPrice;
	}

	public BigDecimal getStandardVolumePrice() {
		return standardVolumePrice;
	}

	public void setStandardVolumePrice(BigDecimal standardVolumePrice) {
		this.standardVolumePrice = standardVolumePrice;
	}

	public BigDecimal getCorpWeightPrice() {
		return corpWeightPrice;
	}

	public void setCorpWeightPrice(BigDecimal corpWeightPrice) {
		this.corpWeightPrice = corpWeightPrice;
	}

	public BigDecimal getCorpVolumePrice() {
		return corpVolumePrice;
	}

	public void setCorpVolumePrice(BigDecimal corpVolumePrice) {
		this.corpVolumePrice = corpVolumePrice;
	}

	public BigDecimal getCustomerWeightPrice() {
		return customerWeightPrice;
	}

	public void setCustomerWeightPrice(BigDecimal customerWeightPrice) {
		this.customerWeightPrice = customerWeightPrice;
	}

	public BigDecimal getCustomerVolumePrice() {
		return customerVolumePrice;
	}

	public void setCustomerVolumePrice(BigDecimal customerVolumePrice) {
		this.customerVolumePrice = customerVolumePrice;
	}

	public BigDecimal getShowWeightPrice() {
		return showWeightPrice;
	}

	public void setShowWeightPrice(BigDecimal showWeightPrice) {
		this.showWeightPrice = showWeightPrice;
	}

	public BigDecimal getShowVolumePrice() {
		return showVolumePrice;
	}

	public void setShowVolumePrice(BigDecimal showVolumePrice) {
		this.showVolumePrice = showVolumePrice;
	}

	public BigDecimal getWeightDiscount() {
		return weightDiscount;
	}

	public void setWeightDiscount(BigDecimal weightDiscount) {
		this.weightDiscount = weightDiscount;
	}

	public BigDecimal getVolumeDiscount() {
		return volumeDiscount;
	}

	public void setVolumeDiscount(BigDecimal volumeDiscount) {
		this.volumeDiscount = volumeDiscount;
	}

	public BigDecimal getMinWeightPrice() {
		return minWeightPrice;
	}

	public void setMinWeightPrice(BigDecimal minWeightPrice) {
		this.minWeightPrice = minWeightPrice;
	}

	public BigDecimal getMinVolumePrice() {
		return minVolumePrice;
	}

	public void setMinVolumePrice(BigDecimal minVolumePrice) {
		this.minVolumePrice = minVolumePrice;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public BigDecimal getMinFreightCharge() {
		return minFreightCharge;
	}

	public void setMinFreightCharge(BigDecimal minFreightCharge) {
		this.minFreightCharge = minFreightCharge;
	}

	public PriceEasyHomeQueryResult getPriceEasyHomeQueryResult() {
		return priceEasyHomeQueryResult;
	}
	public void setPriceEasyHomeQueryResult(PriceEasyHomeQueryResult priceEasyHomeQueryResult) {
		this.priceEasyHomeQueryResult = priceEasyHomeQueryResult;
	}

	public String getUsePriceType() {
		return usePriceType;
	}

	public void setUsePriceType(String usePriceType) {
		this.usePriceType = usePriceType;
	}
}
