package com.hoau.miser.module.biz.extrafee.shared.vo;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PricePackageFeePcEntity;

/**
 * 价格城市包装费vo
 * ClassName: PricePackageFeePcVo 
 * @author 廖文强
 * @date 2016年1月19日
 * @version V1.0
 */
public class PricePackageFeePcVo {

	private PricePackageFeePcEntity ppFeePcEntity;
	private List<PricePackageFeePcEntity> ppFeePcList;
	public PricePackageFeePcEntity getPpFeePcEntity() {
		return ppFeePcEntity;
	}
	public void setPpFeePcEntity(PricePackageFeePcEntity ppFeePcEntity) {
		this.ppFeePcEntity = ppFeePcEntity;
	}
	public List<PricePackageFeePcEntity> getPpFeePcList() {
		return ppFeePcList;
	}
	public void setPpFeePcList(List<PricePackageFeePcEntity> ppFeePcList) {
		this.ppFeePcList = ppFeePcList;
	}
	
	
}
