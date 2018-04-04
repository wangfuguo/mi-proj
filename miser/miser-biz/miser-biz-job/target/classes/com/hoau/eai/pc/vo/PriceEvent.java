package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 优惠活动
 * ClassName: PriceEvnet 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
public class PriceEvent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 修改日期
	 */
	private Date modifyDate;

	/**
	 * 修改人
	 */
	private String modifyUser;

	//优惠折扣明细集合
	private List<PriceEventDiscountDetail> priceEventDiscountSubs;
	//优惠渠道集合
	private List<PriceEventOrderchannelCondition> priceEventOrderchannelSubs;
	//优惠线路集合
	private List<PriceEventRouteCondition> priceEventRouteSubs;
	//优惠门店集合
	private List<PriceEventCorpCondition> priceEventCorpSubs;
	//客户集合
	private List<PriceEventCustomerCondition> priceEventCustomerSubs;

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
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

	public String getIsForceColse() {
		return isForceColse;
	}

	public void setIsForceColse(String isForceColse) {
		this.isForceColse = isForceColse;
	}

	
	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public List<PriceEventDiscountDetail> getPriceEventDiscountSubs() {
		return priceEventDiscountSubs;
	}

	public void setPriceEventDiscountSubs(List<PriceEventDiscountDetail> priceEventDiscountSubs) {
		this.priceEventDiscountSubs = priceEventDiscountSubs;
	}

	public List<PriceEventOrderchannelCondition> getPriceEventOrderchannelSubs() {
		return priceEventOrderchannelSubs;
	}

	public void setPriceEventOrderchannelSubs(List<PriceEventOrderchannelCondition> priceEventOrderchannelSubs) {
		this.priceEventOrderchannelSubs = priceEventOrderchannelSubs;
	}

	public List<PriceEventRouteCondition> getPriceEventRouteSubs() {
		return priceEventRouteSubs;
	}

	public void setPriceEventRouteSubs(List<PriceEventRouteCondition> priceEventRouteSubs) {
		this.priceEventRouteSubs = priceEventRouteSubs;
	}

	public List<PriceEventCorpCondition> getPriceEventCorpSubs() {
		return priceEventCorpSubs;
	}

	public void setPriceEventCorpSubs(List<PriceEventCorpCondition> priceEventCorpSubs) {
		this.priceEventCorpSubs = priceEventCorpSubs;
	}

	public List<PriceEventCustomerCondition> getPriceEventCustomerSubs() {
		return priceEventCustomerSubs;
	}

	public void setPriceEventCustomerSubs(List<PriceEventCustomerCondition> priceEventCustomerSubs) {
		this.priceEventCustomerSubs = priceEventCustomerSubs;
	}
}
