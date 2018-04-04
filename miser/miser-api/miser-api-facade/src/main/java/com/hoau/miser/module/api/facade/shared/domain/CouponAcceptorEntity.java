package com.hoau.miser.module.api.facade.shared.domain;

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
	private String creditCardNo;
	private String relationshipWithCustomer;
	private String alipayAccount;
	private String active;
	private String tpdId;	//越发越惠折扣记录
	private String tpcId;	//越发越惠返券记录
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

}
