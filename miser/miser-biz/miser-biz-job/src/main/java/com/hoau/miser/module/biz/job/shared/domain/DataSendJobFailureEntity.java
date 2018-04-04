package com.hoau.miser.module.biz.job.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * ClassName: DataSendJobFailureEntity 
 * @Description: 数据发送任务失败的对象 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月25日
 * @version V1.0   
 */
public class DataSendJobFailureEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : 序列化ID
	 */
	private static final long serialVersionUID = 4898310622268637383L;

	/**
	 * @Fields type : 发送失败业务类型
	 */
	private String type;
	
	/**
	 * @Fields businessId : 发送失败业务主键
	 */
	private String businessId;
	
	/**
	 * @Fields sendTime : 发送时间
	 */
	private Date sendTime;
	
	/**
	 * @Fields failReason : 发送失败原因
	 */
	private String failReason;
	
	/**
	 * @Fields remark : 备注
	 */
	private String remark;
	
	/**
	 * @Fields retryTimes : 重发次数
	 */
	private Integer retryTimes;
	
	/**
	 * @Fields active : 是否有效：默认有效，被重发成功后更新为无效
	 */
	private String active;
	
	/**
	 * @Fields lastSendTime : 上次发送时间
	 */
	private Date lastSendTime;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
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

	public Date getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	@Override
	public String toString() {
		return "DataSendJobFailureEntity [type=" + type + ", businessId="
				+ businessId + ", sendTime=" + sendTime + ", failReason="
				+ failReason + ", remark=" + remark + ", active=" + active
				+ "]";
	}
	
}
