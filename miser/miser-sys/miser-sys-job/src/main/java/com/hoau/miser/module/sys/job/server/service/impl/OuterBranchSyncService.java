package com.hoau.miser.module.sys.job.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.webservice.components.rest.define.RestErrorCodeConstants;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;
import com.hoau.miser.module.common.shared.util.RestTemplateClient;
import com.hoau.miser.module.sys.job.server.dao.OuterBranchSyncDao;
import com.hoau.miser.module.sys.job.server.service.IOuterBranchSyncService;
import com.hoau.miser.module.sys.job.shared.constant.URLConstants;
import com.hoau.miser.module.sys.job.shared.domain.OutBranchEntity;
import com.hoau.miser.module.util.UUIDUtil;

/**
 * @author：涂婷婷
 * @create：2015年9月10日 下午8:31:29
 * @description：
 */
@Service
public class OuterBranchSyncService implements IOuterBranchSyncService {
	private static final Logger log = LoggerFactory.getLogger(OuterBranchSyncService.class);
	@Resource
	private OuterBranchSyncDao outerBranchSyncDao;
	@Resource
	private RestTemplateClient restTemplateClient;
	
	@Override
	@Transactional
	public void outerBranchMdmSync() {
		Long versionNo = outerBranchSyncDao.getLastUpdateTime();
		if(versionNo == null){
			versionNo = 0l;
		}
		ResponseBaseEntity<List<OutBranchEntity>> result = restTemplateClient.getForObject(URLConstants.MDM_URL+"outbranch/v1/version/{versionNo}", new TypeReference<ResponseBaseEntity<List<OutBranchEntity>>>() {
		}, versionNo);
		if(RestErrorCodeConstants.STATUS_SUCCESS.equals(result.getErrorCode())){
			List<OutBranchEntity> outBEntity = result.getResult();
			if(!CollectionUtils.isEmpty(outBEntity)){
				for (OutBranchEntity entity : outBEntity) {
					if(outerBranchSyncDao.queryMdmCountByCode(entity.getCode())>0){						
						outerBranchSyncDao.updateOuterBranchMdm(entity);					  
					}else{
						outerBranchSyncDao.addOuterBranchMdm(entity);
					}	
				}
				
			}
			log.info("同步外部代理网点信息结束");
		}else{
			log.error("同步外部代理网点信息异常："+result.getErrorMessage());
			throw new BusinessException("调用接口失败"+result.getErrorMessage());
		}
	
	}
	
	@Override
	@Transactional
	public void outerBranchSync() {
		outerBranchSyncDao.updateOuterBranch(UUIDUtil.getVersion(), new Date());
	}

}
