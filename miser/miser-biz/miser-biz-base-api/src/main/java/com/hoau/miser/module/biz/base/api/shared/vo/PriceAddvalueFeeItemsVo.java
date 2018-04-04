package com.hoau.miser.module.biz.base.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceAddvalueFeeItemsEntity;

/**
 * 增值服务费Vo
 * ClassName: PriceAddvalueFeeItemsVo 
 * @author 292078
 * @date 2015年12月22日
 * @version V1.0
 */
public class PriceAddvalueFeeItemsVo implements Serializable{

    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/** 增值服务费集合 */
    private List<PriceAddvalueFeeItemsEntity> pafItemsList;

    /** 增值服务费 */
    PriceAddvalueFeeItemsEntity pafItemsEntity;

	public List<PriceAddvalueFeeItemsEntity> getPafItemsList() {
		return pafItemsList;
	}

	public void setPafItemsList(List<PriceAddvalueFeeItemsEntity> pafItemsList) {
		this.pafItemsList = pafItemsList;
	}

	public PriceAddvalueFeeItemsEntity getPafItemsEntity() {
		return pafItemsEntity;
	}

	public void setPafItemsEntity(PriceAddvalueFeeItemsEntity pafItemsEntity) {
		this.pafItemsEntity = pafItemsEntity;
	}
    

   
}
