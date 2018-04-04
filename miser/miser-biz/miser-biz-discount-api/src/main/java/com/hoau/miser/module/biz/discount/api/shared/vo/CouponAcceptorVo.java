package com.hoau.miser.module.biz.discount.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.discount.api.shared.domain.CouponAcceptorEntity;

/**
 * 
 * ClassName: CouponAcceptorVo 
 * @Description: TODO(返券记录) 
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月15日
 * @version V1.0
 */
public class CouponAcceptorVo implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6471101763037825786L;

	private CouponAcceptorEntity couponAcceptorEntity;
	
	private List<CouponAcceptorEntity> couponAcceptorEntityList;

	public CouponAcceptorEntity getCouponAcceptorEntity() {
		return couponAcceptorEntity;
	}

	public void setCouponAcceptorEntity(CouponAcceptorEntity couponAcceptorEntity) {
		this.couponAcceptorEntity = couponAcceptorEntity;
	}

	public List<CouponAcceptorEntity> getCouponAcceptorEntityList() {
		return couponAcceptorEntityList;
	}

	public void setCouponAcceptorEntityList(
			List<CouponAcceptorEntity> couponAcceptorEntityList) {
		this.couponAcceptorEntityList = couponAcceptorEntityList;
	}
	
	
	
	
}
