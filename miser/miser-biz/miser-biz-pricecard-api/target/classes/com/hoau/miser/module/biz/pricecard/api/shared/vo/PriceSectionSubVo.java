package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionSubEntity;

/**
 * 优惠分段明细 ClassName: PriceSectionVo
 * 
 * @author 王茂
 * @date 2015年12月22日
 * @version V1.0
 */
public class PriceSectionSubVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -8981221895849089830L;
	private PriceSectionSubEntity priceSectionSubEntity;
	/**
	 * 优惠分段集合
	 */
	private List<PriceSectionSubEntity> priceSectionSubList;

	public PriceSectionSubEntity getPriceSectionSubEntity() {
		return priceSectionSubEntity;
	}

	public void setPriceSectionSubEntity(
			PriceSectionSubEntity priceSectionSubEntity) {
		this.priceSectionSubEntity = priceSectionSubEntity;
	}

	public List<PriceSectionSubEntity> getPriceSectionSubList() {
		return priceSectionSubList;
	}

	public void setPriceSectionSubList(
			List<PriceSectionSubEntity> priceSectionSubList) {
		this.priceSectionSubList = priceSectionSubList;
	}

}
