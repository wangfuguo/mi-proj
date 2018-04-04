package com.hoau.miser.module.api.itf.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.miser.module.api.itf.api.shared.domain.WaybillCalculateBaseEntity;

/**
 * 特服费计算条件
 * 
 * @author 蒋落琛
 * @date 2016-6-15下午2:42:52
 */
public class SpecialServerPriceConditionDto extends WaybillCalculateBaseEntity {

	private static final long serialVersionUID = -2512984851786030647L;

	/**
	 * 特服费单项编号
	 */
	private Integer itemCode;

	/**
	 * 重量单价
	 */
	private BigDecimal weightUnitPrice;

	/**
	 * 体积单价
	 */
	private BigDecimal volumeUnitPrice;

	/**
	 * 货物重量
	 */
	private BigDecimal goodsWeight;

	/**
	 * 货物体积
	 */
	private BigDecimal goodsVolume;

	/**
	 * 产品类型
	 */
	private String productType;

	/**
	 * 子产品类型
	 */
	private String subProductType;

	/**
	 * 单项服务项目个数
	 */
	private Integer unitermCount;
	
	/**
     * 是否历史
     */
    private boolean isHistory;
	
	/**
	 * 开单时间
	 */
    private Date billTime;
    
    /**
     * 轻重货类型 0:重货 1:轻货
     */
    private String goodsType;
    
    /**
	 * 是否内部带货
	 */
	private boolean isInternalBelt;

	public Integer getItemCode() {
		return itemCode;
	}

	public void setItemCode(Integer itemCode) {
		this.itemCode = itemCode;
	}
	
	public BigDecimal getWeightUnitPrice() {
		return weightUnitPrice;
	}

	public void setWeightUnitPrice(BigDecimal weightUnitPrice) {
		this.weightUnitPrice = weightUnitPrice;
	}

	public BigDecimal getVolumeUnitPrice() {
		return volumeUnitPrice;
	}

	public void setVolumeUnitPrice(BigDecimal volumeUnitPrice) {
		this.volumeUnitPrice = volumeUnitPrice;
	}

	public BigDecimal getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}

	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}


	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getUnitermCount() {
		return unitermCount;
	}

	public void setUnitermCount(Integer unitermCount) {
		this.unitermCount = unitermCount;
	}

	public boolean isHistory() {
		return isHistory;
	}

	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public boolean isInternalBelt() {
		return isInternalBelt;
	}

	public void setInternalBelt(boolean isInternalBelt) {
		this.isInternalBelt = isInternalBelt;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
}
