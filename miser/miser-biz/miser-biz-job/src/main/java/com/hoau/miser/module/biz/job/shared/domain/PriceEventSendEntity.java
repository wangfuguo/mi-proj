package com.hoau.miser.module.biz.job.shared.domain;

import java.util.Date;
import java.util.List;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
/**
 * 优惠活动
 * ClassName: PriceEvnet 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
public class PriceEventSendEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 相差时间 结束-开始(分钟)
	 */
	public static final long minuteDiff=0;
	/**
	 * 1:活动结束
	 */
	public static final String STATE_1="1";
	/**
	 * 2:活动中
	 */
	public static final String STATE_2="2";
	/**
	 * 3:待生效
	 */
	public static final String STATE_3="3";
	/**
	 * 4:未生效被作废
	 */
	public static final String STATE_4="4";
	/**
	 * 5:被强制取消
	 */
	public static final String STATE_5="5";

	//活动编码 EVENT_CODE
	private String eventCode;
	//活动名称 EVENT_NAME
	private String eventName;
	// 生效时间-EFFECTIVE_TIME
	private Date effectiveTime;
	// 失效时间-INVALID_TIME
	private Date invalidTime;
	//备注 REMARK
	private String remark;
	//是否有效 ACTIVE
	private String active;
	//IS_FORCE_COLSE 是否强制终止
	private String isForceColse;
	
	public String getIsForceColse() {
		return isForceColse;
	}
	public void setIsForceColse(String isForceColse) {
		this.isForceColse = isForceColse;
	}

	//活动状态
	private String state;
	//优惠折扣明细集合
	private List<PriceEventDiscountSubSendEntity> priceEventDiscountSubs;
	//优惠渠道集合
	private List<PriceEventOrderchannelSubSendEntity> priceEventOrderchannelSubs;
	//优惠线路集合
	private List<PriceEventRouteSubSendEntity> priceEventRouteSubs;
	//优惠门店集合
	private List<PriceEventCorpSubSendEntity> priceEventCorpSubs;
	//客户集合
	private List<PriceEventCustomerSubSendEntity> priceEventCustomerSubs;
	
	//出发价格城市 SEND_PRICE_CITY
	private String sendPriceCity;
	//到达价格城市 ARRIVAL_PRICE_CITY
	private String arrivalPriceCity;
	// 运输类型 TRANS_TYPE_CODE
	private String transTypeCode;
	
	//出发门店
	private String corpCode;
	/**
	 * @Fields startTime : 查询数据开始时间
	 */
	private Date startTime;
	
	/**
	 * @Fields endTime : 查询数据结束时间
	 */
	private Date endTime;
	public List<PriceEventCustomerSubSendEntity> getPriceEventCustomerSubs() {
		return priceEventCustomerSubs;
	}
	public void setPriceEventCustomerSubs(
			List<PriceEventCustomerSubSendEntity> priceEventCustomerSubs) {
		this.priceEventCustomerSubs = priceEventCustomerSubs;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
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
	public List<PriceEventDiscountSubSendEntity> getPriceEventDiscountSubs() {
		return priceEventDiscountSubs;
	}
	public void setPriceEventDiscountSubs(
			List<PriceEventDiscountSubSendEntity> priceEventDiscountSubs) {
		this.priceEventDiscountSubs = priceEventDiscountSubs;
	}
	public List<PriceEventOrderchannelSubSendEntity> getPriceEventOrderchannelSubs() {
		return priceEventOrderchannelSubs;
	}
	public void setPriceEventOrderchannelSubs(
			List<PriceEventOrderchannelSubSendEntity> priceEventOrderchannelSubs) {
		this.priceEventOrderchannelSubs = priceEventOrderchannelSubs;
	}
	public List<PriceEventRouteSubSendEntity> getPriceEventRouteSubs() {
		return priceEventRouteSubs;
	}
	public void setPriceEventRouteSubs(
			List<PriceEventRouteSubSendEntity> priceEventRouteSubs) {
		this.priceEventRouteSubs = priceEventRouteSubs;
	}
	public List<PriceEventCorpSubSendEntity> getPriceEventCorpSubs() {
		return priceEventCorpSubs;
	}
	public void setPriceEventCorpSubs(
			List<PriceEventCorpSubSendEntity> priceEventCorpSubs) {
		this.priceEventCorpSubs = priceEventCorpSubs;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
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
        if (PriceEventSendEntity.class.isAssignableFrom((obj.getClass()))) {
            final PriceEventSendEntity other = (PriceEventSendEntity) obj;
           
            if (this.getId() == null) {
                    return false;
            } else if (!this.getId().equals(other.getId())) {
                return false;
            }
            if(!StringUtil.equals(this.getEventCode(),other.getEventCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getEventName(),other.getEventName())){
            	return false;
            }
            if(!this.getEffectiveTime().equals(other.getEffectiveTime())){
            	return false;
            }
            if(!this.getInvalidTime().equals(other.getInvalidTime())){
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
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getCorpCode() {
		return corpCode;
	}
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}
	
}
