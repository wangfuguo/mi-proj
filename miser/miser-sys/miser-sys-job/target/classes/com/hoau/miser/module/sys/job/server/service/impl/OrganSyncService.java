package com.hoau.miser.module.sys.job.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.sys.job.server.service.IOrgFromMdmService;
import com.hoau.miser.module.sys.job.server.service.IOrganSyncService;
import com.hoau.miser.module.sys.job.server.service.ISyncOrgDataService;

/**
 * 
 * @Description: 组织同步
 * @Author 292078
 * @Date 2015年12月16日
 */
@Service
public class OrganSyncService implements IOrganSyncService {
	@Resource
	private IOrgFromMdmService orgFromMdmService;
	@Resource
	private ISyncOrgDataService syncOrgDataService;
	@Override
	public void organeSync() {
		orgFromMdmService.organeSync();
		syncOrgDataService.syncOrgData();
	}
}
