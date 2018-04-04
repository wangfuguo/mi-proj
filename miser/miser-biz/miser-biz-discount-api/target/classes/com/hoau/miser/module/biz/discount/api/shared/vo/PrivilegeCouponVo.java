package com.hoau.miser.module.biz.discount.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeCouponEntity;


/**
 * 
 * ClassName: PrivilegeCouponVo 
 * @Description: TODO(越发越惠返券记录) 
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月19日
 * @version V1.0
 */
public class PrivilegeCouponVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -872144876170130123L;
	private PrivilegeCouponEntity PrivilegeCouponEntity;
	private List<PrivilegeCouponEntity> PrivilegeCouponList;
	public PrivilegeCouponEntity getPrivilegeCouponEntity() {
		return PrivilegeCouponEntity;
	}
	public void setPrivilegeCouponEntity(PrivilegeCouponEntity PrivilegeCouponEntity) {
		this.PrivilegeCouponEntity = PrivilegeCouponEntity;
	}
	public List<PrivilegeCouponEntity> getPrivilegeCouponList() {
		return PrivilegeCouponList;
	}
	public void setPrivilegeCouponList(List<PrivilegeCouponEntity> PrivilegeCouponList) {
		this.PrivilegeCouponList = PrivilegeCouponList;
	}

	
}
