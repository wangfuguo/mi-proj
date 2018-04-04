package com.hoau.miser.module.biz.extrafee.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceExtrafeeStandardEntity;

/**
 * 标准附加费Vo
 * ClassName: PriceExtrafeeStandardEntityVo 
 * @author 王茂
 * @date 2015年12月30日
 * @version V1.0
 */
public class PriceExtrafeeStandardVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 8440235021414622308L;
	private PriceExtrafeeStandardEntity PriceExtrafeeStandardEntity;
	/**
	 *标准附加费集合
	 */
	private List<PriceExtrafeeStandardEntity> priceExtrafeeStandardList;
	public PriceExtrafeeStandardEntity getPriceExtrafeeStandardEntity() {
		return PriceExtrafeeStandardEntity;
	}
	public void setPriceExtrafeeStandardEntity(PriceExtrafeeStandardEntity priceExtrafeeStandardEntity) {
		PriceExtrafeeStandardEntity = priceExtrafeeStandardEntity;
	}
	public List<PriceExtrafeeStandardEntity> getPriceExtrafeeStandardList() {
		return priceExtrafeeStandardList;
	}
	public void setPriceExtrafeeStandardList(List<PriceExtrafeeStandardEntity> priceExtrafeeStandardList) {
		this.priceExtrafeeStandardList = priceExtrafeeStandardList;
	}



	
}
