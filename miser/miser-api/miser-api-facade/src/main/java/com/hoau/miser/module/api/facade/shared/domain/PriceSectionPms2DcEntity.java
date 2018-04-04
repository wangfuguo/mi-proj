package com.hoau.miser.module.api.facade.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
/**
 * @ClassName: PriceSectionEntity
 * @Description: 易到家（易入户，易到家，易安装）分段价格Entity:来自于易入户标准、网店、客户（分段）价格(PriceStandardSection/PriceCorpSection/PriceCustomerSection)
 * @author ZOUYU
 * @date 2016年5月4日 下午1:41:41
 */
public class PriceSectionPms2DcEntity extends BaseEntity {

	/**
	 * @Field serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *@Fields 价格类型：易入户标准、网店、客户（分段）价格（PriceStandardSection/PriceCorpSection/PriceCustomerSection）
	 */
	private String priceSectionType;
	/**
	 *@Fields 网店-迪辰公司编码-网点价格
	 */
	private String corpCode;
	/**
	 * @Fields 客户编码-迪辰客户编码-客户价格
	 */
    private String customerCode;
	/**
	 * @Fields transTypeCode : 运输类型-迪辰运单产品类型
	 */
	private String transTypeCode;
	/**
	 * @Fields sendPriceCity : 出发价格城市-迪辰公司出发价卡城市编号
	 */
	private String sendPriceCityCode;
	/**
	 * @Fields arrivePriceCity : 到达价格城市-迪辰公司到达价卡城市编号
	 */
	private String arrivePriceCityCode;
	/**
	 * @Fields firstWeight : 一段首重
	 */
	private Double firstWeight;
	/**
	 * @Fields firstWeightPrice : 一段首重单价
	 */
	private Double firstWeightPrice;
	/**
	 * @Fields firstAddWeightPrice : 一段续重单价
	 */
	private Double firstAddWeightPrice;
	/**
	 * @Fields secondWeight : 二段首重
	 */
	private Double secondWeight;
	/**
	 * @Fields secondWeightPrice : 二段首重单价
	 */
	private Double secondWeightPrice;
	/**
	 * @Fields secondAddWeightPrice : 二段续重单价
	 */
	private Double secondAddWeightPrice;
	/**
	 * @Fields thirdWeight : 三段首重
	 */
	private Double thirdWeight;
	/**
	 * @Fields thirdWeightPrice : 三段首重单价
	 */
	private Double thirdWeightPrice;
	/**
	 * @Fields thirdAddWeightPrice : 三段续重单价
	 */
	private Double thirdAddWeightPrice;
	/**
	 * @Fields effectiveTime : 生效时间
	 */
	private Date effectiveTime;
	/**
	 * @Fields invalidTime : 失效时间
	 */
	private Date invalidTime;
	/**
	 * @Fields active : 是否可用
	 */
	private String active;
	/**
	 * @Fields freightSectionCode : pms优惠分段oode
	 */
	private String freightSectionCode;
	/**
	 * @Fields priceSectionEntity : 优惠分段
	 */
	private PriceSectionEntity priceSectionEntity;
	
	public String getPriceSectionType() {
		return priceSectionType;
	}
	public void setPriceSectionType(String priceSectionType) {
		this.priceSectionType = priceSectionType;
	}
	public String getCorpCode() {
		return corpCode;
	}
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	public String getSendPriceCityCode() {
		return sendPriceCityCode;
	}
	public void setSendPriceCityCode(String sendPriceCityCode) {
		this.sendPriceCityCode = sendPriceCityCode;
	}
	public String getArrivePriceCityCode() {
		return arrivePriceCityCode;
	}
	public void setArrivePriceCityCode(String arrivePriceCityCode) {
		this.arrivePriceCityCode = arrivePriceCityCode;
	}
	public Double getFirstWeight() {
		return firstWeight;
	}
	public void setFirstWeight(Double firstWeight) {
		this.firstWeight = firstWeight;
	}
	public Double getFirstWeightPrice() {
		return firstWeightPrice;
	}
	public void setFirstWeightPrice(Double firstWeightPrice) {
		this.firstWeightPrice = firstWeightPrice;
	}
	public Double getFirstAddWeightPrice() {
		return firstAddWeightPrice;
	}
	public void setFirstAddWeightPrice(Double firstAddWeightPrice) {
		this.firstAddWeightPrice = firstAddWeightPrice;
	}
	public Double getSecondWeight() {
		return secondWeight;
	}
	public void setSecondWeight(Double secondWeight) {
		this.secondWeight = secondWeight;
	}
	public Double getSecondWeightPrice() {
		return secondWeightPrice;
	}
	public void setSecondWeightPrice(Double secondWeightPrice) {
		this.secondWeightPrice = secondWeightPrice;
	}
	public Double getSecondAddWeightPrice() {
		return secondAddWeightPrice;
	}
	public void setSecondAddWeightPrice(Double secondAddWeightPrice) {
		this.secondAddWeightPrice = secondAddWeightPrice;
	}
	public Double getThirdWeight() {
		return thirdWeight;
	}
	public void setThirdWeight(Double thirdWeight) {
		this.thirdWeight = thirdWeight;
	}
	public Double getThirdWeightPrice() {
		return thirdWeightPrice;
	}
	public void setThirdWeightPrice(Double thirdWeightPrice) {
		this.thirdWeightPrice = thirdWeightPrice;
	}
	public Double getThirdAddWeightPrice() {
		return thirdAddWeightPrice;
	}
	public void setThirdAddWeightPrice(Double thirdAddWeightPrice) {
		this.thirdAddWeightPrice = thirdAddWeightPrice;
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getFreightSectionCode() {
		return freightSectionCode;
	}
	public void setFreightSectionCode(String freightSectionCode) {
		this.freightSectionCode = freightSectionCode;
	}
	public PriceSectionEntity getPriceSectionEntity() {
		return priceSectionEntity;
	}
	public void setPriceSectionEntity(PriceSectionEntity priceSectionEntity) {
		this.priceSectionEntity = priceSectionEntity;
	}

}
