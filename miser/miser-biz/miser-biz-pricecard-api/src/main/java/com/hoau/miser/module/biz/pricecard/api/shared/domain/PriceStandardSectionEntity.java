package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;

/**
 * @ClassName: PriceStandardSectionEntity
 * @Description: 分段标准价格Entity
 * @author zhangqingfu
 * @date 2016年5月4日 下午1:41:41
 *
 */
public class PriceStandardSectionEntity extends BaseEntity {

	private static final long serialVersionUID = -8187772927765781946L;

	/**
	 * @Fields transTypeCode : 运输类型
	 */
	private String transTypeCode;
	/**
	 * @Fields transTypeName : 运输类型名称
	 */
	private String transTypeName;
	/**
	 * @Fields sendPriceCity : 出发价格城市
	 */
	private PriceCityEntity sendPriceCity;
	/**
	 * @Fields sendPriceCityName : 出发价格城市名称
	 */
	@SuppressWarnings("unused")
	private String sendPriceCityName;
	/**
	 * @Fields arrivePriceCity : 到达价格城市
	 */
	private PriceCityEntity arrivePriceCity;
	/**
	 * @Fields sendPriceCityName : 到达价格城市名称
	 */
	@SuppressWarnings("unused")
	private String arrivePriceCityName;
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
	 * @Fields remark : 备注
	 */
	private String remark;
	/**
	 * @Fields active : 是否可用
	 */
	private String active;
	/**
	 * @Fields state : 当前状态
	 */
	private String state;
	/**
	 * @Fields stateName : 当前状态名称
	 */
	@SuppressWarnings("unused")
	private String stateName;

	public String getTransTypeCode() {
		return transTypeCode;
	}

	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	public PriceCityEntity getSendPriceCity() {
		return sendPriceCity;
	}

	public void setSendPriceCity(PriceCityEntity sendPriceCity) {
		this.sendPriceCity = sendPriceCity;
	}

	public PriceCityEntity getArrivePriceCity() {
		return arrivePriceCity;
	}

	public void setArrivePriceCity(PriceCityEntity arrivePriceCity) {
		this.arrivePriceCity = arrivePriceCity;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSendPriceCityName() {
		return this.sendPriceCityName = this.sendPriceCity == null ? "" : this.sendPriceCity.getName();
	}

	public void setSendPriceCityName(String sendPriceCityName) {
		this.sendPriceCityName = sendPriceCityName;
	}

	public String getArrivePriceCityName() {
		return this.arrivePriceCityName = this.arrivePriceCity == null ? "" : this.arrivePriceCity.getName();
	}

	public void setArrivePriceCityName(String arrivePriceCityName) {
		this.arrivePriceCityName = arrivePriceCityName;
	}

	public String getStateName() {
		String result = "";
		if("PASSED".equals(state)){
			result = "已过期";
		}
		if("EFFECTIVE".equals(state)){
			result = "生效中";
		}
		if("WAIT".equals(state)){
			result = "待生效";
		}
		if("DELETED".equals(state)){
			result = "已废弃";
		}
		return result;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
