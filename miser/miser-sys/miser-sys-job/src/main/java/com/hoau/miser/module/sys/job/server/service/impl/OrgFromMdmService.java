package com.hoau.miser.module.sys.job.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.webservice.components.rest.define.RestErrorCodeConstants;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;
import com.hoau.miser.module.common.shared.util.RestTemplateClient;
import com.hoau.miser.module.sys.job.server.dao.OrgFromMdmDao;
import com.hoau.miser.module.sys.job.server.service.IOrgFromMdmService;
import com.hoau.miser.module.sys.job.shared.constant.URLConstants;
import com.hoau.miser.module.sys.job.shared.domain.OrganDto;


/**
 * 从MDM同步组织数据
 * @Author 292078
 * @Date 2015年12月16日
 */
@Service
public class OrgFromMdmService implements IOrgFromMdmService {
	@Resource
	private RestTemplateClient restTemplateClient;

	@Resource
	private OrgFromMdmDao orgFromMdmDao;
	private static final Logger log = LoggerFactory
			.getLogger(OrgFromMdmService.class);

	@Transactional
	public void organeSync() {
		Long versionNo = orgFromMdmDao.getLastUpdateTime();
		ResponseBaseEntity<List<OrganDto>> result;
		// 版本号为空则全部更新
		if (versionNo == null) {
			result = restTemplateClient.getForObject(URLConstants.MDM_URL
					+ "dept/v1/all", new TypeReference<ResponseBaseEntity<List<OrganDto>>>() {
					});
		} else {
			result = restTemplateClient.getForObject(URLConstants.MDM_URL
					+ "dept/v1/version/" + versionNo, new TypeReference<ResponseBaseEntity<List<OrganDto>>>() {
					});
		}
		if (RestErrorCodeConstants.STATUS_SUCCESS.equals(result.getErrorCode())) {
			log.info("组织信息同步开始！");
			List<OrganDto> organs = result.getResult();
			log.info("转换为实体类成功！");
			for (OrganDto org : organs) {
				// 更新组织信息表数据
				if (orgFromMdmDao.selectCountOrganByCode(org.getDeptCode()) == 0) {
					orgFromMdmDao.addOrganMdm(org);
				} else {
					orgFromMdmDao.updateOrganMdm(org);
				}
			}
			log.info("组织更新成功 ！");
		
		} else {
			log.error("同步组织信息异常：" + result.getErrorMessage());
			throw new BusinessException("调用接口失败");
		}
	}

}
