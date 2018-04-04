package com.hoau.miser.module.sys.job.server.service.impl;

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
import com.hoau.miser.module.sys.job.server.dao.DistrictSyncDao;
import com.hoau.miser.module.sys.job.server.service.IDistrictSyncService;
import com.hoau.miser.module.sys.job.shared.constant.URLConstants;
import com.hoau.miser.module.sys.job.shared.domain.DistrictEntity;




/**
 * @author：何巍
 * @create：2015年6月3日 下午1:43:30
 * @description：
 */
@Service
public class DistrictSyncService implements IDistrictSyncService{
	private static final Logger log = LoggerFactory.getLogger(DistrictSyncService.class);
	@Resource
	private DistrictSyncDao districtSyncDao;
	@Resource
	private RestTemplateClient restTemplateClient;
	@Override
	@Transactional
	public void districtSync(){
		log.info("同步行政区域信息开始");
		Long versionNo = districtSyncDao.getLastVersionNo();
		if(versionNo == null){
			versionNo = 0l;
		}
		ResponseBaseEntity<List<DistrictEntity>> result = restTemplateClient.getForObject(URLConstants.MDM_URL+"district/v1/version/{versionNo}", new TypeReference<ResponseBaseEntity<List<DistrictEntity>>>() {
		}, versionNo);
		if(RestErrorCodeConstants.STATUS_SUCCESS.equals(result.getErrorCode())){
			List<DistrictEntity> districts = result.getResult();
			if(!CollectionUtils.isEmpty(districts)){
				for (DistrictEntity district : districts) {
					//Modified by Chenyl @20160329 判断是否存在错误的BUG修改
					if(districtSyncDao.IsExistDistrictnMDMById(district.getId())>0){//存在更新
						districtSyncDao.updateDistrictMDM(district);
					}else{
						districtSyncDao.addDistrictMDM(district);//不存在插入
					}
					
				}
			}
			log.info("同步行政区域信息结束");
		}else{
			log.error("同步行政区域信息异常："+result.getErrorMessage());
			throw new BusinessException("调用接口失败"+result.getErrorMessage());
		}
		//将信息更新到miser的District中
		districtSyncDao.updateDistrictMDM2District();
	}


}
