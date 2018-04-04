package com.hoau.eai.pc.vo;

import java.io.Serializable;

/**
 * 	附加费
 * @author Leslie
 *
 */
public class ExtraInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对应的附加费唯一标识
	 */
	private Integer id;
	/**
	 * 运输类型 D-定日达;Z-整车;P-普通零担
	 */
	private String transportType;
	/**
	 * S-标准附加费，C-客户附加费
	 */
	private String type;
	/**
	 * 客户编号（1. 客户附加费时必填， 2. 标准附加费不填）
	 */
	private String customerId;
	/**
	 * 费用类型（1.标准附加费时必填， 2.客户附加费不填）：
	 * GF-工本费；
	 * TF-提货费；
	 * SF-送货费；
	 * BL-保价费；
	 * BF-保价最低收费；
	 * XF-信息费 
	 */
	private String feeType;
	/**
	 * 金额（1.标准附加费时必填， 2.客户附加费不填）
	 */
	private Double amount;
	/**
	 * 锁定方式（1.标准附加费时必填， 2.客户附加费不填）：
	 * 0-不锁定
	 * 1-向上锁定
	 * 2-向上锁定
	 * 3-完全锁定
	 */
	private Integer lockedStatus;
	/**
	 * 提货费（1. 客户附加费时必填，没有值用0代替，2.标准附加费不填）
	 */
	private Double pickFees;
	/**
	 * 送货费（1. 客户附加费时必填，没有值用0代替，2.标准附加费不填）
	 */
	private Double deliverFees;
	/**
	 * 保价费（1. 客户附加费时必填，没有值用0代替，2.标准附加费不填）
	 */
	private Double insuranceRate;
	/**
	 * 信息费（1. 客户附加费时必填，没有值用0代替，2.标准附加费不填）
	 */
	private Double informationFees;
	/**
	 * 工本费（客户附加费）
	 */
	private Double flatCost;
	/**
	 * 代收款手续费率（1. 客户附加费时必填，没有值用0代替，2.标准附加费不填）
	 */
	private Double payableServiceRate;
	/**
	 * 附加收费（1. 客户附加费时必填，没有值用0代替，2.标准附加费不填）
	 */
	private Double subcharge;
	/**
	 * 保价最低收费（1. 客户附加费时必填，没有值用0代替，2.标准附加费不填）
	 */
	private Double lowestInsuranceFees;
	/**
	 * 包装费（1. 客户附加费时必填，没有值用0代替，2.标准附加费不填）
	 */
	private Double packFees;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除状态0正常
	 */
	private Integer recStatus;
	/**
	 * 燃油附加费
	 */
	private String fuelSubcharge ;
	
	public String getFuelSubcharge() {
		return fuelSubcharge;
	}
	public void setFuelSubcharge(String fuelSubcharge) {
		this.fuelSubcharge = fuelSubcharge;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}
	/**
	 * @param transportType the transportType to set
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the feeType
	 */
	public String getFeeType() {
		return feeType;
	}
	/**
	 * @param feeType the feeType to set
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * @return the lockedStatus
	 */
	public Integer getLockedStatus() {
		return lockedStatus;
	}
	/**
	 * @param lockedStatus the lockedStatus to set
	 */
	public void setLockedStatus(Integer lockedStatus) {
		this.lockedStatus = lockedStatus;
	}
	/**
	 * @return the pickFees
	 */
	public Double getPickFees() {
		return pickFees;
	}
	/**
	 * @param pickFees the pickFees to set
	 */
	public void setPickFees(Double pickFees) {
		this.pickFees = pickFees;
	}
	/**
	 * @return the deliverFees
	 */
	public Double getDeliverFees() {
		return deliverFees;
	}
	/**
	 * @param deliverFees the deliverFees to set
	 */
	public void setDeliverFees(Double deliverFees) {
		this.deliverFees = deliverFees;
	}
	/**
	 * @return the insuranceRate
	 */
	public Double getInsuranceRate() {
		return insuranceRate;
	}
	/**
	 * @param insuranceRate the insuranceRate to set
	 */
	public void setInsuranceRate(Double insuranceRate) {
		this.insuranceRate = insuranceRate;
	}
	/**
	 * @return the informationFees
	 */
	public Double getInformationFees() {
		return informationFees;
	}
	/**
	 * @param informationFees the informationFees to set
	 */
	public void setInformationFees(Double informationFees) {
		this.informationFees = informationFees;
	}
	/**
	 * @return the flatCost
	 */
	public Double getFlatCost() {
		return flatCost;
	}
	/**
	 * @param flatCost the flatCost to set
	 */
	public void setFlatCost(Double flatCost) {
		this.flatCost = flatCost;
	}
	/**
	 * @return the payableServiceRate
	 */
	public Double getPayableServiceRate() {
		return payableServiceRate;
	}
	/**
	 * @param payableServiceRate the payableServiceRate to set
	 */
	public void setPayableServiceRate(Double payableServiceRate) {
		this.payableServiceRate = payableServiceRate;
	}
	/**
	 * @return the subcharge
	 */
	public Double getSubcharge() {
		return subcharge;
	}
	/**
	 * @param subcharge the subcharge to set
	 */
	public void setSubcharge(Double subcharge) {
		this.subcharge = subcharge;
	}
	/**
	 * @return the lowestInsuranceFees
	 */
	public Double getLowestInsuranceFees() {
		return lowestInsuranceFees;
	}
	/**
	 * @param lowestInsuranceFees the lowestInsuranceFees to set
	 */
	public void setLowestInsuranceFees(Double lowestInsuranceFees) {
		this.lowestInsuranceFees = lowestInsuranceFees;
	}
	/**
	 * @return the packFees
	 */
	public Double getPackFees() {
		return packFees;
	}
	/**
	 * @param packFees the packFees to set
	 */
	public void setPackFees(Double packFees) {
		this.packFees = packFees;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(Integer recStatus) {
		this.recStatus = recStatus;
	}

}
