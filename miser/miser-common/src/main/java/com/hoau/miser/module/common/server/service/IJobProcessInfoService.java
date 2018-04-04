package com.hoau.miser.module.common.server.service;

import java.util.List;

import com.hoau.miser.module.common.shared.domain.JobProcessEntity;

public interface IJobProcessInfoService {
	
	/**
	 * 保存job运行信息
	 * @param jobProcessEntity
	 * @author 张贞献
	 * @date 2015年7月14日
	 * @update 
	 */
    void processJobInfo(JobProcessEntity  jobProcess);
    
	/**
	 * 记录job运行开始日志
	 * @param jobProcessEntity
	 * @author 张贞献
	 * @date 2015年7月14日
	 * @update 
	 */
	void addJobInfoBeginLog(JobProcessEntity jobProcessEntity); 
	
	/**
	 * 记录job运行结束日志
	 * @param jobProcessEntity
	 * @author 张贞献
	 * @date 2015年7月14日
	 * @update 
	 */
	void addJobInfoEndLog(JobProcessEntity jobProcessEntity); 
	
	/**
	 * 分页查询job运行信息
	 * @param jobProcessEntity
	 * @author 张贞献
	 * @date 2015年7月14日
	 * @update 
	 */
	List<JobProcessEntity> queryJobProcessList(JobProcessEntity jobProcessEntity,
			int limit, int start); 
	
	/**
	 * 获取分页查询总条数
	 * @param jobProcessEntity
	 * @author 张贞献
	 * @date 2015年7月14日
	 * @update 
	 */
	Long queryTotalCount(JobProcessEntity jobProcessEntity); 
	
	/**
	 * 根据code查询job信息
	 * @param code
	 * @return
	 * @author 高佳
	 * @date 2015年11月18日
	 * @update 
	 */
	JobProcessEntity queryJobProcessByCode(String code);
}
