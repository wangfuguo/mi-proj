package com.hoau.miser.module.biz.base.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.PricePackageFeeItemsEntity;

/**
 * 包装费Vo
 * ClassName: PriceAddvalueFeeItemsVo 
 * @author 292078
 * @date 2015年12月22日
 * @version V1.0
 */
public class PricePackageFeeItemsVo implements Serializable{

    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/** 包装费集合 */
    private List<PricePackageFeeItemsEntity> ppfItemsList;

    /** 包装费 */
    PricePackageFeeItemsEntity ppfItemsEntity;

	public List<PricePackageFeeItemsEntity> getPpfItemsList() {
		return ppfItemsList;
	}

	public void setPpfItemsList(List<PricePackageFeeItemsEntity> ppfItemsList) {
		this.ppfItemsList = ppfItemsList;
	}

	public PricePackageFeeItemsEntity getPpfItemsEntity() {
		return ppfItemsEntity;
	}

	public void setPpfItemsEntity(PricePackageFeeItemsEntity ppfItemsEntity) {
		this.ppfItemsEntity = ppfItemsEntity;
	}

	
    

   
}
