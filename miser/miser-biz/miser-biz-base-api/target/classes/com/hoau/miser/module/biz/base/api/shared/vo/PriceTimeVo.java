package com.hoau.miser.module.biz.base.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceExtrafeeStandardEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceTimeEntity;

public class PriceTimeVo implements Serializable{
	
	private static final long serialVersionUID = 5980445609125286233L;
	
	//价格时效实体
	private PriceTimeEntity priceTimeEntity;
	
	//价格时效实体集合 用于查询结果
	private List<PriceTimeEntity> priceTimeEntityList;
	
	//点击 活动详情时 传递的 活动编码
	private String eventCode;
	
	//价格计算界面 填写的 总体积 总重量
	private String totalVolume;
	private String totalWeight;
	
	//后台价格计算后 用于返回的标准附加费
	private List<PriceExtrafeeStandardEntity> priceExtrafeeStandardEntityList;
	
	//后台价格计算后 纯运费
	private BigDecimal freightFee;
	
	//短信内容发送模板
	private String smsTpl;
	
	//接收短信的手机号
	private String telephone;
	
	//出发 门店
	private String sendSaleDepartment;
	
	//到达门店
	private String arriveSaleDepartment;
	
	//代收类型
	private String collectDeliveryType;
	
	
	
	
	
	private List<PriceSectionEntity> priceSectionEntityList;
	
	
	public PriceTimeEntity getPriceTimeEntity() {
		return priceTimeEntity;
	}
	public void setPriceTimeEntity(PriceTimeEntity priceTimeEntity) {
		this.priceTimeEntity = priceTimeEntity;
	}
	public List<PriceTimeEntity> getPriceTimeEntityList() {
		return priceTimeEntityList;
	}
	public void setPriceTimeEntityList(List<PriceTimeEntity> priceTimeEntityList) {
		this.priceTimeEntityList = priceTimeEntityList;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public List<PriceSectionEntity> getPriceSectionEntityList() {
		return priceSectionEntityList;
	}
	public void setPriceSectionEntityList(
			List<PriceSectionEntity> priceSectionEntityList) {
		this.priceSectionEntityList = priceSectionEntityList;
	}
	public String getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(String totalVolume) {
		this.totalVolume = totalVolume;
	}
	public String getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
	public List<PriceExtrafeeStandardEntity> getPriceExtrafeeStandardEntityList() {
		return priceExtrafeeStandardEntityList;
	}
	public void setPriceExtrafeeStandardEntityList(
			List<PriceExtrafeeStandardEntity> priceExtrafeeStandardEntityList) {
		this.priceExtrafeeStandardEntityList = priceExtrafeeStandardEntityList;
	}
	public BigDecimal getFreightFee() {
		return freightFee;
	}
	public void setFreightFee(BigDecimal freightFee) {
		this.freightFee = freightFee;
	}
	public String getSmsTpl() {
		return smsTpl;
	}
	public void setSmsTpl(String smsTpl) {
		this.smsTpl = smsTpl;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSendSaleDepartment() {
		return sendSaleDepartment;
	}
	public void setSendSaleDepartment(String sendSaleDepartment) {
		this.sendSaleDepartment = sendSaleDepartment;
	}
	public String getArriveSaleDepartment() {
		return arriveSaleDepartment;
	}
	public void setArriveSaleDepartment(String arriveSaleDepartment) {
		this.arriveSaleDepartment = arriveSaleDepartment;
	}
	public String getCollectDeliveryType() {
		return collectDeliveryType;
	}
	public void setCollectDeliveryType(String collectDeliveryType) {
		this.collectDeliveryType = collectDeliveryType;
	}
 
	
	
}
