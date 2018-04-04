/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.shared.vo;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceCollectDeliveryFeeEntity;

/**
 * 代收货款手续费管理
 * @author dengyin
 */

public class PriceCollectDeliveryFeeVo {

	private PriceCollectDeliveryFeeEntity entity;
	private List<PriceCollectDeliveryFeeEntity> entityList;
	private String selectedIdStr;
	
	private String filePath;
	
	private int confirm;
	
	public PriceCollectDeliveryFeeEntity getEntity() {
		return entity;
	}
	public void setEntity(PriceCollectDeliveryFeeEntity entity) {
		this.entity = entity;
	}
	public List<PriceCollectDeliveryFeeEntity> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<PriceCollectDeliveryFeeEntity> entityList) {
		this.entityList = entityList;
	}
	public String getSelectedIdStr() {
		return selectedIdStr;
	}
	public void setSelectedIdStr(String selectedIdStr) {
		this.selectedIdStr = selectedIdStr;
	}
	public int getConfirm() {
		return confirm;
	}
	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	
	
}
