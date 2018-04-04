package com.hoau.miser.module.api.itf.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.WaybillCalculateBaseEntity;

/**
 * 查询增值费参数DTO
 * 
 * @author 蒋落琛
 * @date 2016-6-8下午2:16:41
 */
public class QuerySurchargeDto extends WaybillCalculateBaseEntity
		implements Serializable {

	private static final long serialVersionUID = -4088316274546424215L;

	/**
	 * 出发公司编号
	 */
	private String startOrgCode;

	/**
	 * 到达公司编号
	 */
	private String arrivalOrgCode;

	/**
	 * 产品类型
	 */
	private String productType;

	/**
	 * 子产品类型
	 */
	private String subProductType;

	/**
	 * 收货方式
	 */
	private String pickUpWay;
	
	/**
	 * 送货方式
	 */
	private String deliveryMethod;

	/**
	 * 到达城市编码
	 */
	private String arrivePriceCity;

	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * 体积
	 */
	private BigDecimal volume;
	/**
	 * 是否天猫订单
	 */
	private boolean tmall;

	/**
	 * OMS业务类型
	 */
	private String omsBizType;

	/**
	 * OMS订单渠道
	 */
	private String orderOrign;

	/**
	 * 轻重货类型
	 */
	private String lightHeavyType;

	/**
	 * 客户编号
	 */
	private String customerCode;
	
	/**
	 * 代收货款类型
	 */
	private String collectionPayType;

	/**
	 * 代收货款金额
	 */
	private BigDecimal collectionPayAmount;

	/**
	 * 代收货款费率
	 */
	private BigDecimal collectionPayRate;
	
	/**
	 * 保价费率
	 */
	private BigDecimal insuredRate;
	
	/**
	 * 保价金额
	 */
	private BigDecimal insuredMoney;
	
	/**
	 * 是否内部带货
	 */
	private boolean isInternalBelt;
	
	/**
     * 是否历史
     */
    private boolean isHistory;
	
	/**
	 * 开单时间
	 */
    private Date billTime;
    
    /**
     * 是否短信服务
     */
    private boolean isSmsService;
    
    /**
     * 起运地公司省市区信息
     */
    private OrgPositionInfoTyEntity originPositionInfo;
    
    /**
     * 目的地公司省市区信息
     */
    private OrgPositionInfoTyEntity destPositionInfo;
    
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public boolean isTmall() {
		return tmall;
	}

	public void setTmall(boolean tmall) {
		this.tmall = tmall;
	}

	public String getLightHeavyType() {
		return lightHeavyType;
	}

	public void setLightHeavyType(String lightHeavyType) {
		this.lightHeavyType = lightHeavyType;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getOmsBizType() {
		return omsBizType;
	}

	public void setOmsBizType(String omsBizType) {
		this.omsBizType = omsBizType;
	}

	public String getOrderOrign() {
		return orderOrign;
	}

	public void setOrderOrign(String orderOrign) {
		this.orderOrign = orderOrign;
	}

	public String getArrivePriceCity() {
		return arrivePriceCity;
	}

	public void setArrivePriceCity(String arrivePriceCity) {
		this.arrivePriceCity = arrivePriceCity;
	}

	public String getArrivalOrgCode() {
		return arrivalOrgCode;
	}

	public void setArrivalOrgCode(String arrivalOrgCode) {
		this.arrivalOrgCode = arrivalOrgCode;
	}

	public String getStartOrgCode() {
		return startOrgCode;
	}

	public void setStartOrgCode(String startOrgCode) {
		this.startOrgCode = startOrgCode;
	}

	public BigDecimal getInsuredMoney() {
		return insuredMoney;
	}

	public void setInsuredMoney(BigDecimal insuredMoney) {
		this.insuredMoney = insuredMoney;
	}

	public String getPickUpWay() {
		return pickUpWay;
	}

	public void setPickUpWay(String pickUpWay) {
		this.pickUpWay = pickUpWay;
	}

	public String getCollectionPayType() {
		return collectionPayType;
	}

	public void setCollectionPayType(String collectionPayType) {
		this.collectionPayType = collectionPayType;
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

	public boolean isInternalBelt() {
		return isInternalBelt;
	}

	public void setInternalBelt(boolean isInternalBelt) {
		this.isInternalBelt = isInternalBelt;
	}

	public BigDecimal getInsuredRate() {
		return insuredRate;
	}

	public void setInsuredRate(BigDecimal insuredRate) {
		this.insuredRate = insuredRate;
	}

	public boolean isSmsService() {
		return isSmsService;
	}

	public void setSmsService(boolean isSmsService) {
		this.isSmsService = isSmsService;
	}

	public OrgPositionInfoTyEntity getOriginPositionInfo() {
		return originPositionInfo;
	}

	public void setOriginPositionInfo(OrgPositionInfoTyEntity originPositionInfo) {
		this.originPositionInfo = originPositionInfo;
	}

	public OrgPositionInfoTyEntity getDestPositionInfo() {
		return destPositionInfo;
	}

	public void setDestPositionInfo(OrgPositionInfoTyEntity destPositionInfo) {
		this.destPositionInfo = destPositionInfo;
	}

	public BigDecimal getCollectionPayAmount() {
		return collectionPayAmount;
	}

	public void setCollectionPayAmount(BigDecimal collectionPayAmount) {
		this.collectionPayAmount = collectionPayAmount;
	}

	public BigDecimal getCollectionPayRate() {
		return collectionPayRate;
	}

	public void setCollectionPayRate(BigDecimal collectionPayRate) {
		this.collectionPayRate = collectionPayRate;
	}
}
