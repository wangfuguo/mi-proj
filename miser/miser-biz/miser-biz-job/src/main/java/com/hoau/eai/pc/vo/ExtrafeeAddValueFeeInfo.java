package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
/**
 * 增值费
 * ClassName: ExtrafeeAddValueFeeInfo 
 * @author 刘海飞
 * @date 2016年2月22日
 * @version V1.0
 */
public class ExtrafeeAddValueFeeInfo implements Serializable {


	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String code; // 增值费项目编码
	private String transTypeCode;//运输类型 (D-定日达、P-普通零担、Z-整车)
	private String transTypeName;//运输类型名称
	private String calculationType; // 计算类型
	private Double weightPrice; // 重货单价
	private Double lightPrice; // 轻货单价
	private Double lowestMoney; // 最低收费
	private Double sdfs; // 锁定方式
	private String remark; //备注
	private String pxh;//排序号
	private String sfyx;//是否有效  0无效 1有效
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	public String getTransTypeName() {
		return transTypeName;
	}
	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}
	public String getCalculationType() {
		return calculationType;
	}
	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}
	public Double getWeightPrice() {
		return weightPrice;
	}
	public void setWeightPrice(Double weightPrice) {
		this.weightPrice = weightPrice;
	}
	public Double getLightPrice() {
		return lightPrice;
	}
	public void setLightPrice(Double lightPrice) {
		this.lightPrice = lightPrice;
	}
	public Double getLowestMoney() {
		return lowestMoney;
	}
	public void setLowestMoney(Double lowestMoney) {
		this.lowestMoney = lowestMoney;
	}
	public Double getSdfs() {
		return sdfs;
	}
	public void setSdfs(Double sdfs) {
		this.sdfs = sdfs;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPxh() {
		return pxh;
	}
	public void setPxh(String pxh) {
		this.pxh = pxh;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	
}
