package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 时效价格管理
 * @author Liwy
 *
 */
public class TimeStandardEntity{

	
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
	 * 出发
	 */
	public static final String SEND = "SEND";
	
	/**
	 * 到达
	 */
	public static final String ARRIVAL = "ARRIVAL";
	
	private String id;

	private String transTypeCode;

    private String transTypeName;

    private String sendTimeCity;
    
    private String sendTimeCityName;

    private String arriveTimeCity;
    
    private String arriveTimeCityName;

    private BigDecimal minNotifyDay;

    private BigDecimal maxNotifyDay;

    private String pickupPromDay;

    private BigDecimal deliveryPromDay;

    private Date effectiveTime;

    private Date invalidTime;

    private String remark;

    private Date createTime;

    private String createUserCode;

    private Date modifyTime;

    private String modifyUserCode;

    private String active;
    
    private String activeName = "";

	public String getActiveName() {
		if(active!=null){
			if(this.active.equals(MiserConstants.NO))
	    		return "否";
	    	if(this.active.equals(MiserConstants.YES))
	    		return "是";
		}
		return activeName;
	}

	private String state;
    
    private String stateName = "";
    
	public String getStateName() {
		if(state!=null){
			if(this.state.equals(STATE_1))
	    		return "已失效";
	    	if(this.state.equals(STATE_2))
	    		return "生效中";
	    	if(this.state.equals(STATE_3))
	    		return "待生效";
	    	if(this.state.equals(STATE_4))
	    		return "已过期";
		}
    	
		return this.stateName;
	}


	public String getSendTimeCityName() {
		return sendTimeCityName;
	}

	public void setSendTimeCityName(String sendTimeCityName) {
		this.sendTimeCityName = sendTimeCityName;
	}

	public String getArriveTimeCityName() {
		return arriveTimeCityName;
	}

	public void setArriveTimeCityName(String arriveTimeCityName) {
		this.arriveTimeCityName = arriveTimeCityName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTransTypeCode() {
        return transTypeCode;
    }

    public void setTransTypeCode(String transTypeCode) {
        this.transTypeCode = transTypeCode == null ? null : transTypeCode.trim();
    }

    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName == null ? null : transTypeName.trim();
    }

    public String getSendTimeCity() {
        return sendTimeCity;
    }

    public void setSendTimeCity(String sendTimeCity) {
        this.sendTimeCity = sendTimeCity == null ? null : sendTimeCity.trim();
    }

    public String getArriveTimeCity() {
        return arriveTimeCity;
    }

    public void setArriveTimeCity(String arriveTimeCity) {
        this.arriveTimeCity = arriveTimeCity == null ? null : arriveTimeCity.trim();
    }

    public BigDecimal getMinNotifyDay() {
        return minNotifyDay;
    }

    public void setMinNotifyDay(BigDecimal minNotifyDay) {
        this.minNotifyDay = minNotifyDay;
    }

    public BigDecimal getMaxNotifyDay() {
        return maxNotifyDay;
    }

    public void setMaxNotifyDay(BigDecimal maxNotifyDay) {
        this.maxNotifyDay = maxNotifyDay;
    }

    public String getPickupPromDay() {
        return pickupPromDay;
    }

    public void setPickupPromDay(String pickupPromDay) {
        this.pickupPromDay = pickupPromDay == null ? null : pickupPromDay.trim();
    }

    public BigDecimal getDeliveryPromDay() {
        return deliveryPromDay;
    }

    public void setDeliveryPromDay(BigDecimal deliveryPromDay) {
        this.deliveryPromDay = deliveryPromDay;
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
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode == null ? null : createUserCode.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode == null ? null : modifyUserCode.trim();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
    
}