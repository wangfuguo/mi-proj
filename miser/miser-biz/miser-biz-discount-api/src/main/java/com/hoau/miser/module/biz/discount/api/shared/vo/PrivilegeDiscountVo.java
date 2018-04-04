package com.hoau.miser.module.biz.discount.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeDiscountEntity;


/**
 * 
 * ClassName: PrivilegeDiscountVo 
 * @Description: TODO(越发越惠折扣记录) 
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月19日
 * @version V1.0
 */
public class PrivilegeDiscountVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -872144876170130123L;
	private PrivilegeDiscountEntity PrivilegeDiscountEntity;
	private List<PrivilegeDiscountEntity> PrivilegeDiscountList;
	public PrivilegeDiscountEntity getPrivilegeDiscountEntity() {
		return PrivilegeDiscountEntity;
	}
	public void setPrivilegeDiscountEntity(PrivilegeDiscountEntity PrivilegeDiscountEntity) {
		this.PrivilegeDiscountEntity = PrivilegeDiscountEntity;
	}
	public List<PrivilegeDiscountEntity> getPrivilegeDiscountList() {
		return PrivilegeDiscountList;
	}
	public void setPrivilegeDiscountList(List<PrivilegeDiscountEntity> PrivilegeDiscountList) {
		this.PrivilegeDiscountList = PrivilegeDiscountList;
	}

	
}
