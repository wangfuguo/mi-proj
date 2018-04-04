package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

import java.util.Date;

/**
 * ClassName: DeliveryChargesEntity
 * @author 廖文强
 * @date 2016年06月06日
 * @version V1.0
 */
public class PricePickupFeeTyEntity extends BaseEntity{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/**提货费城市code*/
	private String priceCityCode;

	/**最低收费*/
	private String minMoney;

    /**
     * 重货单价
     */
    private String weightPrice;
    /**
     * 轻货单价
     */
    private String volumnPrice;
    /**
     * 生效时间
     */
    private Date effectiveTime;

    /**
     * 失效时间
     */
    private Date invalidTime;

    /**
     * 运输类型
     */
    private String transportType;

	 /**
     * 是否有效
     */
    private String active;
	/**
	 * 开单时间
	 */
	private Date billTime;

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getPriceCityCode() {
		return priceCityCode;
	}

	public void setPriceCityCode(String priceCityCode) {
		this.priceCityCode = priceCityCode;
	}

	public String getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(String minMoney) {
		this.minMoney = minMoney;
	}

	public String getWeightPrice() {
		return weightPrice;
	}

	public void setWeightPrice(String weightPrice) {
		this.weightPrice = weightPrice;
	}

	public String getVolumnPrice() {
		return volumnPrice;
	}

	public void setVolumnPrice(String volumnPrice) {
		this.volumnPrice = volumnPrice;
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

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
}
