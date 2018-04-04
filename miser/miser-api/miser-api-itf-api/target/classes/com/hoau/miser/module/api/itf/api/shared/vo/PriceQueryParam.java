package com.hoau.miser.module.api.itf.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: TransTypeFyParam
 * @Package com.hoau.miser.module.api.itf.shared.vo
 * @Description: ${TODO}(基础查询实体 接口使用)
 * @date 2016/6/1 11:15
 */
public class PriceQueryParam implements Serializable {
    /** 起运地 组织编码**/
    private String originCode;
    /** 目的地 组织编码**/
    private String destCode;
    /** 开单时间 **/
    private Date billTime;
    /** 是否历史**/
    private boolean isHistory;

    /** 重量**/
    private BigDecimal weightNo;
    /** 体积 **/
    private BigDecimal volumeNo;
    /** 客户编号 **/
    private String custNo;
    /** 收货方式 0：客户自送，1：上门提货 **/
    private String takeDelyType;
    /** 运输类型 **/
    private String transTypeCode;
    /** 父运输类型 **/
    private String parTransTypeCode;
    /** 子运输类型 **/
    private String subTransTypeCode;
    /** 运输类型策略（0：父运输类型，1：子类型策越，2：子不存在选父） **/
    private Integer stgyTransType;

    /**
     * 渠道
     */
    private String orderChannel;

    /**
     * OMS订单外部折扣
     */
    private BigDecimal omsFreightDiscount;
    
    /**
     * 起运地公司省市区信息
     */
    private OrgPositionInfoTyEntity originPositionInfo;
    
    /**
     * 目的地公司省市区信息
     */
    private OrgPositionInfoTyEntity destPositionInfo;

    public String getParTransTypeCode() {
        return parTransTypeCode;
    }

    public void setParTransTypeCode(String parTransTypeCode) {
        this.parTransTypeCode = parTransTypeCode;
    }

    public String getSubTransTypeCode() {
        return subTransTypeCode;
    }

    public void setSubTransTypeCode(String subTransTypeCode) {
        this.subTransTypeCode = subTransTypeCode;
    }

    public Integer getStgyTransType() {
        return stgyTransType;
    }

    public void setStgyTransType(Integer stgyTransType) {
        this.stgyTransType = stgyTransType;
    }

    public String getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(String orderChannel) {
        this.orderChannel = orderChannel;
    }

    public String getTransTypeCode() {
        return transTypeCode;
    }

    public void setTransTypeCode(String transTypeCode) {
        this.transTypeCode = transTypeCode;
    }

    public BigDecimal getWeightNo() {
        return weightNo;
    }

    public void setWeightNo(BigDecimal weightNo) {
        this.weightNo = weightNo;
    }

    public BigDecimal getVolumeNo() {
        return volumeNo;
    }

    public void setVolumeNo(BigDecimal volumeNo) {
        this.volumeNo = volumeNo;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getTakeDelyType() {
        return takeDelyType;
    }

    public void setTakeDelyType(String takeDelyType) {
        this.takeDelyType = takeDelyType;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    public Date getBillTime() {
        return billTime;
    }
    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

	public OrgPositionInfoTyEntity getOriginPositionInfo() {
		return originPositionInfo;
	}

	public void setOriginPositionInfo(OrgPositionInfoTyEntity originPositionInfo) {
		this.originPositionInfo = originPositionInfo;
	}

	public OrgPositionInfoTyEntity getDestPositionInfo() {
		return destPositionInfo;
	}

	public void setDestPositionInfo(OrgPositionInfoTyEntity destPositionInfo) {
		this.destPositionInfo = destPositionInfo;
	}

    public BigDecimal getOmsFreightDiscount() {
        return omsFreightDiscount;
    }

    public void setOmsFreightDiscount(BigDecimal omsFreightDiscount) {
        this.omsFreightDiscount = omsFreightDiscount;
    }
}
