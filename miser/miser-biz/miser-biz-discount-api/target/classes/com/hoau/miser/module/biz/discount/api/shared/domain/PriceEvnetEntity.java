package com.hoau.miser.module.biz.discount.api.shared.domain;

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
public class PriceEvnetEntity extends BaseEntity{
	
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
	private List<PriceEventDiscountSubEntity> priceEventDiscountSubs;
	//优惠渠道集合
	private List<PriceEventOrderchannelSubEntity> priceEventOrderchannelSubs;
	//优惠线路集合
	private List<PriceEventRouteSubEntity> priceEventRouteSubs;
	//优惠门店集合
	private List<PriceEventCorpSubEntity> priceEventCorpSubs;
	//客户集合
	private List<PriceEventCustomerSubEntity> priceEventCustomerSubs;
	
	
	public List<PriceEventCustomerSubEntity> getPriceEventCustomerSubs() {
		return priceEventCustomerSubs;
	}
	public void setPriceEventCustomerSubs(
			List<PriceEventCustomerSubEntity> priceEventCustomerSubs) {
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
	public List<PriceEventDiscountSubEntity> getPriceEventDiscountSubs() {
		return priceEventDiscountSubs;
	}
	public void setPriceEventDiscountSubs(
			List<PriceEventDiscountSubEntity> priceEventDiscountSubs) {
		this.priceEventDiscountSubs = priceEventDiscountSubs;
	}
	public List<PriceEventOrderchannelSubEntity> getPriceEventOrderchannelSubs() {
		return priceEventOrderchannelSubs;
	}
	public void setPriceEventOrderchannelSubs(
			List<PriceEventOrderchannelSubEntity> priceEventOrderchannelSubs) {
		this.priceEventOrderchannelSubs = priceEventOrderchannelSubs;
	}
	public List<PriceEventRouteSubEntity> getPriceEventRouteSubs() {
		return priceEventRouteSubs;
	}
	public void setPriceEventRouteSubs(
			List<PriceEventRouteSubEntity> priceEventRouteSubs) {
		this.priceEventRouteSubs = priceEventRouteSubs;
	}
	public List<PriceEventCorpSubEntity> getPriceEventCorpSubs() {
		return priceEventCorpSubs;
	}
	public void setPriceEventCorpSubs(
			List<PriceEventCorpSubEntity> priceEventCorpSubs) {
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
        if (PriceEvnetEntity.class.isAssignableFrom((obj.getClass()))) {
            final PriceEvnetEntity other = (PriceEvnetEntity) obj;
           
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
	
}
