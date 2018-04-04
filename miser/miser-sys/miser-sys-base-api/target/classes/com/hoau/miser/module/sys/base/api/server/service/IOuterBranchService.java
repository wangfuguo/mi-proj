package com.hoau.miser.module.sys.base.api.server.service;

import java.util.List;

import com.hoau.miser.module.sys.base.api.shared.domain.OuterBranchEntity;

/**
 * 
 * @Description: 偏线公司
 * @Author 292078
 * @Time 2015年12月11日上午10:09:51
 */
public interface IOuterBranchService {
	
	/**
	 * 
	 * @Description: 查询偏线公司信息
	 * @param outerBranchEntity
	 * @param limit
	 * @param start
	 * @return List<OuterBranchEntity>  
	 * @Author 292078
	 * @Time 2015年12月11日上午10:08:13
	 */
	public List<OuterBranchEntity> queryOuterBranchList(OuterBranchEntity outerBranchEntity, int limit, int start);
	/**
	 * 
	 * @Description: 查询偏线公司信息总数
	 * @param outerBranchEntity
	 * @return Long  
	 * @Author 292078
	 * @Time 2015年12月11日上午10:12:08
	 */
	public Long queryOuterBranchCount(OuterBranchEntity outerBranchEntity);
}
