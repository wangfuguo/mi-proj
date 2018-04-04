package com.hoau.miser.module.biz.discount.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
/**
 *优惠活动订单渠道明细
 * ClassName: PriceEventOrderchannelSubEntity 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
public class PriceEventOrderchannelSubEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	//活动编码 EVENT_CODE
	private String eventCode;
	//渠道编码 CHANNEL_CODE
	private String channelCode;
	
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
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
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
        if (PriceEventOrderchannelSubEntity.class.isAssignableFrom((obj.getClass()))) {
            final PriceEventOrderchannelSubEntity other = (PriceEventOrderchannelSubEntity) obj;
           
//            if (this.getId() == null) {
//                    return false;
//            } 
//            else if (!this.getId().equals(other.getId())) {
//                return false;
//            }
            if(!StringUtil.equals(this.getEventCode(),other.getEventCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getChannelCode(),other.getChannelCode())){
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
