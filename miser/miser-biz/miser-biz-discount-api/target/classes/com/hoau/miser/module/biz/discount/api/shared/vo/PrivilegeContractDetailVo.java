package com.hoau.miser.module.biz.discount.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractDetailEntity;

/**
 * 越发越惠客户合同明细 
 * ClassName: PrivilegeContractDetailVo
 * @author 付于令
 * @date 2016年01月13日
 * @version V1.0
 */
public class PrivilegeContractDetailVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -8981221895849089830L;
	private PrivilegeContractDetailEntity privilegeContractDetailEntity;
	/**
	 * 越发越惠客户合同集合
	 */
	private List<PrivilegeContractDetailEntity> privilegeContractDetailList;

	public PrivilegeContractDetailEntity getPrivilegeContractDetailEntity() {
		return privilegeContractDetailEntity;
	}

	public void setPrivilegeContractDetailEntity(
			PrivilegeContractDetailEntity privilegeContractDetailEntity) {
		this.privilegeContractDetailEntity = privilegeContractDetailEntity;
	}

	public List<PrivilegeContractDetailEntity> getPrivilegeContractDetailList() {
		return privilegeContractDetailList;
	}

	public void setPrivilegeContractDetailList(
			List<PrivilegeContractDetailEntity> privilegeContractDetailList) {
		this.privilegeContractDetailList = privilegeContractDetailList;
	}

}
