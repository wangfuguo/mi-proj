package com.hoau.eai.pc.vo;

import java.io.Serializable;
/**
 * 送货费
 * ClassName: PricePickUpFeeInfo 
 * @author 刘海飞
 * @date 2016年2月22日
 * @version V1.0
 */
public class PriceDeliveryFeeInfo implements Serializable {
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 2154067366550795391L;

	/**
	 * 城市类型
	 */
	private String cityType;
	/**
	 * 运输类型:运输类型 D-定日达;Z-整车;P-普通零担
	 */
	private String transportType;
	
	/**
	 * 优惠分段
	 */
	private String yhfdlxId;
    
	/**
	 * 是否有效(0/1):y->1,n->0
	 */
	private String sfyx;
	/**
	 * 备注
	 */
	private String remark;
	public String getCityType() {
		return cityType;
	}
	public void setCityType(String cityType) {
		this.cityType = cityType;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getYhfdlxId() {
		return yhfdlxId;
	}
	public void setYhfdlxId(String yhfdlxId) {
		this.yhfdlxId = yhfdlxId;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
