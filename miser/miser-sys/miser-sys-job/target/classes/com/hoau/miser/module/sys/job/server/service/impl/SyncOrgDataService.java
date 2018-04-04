package com.hoau.miser.module.sys.job.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.miser.module.sys.job.server.dao.OrgSyncDao;
import com.hoau.miser.module.sys.job.server.service.ISyncOrgDataService;
import com.hoau.miser.module.util.UUIDUtil;

/**
 * @author：张贞献
 * @create：2015-7-15 下午5:53:19
 * @description：
 */
@Service
public class SyncOrgDataService implements ISyncOrgDataService {
	
	@Resource
	private OrgSyncDao orgSyncDao;
	@Override
	@Transactional
	public void syncOrgData() {
		long versionNo = UUIDUtil.getVersion();
		Date date = new Date();
		//更新组织主表T_BSE_ORG
		orgSyncDao.updateMergeOrg(versionNo,date);
		
		
		//查询当前区域是否有网点的情况
 
		//针对于存在网点的更新
		orgSyncDao.updateBseDistrictCorp();
		
		//针对于不存在网点的更新
		orgSyncDao.updateBseDistrictWithOutCorp();
		
		
		// 更新场站表T_BSE_ORG
		orgSyncDao.updateMergeTranferCenter(versionNo, date);
		// 删除场站无效数据
		orgSyncDao.deleteInvalidTranferCenter(versionNo, date);
		// 更新平台表T_BSE_PLATFORM
		orgSyncDao.updateMergePlatform(versionNo, date);
		// 删除平台表无效数据
		orgSyncDao.deleteInvalidPlatform(versionNo, date);
		// 更新门店表T_BSE_SALES_DEPARTMENT
		orgSyncDao.updateMergeSalesDepartment(versionNo, date);
		// 删除门店表无效数据
		orgSyncDao.deleteInvalidSalesDepartment(versionNo, date);
	}

}
