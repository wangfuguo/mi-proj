package com.hoau.miser.module.biz.job.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
/**
 * 优惠活动折扣明细
 * ClassName: PriceEventDiscountSubEntity 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
public class PriceEventDiscountSubSendEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 活动编码 EVENT_CODE
	private String eventCode;
	// 运输类型 TRANS_TYPE_CODE
	private String transTypeCode;
	
	//运费分段折扣编码	FREIGHT_SECTION_CODE
	private String freightSectionCode;
	//附加费分段折扣编码	ADD_SECTION_CODE
	private String addSectionCode;
	//燃油费分段折扣编码	FUEL_SECTION_CODE	
	private String fuelSectionCode;
	//提货费分段折扣编码	PICKUP_SECTION_CODE
	private String pickupSectionCode;
	//送货费分段折扣编码	DELIVERY_SECTION_CODE
	private String deliverySectionCode;
	//上楼费分段折扣编码	UPSTAIR_SECTION_CODE
	private String upstairSectionCode;
	//保价率分段折扣编码	INSURANCE_RATE_SECTION_CODE
	private String insuranceRateSectionCode;
	//保价费分段折扣编码	INSURANCE_SECTION_CODE
	private String insuranceSectionCode;
	//工本费分段折扣编码	PAPER_SECTION_CODE
	private String paperSectionCode;
	//信息费分段折扣编码	SMS_SECTION_CODE
	private String smsSectionCode;
	//代收手续费率分段折扣编码	COLLECTION_RATE_SECTION_CODE
	private String collectionRateSectionCode;
	//代收手续费分段折扣编码	COLLECTION_SECTION_CODE	
	private String collectionSectionCode;
	
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
	
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	public String getFreightSectionCode() {
		return freightSectionCode;
	}
	public void setFreightSectionCode(String freightSectionCode) {
		this.freightSectionCode = freightSectionCode;
	}
	public String getAddSectionCode() {
		return addSectionCode;
	}
	public void setAddSectionCode(String addSectionCode) {
		this.addSectionCode = addSectionCode;
	}
	public String getFuelSectionCode() {
		return fuelSectionCode;
	}
	public void setFuelSectionCode(String fuelSectionCode) {
		this.fuelSectionCode = fuelSectionCode;
	}
	public String getPickupSectionCode() {
		return pickupSectionCode;
	}
	public void setPickupSectionCode(String pickupSectionCode) {
		this.pickupSectionCode = pickupSectionCode;
	}
	public String getDeliverySectionCode() {
		return deliverySectionCode;
	}
	public void setDeliverySectionCode(String deliverySectionCode) {
		this.deliverySectionCode = deliverySectionCode;
	}
	public String getUpstairSectionCode() {
		return upstairSectionCode;
	}
	public void setUpstairSectionCode(String upstairSectionCode) {
		this.upstairSectionCode = upstairSectionCode;
	}
	public String getInsuranceRateSectionCode() {
		return insuranceRateSectionCode;
	}
	public void setInsuranceRateSectionCode(String insuranceRateSectionCode) {
		this.insuranceRateSectionCode = insuranceRateSectionCode;
	}
	public String getInsuranceSectionCode() {
		return insuranceSectionCode;
	}
	public void setInsuranceSectionCode(String insuranceSectionCode) {
		this.insuranceSectionCode = insuranceSectionCode;
	}
	public String getPaperSectionCode() {
		return paperSectionCode;
	}
	public void setPaperSectionCode(String paperSectionCode) {
		this.paperSectionCode = paperSectionCode;
	}
	public String getSmsSectionCode() {
		return smsSectionCode;
	}
	public void setSmsSectionCode(String smsSectionCode) {
		this.smsSectionCode = smsSectionCode;
	}
	public String getCollectionRateSectionCode() {
		return collectionRateSectionCode;
	}
	public void setCollectionRateSectionCode(String collectionRateSectionCode) {
		this.collectionRateSectionCode = collectionRateSectionCode;
	}
	public String getCollectionSectionCode() {
		return collectionSectionCode;
	}
	public void setCollectionSectionCode(String collectionSectionCode) {
		this.collectionSectionCode = collectionSectionCode;
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
        if (PriceEventDiscountSubSendEntity.class.isAssignableFrom((obj.getClass()))) {
            final PriceEventDiscountSubSendEntity other = (PriceEventDiscountSubSendEntity) obj;
           
            if (this.getId() == null) {
                    return false;
            } else if (!this.getId().equals(other.getId())) {
                return false;
            }
            if(!StringUtil.equals(this.getEventCode(),other.getEventCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getTransTypeCode(),other.getTransTypeCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getFreightSectionCode(),other.getFreightSectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getAddSectionCode(),other.getAddSectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getFuelSectionCode(),other.getFuelSectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getPickupSectionCode(),other.getPickupSectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getDeliverySectionCode(),other.getDeliverySectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getUpstairSectionCode(),other.getUpstairSectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getInsuranceRateSectionCode(),other.getInsuranceRateSectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getInsuranceSectionCode(),other.getInsuranceSectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getPaperSectionCode(),other.getPaperSectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getSmsSectionCode(),other.getSmsSectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getCollectionRateSectionCode(),other.getCollectionRateSectionCode())){
            	return false;
            }
            if(!StringUtil.equals(this.getCollectionSectionCode(),other.getCollectionSectionCode())){
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
