package com.hoau.miser.module.biz.extrafee.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.ExtrafeeAddValueFeeEntity;


/**
 * 增值费Vo
 * ClassName: ExtrafeeAddvalueFeeVo 
 * @author 王茂
 * @date 2015年12月30日
 * @version V1.0
 */
public class ExtrafeeAddValueFeeVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -872144876170130123L;
	private ExtrafeeAddValueFeeEntity extrafeeAddValueFeeEntity;
	/**
	 *增值费集合
	 */
	private List<ExtrafeeAddValueFeeEntity> extrafeeAddValueFeeList;
	public ExtrafeeAddValueFeeEntity getExtrafeeAddValueFeeEntity() {
		return extrafeeAddValueFeeEntity;
	}
	public void setExtrafeeAddValueFeeEntity(ExtrafeeAddValueFeeEntity extrafeeAddValueFeeEntity) {
		this.extrafeeAddValueFeeEntity = extrafeeAddValueFeeEntity;
	}
	public List<ExtrafeeAddValueFeeEntity> getExtrafeeAddValueFeeList() {
		return extrafeeAddValueFeeList;
	}
	public void setExtrafeeAddValueFeeList(List<ExtrafeeAddValueFeeEntity> extrafeeAddValueFeeList) {
		this.extrafeeAddValueFeeList = extrafeeAddValueFeeList;
	}

	
}
