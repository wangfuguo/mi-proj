package com.hoau.miser.module.biz.discount.api.shared.domain;

import java.util.List;

import com.hoau.hbdp.framework.entity.BaseEntity;
/**
 * 辅助活动的组织类
 * ClassName: PriceEventOrgEntity 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
public class PriceEventOrgEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//组织编码 code
	private String code;
	//组织名称  name
	private String name;
	//parent_code
	private String parentCode;
	//parent_name
	private String parentName;
	//是否选中
	private String isCheck;
	
	//活动code
	private String eventCode;
	//活动id
	private String eventCorpId;
	
	//范围类型
	private String corpType;
	
	/**
	 * 下属部门
	 */
	private List<PriceEventOrgEntity> children;
	
	
	public List<PriceEventOrgEntity> getChildren() {
		return children;
	}
	public void setChildren(List<PriceEventOrgEntity> children) {
		this.children = children;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public String getEventCorpId() {
		return eventCorpId;
	}
	public void setEventCorpId(String eventCorpId) {
		this.eventCorpId = eventCorpId;
	}
	
	public String getCorpType() {
		return corpType;
	}
	public void setCorpType(String corpType) {
		this.corpType = corpType;
	}
	
}
