package com.hoau.miser.module.biz.discount.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
/**
 * 
 * ClassName: PricePrivilegeEntity 
 * @author 王茂
 * @date 2016年1月12日
 * @version V1.0
 */
public class DiscountPrivilegeEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -5040034235497904351L;
	private Double startAmount;	//发货金额段起
	private Double endAmount;	//发货金额段止
	private Double ddMinFreightDiscount;	//定日达纯运费最低折扣
	private Double duMinFreightDiscount;	//经济快运纯运费最后折扣
	private String  hasCoupon;				//是否返券
	private Double  maxCouponScale;			//最高返券比例
	private Date effectiveTime; // 生效时间
	private Date invalidTime; // 失效时间
	private String remark;
	private String active;
	private String state;
	private String isAlert;
	public Double getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(Double startAmount) {
		this.startAmount = startAmount;
	}
	public Double getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(Double endAmount) {
		this.endAmount = endAmount;
	}
	public Double getDdMinFreightDiscount() {
		return ddMinFreightDiscount;
	}
	public void setDdMinFreightDiscount(Double ddMinFreightDiscount) {
		this.ddMinFreightDiscount = ddMinFreightDiscount;
	}
	public Double getDuMinFreightDiscount() {
		return duMinFreightDiscount;
	}
	public void setDuMinFreightDiscount(Double duMinFreightDiscount) {
		this.duMinFreightDiscount = duMinFreightDiscount;
	}
	public String getHasCoupon() {
		return hasCoupon;
	}
	public void setHasCoupon(String hasCoupon) {
		this.hasCoupon = hasCoupon;
	}
	public Double getMaxCouponScale() {
		return maxCouponScale;
	}
	public void setMaxCouponScale(Double maxCouponScale) {
		this.maxCouponScale = maxCouponScale;
	}
	public Date getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public Date getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsAlert() {
		return isAlert;
	}
	public void setIsAlert(String isAlert) {
		this.isAlert = isAlert;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
