package com.hoau.miser.module.sys.job.server.job;

import com.hoau.miser.module.common.server.service.impl.AbstractComonJob;
import com.hoau.miser.module.common.shared.define.JobElementType;
import com.hoau.miser.module.sys.job.server.service.IDistrictSyncService;
import com.hoau.miser.module.sys.job.server.service.IEmployeeSyncService;
import com.hoau.miser.module.sys.job.server.service.IOrganSyncService;
import com.hoau.miser.module.sys.job.server.service.IOuterBranchSyncService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * MDMDataSyncJob
 * MDM基础数据同步job，包含行政区县、人事信息、组织机构、外发部门
 * @date 2016年3月30日15:36:54
 * @author yulin.chen@newhoau.com.cn
 */
public class MDMDataSyncJob extends AbstractComonJob{

	@Override
	protected void jobExecute(JobExecutionContext context)
			throws JobExecutionException {
		//行政区县同步
		IDistrictSyncService districtService=getBean("districtSyncService", IDistrictSyncService.class);
		districtService.districtSync();

		//人员信息同步
		IEmployeeSyncService employeeSyncService = getBean("employeeSyncService", IEmployeeSyncService.class);
		//同步员工信息数据
		employeeSyncService.employeeSync();
		//作废已离职员工用户
		employeeSyncService.updateInvalidUser();

		//组织信息同步
		IOrganSyncService organSyncService = getBean("organSyncService", IOrganSyncService.class);
		organSyncService.organeSync();

		//外发偏线部门同步
		IOuterBranchSyncService outerBranchService=getBean("outerBranchSyncService", IOuterBranchSyncService.class);
		outerBranchService.outerBranchMdmSync();
		outerBranchService.outerBranchSync();
	}

	@Override
	protected JobElementType getJobType() throws JobExecutionException {
		return JobElementType.JOB_SYS_MDM_DATA_SYNC;
	}

}
