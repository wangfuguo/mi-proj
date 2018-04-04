package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 特服费项目定义
 * ClassName: ExtrafeeAddValueFeeItemsInfo 
 * @author 刘海飞
 * @date 2016年2月23日
 * @version V1.0
 */
public class ExtrafeeAddValueFeeItemsInfo implements Serializable{

	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 7568485126011647019L;
	
	private String code;//code
	private String name;//名称
	private String remark;//备注
	private String sfyx;//是否有效
	private String czy;//操作人
	private Date recordDate;//记录时间
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	public String getCzy() {
		return czy;
	}
	public void setCzy(String czy) {
		this.czy = czy;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	
}
