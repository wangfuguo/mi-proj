package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionSubEntity;

public class PriceCustomerSectionVo implements Serializable {
	
	private static final long serialVersionUID = -8897956945179173638L;
	
	private PriceCustomerSectionEntity priceCustomerSectionEntity;

	private List<PriceCustomerSectionEntity> priceCunsomerSectionList;
	
	private List<PriceCustomerSectionSubEntity> PriceCustomerSectionSubEntityList;
	
	//用于作废的记录  id 拼接串
    private String destoryIdStr = null;
	
    private String filePath;
    
    private String exportType;
    
	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDestoryIdStr() {
		return destoryIdStr;
	}

	public void setDestoryIdStr(String destoryIdStr) {
		this.destoryIdStr = destoryIdStr;
	}

	public void setPriceCustomerSectionEntity(PriceCustomerSectionEntity priceCustomerSectionEntity) {
		this.priceCustomerSectionEntity = priceCustomerSectionEntity;
	}

	public PriceCustomerSectionEntity getPriceCustomerSectionEntity() {
		return priceCustomerSectionEntity;
	}
	
	public List<PriceCustomerSectionEntity> getPriceCunsomerSectionList() {
		return priceCunsomerSectionList;
	}

	public void setPriceCunsomerSectionList(List<PriceCustomerSectionEntity> priceCunsomerSectionList) {
		this.priceCunsomerSectionList = priceCunsomerSectionList;
	}

	public List<PriceCustomerSectionSubEntity> getPriceCustomerSectionSubEntityList() {
		return PriceCustomerSectionSubEntityList;
	}

	public void setPriceCustomerSectionSubEntityList(
			List<PriceCustomerSectionSubEntity> priceCustomerSectionSubEntityList) {
		PriceCustomerSectionSubEntityList = priceCustomerSectionSubEntityList;
	}
	

}
