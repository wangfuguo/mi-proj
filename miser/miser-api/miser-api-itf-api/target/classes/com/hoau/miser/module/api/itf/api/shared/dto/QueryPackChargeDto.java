package com.hoau.miser.module.api.itf.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.WaybillCalculateBaseEntity;


/**
 * 包装费计算查询参数
 *
 * @author 蒋落琛
 * @date 2016-6-15下午7:33:22
 */
public class QueryPackChargeDto extends WaybillCalculateBaseEntity implements Serializable{

	private static final long serialVersionUID = 7044909846211326924L;

	/**
	 * 服务项目编号
	 */
	private String itemCode;

	/**
	 * 出发公司编号
	 */
	private String startOrgCode;
	
	/**
	 * 产品类型
	 */
	private String productType;

	/**
	 * 子产品类型
	 */
	private String subProductType;
	
	/**
	 * 数量或体积
	 */
	private BigDecimal num;
	
	/**
     * 是否历史
     */
    private boolean isHistory;
	
	/**
	 * 开单时间
	 */
    private Date billTime;
    
    /**
	 * 是否内部带货
	 */
	private boolean isInternalBelt;
	
	/**
     * 起运地公司省市区信息
     */
    private OrgPositionInfoTyEntity originPositionInfo;
    
    /**
     * 目的地公司省市区信息
     */
    private OrgPositionInfoTyEntity destPositionInfo;

	public String getStartOrgCode() {
		return startOrgCode;
	}

	public void setStartOrgCode(String startOrgCode) {
		this.startOrgCode = startOrgCode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public boolean isHistory() {
		return isHistory;
	}

	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public boolean isInternalBelt() {
		return isInternalBelt;
	}

	public void setInternalBelt(boolean isInternalBelt) {
		this.isInternalBelt = isInternalBelt;
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

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
}
