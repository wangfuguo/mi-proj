package com.hoau.miser.module.biz.discount.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractEntity;

/**
 * 越发越惠客户合同
 * ClassName: PrivilegeContractVo 
 * @author 付于令
 * @date 2016年01月12日
 * @version V1.0
 */
public class PrivilegeContractVo implements Serializable {
	private static final long serialVersionUID = -4456286024126547645L;
	private PrivilegeContractEntity privilegeContractEntity;
	/**
	 *越发越惠客户合同集合
	 */
	private List<PrivilegeContractEntity> privilegeContractList;
	
	private String filePath;

	public List<PrivilegeContractEntity> getPrivilegeContractList() {
		return privilegeContractList;
	}

	public void setPrivilegeContractList(List<PrivilegeContractEntity> privilegeContractList) {
		this.privilegeContractList = privilegeContractList;
	}

	public PrivilegeContractEntity getPrivilegeContractEntity() {
		return privilegeContractEntity;
	}

	public void setPrivilegeContractEntity(PrivilegeContractEntity privilegeContractEntity) {
		this.privilegeContractEntity = privilegeContractEntity;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
