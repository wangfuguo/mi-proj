package com.hoau.eai.pc.vo;

import java.io.Serializable;

/**
 * 价格城市映射同步实体类
 * ClassName: PriceCityMappingInfo 
 * @author 廖文强
 * @date 2016年2月28日
 * @version V1.0
 */
public class PriceCityMappingInfo implements Serializable { /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String active;
	private String areaCode;//区
	private String priceCity;//价格城市
	private String type;//类型（ARRIVAL,SEND）
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getPriceCity() {
		return priceCity;
	}
	public void setPriceCity(String priceCity) {
		this.priceCity = priceCity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
