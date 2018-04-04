package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceStandardEntity;

/**
 * 
 * @Author 廖文强
 * @Date 2015年12月15日
 */
public class PriceStandardVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2719021870354921278L;
	
	/**
	 *标准价格实体对象
	 */
	private PriceStandardEntity priceStandardEntity;
	
	//出发省
	private String sendProvinceCode;
	//出发市
	private String sendCityCode;
	//出发区
	private String sendAreaCode;
	//到达省
	private String arriveProvinceCode;
	//到达市
	private String arriveCityCode;
	//到达区
	private String arriveAreaCode;
	
	//有效时间点
	private String effectiveTime;
	
	//文件路径
	private String filePath;
	
	
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	/**
	 * 选择的对象集合
	 */
	public String selects;
	
	
	public String getSelects() {
		return selects;
	}

	public void setSelects(String selects) {
		this.selects = selects;
	}
	
	/**
	 * 是否确认
	 */
	private String isConfirm;
	
	
	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}


	private String order;
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
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


	/**
	 *标准价格对象集合
	 */
	private List<PriceStandardEntity> priceStandardList;

	public PriceStandardEntity getPriceStandardEntity() {
		return priceStandardEntity;
	}

	public void setPriceStandardEntity(PriceStandardEntity priceStandardEntity) {
		this.priceStandardEntity = priceStandardEntity;
	}

	public List<PriceStandardEntity> getPriceStandardList() {
		return priceStandardList;
	}

	public void setPriceStandardList(List<PriceStandardEntity> priceStandardList) {
		this.priceStandardList = priceStandardList;
	}

}
