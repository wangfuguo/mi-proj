package com.hoau.miser.module.biz.job.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 越发越惠客户合同明细实体类
 * ClassName: DiscountPrivilegeContractDetailEntity 
 * @author 286330付于令
 * @date 2016年1月12日
 * @version V1.0
 */
public class PrivilegeContractDetailEntity extends BaseEntity {
	private static final long serialVersionUID = 5097013822892334695L;
	
	private String customerContractId; // 越发越惠客户合同ID
	private Double startAmount; // 发货金额段起
	private Double endAmount; // 发货金额段止
	private Double ddMinFreightDiscount; // 定日达纯运费最低折扣
	private Double duMinFreightDiscount; // 经济快运纯运费最低折扣
	private Double maxCouponScale; // 最高返券比例
	private String dataOrign; // 数据来源
	private String remark; // 备注
	private String active; // 是否可用
	
	public String getCustomerContractId() {
		return customerContractId;
	}
	public void setCustomerContractId(String customerContractId) {
		this.customerContractId = customerContractId;
	}
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
	public Double getMaxCouponScale() {
		return maxCouponScale;
	}
	public void setMaxCouponScale(Double maxCouponScale) {
		this.maxCouponScale = maxCouponScale;
	}
	public String getDataOrign() {
		return dataOrign;
	}
	public void setDataOrign(String dataOrign) {
		this.dataOrign = dataOrign;
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
	
}
