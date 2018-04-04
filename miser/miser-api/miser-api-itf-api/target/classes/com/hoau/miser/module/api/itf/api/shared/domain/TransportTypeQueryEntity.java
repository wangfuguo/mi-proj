package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

import java.util.Date;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: TimeStandardEnity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 运输类型查询参数
 * @date 2016/6/1 17:09
 */
public class TransportTypeQueryEntity extends BaseEntity {

	/** 查询时间 */
	private Date queryTime;

    /** 出发时效城市**/
    private String sendTimeCity;

    /** 到达时效城市**/
    private String arriveTimeCity;

	/** 出发价格城市 */
    private String sendPriceCity;

	/** 到达价格城市 */
    private String arrivePriceCity;

	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}

	public String getSendTimeCity() {
		return sendTimeCity;
	}

	public void setSendTimeCity(String sendTimeCity) {
		this.sendTimeCity = sendTimeCity;
	}

	public String getArriveTimeCity() {
		return arriveTimeCity;
	}

	public void setArriveTimeCity(String arriveTimeCity) {
		this.arriveTimeCity = arriveTimeCity;
	}

	public String getSendPriceCity() {
		return sendPriceCity;
	}

	public void setSendPriceCity(String sendPriceCity) {
		this.sendPriceCity = sendPriceCity;
	}

	public String getArrivePriceCity() {
		return arrivePriceCity;
	}

	public void setArrivePriceCity(String arrivePriceCity) {
		this.arrivePriceCity = arrivePriceCity;
	}
}
