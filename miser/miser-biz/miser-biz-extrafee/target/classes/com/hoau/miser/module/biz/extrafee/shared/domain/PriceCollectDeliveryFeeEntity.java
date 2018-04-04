/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代收货款手续费管理
 * @author dengyin
 *
 */
public class PriceCollectDeliveryFeeEntity implements Serializable{

	private static final long serialVersionUID = 5995659625726175041L;
	
    //主键
	private String id;
	 
    //出发价格城市编码
	private String beginPriceCityCode;
	
	//出发价格城市名称
	private String beginPriceCityName;
	
    public String getBeginPriceCityName() {
		return beginPriceCityName;
	}

	public void setBeginPriceCityName(String beginPriceCityName) {
		this.beginPriceCityName = beginPriceCityName;
	}

	//运输类型编码
	private String transTypeCode;
	
	//运输类型名称
	private String transTypeName;
	
    //代收类型标识 0 24小时汇 1 72小时汇
	private Integer collectDeliveryType;
	
	private String collectDeliveryName;
	
	
    //代收手续费率
	private BigDecimal collectDeliveryRate;
	
    //费率锁定方式
	private Integer rateLockType;
	
	private String rateLockName;
	
    //最低代收手续费
	private BigDecimal lowestCollectDeliveryFee;
	
    //最高代收手续费
	private BigDecimal highestCollectDeliveryFee;
	
	//生效时间
	private Date effectiveTime;
	
	//失效时间
	private Date invalidTime;
	
	//生效时间
	private String effectiveTimeStr;
	
	//失效时间
	private String invalidTimeStr;
	
	
	
    //备注
	private String remark;
	
    //创建时间
	private Date createTime;
	
    //创建人
	private String createUserCode;
	
    //更新时间
	private Date modifyTime;
	
    //更新人
	private String modifyUserCode;
	
    //是否可用
	private String active;
	
	//状态
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBeginPriceCityCode() {
		return beginPriceCityCode;
	}

	public void setBeginPriceCityCode(String beginPriceCityCode) {
		this.beginPriceCityCode = beginPriceCityCode;
	}

	public String getTransTypeCode() {
		return transTypeCode;
	}

	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}

	public Integer getCollectDeliveryType() {
		return collectDeliveryType;
	}

	public void setCollectDeliveryType(Integer collectDeliveryType) {
		this.collectDeliveryType = collectDeliveryType;
	}

	public BigDecimal getCollectDeliveryRate() {
		return collectDeliveryRate;
	}

	public void setCollectDeliveryRate(BigDecimal collectDeliveryRate) {
		this.collectDeliveryRate = collectDeliveryRate;
	}

	public Integer getRateLockType() {
		return rateLockType;
	}

	public void setRateLockType(Integer rateLockType) {
		this.rateLockType = rateLockType;
	}

	public BigDecimal getLowestCollectDeliveryFee() {
		return lowestCollectDeliveryFee;
	}

	public void setLowestCollectDeliveryFee(BigDecimal lowestCollectDeliveryFee) {
		this.lowestCollectDeliveryFee = lowestCollectDeliveryFee;
	}

	public BigDecimal getHighestCollectDeliveryFee() {
		return highestCollectDeliveryFee;
	}

	public void setHighestCollectDeliveryFee(BigDecimal highestCollectDeliveryFee) {
		this.highestCollectDeliveryFee = highestCollectDeliveryFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		this.createUserCode = createUserCode;
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
		this.modifyUserCode = modifyUserCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCollectDeliveryName() {
		return collectDeliveryName;
	}

	public void setCollectDeliveryName(String collectDeliveryName) {
		this.collectDeliveryName = collectDeliveryName;
	}

	public String getRateLockName() {
		return rateLockName;
	}

	public void setRateLockName(String rateLockName) {
		this.rateLockName = rateLockName;
	}

	public String getEffectiveTimeStr() {
		return effectiveTimeStr;
	}

	public void setEffectiveTimeStr(String effectiveTimeStr) {
		this.effectiveTimeStr = effectiveTimeStr;
	}

	public String getInvalidTimeStr() {
		return invalidTimeStr;
	}

	public void setInvalidTimeStr(String invalidTimeStr) {
		this.invalidTimeStr = invalidTimeStr;
	}

	
	
}
