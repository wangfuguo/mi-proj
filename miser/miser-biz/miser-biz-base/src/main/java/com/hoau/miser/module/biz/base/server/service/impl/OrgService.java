package com.hoau.miser.module.biz.base.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.biz.base.api.server.service.IOrgService;
import com.hoau.miser.module.biz.base.api.shared.domain.OrgEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.OrgInfoEntity;
import com.hoau.miser.module.biz.base.server.dao.OrgDao;
/**
 * 组织查询实现类
 * ClassName: OrgService 
 * @author 286330付于令
 * @date 2016年1月18日
 * @version V1.0
 */
@Service
public class OrgService implements IOrgService {
	
	@Resource
	private OrgDao OrgDao;

	@Override
	public OrgEntity queryCurrentUserOrg(OrgEntity entity) {
		OrgEntity orgEntity = OrgDao.queryOrgByUserName(entity);
		if(orgEntity == null) {
			return null;
		}
		// 门店则直接返回，非门店则进入if语句进一步判断
		String isSalesDepartment = orgEntity.getIsSalesDepartment();
		if(!"Y".equals(isSalesDepartment)) {
			OrgEntity newOrgEntity = new OrgEntity();
			// 路区
			String isRoadArea = orgEntity.getIsRoadArea();
			if("Y".equals(isRoadArea)) {
				newOrgEntity.setRoadAreaCode(orgEntity.getSalesDepartmentCode());
				newOrgEntity.setRoadAreaName(orgEntity.getSalesDepartmentName());
				newOrgEntity.setBigRegionCode(orgEntity.getRoadAreaCode());
				newOrgEntity.setBigRegionName(orgEntity.getRoadAreaName());
				newOrgEntity.setDivisionCode(orgEntity.getBigRegionCode());
				newOrgEntity.setDivisionName(orgEntity.getBigRegionName());
				return newOrgEntity;
			}
			// 大区
			String isBigRegion = orgEntity.getIsBigRegion();
			if("Y".equals(isBigRegion)) {
				newOrgEntity.setBigRegionCode(orgEntity.getSalesDepartmentCode());
				newOrgEntity.setBigRegionName(orgEntity.getSalesDepartmentName());
				newOrgEntity.setDivisionCode(orgEntity.getRoadAreaCode());
				newOrgEntity.setDivisionName(orgEntity.getRoadAreaName());
				return newOrgEntity;
			}
			//事业部
			String isDivision = orgEntity.getIsDivision();
			if("Y".equals(isDivision)) {
				newOrgEntity.setDivisionCode(orgEntity.getSalesDepartmentCode());
				newOrgEntity.setDivisionName(orgEntity.getSalesDepartmentName());
				return newOrgEntity;
			}
		}
		return orgEntity;
	}
	
	
	/**
	 * add by dengyin 2016-5-3 11:05:31 价格时效查询需求 添加查询组织机构完整信息
	 * @param entity
	 * @return
	 */
	public OrgInfoEntity queryOrgaInfoByEntity(OrgInfoEntity entity){
		return OrgDao.queryOrgaInfoByEntity(entity);
	}

}
