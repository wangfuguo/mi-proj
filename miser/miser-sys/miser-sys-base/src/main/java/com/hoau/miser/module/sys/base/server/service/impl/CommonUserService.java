package com.hoau.miser.module.sys.base.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.sys.base.api.shared.domain.CommonUserEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.UserSearchConditionEntity;
import com.hoau.miser.module.sys.base.server.dao.CommonUserDao;

/**
 * @author：李旭锋
 * @create：2015年7月20日 下午7:06:30
 * @description：
 */
@Service
public class CommonUserService implements ICommonUserService {

	@Resource
	private CommonUserDao commonUserDao;
	
	@Override
	public List<CommonUserEntity> queryUserByParam(UserSearchConditionEntity userSearchConditionEntity){
		RowBounds row = new RowBounds(userSearchConditionEntity.getStart(),userSearchConditionEntity.getLimit());
		return commonUserDao.queryUserByParam(userSearchConditionEntity, row);
		
	}

	@Override
	public Long queryCountUserByParam(UserSearchConditionEntity userSearchConditionEntity) {
		return commonUserDao.countUserByParam(userSearchConditionEntity);
	}
}
