package com.hoau.miser.module.api.facade.shared.vo;

/**
 * 越发越惠返券申请VO
 * ClassName: CouponApplyVo 
 * @author 286330付于令
 * @date 2016年4月8日
 * @version V1.0
 */
public class CouponApplyVo {
	
	private String customerCode; // 客户编号
	private String contractStartTime; // 合同开始时间
	private String contractEndTime; // 合同结束时间
	private String recordMonth; // 执行年月
	private CouponAcceptorVo acceptor; // 收券人
	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getContractStartTime() {
		return contractStartTime;
	}
	public void setContractStartTime(String contractStartTime) {
		this.contractStartTime = contractStartTime;
	}
	public String getContractEndTime() {
		return contractEndTime;
	}
	public void setContractEndTime(String contractEndTime) {
		this.contractEndTime = contractEndTime;
	}
	public String getRecordMonth() {
		return recordMonth;
	}
	public void setRecordMonth(String recordMonth) {
		this.recordMonth = recordMonth;
	}
	public CouponAcceptorVo getAcceptor() {
		return acceptor;
	}
	public void setAcceptor(CouponAcceptorVo acceptor) {
		this.acceptor = acceptor;
	}
}
