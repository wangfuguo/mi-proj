package com.hoau.miser.module.sys.job.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.webservice.components.rest.define.RestErrorCodeConstants;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;
import com.hoau.miser.module.common.shared.define.JobElementType;
import com.hoau.miser.module.common.shared.util.RestTemplateClient;
import com.hoau.miser.module.sys.job.server.dao.EmployeeSyncDao;
import com.hoau.miser.module.sys.job.server.service.IEmployeeSyncService;
import com.hoau.miser.module.sys.job.shared.constant.URLConstants;
import com.hoau.miser.module.sys.job.shared.domain.EmployeeEntity;
import com.hoau.miser.module.util.UUIDUtil;

/**
 * 
 * @author 涂婷婷
 * @date 2015年6月4日
 */
@Service
public class EmployeeSyncService implements IEmployeeSyncService {

	private static final Logger log = LoggerFactory
			.getLogger(EmployeeSyncService.class);
	@Resource
	private EmployeeSyncDao employeeSyncDao;
	@Resource
	private RestTemplateClient restTemplateClient;

	@Override
	@Transactional
	public void employeeSync() {
		Long versionNo = employeeSyncDao.getLastVersionNo();
		if(versionNo.equals(new Long("1440079202123")))versionNo=null;
		ResponseBaseEntity<List<EmployeeEntity>> result;
		if (versionNo == null) {
			// 全部更新
			result = restTemplateClient.getForObject(URLConstants.MDM_URL
					+ "employee/v1/all", new TypeReference<ResponseBaseEntity<List<EmployeeEntity>>>() {
					});
		} else {
			result = restTemplateClient.getForObject(URLConstants.MDM_URL
					+ "employee/v1/version/{versionNo}", new TypeReference<ResponseBaseEntity<List<EmployeeEntity>>>() {
					},versionNo);
		}
		List<EmployeeEntity> employees = result.getResult();
		if (RestErrorCodeConstants.STATUS_SUCCESS.equals(result.getErrorCode())) {
			log.info("提取数据成功！");
			for (EmployeeEntity employeeEntity : employees) {
				if (employeeSyncDao.queryEmployeeByCode(employeeEntity
						.getEmployeeCode()) == null) {
					employeeSyncDao.addEmployee(employeeEntity);
				} else {
					employeeSyncDao.updateEmployee(employeeEntity);
				}
			}
		} else {
			log.error("同步人员信息异常：" + result.getErrorMessage());
			throw new BusinessException("调用接口失败" + result.getErrorMessage());
		}
	}

	@Override
	@Transactional
	public void updateInvalidUser() {
		List<String> userCodes = employeeSyncDao.queryInvalidUser();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("modifyDate", new Date());
		map.put("versionNo", UUIDUtil.getVersion());
		map.put("modifyUser", JobElementType.JOB_SYS_MDM_DATA_SYNC.getCode());
		for (String userCode : userCodes) {
			map.put("userCode", userCode);
			employeeSyncDao.updateInvalidUser(map);
		}
	}
	
}
