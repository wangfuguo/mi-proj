package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class PriceCorpEntity extends BaseEntity{

    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 4294297417004700155L;

	private String transTypeCode;

    private String transTypeName;

    private String corpCode;

    private String corpName;

    private String arrivePriceCity;

    private BigDecimal weightPrice;

    private BigDecimal volumePrice;

    private BigDecimal addFee;

    private BigDecimal lowestFee;

    private String fuelFeeSection;

    private Date effectiveTime;

    private Date invalidTime;

    private String remark;

    private Date createTime;

    private String createUserCode;

    private Date modifyTime;

    private String modifyUserCode;

    private String active;
    
    private String dataOrign;

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

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode == null ? null : corpCode.trim();
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName == null ? null : corpName.trim();
    }

    public String getArrivePriceCity() {
        return arrivePriceCity;
    }

    public void setArrivePriceCity(String arrivePriceCity) {
        this.arrivePriceCity = arrivePriceCity == null ? null : arrivePriceCity.trim();
    }

    public BigDecimal getWeightPrice() {
        return weightPrice;
    }

    public void setWeightPrice(BigDecimal weightPrice) {
        this.weightPrice = weightPrice;
    }

    public BigDecimal getVolumePrice() {
        return volumePrice;
    }

    public void setVolumePrice(BigDecimal volumePrice) {
        this.volumePrice = volumePrice;
    }

    public BigDecimal getAddFee() {
        return addFee;
    }

    public void setAddFee(BigDecimal addFee) {
        this.addFee = addFee;
    }

    public BigDecimal getLowestFee() {
        return lowestFee;
    }

    public void setLowestFee(BigDecimal lowestFee) {
        this.lowestFee = lowestFee;
    }

    public String getFuelFeeSection() {
        return fuelFeeSection;
    }

    public void setFuelFeeSection(String fuelFeeSection) {
        this.fuelFeeSection = fuelFeeSection == null ? null : fuelFeeSection.trim();
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

	public String getDataOrign() {
		return dataOrign;
	}

	public void setDataOrign(String dataOrign) {
		this.dataOrign = dataOrign;
	}
}