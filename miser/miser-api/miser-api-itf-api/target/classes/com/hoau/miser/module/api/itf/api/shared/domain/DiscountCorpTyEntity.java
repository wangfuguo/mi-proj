package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

import java.util.Date;

public class DiscountCorpTyEntity extends BaseEntity{

    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String orgCode;

    private String transTypeCode;

    private String freightSectionCode;

    private String pickupSectionCode;

    private String deliverySectionCode;

    private String insuranceRateSectionCode;

    private String insuranceSectionCode;

    private String paperSectionCode;

    private String smsSectionCode;

    private String fuelSectionCode;

    private String collectionRateSectionCode;

    private String collectionSectionCode;

    private Date effectiveTime;

    private Date invalidTime;

    private String remark;

    private String active;

    private String addSectionCode;

    private String upstairsSectionCode;

    //扩展信息
    private String roadArea;
    private String bigRegion;
    private String division;
    private String orgCodeName;
    private String transTypeName;
    private String freightSectionName;
    private String pickupSectionName;
    private String deliverySectionName;
    private String insuranceRateSectionName;
    private String insuranceSectionName;
    private String paperSectionName;
    private String smsSectionName;
    private String fuelSectionName;
    private String collectionRateSectionName;
    private String collectionSectionName;
    private String addSectionName;
    private String state;
    private Date nowDate;
    private String upstairsSectionName;
    private String createUserName;//创建人
    private String modifyUserName;//修改人
    private String dataOrign;//数据来源

    private String logistCode;//物流代码

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getTransTypeCode() {
        return transTypeCode;
    }

    public void setTransTypeCode(String transTypeCode) {
        this.transTypeCode = transTypeCode == null ? null : transTypeCode.trim();
    }

    public String getFreightSectionCode() {
        return freightSectionCode;
    }

    public void setFreightSectionCode(String freightSectionCode) {
        this.freightSectionCode = freightSectionCode == null ? null : freightSectionCode.trim();
    }

    public String getPickupSectionCode() {
        return pickupSectionCode;
    }

    public void setPickupSectionCode(String pickupSectionCode) {
        this.pickupSectionCode = pickupSectionCode == null ? null : pickupSectionCode.trim();
    }

    public String getDeliverySectionCode() {
        return deliverySectionCode;
    }

    public void setDeliverySectionCode(String deliverySectionCode) {
        this.deliverySectionCode = deliverySectionCode == null ? null : deliverySectionCode.trim();
    }

    public String getInsuranceRateSectionCode() {
        return insuranceRateSectionCode;
    }

    public void setInsuranceRateSectionCode(String insuranceRateSectionCode) {
        this.insuranceRateSectionCode = insuranceRateSectionCode == null ? null : insuranceRateSectionCode.trim();
    }

    public String getInsuranceSectionCode() {
        return insuranceSectionCode;
    }

    public void setInsuranceSectionCode(String insuranceSectionCode) {
        this.insuranceSectionCode = insuranceSectionCode == null ? null : insuranceSectionCode.trim();
    }

    public String getPaperSectionCode() {
        return paperSectionCode;
    }

    public void setPaperSectionCode(String paperSectionCode) {
        this.paperSectionCode = paperSectionCode == null ? null : paperSectionCode.trim();
    }

    public String getSmsSectionCode() {
        return smsSectionCode;
    }

    public void setSmsSectionCode(String smsSectionCode) {
        this.smsSectionCode = smsSectionCode == null ? null : smsSectionCode.trim();
    }

    public String getFuelSectionCode() {
        return fuelSectionCode;
    }

    public void setFuelSectionCode(String fuelSectionCode) {
        this.fuelSectionCode = fuelSectionCode == null ? null : fuelSectionCode.trim();
    }

    public String getCollectionRateSectionCode() {
        return collectionRateSectionCode;
    }

    public void setCollectionRateSectionCode(String collectionRateSectionCode) {
        this.collectionRateSectionCode = collectionRateSectionCode == null ? null : collectionRateSectionCode.trim();
    }

    public String getCollectionSectionCode() {
        return collectionSectionCode;
    }

    public void setCollectionSectionCode(String collectionSectionCode) {
        this.collectionSectionCode = collectionSectionCode == null ? null : collectionSectionCode.trim();
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

	public String getAddSectionCode() {
		return addSectionCode;
	}

	public void setAddSectionCode(String addSectionCode) {
		this.addSectionCode = addSectionCode;
	}

	public String getRoadArea() {
		return roadArea;
	}

	public void setRoadArea(String roadArea) {
		this.roadArea = roadArea;
	}

	public String getBigRegion() {
		return bigRegion;
	}

	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getOrgCodeName() {
		return orgCodeName;
	}
	public void setOrgCodeName(String orgCodeName) {
		this.orgCodeName = orgCodeName;
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

	public String getFuelSectionName() {
		return fuelSectionName;
	}

	public void setFuelSectionName(String fuelSectionName) {
		this.fuelSectionName = fuelSectionName;
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

	public String getAddSectionName() {
		return addSectionName;
	}

	public void setAddSectionName(String addSectionName) {
		this.addSectionName = addSectionName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getNowDate() {
		return nowDate;
	}

	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}

	/**
	 * @return the upstairsSectionCode
	 */
	public String getUpstairsSectionCode() {
		return upstairsSectionCode;
	}

	/**
	 * @return the upstairsSectionName
	 */
	public String getUpstairsSectionName() {
		return upstairsSectionName;
	}

	/**
	 * @param upstairsSectionCode the upstairsSectionCode to set
	 */
	public void setUpstairsSectionCode(String upstairsSectionCode) {
		this.upstairsSectionCode = upstairsSectionCode;
	}

	/**
	 * @param upstairsSectionName the upstairsSectionName to set
	 */
	public void setUpstairsSectionName(String upstairsSectionName) {
		this.upstairsSectionName = upstairsSectionName;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @return the modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @param modifyUserName the modifyUserName to set
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getDataOrign() {
		return dataOrign;
	}

	public void setDataOrign(String dataOrign) {
		this.dataOrign = dataOrign;
	}

	public String getLogistCode() {
		return logistCode;
	}

	public void setLogistCode(String logistCode) {
		this.logistCode = logistCode;
	}


}