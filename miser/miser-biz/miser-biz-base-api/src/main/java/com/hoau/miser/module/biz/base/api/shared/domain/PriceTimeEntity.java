/**
 * 
 */
package com.hoau.miser.module.biz.base.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 2016-4-25 10:06:34
 * 价格时效查询结果
 * @author dengyin
 */
public class PriceTimeEntity extends BaseEntity{
	private static final long serialVersionUID = -6778393276099899669L;
	
	//标准价格的id
    private String standardPriceId;
    
    //标准价格的运输类型编码
    private String standardTransTypeCode;
    
    //标准价格 运输类型名称
    private String standardTransTypeName;
    
    //标准价格  出发价格城市 编码
    private String standardSendPriceCity;
    
  //标准价格  出发价格城市 名称
    private String standardSendPriceCityName;
    
    
    //标准价格  到达价格城市 编码
    private String standardArrivePriceCity;
    
    //标准价格  到达价格城市 名称
    private String standardArrivePriceCityName;    
    
	
    //标准价格  重量单价
    private String standardWeightPrice;
    
    //标准价格  体积单价
    private String standardVolumePrice;
    
    //标准价格  重量体积单价拼接描述
    private String standardWeightVolumneDesc;
    
    //标准价格  最低价格描述
    private String standardLowestFee;

    //标准价格  出发价格城市对应省份编码
    private String sendProvinceCode;
   
    //标准价格  出发价格城市对应城市编码
    private String sendCityCode;
    
    //标准价格  出发价格城市对应区县编码
    private String sendAreaCode;

    //标准价格  到达价格城市 对应省份编码
    private String arriveProvinceCode;

    //标准价格  到达价格城市 对应城市编码
    private String arriveCityCode;
    
    //标准价格  到达价格城市 对应区县编码
    private String arriveAreaCode;
    
    //涉及活动ID编码串
    private String eventIdStr;
    
    //涉及活动名称串
    private String eventNameStr;
    
    //涉及活动编码串
    private String eventCodeStr;
    
    //时效表 客户自提 送货上门时间
    private String pickupDeliveryDesc;
    
    //客户自提时间
    private String pickupPromDay;
    
    //送货上门时间
    private String deliveryPromDay;
    
    //出发省市区
    private String sendPathName;
    
    //到达省市区
    private String arrivePathName;
     
	public String getSendPathName() {
		return sendPathName;
	}

	public void setSendPathName(String sendPathName) {
		this.sendPathName = sendPathName;
	}

	public String getArrivePathName() {
		return arrivePathName;
	}

	public void setArrivePathName(String arrivePathName) {
		this.arrivePathName = arrivePathName;
	}
	
	

	public String getStandardPriceId() {
		return standardPriceId;
	}

	public void setStandardPriceId(String standardPriceId) {
		this.standardPriceId = standardPriceId;
	}

	public String getStandardTransTypeCode() {
		return standardTransTypeCode;
	}

	public void setStandardTransTypeCode(String standardTransTypeCode) {
		this.standardTransTypeCode = standardTransTypeCode;
	}

	public String getStandardTransTypeName() {
		return standardTransTypeName;
	}

	public void setStandardTransTypeName(String standardTransTypeName) {
		this.standardTransTypeName = standardTransTypeName;
	}

	public String getStandardSendPriceCity() {
		return standardSendPriceCity;
	}

	public void setStandardSendPriceCity(String standardSendPriceCity) {
		this.standardSendPriceCity = standardSendPriceCity;
	}

	public String getStandardArrivePriceCity() {
		return standardArrivePriceCity;
	}

	public void setStandardArrivePriceCity(String standardArrivePriceCity) {
		this.standardArrivePriceCity = standardArrivePriceCity;
	}

	public String getStandardWeightPrice() {
		return standardWeightPrice;
	}

	public void setStandardWeightPrice(String standardWeightPrice) {
		this.standardWeightPrice = standardWeightPrice;
	}

	public String getStandardVolumePrice() {
		return standardVolumePrice;
	}

	public void setStandardVolumePrice(String standardVolumePrice) {
		this.standardVolumePrice = standardVolumePrice;
	}

	public String getStandardWeightVolumneDesc() {
		return standardWeightVolumneDesc;
	}

	public void setStandardWeightVolumneDesc(String standardWeightVolumneDesc) {
		this.standardWeightVolumneDesc = standardWeightVolumneDesc;
	}

	public String getStandardLowestFee() {
		return standardLowestFee;
	}

	public void setStandardLowestFee(String standardLowestFee) {
		this.standardLowestFee = standardLowestFee;
	}

	public String getSendProvinceCode() {
		return sendProvinceCode;
	}

	public void setSendProvinceCode(String sendProvinceCode) {
		this.sendProvinceCode = sendProvinceCode;
	}

	public String getSendCityCode() {
		return sendCityCode;
	}

	public void setSendCityCode(String sendCityCode) {
		this.sendCityCode = sendCityCode;
	}

	public String getSendAreaCode() {
		return sendAreaCode;
	}

	public void setSendAreaCode(String sendAreaCode) {
		this.sendAreaCode = sendAreaCode;
	}

	public String getArriveProvinceCode() {
		return arriveProvinceCode;
	}

	public void setArriveProvinceCode(String arriveProvinceCode) {
		this.arriveProvinceCode = arriveProvinceCode;
	}

	public String getArriveCityCode() {
		return arriveCityCode;
	}

	public void setArriveCityCode(String arriveCityCode) {
		this.arriveCityCode = arriveCityCode;
	}

	public String getArriveAreaCode() {
		return arriveAreaCode;
	}

	public void setArriveAreaCode(String arriveAreaCode) {
		this.arriveAreaCode = arriveAreaCode;
	}

	public String getEventIdStr() {
		return eventIdStr;
	}

	public void setEventIdStr(String eventIdStr) {
		this.eventIdStr = eventIdStr;
	}

	public String getEventNameStr() {
		return eventNameStr;
	}

	public void setEventNameStr(String eventNameStr) {
		this.eventNameStr = eventNameStr;
	}

	public String getPickupDeliveryDesc() {
		return pickupDeliveryDesc;
	}

	public void setPickupDeliveryDesc(String pickupDeliveryDesc) {
		this.pickupDeliveryDesc = pickupDeliveryDesc;
	}

	public String getPickupPromDay() {
		return pickupPromDay;
	}

	public void setPickupPromDay(String pickupPromDay) {
		this.pickupPromDay = pickupPromDay;
	}

	public String getDeliveryPromDay() {
		return deliveryPromDay;
	}

	public void setDeliveryPromDay(String deliveryPromDay) {
		this.deliveryPromDay = deliveryPromDay;
	}

	public String getEventCodeStr() {
		return eventCodeStr;
	}

	public void setEventCodeStr(String eventCodeStr) {
		this.eventCodeStr = eventCodeStr;
	}

	public String getStandardSendPriceCityName() {
		return standardSendPriceCityName;
	}

	public void setStandardSendPriceCityName(String standardSendPriceCityName) {
		this.standardSendPriceCityName = standardSendPriceCityName;
	}

	public String getStandardArrivePriceCityName() {
		return standardArrivePriceCityName;
	}

	public void setStandardArrivePriceCityName(String standardArrivePriceCityName) {
		this.standardArrivePriceCityName = standardArrivePriceCityName;
	}
	
    
}
