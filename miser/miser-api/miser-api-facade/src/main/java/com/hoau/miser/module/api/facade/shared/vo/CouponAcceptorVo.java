package com.hoau.miser.module.api.facade.shared.vo;


/**
 * 收券人VO
 * ClassName: CouponAcceptorVo 
 * @author 286330付于令
 * @date 2016年4月8日
 * @version V1.0
 */
public class CouponAcceptorVo {
	
	private String acceptorName; // 收券人
	private String acceptorPhone; // 收券人手机号
	private String identityCardNo; // 收券人身份证号
	private String bankName; // 开户行
	private String creditCardNo; // 银行卡号
	private String relationshipWithCustomer; // 与客户的关系
	private String alipayAccount; // 支付宝账号

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
}
