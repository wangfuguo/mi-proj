package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


public class PriceCorpSectionEntity{
	
	/**
	 * 1:已失效
	 */
	public static final String STATE_1="PASSED";
	/**
	 * 2:生效中
	 */
	public static final String STATE_2="EFFECTIVE";
	/**
	 * 3:待生效
	 */
	public static final String STATE_3="WAIT";
	/**
	 * 4:已废弃
	 */
	public static final String STATE_4="DELETED";

	private Long index;
	
	private String id;

    private String transTypeCode;

    private String transTypeName;

    private String corpCode;

    private String corpName;

    private String arrivePriceCity;

    private Date effectiveTime;

    private Date invalidTime;

    private String remark;

    private Date createTime;

    private String createUserCode;

    private Date modifyTime;

    private String modifyUserCode;

    private String active;

    private String dataorign;

    private String freightSectionCode;

    private BigDecimal firstWeight;

    private BigDecimal firstWeightPrice;

    private BigDecimal firstAddWeightPrice;

    private BigDecimal secondWeight;

    private BigDecimal secondWeightPrice;

    private BigDecimal secondAddWeightPrice;

    private BigDecimal thirdWeight;

    private BigDecimal thirdWeightPrice;

    private BigDecimal thirdAddWeightPrice;
    
    /****************页面显示多余字段**********************/
    private String arrivePriceCityName;
    private String logisticName;
    
    private String createUserName;
    private String modifyUserName;
    
    private String divisionCode;
    private String divisionName;
    
    private String bigRegionCode;
    private String bigRegionName;
    
    private String roadAreaCode;
    private String roadAreaName;
    
    private String freightSectionName;
    
	private String state;
    private String stateName;
    /******************************************************/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode == null ? null : corpCode.trim();
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName == null ? null : corpName.trim();
    }

    public String getArrivePriceCity() {
        return arrivePriceCity;
    }

    public void setArrivePriceCity(String arrivePriceCity) {
        this.arrivePriceCity = arrivePriceCity == null ? null : arrivePriceCity.trim();
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
        this.remark = remark == null ? null : remark.trim();
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
        this.createUserCode = createUserCode == null ? null : createUserCode.trim();
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
        this.modifyUserCode = modifyUserCode == null ? null : modifyUserCode.trim();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

    public String getDataorign() {
        return dataorign;
    }

    public void setDataorign(String dataorign) {
        this.dataorign = dataorign == null ? null : dataorign.trim();
    }

    public String getFreightSectionCode() {
        return freightSectionCode;
    }

    public void setFreightSectionCode(String freightSectionCode) {
        this.freightSectionCode = freightSectionCode == null ? null : freightSectionCode.trim();
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

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getBigRegionCode() {
		return bigRegionCode;
	}

	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}

	public String getBigRegionName() {
		return bigRegionName;
	}

	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}

	public String getRoadAreaCode() {
		return roadAreaCode;
	}

	public void setRoadAreaCode(String roadAreaCode) {
		this.roadAreaCode = roadAreaCode;
	}

	public String getRoadAreaName() {
		return roadAreaName;
	}

	public void setRoadAreaName(String roadAreaName) {
		this.roadAreaName = roadAreaName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateUserName() {
		
		return createUserCode + " " + createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserCode + " " + modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getLogisticName() {
		return logisticName;
	}

	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}

	public String getArrivePriceCityName() {
		return arrivePriceCityName;
	}

	public void setArrivePriceCityName(String arrivePriceCityName) {
		this.arrivePriceCityName = arrivePriceCityName;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public String getStateName() {
		if(StringUtils.isEmpty(stateName)){
			if("PASSED".equals(state)){
				stateName= "已失效";
			}else if("EFFECTIVE".equals(state)){
				stateName= "生效中";
			}else if("WAIT".equals(state)){
				stateName= "待生效";
			}else if("DELETED".equals(state)){
				stateName= "已过期";
			}
		}
		return stateName;
	}
	
	 public String getFreightSectionName() {
			return freightSectionName;
	}

	public void setFreightSectionName(String freightSectionName) {
		this.freightSectionName = freightSectionName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
    
}