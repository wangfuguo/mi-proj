package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceSectionBaseTyEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 分段价卡基类
 * @date 2016/7/5
 */
public class PriceSectionBaseTyEntity extends BaseEntity {

    /**
     * 价卡类型 1：标准，2：客户价格，3：网点价格
     **/
    protected int type;
    /**
     * 运输类型
     **/
    private String transTypeCode;

    /**
     * 出发价格城市
     **/
    private String sendPriceCityCode;
    /**
     * 到达价格城市
     **/
    private String arrivePriceCityCode;

    /**
     * @Fields firstWeight : 一段首重
     */
    private BigDecimal firstWeight;
    /**
     * @Fields firstWeightPrice : 一段首重单价
     */
    private BigDecimal firstWeightPrice;
    /**
     * @Fields firstAddWeightPrice : 一段续重单价
     */
    private BigDecimal firstAddWeightPrice;
    /**
     * @Fields secondWeight : 二段首重
     */
    private BigDecimal secondWeight;
    /**
     * @Fields secondWeightPrice : 二段首重单价
     */
    private BigDecimal secondWeightPrice;
    /**
     * @Fields secondAddWeightPrice : 二段续重单价
     */
    private BigDecimal secondAddWeightPrice;
    /**
     * @Fields thirdWeight : 三段首重
     */
    private BigDecimal thirdWeight;
    /**
     * @Fields thirdWeightPrice : 三段首重单价
     */
    private BigDecimal thirdWeightPrice;
    /**
     * @Fields thirdAddWeightPrice : 三段续重单价
     */
    private BigDecimal thirdAddWeightPrice;

    /**
     * 生效时间
     **/
    private Date effectiveTime;

    /**
     * 失效时间
     **/
    private Date invalidTime;


    public int getType() {
        return type;
    }

    public String getTransTypeCode() {
        return transTypeCode;
    }

    public void setTransTypeCode(String transTypeCode) {
        this.transTypeCode = transTypeCode;
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

    public BigDecimal getFirstWeight() {
        return firstWeight;
    }

    public void setFirstWeight(BigDecimal firstWeight) {
        this.firstWeight = firstWeight;
    }

    public BigDecimal getFirstWeightPrice() {
        return firstWeightPrice;
    }

    public void setFirstWeightPrice(BigDecimal firstWeightPrice) {
        this.firstWeightPrice = firstWeightPrice;
    }

    public BigDecimal getFirstAddWeightPrice() {
        return firstAddWeightPrice;
    }

    public void setFirstAddWeightPrice(BigDecimal firstAddWeightPrice) {
        this.firstAddWeightPrice = firstAddWeightPrice;
    }

    public BigDecimal getSecondWeight() {
        return secondWeight;
    }

    public void setSecondWeight(BigDecimal secondWeight) {
        this.secondWeight = secondWeight;
    }

    public BigDecimal getSecondWeightPrice() {
        return secondWeightPrice;
    }

    public void setSecondWeightPrice(BigDecimal secondWeightPrice) {
        this.secondWeightPrice = secondWeightPrice;
    }

    public BigDecimal getSecondAddWeightPrice() {
        return secondAddWeightPrice;
    }

    public void setSecondAddWeightPrice(BigDecimal secondAddWeightPrice) {
        this.secondAddWeightPrice = secondAddWeightPrice;
    }

    public BigDecimal getThirdWeight() {
        return thirdWeight;
    }

    public void setThirdWeight(BigDecimal thirdWeight) {
        this.thirdWeight = thirdWeight;
    }

    public BigDecimal getThirdWeightPrice() {
        return thirdWeightPrice;
    }

    public void setThirdWeightPrice(BigDecimal thirdWeightPrice) {
        this.thirdWeightPrice = thirdWeightPrice;
    }

    public BigDecimal getThirdAddWeightPrice() {
        return thirdAddWeightPrice;
    }

    public void setThirdAddWeightPrice(BigDecimal thirdAddWeightPrice) {
        this.thirdAddWeightPrice = thirdAddWeightPrice;
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
}
