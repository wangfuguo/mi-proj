package com.hoau.miser.module.sys.base.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.sys.base.api.server.service.ICommonOrgService;
import com.hoau.miser.module.sys.base.api.shared.domain.CommonOrgEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.OrgSearchConditionEntity;
import com.hoau.miser.module.sys.base.server.dao.CommonOrgDao;

/**
 * @author：高佳
 * @create：2015年6月30日 下午2:00:59
 * @description：
 */
@Service
public class CommonOrgService implements ICommonOrgService{
	@Resource
	private CommonOrgDao commonOrgDao;
	
	@Override
	public List<CommonOrgEntity> queryOrgByParam(
			OrgSearchConditionEntity orgSearchConditionEntity) {
		return commonOrgDao.queryOrgByParam(orgSearchConditionEntity,new RowBounds(orgSearchConditionEntity.getStart(),orgSearchConditionEntity.getLimit()));
	}

	@Override
	public Long countOrgByParam(
			OrgSearchConditionEntity orgSearchConditionEntity) {
		return commonOrgDao.countOrgByParam(orgSearchConditionEntity);
	}
	
	/**
	 * 根据传递的 组织编码 递归的查询其父层的信息
	 * @param code 
	 * @return
	 * @author dengyin
	 * @date 2015年7月15日
	 * @update 
	 */
	public List<CommonOrgEntity> queryCommonOrgListFetchByCode(String code){
		return commonOrgDao.queryCommonOrgListFetchByCode(code);
	}

	@Override
	public CommonOrgEntity queryByName(String orgName) {
		// TODO Auto-generated method stub
		return commonOrgDao.queryByName(orgName);
	}

}
