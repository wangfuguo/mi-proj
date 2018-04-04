package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 价格信息 （S-标准价格、B-大客户价格、C-客户专属价格、N-网点专属价格）
 * @author Leslie
 *
 */
public class PriceCardInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对应的价格唯一标识（价格主键）
	 */
	private Integer id;
	/**
	 * 价格类型：(S-标准价格、B-大客户价格、C-客户专属价格、N-网点专属价格)
	 */
	private String type;
	/**
	 * 运输类型：(D-定日达、P-普通零担、Z-整车、O-其它运输类型)
	 */
	private String transportType;
	/**
	 *  优惠分段编号
	 */
	private String sectionId;
	/**
	 * 1）如果价格类型为S时：为空
	 * 2）如果价格类型为B时：存放的是大客户编号
	 * 3）如果价格类型为C时：存放的是客户代码
	 * 4）如果价格类型为N时：存放的是网点编号
	 */
	private String standardId;
	
	/**
	 * 对照类型：1）当C为价格城市类型；2）当X为行政城市类型
	 */
	private String cityMapType;
	/**
	 * 1)当对照类型为C时：不填 2）当对照类型为X时：行政出发省名称
	 */
	private String startProvinceName;
	/**
	 * 1）如果价格类型为S时：a)对照类型为C时为出发城市编号 ，b）对照类型为X时为行政出发城市名称
	 * 2）如果价格类型为B时：a)对照类型为C时为出发城市编号 ，b）对照类型为X时为行政出发城市名称
	 * 3）如果价格类型为C时：为空
	 * 4）如果价格类型为N时：为空
	 */
	private String startCityName;
	/**
	 * 1)当对照类型为C时：不填 2）当对照类型为X时：行政出发区县名称
	 */
	private String startRegionName;
	/**
	 * 1)当对照类型为C时：不填 2）当对照类型为X时：行政到达省名称
	 */
	private String arriveProviceName;
	/**
	 *  a)对照类型为C时为目的城市编号，b）对照类型为X时为行政到达城市名称
	 */
	private String arriveCityName;
	/**
	 * 1)当对照类型为C时：不填 2）当对照类型为X时：行政到达区县名称
	 */
	private String arriveRegionName;
	/**
	 * 最低收费
	 */
	private Double startPrice;
	/**
	 * 重量单价
	 */
	private Double weightPrice;
	/**
	 * 体积单价
	 */
	private Double volumePrice;
	/**
	 * 附加收费
	 */
	private Double extraChargePrice;
	/**
	 * 轻货折扣(属于标准价格、网点价格、客户价格)
	 */
	private Double lightDiscount;
	/**
	 * 重货折扣(属于标准价格、网点价格、客户价格)
	 */
	private Double heavyDiscount;
	/**
	 * 运输时间（小时）(属于标准价格、网点价格、客户价格)
	 */
	private String transportTime;
	/**
	 * 提货时间（天） (属于大客户价格)
	 */
	private String pickTime;
	/**
	 * 送货时间（天） (属于大客户价格)
	 */
	private String deliveryTime;
	/**
	 * 主线距离(属于标准价格)
	 */
	private Integer lineDistance;
	/**
	 * 轻重货(属于标准价格)
	 */
	private String lightWeightType;
	/**
	 * 冷热线(属于标准价格)
	 */
	private String coldHotType;
	/**
	 * 主偏线(属于标准价格)
	 */
	private String majorMinorType;
	
	/**
	 * 计费方式(属于大客户价格)
	 */
	private String chargeType;
	/**
	 * 基础费(属于大客户价格)
	 */
	private Double basePrice;
	/**
	 * 提货费(属于大客户价格)
	 */
	private Double pickPrice;
	/**
	 * 送货费(属于大客户价格)
	 */
	private Double deliveryPrice;
	/**
	 * 生效日期
	 */
	private Date effectiveDate;
	/**
	 * 失效日期
	 */
	private Date invalidDate;
	/**
	 * 燃油费分段编号
	 */
	private String fuelSubchargeId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除标示（0-有效；1-逻辑删除）
	 */
	private Integer recStatus;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}
	/**
	 * @param transportType the transportType to set
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	/**
	 * @return the standardId
	 */
	public String getStandardId() {
		return standardId;
	}
	/**
	 * @param standardId the standardId to set
	 */
	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	/**
	 * @return the sectionId
	 */
	public String getSectionId() {
		return sectionId;
	}
	/**
	 * @param sectionId the sectionId to set
	 */
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	/**
	 * @return the startPrice
	 */
	public Double getStartPrice() {
		return startPrice;
	}
	/**
	 * @param startPrice the startPrice to set
	 */
	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}
	/**
	 * @return the weightPrice
	 */
	public Double getWeightPrice() {
		return weightPrice;
	}
	/**
	 * @param weightPrice the weightPrice to set
	 */
	public void setWeightPrice(Double weightPrice) {
		this.weightPrice = weightPrice;
	}
	/**
	 * @return the volumePrice
	 */
	public Double getVolumePrice() {
		return volumePrice;
	}
	/**
	 * @param volumePrice the volumePrice to set
	 */
	public void setVolumePrice(Double volumePrice) {
		this.volumePrice = volumePrice;
	}
	/**
	 * @return the extraChargePrice
	 */
	public Double getExtraChargePrice() {
		return extraChargePrice;
	}
	/**
	 * @param extraChargePrice the extraChargePrice to set
	 */
	public void setExtraChargePrice(Double extraChargePrice) {
		this.extraChargePrice = extraChargePrice;
	}
	/**
	 * @return the lightDiscount
	 */
	public Double getLightDiscount() {
		return lightDiscount;
	}
	/**
	 * @param lightDiscount the lightDiscount to set
	 */
	public void setLightDiscount(Double lightDiscount) {
		this.lightDiscount = lightDiscount;
	}
	/**
	 * @return the heavyDiscount
	 */
	public Double getHeavyDiscount() {
		return heavyDiscount;
	}
	/**
	 * @param heavyDiscount the heavyDiscount to set
	 */
	public void setHeavyDiscount(Double heavyDiscount) {
		this.heavyDiscount = heavyDiscount;
	}
	/**
	 * @return the transportTime
	 */
	public String getTransportTime() {
		return transportTime;
	}
	/**
	 * @param transportTime the transportTime to set
	 */
	public void setTransportTime(String transportTime) {
		this.transportTime = transportTime;
	}
	/**
	 * @return the lineDistance
	 */
	public Integer getLineDistance() {
		return lineDistance;
	}
	/**
	 * @param lineDistance the lineDistance to set
	 */
	public void setLineDistance(Integer lineDistance) {
		this.lineDistance = lineDistance;
	}
	public String getLightWeightType() {
		return lightWeightType;
	}
	public void setLightWeightType(String lightWeightType) {
		this.lightWeightType = lightWeightType;
	}
	/**
	 * @return the coldHotType
	 */
	public String getColdHotType() {
		return coldHotType;
	}
	/**
	 * @param coldHotType the coldHotType to set
	 */
	public void setColdHotType(String coldHotType) {
		this.coldHotType = coldHotType;
	}
	/**
	 * @return the majorMinorType
	 */
	public String getMajorMinorType() {
		return majorMinorType;
	}
	/**
	 * @param majorMinorType the majorMinorType to set
	 */
	public void setMajorMinorType(String majorMinorType) {
		this.majorMinorType = majorMinorType;
	}
	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}
	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	/**
	 * @return the basePrice
	 */
	public Double getBasePrice() {
		return basePrice;
	}
	/**
	 * @param basePrice the basePrice to set
	 */
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
	/**
	 * @return the pickPrice
	 */
	public Double getPickPrice() {
		return pickPrice;
	}
	/**
	 * @param pickPrice the pickPrice to set
	 */
	public void setPickPrice(Double pickPrice) {
		this.pickPrice = pickPrice;
	}
	/**
	 * @return the deliveryPrice
	 */
	public Double getDeliveryPrice() {
		return deliveryPrice;
	}
	/**
	 * @param deliveryPrice the deliveryPrice to set
	 */
	public void setDeliveryPrice(Double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}
	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @return the invalidDate
	 */
	public Date getInvalidDate() {
		return invalidDate;
	}
	/**
	 * @param invalidDate the invalidDate to set
	 */
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(Integer recStatus) {
		this.recStatus = recStatus;
	}
	/**
	 * @return the fuelSubchargeId
	 */
	public String getFuelSubchargeId() {
		return fuelSubchargeId;
	}
	/**
	 * @param fuelSubchargeId the fuelSubchargeId to set
	 */
	public void setFuelSubchargeId(String fuelSubchargeId) {
		this.fuelSubchargeId = fuelSubchargeId;
	}
	public String getCityMapType() {
		return cityMapType;
	}
	public void setCityMapType(String cityMapType) {
		this.cityMapType = cityMapType;
	}
	public String getStartProvinceName() {
		return startProvinceName;
	}
	public void setStartProvinceName(String startProvinceName) {
		this.startProvinceName = startProvinceName;
	}
	public String getStartCityName() {
		return startCityName;
	}
	public void setStartCityName(String startCityName) {
		this.startCityName = startCityName;
	}
	public String getStartRegionName() {
		return startRegionName;
	}
	public void setStartRegionName(String startRegionName) {
		this.startRegionName = startRegionName;
	}
	public String getArriveProviceName() {
		return arriveProviceName;
	}
	public void setArriveProviceName(String arriveProviceName) {
		this.arriveProviceName = arriveProviceName;
	}
	public String getArriveCityName() {
		return arriveCityName;
	}
	public void setArriveCityName(String arriveCityName) {
		this.arriveCityName = arriveCityName;
	}
	public String getArriveRegionName() {
		return arriveRegionName;
	}
	public void setArriveRegionName(String arriveRegionName) {
		this.arriveRegionName = arriveRegionName;
	}
	public String getPickTime() {
		return pickTime;
	}
	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	@Override
	public String toString() {
		return "对照类型:"+this.getCityMapType()+" 类型:"+this.getType()+" 运输类型:"+this.getTransportType()+" StartCityName:"+this.getStartCityName()+" ArriveCityName:"+this.getArriveCityName()+ " StantardId:"+this.getStandardId();
	}
	
}
