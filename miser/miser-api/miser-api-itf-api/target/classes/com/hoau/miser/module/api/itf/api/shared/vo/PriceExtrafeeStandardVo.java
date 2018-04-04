package com.hoau.miser.module.api.itf.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceExtrafeeStandardEntity;

/**
 * 标准附加费Vo
 * 
 * @author 蒋落琛
 * @date 2016-6-2下午4:05:48
 */
public class PriceExtrafeeStandardVo implements Serializable {

	private static final long serialVersionUID = 8440235021414622308L;

	private PriceExtrafeeStandardEntity PriceExtrafeeStandardEntity;

	/**
	 * 标准附加费集合
	 */
	private List<PriceExtrafeeStandardEntity> priceExtrafeeStandardList;

	public PriceExtrafeeStandardEntity getPriceExtrafeeStandardEntity() {
		return PriceExtrafeeStandardEntity;
	}

	public void setPriceExtrafeeStandardEntity(
			PriceExtrafeeStandardEntity priceExtrafeeStandardEntity) {
		PriceExtrafeeStandardEntity = priceExtrafeeStandardEntity;
	}

	public List<PriceExtrafeeStandardEntity> getPriceExtrafeeStandardList() {
		return priceExtrafeeStandardList;
	}

	public void setPriceExtrafeeStandardList(
			List<PriceExtrafeeStandardEntity> priceExtrafeeStandardList) {
		this.priceExtrafeeStandardList = priceExtrafeeStandardList;
	}

}
