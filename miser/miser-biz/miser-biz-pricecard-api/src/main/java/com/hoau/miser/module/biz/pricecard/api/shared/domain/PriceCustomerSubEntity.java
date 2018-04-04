/**   
* @Title: PriceCustomerEntity.java 
* @Package com.hoau.miser.module.biz.pricecard.shared.domain 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dengyin
* @date 2016年1月5日 下午1:43:48 
* @version V1.0   
*/
package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 客户专属价格实体类
 * ClassName: PriceCustomerEntity 
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0   
 */
public class PriceCustomerSubEntity extends BaseEntity{
 
	private static final long serialVersionUID = -5697720429427197730L;

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

	//重量单价    
    private BigDecimal weightPrice;

	//体积单价    
    private BigDecimal volumePrice;

	//附加费    
    private BigDecimal addFee;

	//最低收费    
    private BigDecimal lowestFee;

	//燃油费分段编号    
    private String fuelFeeSection;
    
	//燃油费分段名称
    private String fuelFeeSectionName;

	//生效时间    
    private Date effectiveTime;

	//失效时间    
    private Date invalidTime;

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
    private String freightFeeSection;

    //运费优惠分段名字
    private String freightFeeSectionName;
    
    //重货折扣
    private BigDecimal weightDiscount;
    
    //轻货折扣
    private BigDecimal volumeDiscount;
    
    //add by dengyin 2016-2-22 14:34:26 添加 数据来源
    
    private String dataOrign;
    
	public String getDataOrign() {
		return dataOrign;
	}

	public void setDataOrign(String dataOrign) {
		this.dataOrign = dataOrign;
	}
	
	//end by dengyin 2016-2-22 14:34:26 添加 数据来源    
	
	//add by dengyin 2016-4-11 12:28:03 添加所在分区名
	private String partitionName;
	
	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}
	//end by dengyin 2016-4-11 12:28:03 添加所在分区名


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
		this.transTypeCode = transTypeCode == null ? null : transTypeCode.trim();
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName == null ? null : transTypeName.trim();
	}

	public BigDecimal getWeightPrice() {
		return weightPrice;
	}

	public void setWeightPrice(BigDecimal weightPrice) {
		this.weightPrice = weightPrice;
	}

	public BigDecimal getVolumePrice() {
		return volumePrice;
	}

	public void setVolumePrice(BigDecimal volumePrice) {
		this.volumePrice = volumePrice;
	}

	public BigDecimal getAddFee() {
		return addFee;
	}

	public void setAddFee(BigDecimal addFee) {
		this.addFee = addFee;
	}

	public BigDecimal getLowestFee() {
		return lowestFee;
	}

	public void setLowestFee(BigDecimal lowestFee) {
		this.lowestFee = lowestFee;
	}

	public String getFuelFeeSection() {
		return fuelFeeSection;
	}

	public void setFuelFeeSection(String fuelFeeSection) {
		this.fuelFeeSection = fuelFeeSection == null ? fuelFeeSection : fuelFeeSection.trim();
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getSendPriceCity() {
		return sendPriceCity;
	}

	public void setSendPriceCity(String sendPriceCity) {
		this.sendPriceCity = sendPriceCity;
	}

	public String getArrivePriceCity() {
		return arrivePriceCity;
	}

	public void setArrivePriceCity(String arrivePriceCity) {
		this.arrivePriceCity = arrivePriceCity;
	}

	public String getFreightFeeSection() {
		return freightFeeSection;
	}

	public void setFreightFeeSection(String freightFeeSection) {
		this.freightFeeSection = freightFeeSection == null ? freightFeeSection : freightFeeSection.trim();
	}

	public String getSendPriceCityName() {
		return sendPriceCityName;
	}

	public void setSendPriceCityName(String sendPriceCityName) {
		this.sendPriceCityName = sendPriceCityName;
	}

	public String getArrivePriceCityName() {
		return arrivePriceCityName;
	}

	public void setArrivePriceCityName(String arrivePriceCityName) {
		this.arrivePriceCityName = arrivePriceCityName;
	}

	public String getFuelFeeSectionName() {
		return fuelFeeSectionName;
	}

	public void setFuelFeeSectionName(String fuelFeeSectionName) {
		this.fuelFeeSectionName = fuelFeeSectionName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getFreightFeeSectionName() {
		return freightFeeSectionName;
	}

	public void setFreightFeeSectionName(String freightFeeSectionName) {
		this.freightFeeSectionName = freightFeeSectionName;
	}

	public BigDecimal getWeightDiscount() {
		return weightDiscount;
	}

	public void setWeightDiscount(BigDecimal weightDiscount) {
		this.weightDiscount = weightDiscount;
	}

	public BigDecimal getVolumeDiscount() {
		return volumeDiscount;
	}

	public void setVolumeDiscount(BigDecimal volumeDiscount) {
		this.volumeDiscount = volumeDiscount;
	}
    
	
	
}
