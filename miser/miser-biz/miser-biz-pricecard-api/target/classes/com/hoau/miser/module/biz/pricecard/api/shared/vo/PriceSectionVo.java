package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;

/**
 * 优惠分段
 * ClassName: PriceSectionVo 
 * @author 王茂
 * @date 2015年12月22日
 * @version V1.0
 */
public class PriceSectionVo implements Serializable {

	
	

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -872144876170130123L;
	private PriceSectionEntity priceSectionEntity;
	/**
	 *优惠分段集合
	 */
	private List<PriceSectionEntity> priceSectionList;

	public List<PriceSectionEntity> getPriceSectionList() {
		return priceSectionList;
	}

	public void setPriceSectionList(List<PriceSectionEntity> priceSectionList) {
		this.priceSectionList = priceSectionList;
	}

	public PriceSectionEntity getPriceSectionEntity() {
		return priceSectionEntity;
	}

	public void setPriceSectionEntity(PriceSectionEntity priceSectionEntity) {
		this.priceSectionEntity = priceSectionEntity;
	}
	
}
