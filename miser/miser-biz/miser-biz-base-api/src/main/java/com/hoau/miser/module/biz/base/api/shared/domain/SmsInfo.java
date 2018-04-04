package com.hoau.miser.module.biz.base.api.shared.domain;

import java.util.List;

import com.hoau.hbdp.framework.entity.BaseEntity;

public class SmsInfo extends BaseEntity{
	 
	private static final long serialVersionUID = 9014430730839153807L;
	
	/**
	 * {
	    "systemName":"OHTER",
	    "businessType":"00001",
	    "requestContentList":[
	        {
	            "smsId": "",
	            "mobile": "18616777015",
	            "content": "你好,一条测试短信!"
	        }
	    ]
	   }
	 */
	
	private String systemName;
	private String businessType;
	private List<SmsCnt> requestContentList;
	
	public String getSystemName() {
		return systemName;
	}
	
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	public String getBusinessType() {
		return businessType;
	}
	
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	public List<SmsCnt> getRequestContentList() {
		return requestContentList;
	}
	
	public void setRequestContentList(List<SmsCnt> requestContentList) {
		this.requestContentList = requestContentList;
	}
	
	
	
}
