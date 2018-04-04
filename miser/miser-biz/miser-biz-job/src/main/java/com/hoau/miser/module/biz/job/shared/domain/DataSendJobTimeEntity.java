package com.hoau.miser.module.biz.job.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * ClassName: DataSendJobTimeEntity 
 * @Description: 数据发送任务执行时间数据类 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月25日
 * @version V1.0   
 */
public class DataSendJobTimeEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : 序列化ID
	 */
	private static final long serialVersionUID = 4719158119579444139L;

	/**
	 * @Fields jobCode : 同步任务编码
	 */
	private String jobCode;
	
	/**
	 * @Fields jobName : 同步任务名称
	 */
	private String jobName;
	
	/**
	 * @Fields lastSendTime : 上次同步时间
	 */
	private Date lastSendTime;
	
	/**
	 * @Fields remark : 备注
	 */
	private String remark;
	
	/**
	 * @Fields active : 是否有效
	 */
	private String active;

	public DataSendJobTimeEntity() {}

	public DataSendJobTimeEntity(String jobCode, Date lastSendTime) {
		this.jobCode = jobCode;
		this.jobName = DataSendJobConstant.getNameByCode(jobCode);
		this.lastSendTime = lastSendTime;
		this.active = MiserConstants.ACTIVE;
		super.setId(UUIDUtil.getUUID());
		super.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
		super.setCreateDate(new Date());
		super.setModifyDate(new Date());
		super.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Date getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
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

	@Override
	public String toString() {
		return "DataSendJobTimeEntity [jobCode=" + jobCode + ", jobName="
				+ jobName + ", lastSendTime=" + lastSendTime + ", remark="
				+ remark + ", active=" + active + "]";
	}
	
	
}
