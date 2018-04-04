package com.hoau.miser.module.biz.discount.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
/**
 * 优惠门店
 * ClassName: PriceEventCorpSubEntity 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
public class PriceEventCorpSubEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//活动编码 EVENT_CODE
	private String eventCode;
	//门店类型 CORP_TYPE  SEND:发货区域 ARRIVAL:到货区域 ALL:发货和到货区域
	private String corpType;
	//非偏线的行政组织编码或偏线的网点代码 ORG_CODE
	private String orgCode;
	//备注 REMARK
	private String remark;
	//是否有效 ACTIVE
	private String active;
	
	public String getCorpType() {
		return corpType;
	}
	public void setCorpType(String corpType) {
		this.corpType = corpType;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
        if (PriceEventCorpSubEntity.class.isAssignableFrom((obj.getClass()))) {
            final PriceEventCorpSubEntity other = (PriceEventCorpSubEntity) obj;
           
            if (this.getId() == null) {
                    return false;
            }else if (!this.getId().equals(other.getId())) {
                return false;
            }
            if(!StringUtil.equals(this.getEventCode(),other.getEventCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getCorpType(),other.getCorpType())){
            	return false;
            }
            if(!StringUtil.equals(this.getOrgCode(),other.getOrgCode())){
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
