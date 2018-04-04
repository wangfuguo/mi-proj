package com.hoau.miser.module.biz.extrafee.shared.vo;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PricePackageFeeStandardEntity;

/**
 * 标准包装费VO
 * ClassName: PricePackageFeeStandardVO 
 * @author 292078
 * @date 2015年12月28日
 * @version V1.0
 */
public class PricePackageFeeStandardVO {

	private PricePackageFeeStandardEntity ppFeeStandardEntity;
	private List<PricePackageFeeStandardEntity> ppFeeStandardList;
	public PricePackageFeeStandardEntity getPpFeeStandardEntity() {
		return ppFeeStandardEntity;
	}
	public void setPpFeeStandardEntity(
			PricePackageFeeStandardEntity ppFeeStandardEntity) {
		this.ppFeeStandardEntity = ppFeeStandardEntity;
	}
	public List<PricePackageFeeStandardEntity> getPpFeeStandardList() {
		return ppFeeStandardList;
	}
	public void setPpFeeStandardList(
			List<PricePackageFeeStandardEntity> ppFeeStandardList) {
		this.ppFeeStandardList = ppFeeStandardList;
	}
	
}
