/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeEntity;

/**配送费管理
 * @author dengyin
 *
 */
public class PriceDeliveryFeeVo implements Serializable{
 
	private static final long serialVersionUID = 3396232531930442629L;

	//0 不覆盖 1覆盖
	private int confirm;
	
 
	public String selects;
	
	private PriceDeliveryFeeEntity priceDeliveryFeeEntity;
	
	private List<PriceDeliveryFeeEntity> priceDeliveryFeeEntityList;

	public PriceDeliveryFeeEntity getPriceDeliveryFeeEntity() {
		return priceDeliveryFeeEntity;
	}

	public void setPriceDeliveryFeeEntity(
			PriceDeliveryFeeEntity priceDeliveryFeeEntity) {
		this.priceDeliveryFeeEntity = priceDeliveryFeeEntity;
	}

	public List<PriceDeliveryFeeEntity> getPriceDeliveryFeeEntityList() {
		return priceDeliveryFeeEntityList;
	}

	public void setPriceDeliveryFeeEntityList(
			List<PriceDeliveryFeeEntity> priceDeliveryFeeEntityList) {
		this.priceDeliveryFeeEntityList = priceDeliveryFeeEntityList;
	}
 

	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}

	public String getSelects() {
		return selects;
	}

	public void setSelects(String selects) {
		this.selects = selects;
	}
	
	
	
}
