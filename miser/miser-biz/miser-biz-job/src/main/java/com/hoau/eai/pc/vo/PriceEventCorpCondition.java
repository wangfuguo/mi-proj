package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠门店
 * ClassName: PriceEventCorpCondition
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
public class PriceEventCorpCondition implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//活动编码 EVENT_CODE
	private String eventCode;
	//门店类型 CORP_TYPE  SEND:发货区域 ARRIVAL:到货区域 ALL:发货和到货区域
	private String corpType;
	//非偏线的行政组织编码或偏线的网点代码 ORG_CODE
	private String orgCode;
	//备注 REMARK
	private String remark;
	//是否有效 ACTIVE
	private String active;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 修改日期
	 */
	private Date modifyDate;

	/**
	 * 修改人
	 */
	private String modifyUser;

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getCorpType() {
		return corpType;
	}

	public void setCorpType(String corpType) {
		this.corpType = corpType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
