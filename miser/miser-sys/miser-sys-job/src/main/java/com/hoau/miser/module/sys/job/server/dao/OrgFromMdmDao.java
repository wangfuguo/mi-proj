package com.hoau.miser.module.sys.job.server.dao;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.sys.job.shared.domain.OrganDto;

/**
 * 
 * @Author 292078
 * @Date 2015年12月16日
 */
@Repository
public interface OrgFromMdmDao {
	

	/**
	 * 查询上最大时间截
	 * @return Long  
	 * @Author 292078
	 * @Time 2015年12月16日上午11:54:30
	 */
	public Long getLastUpdateTime();
	
	/**
	 * 同步MDM修改组织数据
	 * @param organDto void  
	 * @Author 292078
	 * @Time 2015年12月16日上午11:54:41
	 */
	void updateOrganMdm(OrganDto organDto);
	
	/**
	 * 同步MDM新增组织数据
	 * @param organDto void  
	 * @Author 292078
	 * @Time 2015年12月16日上午11:54:57
	 */
	void addOrganMdm(OrganDto organDto);
	
	/**
	 * 判断是否以存在部门数据
	 * @param code
	 * @return Long  
	 * @Author 292078
	 * @Time 2015年12月16日上午11:55:12
	 */
	Long selectCountOrganByCode(String code);
	

	
}
