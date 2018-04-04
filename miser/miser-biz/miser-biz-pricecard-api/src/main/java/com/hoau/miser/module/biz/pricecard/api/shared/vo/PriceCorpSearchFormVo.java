package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.util.Date;

public class PriceCorpSearchFormVo {
	
	/**
	 * 1:已失效
	 */
	public static final String STATE_1="PASSED";
	/**
	 * 2:生效中
	 */
	public static final String STATE_2="EFFECTIVE";
	/**
	 * 3:待生效
	 */
	public static final String STATE_3="WAIT";
	/**
	 * 4:已废弃
	 */
	public static final String STATE_4="DELETED";
	
	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 事业部
	 */
	private String division;
	
	/**
	 * 大区
	 */
	private String bigRegion;
	
	/**
	 * 路区
	 */
	private String roadArea;
	
	/**
	 * 门店
	 */
	private String sales;
	
	/**
	 * 状态
	 */
	private String state;
	
	/**
	 * 有效时间
	 */
	private Date effective;

	/**
	 * 排序
	 */
	private String order;
	
	/**
	 * 数据来源
	 */
	private String dataOrign;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getBigRegion() {
		return bigRegion;
	}

	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	public String getRoadArea() {
		return roadArea;
	}

	public void setRoadArea(String roadArea) {
		this.roadArea = roadArea;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getEffective() {
		return effective;
	}

	public void setEffective(Date effective) {
		this.effective = effective;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDataOrign() {
		return dataOrign;
	}

	public void setDataOrign(String dataOrign) {
		this.dataOrign = dataOrign;
	}
	
	
}
