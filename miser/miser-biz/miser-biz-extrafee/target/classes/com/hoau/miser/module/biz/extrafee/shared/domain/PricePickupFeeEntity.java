package com.hoau.miser.module.biz.extrafee.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * ClassName: DeliveryChargesEntity 
 * @author 刘海飞
 * @date 2016年1月4日
 * @version V1.0
 */
public class PricePickupFeeEntity extends BaseEntity{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	/**提货费城市code*/
	private String priceCityCode;
	
	/**提货费城市名称*/
	private String priceCityName;
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
     * 生效时间
     */
    private Date effectiveTime;	
    
    /**
     * 失效时间
     */
    private Date invalidTime;			
    
    /**
     * 运输类型
     */
    private String transportType;
    
    /**
     * 运输类型名称
     */
    private String transportTypeName;
    
	 /**
     * 是否有效
     */
    private String active;
    
    /**
     *备注
     */
    private String remark;

    /**
     *状态
     */
    private String state;
    private String stateName;
    
    /**
     * 导出使用的字段
     */
    private Date createDate;
    private Date modifyDate;
    private String createUser;
    private String modifyUser;
	public String getStateName() {
		String msg="";
		if(PricePickupFeeEntity.STATE_1.equals(this.state)){
			msg="已失效";
		}else if(PricePickupFeeEntity.STATE_2.equals(this.state)){
			msg="生效中";
		}else if(PricePickupFeeEntity.STATE_3.equals(this.state)){
			msg="待生效";
		}else if(PricePickupFeeEntity.STATE_4.equals(this.state)){
			msg="已废弃";
		}
		stateName=msg;
		return stateName;
	}
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPriceCityName() {
		return priceCityName;
	}

	public void setPriceCityName(String priceCityName) {
		this.priceCityName = priceCityName;
	}

	public String getPriceCityCode() {
		return priceCityCode;
	}

	public void setPriceCityCode(String priceCityCode) {
		this.priceCityCode = priceCityCode;
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

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTransportTypeName() {
		return transportTypeName;
	}

	public void setTransportTypeName(String transportTypeName) {
		this.transportTypeName = transportTypeName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
    
    
}
