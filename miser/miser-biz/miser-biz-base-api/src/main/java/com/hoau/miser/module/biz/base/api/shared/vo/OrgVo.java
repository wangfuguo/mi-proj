package com.hoau.miser.module.biz.base.api.shared.vo;

import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.OrgEntity;

/**
 * 组织查询
 * ClassName: OrgVo 
 * @author 286330付于令
 * @date 2016年1月18日
 * @version V1.0
 */
public class OrgVo {
	
	private OrgEntity orgEntity;
	
	private List<OrgEntity> orgEntityList;

	/**
	 * @return the orgEntity
	 */
	public OrgEntity getOrgEntity() {
		return orgEntity;
	}

	/**
	 * @param orgEntity the orgEntity to set
	 */
	public void setOrgEntity(OrgEntity orgEntity) {
		this.orgEntity = orgEntity;
	}

	/**
	 * @return the orgEntityList
	 */
	public List<OrgEntity> getOrgEntityList() {
		return orgEntityList;
	}

	/**
	 * @param orgEntityList the orgEntityList to set
	 */
	public void setOrgEntityList(List<OrgEntity> orgEntityList) {
		this.orgEntityList = orgEntityList;
	}

}
