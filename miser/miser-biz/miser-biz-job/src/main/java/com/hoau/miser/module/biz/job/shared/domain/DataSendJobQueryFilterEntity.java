package com.hoau.miser.module.biz.job.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * ClassName: DataSendJobQueryFilterEntity 
 * @Description: 需发送数据发送查询条件 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月25日
 * @version V1.0   
 */
public class DataSendJobQueryFilterEntity extends BaseEntity {
	
	/**
	 * @Fields serialVersionUID : 序列化ID
	 */
	private static final long serialVersionUID = 7649095697550886098L;

	/**
	 * @Fields startTime : 查询数据开始时间
	 */
	private Date startTime;
	
	/**
	 * @Fields endTime : 查询数据结束时间
	 */
	private Date endTime;
	
	/**
	 * 是否有效 Y：有效,N:无效
	 */
	private String active; 

	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
