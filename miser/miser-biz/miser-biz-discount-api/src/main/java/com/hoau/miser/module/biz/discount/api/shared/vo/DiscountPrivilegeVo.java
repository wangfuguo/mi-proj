package com.hoau.miser.module.biz.discount.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountPrivilegeEntity;


/**
 * 越发越惠定义
 * ClassName: DiscountPrivilegeVo 
 * @author 王茂
 * @date 2016年1月12日
 * @version V1.0
 */
public class DiscountPrivilegeVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -872144876170130123L;
	private DiscountPrivilegeEntity discountPrivilegeEntity;
	/**
	 *越发越惠集合
	 */
	private List<DiscountPrivilegeEntity> discountPrivilegeList;
	public DiscountPrivilegeEntity getDiscountPrivilegeEntity() {
		return discountPrivilegeEntity;
	}
	public void setDiscountPrivilegeEntity(DiscountPrivilegeEntity discountPrivilegeEntity) {
		this.discountPrivilegeEntity = discountPrivilegeEntity;
	}
	public List<DiscountPrivilegeEntity> getDiscountPrivilegeList() {
		return discountPrivilegeList;
	}
	public void setDiscountPrivilegeList(List<DiscountPrivilegeEntity> discountPrivilegeList) {
		this.discountPrivilegeList = discountPrivilegeList;
	}

	
}
