package com.hoau.eai.pc.vo;

import java.io.Serializable;

/**
 * 越发越惠返券人
 * ClassName: PrivilegeCouponAcceptorEntity 
 * @author 286330付于令
 * @date 2016年2月24日
 * @version V1.0
 */
public class PrivilegeCouponAcceptorInfo implements Serializable {
	private static final long serialVersionUID = -5415234632271168096L;
	
	private String acceptorName;
	private String acceptorPhone;
	private String identityCardNo;
	private String bankName;
	private String subbranchName;
	private String creditCardNo;
	private String relationshipWithCustomer;
	private String alipayAccount;
	
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
	 * @return the alipayAccount
	 */
	public String getAlipayAccount() {
		return alipayAccount;
	}

	/**
	 * @param alipayAccount the alipayAccount to set
	 */
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

}
