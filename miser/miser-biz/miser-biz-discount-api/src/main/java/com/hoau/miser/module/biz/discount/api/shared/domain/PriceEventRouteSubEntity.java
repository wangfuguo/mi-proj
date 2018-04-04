package com.hoau.miser.module.biz.discount.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
/**
 * 优惠活动线路明细
 * ClassName: PriceEventRouteSubEntity 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
public class PriceEventRouteSubEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	//活动编码 EVENT_CODE
	private String eventCode;
	//出发价格城市 SEND_PRICE_CITY
	private String sendPriceCity;
	//到达价格城市 ARRIVAL_PRICE_CITY
	private String arrivalPriceCity;
	//出发价格城市 SEND_PRICE_CITY
	private String sendPriceCityName;
	//到达价格城市 ARRIVAL_PRICE_CITY
	private String arrivalPriceCityName;
	//备注 REMARK
	private String remark;
	//是否有效 ACTIVE
	private String active;
	
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getSendPriceCity() {
		return sendPriceCity;
	}
	public void setSendPriceCity(String sendPriceCity) {
		this.sendPriceCity = sendPriceCity;
	}
	public String getArrivalPriceCity() {
		return arrivalPriceCity;
	}
	public void setArrivalPriceCity(String arrivalPriceCity) {
		this.arrivalPriceCity = arrivalPriceCity;
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
	public int hashCode() {
        return super.hashCode();
    }
	public String getSendPriceCityName() {
		return sendPriceCityName;
	}
	public void setSendPriceCityName(String sendPriceCityName) {
		this.sendPriceCityName = sendPriceCityName;
	}
	public String getArrivalPriceCityName() {
		return arrivalPriceCityName;
	}
	public void setArrivalPriceCityName(String arrivalPriceCityName) {
		this.arrivalPriceCityName = arrivalPriceCityName;
	}
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass().getPackage() != obj.getClass().getPackage()) {
            return false;
        }
        if (PriceEventRouteSubEntity.class.isAssignableFrom((obj.getClass()))) {
            final PriceEventRouteSubEntity other = (PriceEventRouteSubEntity) obj;
           
            if (this.getId() == null) {
                    return false;
            } else if (!this.getId().equals(other.getId())) {
                return false;
            }
            if(!StringUtil.equals(this.getEventCode(),other.getEventCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getSendPriceCity(),other.getSendPriceCity())){
            	return false;
            }
            if(!StringUtil.equals(this.getArrivalPriceCity(),other.getArrivalPriceCity())){
            	return false;
            }
            if(!StringUtil.equals(this.getRemark(), other.getRemark())){
            	return false;
            }
            if(!StringUtil.equals(this.getActive(), other.getActive())){
            	return false;
            }
            
            return true;
        }
        return false;
    }
}
