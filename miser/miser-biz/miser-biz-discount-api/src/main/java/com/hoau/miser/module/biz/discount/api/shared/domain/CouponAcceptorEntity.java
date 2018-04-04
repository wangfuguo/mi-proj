package com.hoau.miser.module.biz.discount.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 
 * ClassName: CouponAcceptorEntity
 * 
 * @Description: TODO(越发越惠返券收券人)
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月15日
 * @version V1.0
 */
public class CouponAcceptorEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -5415234632271168096L;
	
	private String acceptorName;
	private String acceptorPhone;
	private String identityCardNo;
	private String bankName;
	private String subbranchName;
	private String creditCardNo;
	private String relationshipWithCustomer;
	private String alipayAccount;
	private String active;
	private String tpdId;	//越发越惠折扣记录
	private String tpcId;	//越发越惠返券记录
	private String contractCode;
	private Date recordMonth;
	private List<CouponStateEntity> conponStateList;
	public String getAcceptorName() {
		return acceptorName;
	}

	public void setAcceptorName(String acceptorName) {
		this.acceptorName = acceptorName;
	}

	public String getAcceptorPhone() {
		return acceptorPhone;
	}

	public void setAcceptorPhone(String acceptorPhone) {
		this.acceptorPhone = acceptorPhone;
	}

	public String getIdentityCardNo() {
		return identityCardNo;
	}

	public void setIdentityCardNo(String identityCardNo) {
		this.identityCardNo = identityCardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getRelationshipWithCustomer() {
		return relationshipWithCustomer;
	}

	public void setRelationshipWithCustomer(String relationshipWithCustomer) {
		this.relationshipWithCustomer = relationshipWithCustomer;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getTpdId() {
		return tpdId;
	}

	public void setTpdId(String tpdId) {
		this.tpdId = tpdId;
	}

	public String getTpcId() {
		return tpcId;
	}

	public void setTpcId(String tpcId) {
		this.tpcId = tpcId;
	}

	/**
	 * @return the contractCode
	 */
	public String getContractCode() {
		return contractCode;
	}

	/**
	 * @param contractCode the contractCode to set
	 */
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	/**
	 * @return the recordMonth
	 */
	public Date getRecordMonth() {
		return recordMonth;
	}

	/**
	 * @param recordMonth the recordMonth to set
	 */
	public void setRecordMonth(Date recordMonth) {
		this.recordMonth = recordMonth;
	}

	/**
	 * @return the subbranchName
	 */
	public String getSubbranchName() {
		return subbranchName;
	}

	/**
	 * @param subbranchName the subbranchName to set
	 */
	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}

	/**
	 * @return the conponStateList
	 */
	public List<CouponStateEntity> getConponStateList() {
		return conponStateList;
	}

	/**
	 * @param conponStateList the conponStateList to set
	 */
	public void setConponStateList(List<CouponStateEntity> conponStateList) {
		this.conponStateList = conponStateList;
	}

}
