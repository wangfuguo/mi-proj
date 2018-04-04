package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceStandardSectionEntity;

/**
 * @ClassName: PriceStandardSectionVo
 * @Description: 易入户标准价格管理Vo
 * @author zhangqingfu
 * @date 2016年5月4日 下午2:04:47
 *
 */
public class PriceStandardSectionVo implements Serializable {

	private static final long serialVersionUID = -5657715647572742014L;

	/**
	 * @Fields priceStandardSectionEntity : 分段标准价格Entity
	 */
	private PriceStandardSectionEntity priceStandardSectionEntity;
	private List<PriceStandardSectionEntity> priceStandardSectionList;
	/**
	 * @Fields sendProvinceCode : 出发省
	 */
	private String sendProvinceCode;
	/**
	 * @Fields sendCityCode : 出发市
	 */
	private String sendCityCode;
	/**
	 * @Fields sendAreaCode : 出发区
	 */
	private String sendAreaCode;
	/**
	 * @Fields arriveProvinceCode : 到达省
	 */
	private String arriveProvinceCode;
	/**
	 * @Fields arriveCityCode : 到达市
	 */
	private String arriveCityCode;
	/**
	 * @Fields arriveAreaCode : 到达区
	 */
	private String arriveAreaCode;
	/**
	 * @Fields effectiveTime : 有效时间点
	 */
	private String effectiveTime;
	/**
	 * @Fields filePath : 文件路径
	 */
	private String filePath;
	
	private String order;
	/**
	 * @Fields isConfirm : 是否确认
	 */
	private String isConfirm;
	/**
	 * @Fields selects : 选择的对象集合
	 */
	public String selects;

	public PriceStandardSectionEntity getPriceStandardSectionEntity() {
		return priceStandardSectionEntity;
	}

	public void setPriceStandardSectionEntity(PriceStandardSectionEntity priceStandardSectionEntity) {
		this.priceStandardSectionEntity = priceStandardSectionEntity;
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

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<PriceStandardSectionEntity> getPriceStandardSectionList() {
		return priceStandardSectionList;
	}

	public void setPriceStandardSectionList(List<PriceStandardSectionEntity> priceStandardSectionList) {
		this.priceStandardSectionList = priceStandardSectionList;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getSelects() {
		return selects;
	}

	public void setSelects(String selects) {
		this.selects = selects;
	}

}
