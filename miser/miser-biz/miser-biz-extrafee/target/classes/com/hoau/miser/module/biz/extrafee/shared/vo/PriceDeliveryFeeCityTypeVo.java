package com.hoau.miser.module.biz.extrafee.shared.vo;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeCityTypeEntity;

/**
 * 送货费vo ClassName: PriceDeliveryFeeCityTypeVo
 * 
 * @author 廖文强
 * @date 2016年1月4日
 * @version V1.0
 */
public class PriceDeliveryFeeCityTypeVo {

	private PriceDeliveryFeeCityTypeEntity pdfctEntity;
	private List<PriceDeliveryFeeCityTypeEntity> pdfctList;
	private String IsConfirm;
	/**
	 * 选择的对象集合
	 */
	public String selects;

	public String getSelects() {
		return selects;
	}

	private String order;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setSelects(String selects) {
		this.selects = selects;
	}

	public String getIsConfirm() {
		return IsConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		IsConfirm = isConfirm;
	}

	public PriceDeliveryFeeCityTypeEntity getPdfctEntity() {
		return pdfctEntity;
	}

	public void setPdfctEntity(PriceDeliveryFeeCityTypeEntity pdfctEntity) {
		this.pdfctEntity = pdfctEntity;
	}

	public List<PriceDeliveryFeeCityTypeEntity> getPdfctList() {
		return pdfctList;
	}

	public void setPdfctList(List<PriceDeliveryFeeCityTypeEntity> pdfctList) {
		this.pdfctList = pdfctList;
	}

}
