package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BasePriceTyEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 价卡基类
 * @date 2016/6/6 15:12
 */
public class PriceBaseTyEntity extends BaseEntity {
    /**
     * 价卡类型 1：标准，2：客户价格，3：网点价格
     **/
    protected int type;

    /**
     * 运输类型
     **/
    private String transType;

    /**
     * 出发价格城市
     **/
    private String sendPriceCityCode;
    /**
     * 到达价格城市
     **/
    private String arrivePriceCityCode;

    /**
     * 重量单价
     **/
    private BigDecimal weightPrice;
    /**
     * 体积单价
     **/
    private BigDecimal volumePrice;
    /**
     * 最低收费
     **/
    private BigDecimal lowestFee;

    /**
     * 生效时间
     **/
    private Date effectiveTime;

    /**
     * 失效时间
     **/
    private Date invalidTime;

    /** 附加费**/
    private BigDecimal addFee;
    
    /** 
     * 燃油费分段编码
     **/
    private String fuelFeeSection;
    
    public BigDecimal getAddFee() {
        return addFee;
    }

    public void setAddFee(BigDecimal addFee) {
        this.addFee = addFee;
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

    public int getType() {
        return type;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getSendPriceCityCode() {
        return sendPriceCityCode;
    }

    public void setSendPriceCityCode(String sendPriceCityCode) {
        this.sendPriceCityCode = sendPriceCityCode;
    }

    public String getArrivePriceCityCode() {
        return arrivePriceCityCode;
    }

    public void setArrivePriceCityCode(String arrivePriceCityCode) {
        this.arrivePriceCityCode = arrivePriceCityCode;
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
		this.fuelFeeSection = fuelFeeSection;
	}
}
