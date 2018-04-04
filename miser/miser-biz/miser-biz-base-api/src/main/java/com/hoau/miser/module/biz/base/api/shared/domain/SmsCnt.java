package com.hoau.miser.module.biz.base.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class SmsCnt extends BaseEntity{
	 
	private static final long serialVersionUID = 391510117930344661L;
	
	private String smsId;
	private String mobile;
	private String content;
	
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
