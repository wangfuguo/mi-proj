package com.hoau.miser.module.api.itf.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;

/**
 * 优惠活动
 * 
 * @author 蒋落琛
 * @date 2016-6-7上午8:40:38
 */
public class PriceEventEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 相差时间 结束-开始(分钟)
	 */
	public static final long minuteDiff = 0;
	/**
	 * 1:活动结束
	 */
	public static final String STATE_1 = "1";
	/**
	 * 2:活动中
	 */
	public static final String STATE_2 = "2";
	/**
	 * 3:待生效
	 */
	public static final String STATE_3 = "3";
	/**
	 * 4:未生效被作废
	 */
	public static final String STATE_4 = "4";
	/**
	 * 5:被强制取消
	 */
	public static final String STATE_5 = "5";

	// 活动编码 EVENT_CODE
	private String eventCode;
	// 活动名称 EVENT_NAME
	private String eventName;
	// 生效时间-EFFECTIVE_TIME
	private Date effectiveTime;
	// 失效时间-INVALID_TIME
	private Date invalidTime;
	// 备注 REMARK
	private String remark;
	// 是否有效 ACTIVE
	private String active;
	// IS_FORCE_COLSE 是否强制终止
	private String isForceColse;
	// 活动状态
	private String state;

	//优惠折扣明细集合
	private List<PriceEventDiscountSubEntity> priceEventDiscountSubs;

	public String getIsForceColse() {
		return isForceColse;
	}

	public void setIsForceColse(String isForceColse) {
		this.isForceColse = isForceColse;
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
		if (PriceEventEntity.class.isAssignableFrom((obj.getClass()))) {
			final PriceEventEntity other = (PriceEventEntity) obj;

			if (this.getId() == null) {
				return false;
			} else if (!this.getId().equals(other.getId())) {
				return false;
			}
			if (!StringUtil.equals(this.getEventCode(), other.getEventCode())) {
				return false;
			}
			if (!StringUtil.equals(this.getEventName(), other.getEventName())) {
				return false;
			}
			if (!this.getEffectiveTime().equals(other.getEffectiveTime())) {
				return false;
			}
			if (!this.getInvalidTime().equals(other.getInvalidTime())) {
				return false;
			}
			if (!StringUtil.equals(this.getRemark(), other.getRemark())) {
				return false;
			}
			if (!StringUtil.equals(this.getActive(), other.getActive())) {
				return false;
			}

			return true;
		}
		return false;
	}

	public List<PriceEventDiscountSubEntity> getPriceEventDiscountSubs() {
		return priceEventDiscountSubs;
	}

	public void setPriceEventDiscountSubs(
			List<PriceEventDiscountSubEntity> priceEventDiscountSubs) {
		this.priceEventDiscountSubs = priceEventDiscountSubs;
	}

}
