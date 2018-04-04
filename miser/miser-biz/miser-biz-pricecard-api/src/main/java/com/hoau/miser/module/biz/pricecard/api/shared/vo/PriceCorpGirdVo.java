package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;

public class PriceCorpGirdVo {
	
	/**
	 * 索引
	 */
	private Integer index;
	
	/**
	 * id
	 */
	private String id;

	/**
	 * 运输类型代码
	 */
    private String transTypeCode;

    /**
     * 运输类型名称
     */
    private String transTypeName;

    /**
     * code
     */
    private String corpCode;
    
    /**
     * Name;
     */
    private String corpName;

    /**
     * 到达价格城市
     */
    private String arrivePriceCity;
    
    /**
     * 到达城市显示名称
     */
    private String arrivePriceCityName;

    /**
     * 重量单价
     */
    private BigDecimal weightPrice;

    /**
     * 体积单价
     */
    private BigDecimal volumePrice;

    /**
     * 附加费
     */
    private BigDecimal addFee;

    /**
     * 最低收费
     */
    private BigDecimal lowestFee;

    /**
     * 燃油费
     */
    private String fuelAddFeeSection;
    
    /**
     * 燃油费名称
     */
    private String fuelAddFeeSectionName;

    /**
     * 生效时间
     */
    private Date effectiveTime;

    /**
     * 失效时间
     */
    private Date invalidTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建用户
     */
    private String createUserCode;

    /**
     * 更改时间
     */
    private Date modifyTime;

    /**
     * 更改人
     */
    private String modifyUserCode;

    /**
     * 状态
     */
    private String state;
    
    /**
     * 事业部
	 */
	private String division;
	
	/**
	 * 大区
	 */
	private String bigRegion;
	
	/**
	 * 路区
	 */
	private String roadArea;
	
	/**
     * 事业部代码
	 */
	private String divisionCode;
	
	/**
	 * 大区代码
	 */
	private String bigRegionCode;
	
	/**
	 * 路区代码
	 */
	private String roadAreaCode;
	
	/**
	 * 导入exel文件或excel压缩包路径
	 */
	private String filePath;
	
	/**
	 * 导入exel数据错误信息
	 */
	private String errorMsg;
	
	/**
	 * 数据状态
	 */
	private String stateName;
	
	/**
	 * 数据来源目前默认为PMS
	 * 
	 * 2016年2月22日
	 * 更改人：李玮琰
	 */
	private String dataOrign;

	/**
	 * 迪辰中使用的物流代码
	 */
	private String logisticName;
	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getStateName() {
		return stateName;
	}

	/**
	 * 获取中文状态
	 * @Description: 
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年2月22日
	 */
	public void changeStateToCn() {
		if(StringUtils.isEmpty(stateName)){
			if("PASSED".equals(state)){
				stateName= "已失效";
			}else if("EFFECTIVE".equals(state)){
				stateName= "生效中";
			}else if("WAIT".equals(state)){
				stateName= "待生效";
			}else if("DELETED".equals(state)){
				stateName= "已作废";
			}
		}
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
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

	public String getFuelAddFeeSection() {
		return fuelAddFeeSection;
	}

	public void setFuelAddFeeSection(String fuelAddFeeSection) {
		this.fuelAddFeeSection = fuelAddFeeSection;
	}
	
	public String getFuelAddFeeSectionName() {
		return fuelAddFeeSectionName;
	}

	public void setFuelAddFeeSectionName(String fuelAddFeeSectionName) {
		this.fuelAddFeeSectionName = fuelAddFeeSectionName;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getBigRegion() {
		return bigRegion;
	}

	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	public String getRoadArea() {
		return roadArea;
	}

	public void setRoadArea(String roadArea) {
		this.roadArea = roadArea;
	}
	
	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getBigRegionCode() {
		return bigRegionCode;
	}

	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}

	public String getRoadAreaCode() {
		return roadAreaCode;
	}

	public void setRoadAreaCode(String roadAreaCode) {
		this.roadAreaCode = roadAreaCode;
	}

	public String getDataOrign() {
		return dataOrign;
	}

	public void setDataOrign(String dataOrign) {
		this.dataOrign = dataOrign;
	}

	public String getLogisticName() {
		return logisticName;
	}

	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}

	/**
	 * @Description: 校验这个来自页面的vo必填数据是否有空缺
	 * @throws Exception   
	 * @return String 空缺提示
	 * @author zouyu
	 * @date 2016年1月20日
	 */
	public String verify(){
		if(getEffectiveTime() == null){
			return "填写正确的生效时间";
		}
		if(StringUtils.isEmpty(getCorpCode())){
			return "选择正确的网点";
		}
		if(StringUtils.isEmpty(getCorpName())){
			return "选择正确的网点";
		}
		if(StringUtils.isEmpty(getTransTypeCode())){
			return "选择正确的运输类型";
		}
		if(StringUtils.isEmpty(getTransTypeName())){
			return "选择正确的运输类型";
		}
		if(StringUtils.isEmpty(getTransTypeName())){
			return "选择正确的运输类型";
		}
		if(StringUtils.isEmpty(getArrivePriceCity())){
			return "选择正确的到达价格城市";
		}
		return null;
	}
	
	/**
	 * @Description: 页面来的vo创建一条数据库新纪录
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年1月20日
	 */
	public PriceCorpGirdVo copy(){
		PriceCorpGirdVo entity = new PriceCorpGirdVo();
		entity.setTransTypeCode(getTransTypeCode());
		entity.setTransTypeName(getTransTypeName());
		entity.setCorpCode(getCorpCode());
		entity.setCorpName(getCorpName());
		entity.setArrivePriceCity(getArrivePriceCity());
		entity.setArrivePriceCityName(getArrivePriceCityName());
		entity.setWeightPrice(getWeightPrice());
		entity.setVolumePrice(getVolumePrice());
		entity.setAddFee(getAddFee());
		entity.setLowestFee(getLowestFee());
		entity.setFuelAddFeeSection(getFuelAddFeeSection());
		entity.setFuelAddFeeSectionName(getFuelAddFeeSectionName());
		entity.setEffectiveTime(getEffectiveTime());
		entity.setInvalidTime(getInvalidTime());
		entity.setRemark(getRemark());
		entity.setCreateUserCode(MiserUserContext.getCurrentUser().getUserName());
		entity.setModifyUserCode(MiserUserContext.getCurrentUser().getUserName());
		entity.setState(getState());
		return entity;
	}

}
