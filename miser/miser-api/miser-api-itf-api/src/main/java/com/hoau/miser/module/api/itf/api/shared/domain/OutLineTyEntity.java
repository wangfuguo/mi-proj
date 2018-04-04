/**
 *
 */
package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 偏线
 * @author 廖文强
 *
 */
public class OutLineTyEntity extends BaseEntity{

	private static final long serialVersionUID = 6607138231613433506L;

	/*
	id	varchar2(36)	id
	province_code	varchar2(20)	省份编码
	city_code	varchar2(50)	城市编码
	area_code	varchar2(20)	区县编码
	lowest_fee	number(10,2)	外发系数最低收费
	weight_fee	number(10,2)	重量外发系数
	volume_fee	number(10,2)	体积外发系数
	remark	varchar2(400)	备注
	create_time	date	创建时间
	create_user_code	varchar2(50)	创建人
	modify_time	date	更新时间
	modify_user_code	varchar2(50)	更新人
	active	char(1)	是否可用
	*/

	private String id;
	private String provinceCode;
	private String provinceName;
	private String cityCode;
	private String cityName;
	private String areaCode;
	private String areaName;
	private BigDecimal lowestFee;
	private BigDecimal weightRate;
	private BigDecimal volumeRate;
	private String remark;
	private Date createTime;
	private String createUserCode;
	private String createUserName;
	private Date modifyTime;
	private String modifyUserCode;
	private String modifyUserName;
	private String active;





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
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}



}
