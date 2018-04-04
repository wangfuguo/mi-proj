package com.hoau.miser.module.biz.job.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;

public class PriceEventCustomerSubSendEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	//活动编码 EVENT_CODE
	private String eventCode;
	//客户编号 CUSTOMER_CODE
	private String customerCode;
	//客户名称 CUSTOMER_NAME
	private String customerName;
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
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
        if (PriceEventCustomerSubSendEntity.class.isAssignableFrom((obj.getClass()))) {
            final PriceEventCustomerSubSendEntity other = (PriceEventCustomerSubSendEntity) obj;
           
            if (this.getId() == null) {
                    return false;
            } else if (!this.getId().equals(other.getId())) {
                return false;
            }
            if(!StringUtil.equals(this.getEventCode(),other.getEventCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getCustomerCode(),other.getCustomerCode())){
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
