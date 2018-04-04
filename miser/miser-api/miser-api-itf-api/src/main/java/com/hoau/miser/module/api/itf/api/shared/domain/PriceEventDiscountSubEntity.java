package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;

/**
 * 优惠活动折扣明细
 *
 * @author 蒋落琛
 * @date 2016-6-7上午10:03:28
 */
public class PriceEventDiscountSubEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	// 活动编码 EVENT_CODE
	private String eventCode;
	// 运输类型 TRANS_TYPE_CODE
	private String transTypeCode;
	// 运输类型 TRANS_TYPE_NAME
	private String transTypeName;
	//运费分段折扣编码	FREIGHT_SECTION_CODE
	private String freightSectionCode;
	private String freightSectionName;
	//附加费分段折扣编码	ADD_SECTION_CODE
	private String addSectionCode;
	private String addSectionName;
	//燃油费分段折扣编码	FUEL_SECTION_CODE	
	private String fuelSectionCode;
	private String fuelSectionName;
	//提货费分段折扣编码	PICKUP_SECTION_CODE
	private String pickupSectionCode;
	private String pickupSectionName;
	//送货费分段折扣编码	DELIVERY_SECTION_CODE
	private String deliverySectionCode;
	private String deliverySectionName;
	//上楼费分段折扣编码	UPSTAIR_SECTION_CODE
	private String upstairSectionCode;
	private String upstairSectionName;
	//保价率分段折扣编码	INSURANCE_RATE_SECTION_CODE
	private String insuranceRateSectionCode;
	private String insuranceRateSectionName;
	//保价费分段折扣编码	INSURANCE_SECTION_CODE
	private String insuranceSectionCode;
	private String insuranceSectionName;
	//工本费分段折扣编码	PAPER_SECTION_CODE
	private String paperSectionCode;
	private String paperSectionName;
	//信息费分段折扣编码	SMS_SECTION_CODE
	private String smsSectionCode;
	private String smsSectionName;
	//代收手续费率分段折扣编码	COLLECTION_RATE_SECTION_CODE
	private String collectionRateSectionCode;
	private String collectionRateSectionName;
	//代收手续费分段折扣编码	COLLECTION_SECTION_CODE	
	private String collectionSectionCode;
	private String collectionSectionName;

	//备注 REMARK
	private String remark;
	//是否有效 ACTIVE
	private String active;
	
	public static final String FEE_TYPE_FREIGHT = "FREIGHT";
	public static final String FEE_TYPE_EXTRA_FEE = "EXTRA_FEE";
	public static final String FEE_TYPE_FUEL = "FUEL";
	public static final String FEE_TYPE_PICKUP = "PICKUP";
	public static final String FEE_TYPE_DELIVERY = "DELIVERY";
	public static final String FEE_TYPE_UPSTAIR = "UPSTAIR";
	public static final String FEE_TYPE_INSURANCE_RATE = "INSURANCE_RATE";
	public static final String FEE_TYPE_INSURANCE = "INSURANCE";
	public static final String FEE_TYPE_PAPER = "PAPER";
	public static final String FEE_TYPE_SMS = "SMS";
	public static final String FEE_TYPE_COLLECTION_RATE = "COLLECTION_RATE";
	public static final String FEE_TYPE_COLLECTION = "COLLECTION";

	/** 
	 * @Title: getTjzlfdzksByType 
	 * @Description: 根据费用类型获取此费用类型的优惠分段
	 * @param feeType 		费用类型
	 * @return Tjzlfdzk[]   分段明细 
	 * @throws 
	*/
	public String getSectionByType(String feeType) 
	{
		if (FEE_TYPE_FREIGHT.equals(feeType)) 
		{
			return getFreightSectionCode();
		}
		else if (FEE_TYPE_EXTRA_FEE.equals(feeType)) {
			return getAddSectionCode();
		}
		else if (FEE_TYPE_FUEL.equals(feeType)) {
			return getFuelSectionCode();
		}
		else if (FEE_TYPE_PICKUP.equals(feeType)) {
			return getPickupSectionCode();
		}
		else if (FEE_TYPE_DELIVERY.equals(feeType)) {
			return getDeliverySectionCode();
		}
		else if (FEE_TYPE_UPSTAIR.equals(feeType)) {
			return getUpstairSectionCode();
		}
		else if (FEE_TYPE_INSURANCE_RATE.equals(feeType)) {
			return getInsuranceRateSectionCode();
		}
		else if (FEE_TYPE_INSURANCE.equals(feeType)) {
			return getInsuranceSectionCode();
		}
		else if (FEE_TYPE_PAPER.equals(feeType)) {
			return getPaperSectionCode();
		}
		else if (FEE_TYPE_SMS.equals(feeType)) {
			return getSmsSectionCode();
		}
		else if (FEE_TYPE_COLLECTION_RATE.equals(feeType)) {
			return getCollectionRateSectionCode();
		}
		else if (FEE_TYPE_COLLECTION.equals(feeType)) {
			return getCollectionSectionCode();
		}
		else {
			return getFreightSectionCode();
		}
	}
	
	public String getTransTypeName() {
		return transTypeName;
	}
	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}
	public String getFreightSectionName() {
		return freightSectionName;
	}
	public void setFreightSectionName(String freightSectionName) {
		this.freightSectionName = freightSectionName;
	}
	public String getAddSectionName() {
		return addSectionName;
	}
	public void setAddSectionName(String addSectionName) {
		this.addSectionName = addSectionName;
	}
	public String getFuelSectionName() {
		return fuelSectionName;
	}
	public void setFuelSectionName(String fuelSectionName) {
		this.fuelSectionName = fuelSectionName;
	}
	public String getPickupSectionName() {
		return pickupSectionName;
	}
	public void setPickupSectionName(String pickupSectionName) {
		this.pickupSectionName = pickupSectionName;
	}
	public String getDeliverySectionName() {
		return deliverySectionName;
	}
	public void setDeliverySectionName(String deliverySectionName) {
		this.deliverySectionName = deliverySectionName;
	}
	public String getUpstairSectionName() {
		return upstairSectionName;
	}
	public void setUpstairSectionName(String upstairSectionName) {
		this.upstairSectionName = upstairSectionName;
	}
	public String getInsuranceRateSectionName() {
		return insuranceRateSectionName;
	}
	public void setInsuranceRateSectionName(String insuranceRateSectionName) {
		this.insuranceRateSectionName = insuranceRateSectionName;
	}
	public String getInsuranceSectionName() {
		return insuranceSectionName;
	}
	public void setInsuranceSectionName(String insuranceSectionName) {
		this.insuranceSectionName = insuranceSectionName;
	}
	public String getPaperSectionName() {
		return paperSectionName;
	}
	public void setPaperSectionName(String paperSectionName) {
		this.paperSectionName = paperSectionName;
	}
	public String getSmsSectionName() {
		return smsSectionName;
	}
	public void setSmsSectionName(String smsSectionName) {
		this.smsSectionName = smsSectionName;
	}
	public String getCollectionRateSectionName() {
		return collectionRateSectionName;
	}
	public void setCollectionRateSectionName(String collectionRateSectionName) {
		this.collectionRateSectionName = collectionRateSectionName;
	}
	public String getCollectionSectionName() {
		return collectionSectionName;
	}
	public void setCollectionSectionName(String collectionSectionName) {
		this.collectionSectionName = collectionSectionName;
	}
	
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
        if (PriceEventDiscountSubEntity.class.isAssignableFrom((obj.getClass()))) {
            final PriceEventDiscountSubEntity other = (PriceEventDiscountSubEntity) obj;
           
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
