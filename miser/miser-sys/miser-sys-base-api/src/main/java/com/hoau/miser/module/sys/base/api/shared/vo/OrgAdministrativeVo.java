package com.hoau.miser.module.sys.base.api.shared.vo;

import java.io.Serializable;

import com.hoau.miser.module.sys.base.api.shared.domain.OrgAdministrativeInfoEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.PlatformEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.SalesDepartmentEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.TransferCenterEntity;

/**
 * @author：张贞献
 * @create：2015-7-16 下午5:37:21
 * @description：
 */
public class OrgAdministrativeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9081668583593546963L;

	/**
	 *
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 组织信息
	 */
	private OrgAdministrativeInfoEntity orgAdministrativeInfoEntity;
	
    /**
     * 平台
     */
    private PlatformEntity platformEntity;
    
    /**
     * 门店
     */
    private SalesDepartmentEntity salesDepartmentEntity;
    
    /**
     * 组织编码
     */
    private String orgCode;
    
    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
     *场站
     */
    private TransferCenterEntity transferCenterEntity;

	public OrgAdministrativeInfoEntity getOrgAdministrativeInfoEntity() {
		return orgAdministrativeInfoEntity;
	}

	public void setOrgAdministrativeInfoEntity(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		this.orgAdministrativeInfoEntity = orgAdministrativeInfoEntity;
	}

	public PlatformEntity getPlatformEntity() {
		return platformEntity;
	}

	public void setPlatformEntity(PlatformEntity platformEntity) {
		this.platformEntity = platformEntity;
	}

	public SalesDepartmentEntity getSalesDepartmentEntity() {
		return salesDepartmentEntity;
	}

	public void setSalesDepartmentEntity(SalesDepartmentEntity salesDepartmentEntity) {
		this.salesDepartmentEntity = salesDepartmentEntity;
	}

	public TransferCenterEntity getTransferCenterEntity() {
		return transferCenterEntity;
	}

	public void setTransferCenterEntity(TransferCenterEntity transferCenterEntity) {
		this.transferCenterEntity = transferCenterEntity;
	}
    
    
}
