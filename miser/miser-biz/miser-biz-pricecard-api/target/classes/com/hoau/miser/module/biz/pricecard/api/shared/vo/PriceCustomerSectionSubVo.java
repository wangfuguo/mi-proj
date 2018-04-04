package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionSubEntity;


public class PriceCustomerSectionSubVo implements Serializable {

	private static final long serialVersionUID = 780264157529163740L;
	
	private PriceCustomerSectionSubEntity priceCustomerSubEntity;
	private List<PriceCustomerSectionSubEntity> priceCustomerSubEntityList;
	
	//新增记录ID
	private String addRecordId = null;
	
	//修改记录ID
	private String updateRecordId = null;
	
	//批量作废的 id 串
	private String batchDeleteIdStr = null;

	public PriceCustomerSectionSubEntity getPriceCustomerSubEntity() {
		return priceCustomerSubEntity;
	}

	public void setPriceCustomerSubEntity(PriceCustomerSectionSubEntity priceCustomerSubEntity) {
		this.priceCustomerSubEntity = priceCustomerSubEntity;
	}

	public List<PriceCustomerSectionSubEntity> getPriceCustomerSubEntityList() {
		return priceCustomerSubEntityList;
	}

	public void setPriceCustomerSubEntityList(List<PriceCustomerSectionSubEntity> priceCustomerSubEntityList) {
		this.priceCustomerSubEntityList = priceCustomerSubEntityList;
	}

	public String getAddRecordId() {
		return addRecordId;
	}

	public void setAddRecordId(String addRecordId) {
		this.addRecordId = addRecordId;
	}

	public String getUpdateRecordId() {
		return updateRecordId;
	}

	public void setUpdateRecordId(String updateRecordId) {
		this.updateRecordId = updateRecordId;
	}

	public String getBatchDeleteIdStr() {
		return batchDeleteIdStr;
	}

	public void setBatchDeleteIdStr(String batchDeleteIdStr) {
		this.batchDeleteIdStr = batchDeleteIdStr;
	}

	
}
