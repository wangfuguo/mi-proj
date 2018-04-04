/**   
 * @Title: PriceCustomerVo.java 
 * @Package com.hoau.miser.module.biz.pricecard.shared.vo 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月8日 上午10:36:15 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSubEntity;

/**
 * ClassName: PriceCustomerVo
 * 
 * @author dengyin
 * @date 2016年1月8日
 * @version V1.0
 */
public class PriceCustomerVo implements Serializable {

	private static final long serialVersionUID = -502791611032605603L;

	private PriceCustomerEntity priceCustomerEntity;
	private List<PriceCustomerEntity> priceCustomerEntityList;
	private List<PriceCustomerSubEntity> priceCustomerSubEntityList;
	private String filePath;
	
	//add by dengyin 2016-3-3 11:17:46 用于区分是在 查询详情 还是在 修改界面的 导出 因为两处地方导出的字段明细不一样
	private String exportType;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}	
	//用于作废的记录  id 拼接串
	private String destoryIdStr = null;

	public PriceCustomerEntity getPriceCustomerEntity() {
		return priceCustomerEntity;
	}

	public void setPriceCustomerEntity(PriceCustomerEntity priceCustomerEntity) {
		this.priceCustomerEntity = priceCustomerEntity;
	}

	public List<PriceCustomerEntity> getPriceCustomerEntityList() {
		return priceCustomerEntityList;
	}

	public void setPriceCustomerEntityList(
			List<PriceCustomerEntity> priceCustomerEntityList) {
		this.priceCustomerEntityList = priceCustomerEntityList;
	}

	public List<PriceCustomerSubEntity> getPriceCustomerSubEntityList() {
		return priceCustomerSubEntityList;
	}

	public void setPriceCustomerSubEntityList(
			List<PriceCustomerSubEntity> priceCustomerSubEntityList) {
		this.priceCustomerSubEntityList = priceCustomerSubEntityList;
	}

	public String getDestoryIdStr() {
		return destoryIdStr;
	}

	public void setDestoryIdStr(String destoryIdStr) {
		this.destoryIdStr = destoryIdStr;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	
	
	

}
