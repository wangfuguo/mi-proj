package com.hoau.miser.module.biz.job.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @Description: 偏线外发配置
 * 
 * @author 赵金义
 * @date 2016年3月17日
 * @version V1.0
 */
public class OuterBranchPriceSendEntity {

	private String id;
	private String sendId;

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	private String provinceCode;

	private String cityCode;

	private String areaCode;

	private BigDecimal lowestFee;

	private BigDecimal weightRate;

	private BigDecimal volumeRate;

	private String remark;

	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public BigDecimal getLowestFee() {
		return lowestFee;
	}

	public void setLowestFee(BigDecimal lowestFee) {
		this.lowestFee = lowestFee;
	}

	public BigDecimal getWeightRate() {
		return weightRate;
	}

	public void setWeightRate(BigDecimal weightRate) {
		this.weightRate = weightRate;
	}

	public BigDecimal getVolumeRate() {
		return volumeRate;
	}

	public void setVolumeRate(BigDecimal volumeRate) {
		this.volumeRate = volumeRate;
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

	private String createUserCode;

	private Date modifyTime;

	private String modifyUserCode;

	private String active;

}
