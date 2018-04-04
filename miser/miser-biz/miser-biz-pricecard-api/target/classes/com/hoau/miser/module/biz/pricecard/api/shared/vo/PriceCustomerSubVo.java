/**   
 * @Title: PriceCustomerSubVo.java 
 * @Package com.hoau.miser.module.biz.pricecard.shared.vo 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月8日 上午11:05:20 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSubEntity;

/**
 * ClassName: PriceCustomerSubVo
 * 
 * @author dengyin
 * @date 2016年1月8日
 * @version V1.0
 */
public class PriceCustomerSubVo implements Serializable {
 
	private static final long serialVersionUID = -1725745814899977423L;
	
	private PriceCustomerSubEntity priceCustomerSubEntity;
	private List<PriceCustomerSubEntity> priceCustomerSubEntityList;
	
	//新增记录ID
	private String addRecordId = null;
	
	//修改记录ID
	private String updateRecordId = null;
	
	//批量作废的 id 串
	private String batchDeleteIdStr = null;

	public PriceCustomerSubEntity getPriceCustomerSubEntity() {
		return priceCustomerSubEntity;
	}

	public void setPriceCustomerSubEntity(
			PriceCustomerSubEntity priceCustomerSubEntity) {
		this.priceCustomerSubEntity = priceCustomerSubEntity;
	}

	public List<PriceCustomerSubEntity> getPriceCustomerSubEntityList() {
		return priceCustomerSubEntityList;
	}

	public void setPriceCustomerSubEntityList(
			List<PriceCustomerSubEntity> priceCustomerSubEntityList) {
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
