package com.hoau.miser.module.sys.base.server.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.sys.base.api.server.service.IUserOrgDataAutService;
import com.hoau.miser.module.sys.base.api.shared.domain.UserOrgDataAutEntity;
import com.hoau.miser.module.sys.base.api.shared.exception.UserOrgDateAutException;
import com.hoau.miser.module.sys.base.server.dao.UserOrgDataAutDao;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：涂婷婷
 * @create：2015年7月21日 上午8:36:37
 * @description：
 */
@Service
public class UserOrgDataAutService implements IUserOrgDataAutService {

	@Resource
	private UserOrgDataAutDao userOrgDataAutDao;

	@Override
	public List<UserOrgDataAutEntity> queryUserOrgDataAut(
			UserOrgDataAutEntity userOrgDataAutEntity, int limit, int start) {
		userOrgDataAutEntity.setActive(MiserConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, limit);
		return userOrgDataAutDao.queryUserOrgDataAut(userOrgDataAutEntity,
				rowBounds);
	}

	@Override
	public Long queryUserOrgDataAutCount(
			UserOrgDataAutEntity userOrgDataAutEntity) {
		userOrgDataAutEntity.setActive(MiserConstants.ACTIVE);
		return userOrgDataAutDao.queryUserOrgDataAutCount(userOrgDataAutEntity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addUserOrgDataAut(UserOrgDataAutEntity userOrgDataAutEntity) {
		if (StringUtil.isEmpty(userOrgDataAutEntity.getUserName())) {
			throw new UserOrgDateAutException(
					UserOrgDateAutException.USERNAME_NULL);
		}
		if (StringUtil.isEmpty(userOrgDataAutEntity.getOrgCode())) {
			throw new UserOrgDateAutException(
					UserOrgDateAutException.ORGCODE_NULL);
		}
		if (!CollectionUtils.isEmpty(this
				.queryUserOrgCountByUserName(userOrgDataAutEntity))) {
			throw new UserOrgDateAutException(
					UserOrgDateAutException.USERORG_EXISTL);
			}		
		Date date = new Date();
		userOrgDataAutEntity.setVersionNo(UUIDUtil.getVersion());
		userOrgDataAutEntity.setId(UUIDUtil.getUUID());
		userOrgDataAutEntity.setActive(MiserConstants.ACTIVE);
		userOrgDataAutEntity.setCreateDate(date);
		userOrgDataAutEntity.setCreateUser(MiserUserContext.getCurrentUser()
				.getUserName());
		userOrgDataAutEntity.setModifyDate(date);
		userOrgDataAutEntity.setModifyUser(MiserUserContext.getCurrentUser()
				.getUserName());
		userOrgDataAutDao.addUserOrgDataAut(userOrgDataAutEntity);

	}

	@Override
	@Transactional
	public void deleteUserOrgDataAut(
			List<UserOrgDataAutEntity> userOrgDataAutList) {
		Date date = new Date();
		for (UserOrgDataAutEntity user : userOrgDataAutList)
		{
			user.setActive(MiserConstants.NO);
			user.setVersionNo(UUIDUtil.getVersion());
			user.setModifyDate(date);
			user.setModifyUser(MiserUserContext.getCurrentUser()
					.getUserName());
			userOrgDataAutDao.deleteUserOrgDataAut(user);
		}
	}

	@Override
	public List<UserOrgDataAutEntity> queryUserOrgCountByUserName(
			UserOrgDataAutEntity userOrgDataAutEntity) {
		userOrgDataAutEntity.setActive(MiserConstants.ACTIVE);
		return userOrgDataAutDao
				.queryUserOrgCountByUserName(userOrgDataAutEntity);
	} 

	@Override
	public List<UserOrgDataAutEntity> queryUserOrgListByUserName(
			String userName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", userName);
		map.put("active", MiserConstants.ACTIVE);
		return userOrgDataAutDao.queryUserOrgListByUserName(map);
	}

	@Override
	public void deleteUserOrgDataAutByUserName(String userName) {
		userOrgDataAutDao.deleteUserOrgDataAutByUserName(userName,MiserUserContext.getCurrentUser().getUserName(),new Date());
	}

}
