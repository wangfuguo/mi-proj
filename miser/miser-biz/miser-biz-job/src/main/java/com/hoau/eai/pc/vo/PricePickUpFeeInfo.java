package com.hoau.eai.pc.vo;

import java.io.Serializable;
/**
 * 提货费
 * ClassName: PricePickUpFeeInfo 
 * @author 刘海飞
 * @date 2016年2月22日
 * @version V1.0
 */
public class PricePickUpFeeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**提货费城市code*/
	private String priceCityCode;
	/**
	 * 运输类型：(D-定日达、P-普通零担、Z-整车)
	 */
	private String transportType;
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
	 * 备注
	 */
    private String remark;
    /**
     * 是否有效 0无效 1有效
     */
    private String sfyx;
    
	public String getPriceCityCode() {
		return priceCityCode;
	}
	public void setPriceCityCode(String priceCityCode) {
		this.priceCityCode = priceCityCode;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
    
}
