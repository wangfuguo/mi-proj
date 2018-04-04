package com.hoau.miser.module.sys.job.server.service;



/**
 * 
 *
 * @author 涂婷婷
 * @date 2015年9月10日
 */
public interface IOuterBranchSyncService {
	/**
	 * 同步外部代理网点信息临时表
	 * 
	 * @author 涂婷婷
	 * @date 2015年9月10日
	 * @update
	 */
	void outerBranchMdmSync();
	/**
	 * 同步外部代理网点信息表
	 * 
	 * @author 涂婷婷
	 * @date 2015年9月10日
	 * @update
	 */
	void outerBranchSync();
}
