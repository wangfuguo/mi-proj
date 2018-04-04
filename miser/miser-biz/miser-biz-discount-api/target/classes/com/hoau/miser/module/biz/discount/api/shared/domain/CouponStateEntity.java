package com.hoau.miser.module.biz.discount.api.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 返券状态记录实体
 * ClassName: CouponStateEntity 
 * @author 286330付于令
 * @date 2016年4月14日
 * @version V1.0
 */
public class CouponStateEntity extends BaseEntity {
	private static final long serialVersionUID = -988130994858447259L;
	
	private String contractCode; // 合同CODE
	private Date recordMonth; // 月份
	private String executeState; // 记录状态
	private String executeStateDes; // 状态描述
	private String executeUser; // 记录人 
	private Date executeTime; // 记录时间
	private String executeRemark; // 记录备注
	private String active; // 是否删除
	
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public Date getRecordMonth() {
		return recordMonth;
	}
	public void setRecordMonth(Date recordMonth) {
		this.recordMonth = recordMonth;
	}
	public String getExecuteState() {
		return executeState;
	}
	public void setExecuteState(String executeState) {
		this.executeState = executeState;
	}
	public String getExecuteUser() {
		return executeUser;
	}
	public void setExecuteUser(String executeUser) {
		this.executeUser = executeUser;
	}
	public Date getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}
	public String getExecuteRemark() {
		return executeRemark;
	}
	public void setExecuteRemark(String executeRemark) {
		this.executeRemark = executeRemark;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return the executeStateDes
	 */
	public String getExecuteStateDes() {
		return executeStateDes;
	}
	/**
	 * @param executeStateDes the executeStateDes to set
	 */
	public void setExecuteStateDes(String executeStateDes) {
		this.executeStateDes = executeStateDes;
	}
}
