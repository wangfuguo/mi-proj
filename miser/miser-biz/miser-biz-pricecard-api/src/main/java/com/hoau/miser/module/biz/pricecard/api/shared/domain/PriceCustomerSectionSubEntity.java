package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 客户专属价格实体类
 * ClassName: PriceCustomerSectionSubEntity 
 * @author 何羽
 * @date 2016年5月5日
 * @version V2.0   
 */
public class PriceCustomerSectionSubEntity extends BaseEntity {

	private static final long serialVersionUID = 5552304534330256460L;

	//id
    private String id;
    
    //主表主键
    private String parentId;

     //运输类型
    private String transTypeCode;

	//运输类型名称    
    private String transTypeName;

	//出发价格城市    
    private String sendPriceCity;
    
	//出发价格城市名字   
    private String sendPriceCityName;   

	//到达价格城市    
    private String arrivePriceCity;
    
	//到达价格城市名字    
    private String arrivePriceCityName;

	//一段首重
    private BigDecimal firstWeight;
    
    //一段首重单价
    private BigDecimal firstWeightPrice;
    
    //一段续重单价
    private BigDecimal firstAddWeightPrice;

    //二段首重
    private BigDecimal secondWeight;
    
    //二段首重单价
    private BigDecimal secondWeightPrice;
    
    //二段续重单价
    private BigDecimal secondAddWeightPrice;
    
    //三段首重
    private BigDecimal thirdWeight;
    
    //三段首重单价
    private BigDecimal thirdWeightPrice;
    
    //三段续重单价
    private BigDecimal thirdAddWeightPrice;
    
	//备注    
    private String remark;
    
    //创建时间
    private Date createTime;

    //创建人    
    private String createUserCode;
    
    //创建人名字
    private String createUserName;    

    //更新时间    
    private Date modifyTime;

    //更新人    
    private String modifyUserCode;
    
    //更新人名字
    private String modifyUserName;    

	//是否可用    
    private String active;
    
    //运费优惠分段
    private String freightSectionCode;

    //运费优惠分段名字
    private String freightSectionCodeName;
    
    //数据来源
    private String dataOrign;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getSendPriceCity() {
		return sendPriceCity;
	}

	public void setSendPriceCity(String sendPriceCity) {
		this.sendPriceCity = sendPriceCity;
	}

	public String getSendPriceCityName() {
		return sendPriceCityName;
	}

	public void setSendPriceCityName(String sendPriceCityName) {
		this.sendPriceCityName = sendPriceCityName;
	}

	public String getArrivePriceCity() {
		return arrivePriceCity;
	}

	public void setArrivePriceCity(String arrivePriceCity) {
		this.arrivePriceCity = arrivePriceCity;
	}

	public String getArrivePriceCityName() {
		return arrivePriceCityName;
	}

	public void setArrivePriceCityName(String arrivePriceCityName) {
		this.arrivePriceCityName = arrivePriceCityName;
	}

	public BigDecimal getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(BigDecimal firstWeight) {
		this.firstWeight = firstWeight;
	}

	public BigDecimal getFirstWeightPrice() {
		return firstWeightPrice;
	}

	public void setFirstWeightPrice(BigDecimal firstWeightPrice) {
		this.firstWeightPrice = firstWeightPrice;
	}

	public BigDecimal getFirstAddWeightPrice() {
		return firstAddWeightPrice;
	}

	public void setFirstAddWeightPrice(BigDecimal firstAddWeightPrice) {
		this.firstAddWeightPrice = firstAddWeightPrice;
	}

	public BigDecimal getSecondWeight() {
		return secondWeight;
	}

	public void setSecondWeight(BigDecimal secondWeight) {
		this.secondWeight = secondWeight;
	}

	public BigDecimal getSecondWeightPrice() {
		return secondWeightPrice;
	}

	public void setSecondWeightPrice(BigDecimal secondWeightPrice) {
		this.secondWeightPrice = secondWeightPrice;
	}

	public BigDecimal getSecondAddWeightPrice() {
		return secondAddWeightPrice;
	}

	public void setSecondAddWeightPrice(BigDecimal secondAddWeightPrice) {
		this.secondAddWeightPrice = secondAddWeightPrice;
	}

	public BigDecimal getThirdWeight() {
		return thirdWeight;
	}

	public void setThirdWeight(BigDecimal thirdWeight) {
		this.thirdWeight = thirdWeight;
	}

	public BigDecimal getThirdWeightPrice() {
		return thirdWeightPrice;
	}

	public void setThirdWeightPrice(BigDecimal thirdWeightPrice) {
		this.thirdWeightPrice = thirdWeightPrice;
	}

	public BigDecimal getThirdAddWeightPrice() {
		return thirdAddWeightPrice;
	}

	public void setThirdAddWeightPrice(BigDecimal thirdAddWeightPrice) {
		this.thirdAddWeightPrice = thirdAddWeightPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getFreightSectionCode() {
		return freightSectionCode;
	}

	public void setFreightSectionCode(String freightSectionCode) {
		this.freightSectionCode = freightSectionCode;
	}

	public String getFreightSectionCodeName() {
		return freightSectionCodeName;
	}

	public void setFreightSectionCodeName(String freightSectionCodeName) {
		this.freightSectionCodeName = freightSectionCodeName;
	}

	public String getDataOrign() {
		return dataOrign;
	}

	public void setDataOrign(String dataOrign) {
		this.dataOrign = dataOrign;
	}
    
}
