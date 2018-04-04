package com.hoau.miser.module.biz.extrafee.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PricePickupFeeEntity;
/**
 * ClassName: DeliveryChargesVo 
 * @author 刘海飞
 * @date 2016年1月4日
 * @version V1.0
 */
public class PricePickupFeeVo implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	/** 提货费集合 */
    private List<PricePickupFeeEntity> pricePickupFeeList;

    /** 提货  费 */
    PricePickupFeeEntity pricePickupFeeEntity;

    /**是否确认*/
    private String isConfirm;

	public List<PricePickupFeeEntity> getPricePickupFeeList() {
		return pricePickupFeeList;
	}

	public void setPricePickupFeeList(List<PricePickupFeeEntity> pricePickupFeeList) {
		this.pricePickupFeeList = pricePickupFeeList;
	}

	public PricePickupFeeEntity getPricePickupFeeEntity() {
		return pricePickupFeeEntity;
	}

	public void setPricePickupFeeEntity(PricePickupFeeEntity pricePickupFeeEntity) {
		this.pricePickupFeeEntity = pricePickupFeeEntity;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
    
	
    
    
}
