package com.hoau.miser.module.sys.base.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.entity.IUser;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.sys.base.api.server.service.IRoleResourceService;
import com.hoau.miser.module.sys.base.api.server.service.IUserService;
import com.hoau.miser.module.sys.base.api.shared.domain.RoleEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.RoleResourceEntity;
import com.hoau.miser.module.sys.base.api.shared.exception.RoleException;
import com.hoau.miser.module.sys.base.server.cache.RoleCache;
import com.hoau.miser.module.sys.base.server.cache.UserCache;
import com.hoau.miser.module.sys.base.server.cache.UserOrgRoleResCache;
import com.hoau.miser.module.sys.base.server.dao.RoleResourceDao;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：高佳
 * @create：2015年6月13日 上午9:19:14
 * @description：角色权限service
 */
@Service
public class RoleResourceService implements IRoleResourceService {
	@Resource
	private IUserService userService;
	@Resource
	private RoleResourceDao roleResourceDao;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void updateRoleResource(String roleCode,
			Set<String> addResourceCodes, Set<String> deleteResourceCodes) {
		// 如果角色的编码为空，则直接返回
		if (StringUtil.isEmpty(roleCode)) {
			throw new RoleException(RoleException.ROLE_CODE_NULL);
		}
		String operUserCode = MiserUserContext.getCurrentInfo().getUserName();
		// 通过用户的角色编码获取 角色权限对象
		RoleResourceEntity roleResourceEntity = new RoleResourceEntity();
		roleResourceEntity.setRoleCode(roleCode);
		for (String addResourceCode : addResourceCodes) {
			// 判断保存时是否存在该权限,若该用户存在该权限的话
			RoleEntity role = (RoleEntity)CacheManager.getInstance()
					.getCache(RoleCache.ROLE_CACHE_UUID).get(roleCode);
			if (CollectionUtils.isEmpty(role.getResIds())||!role.getResIds().contains(addResourceCode)) {
				RoleResourceEntity addEntity = new RoleResourceEntity();
				addEntity.setId(UUIDUtil.getUUID());
				addEntity.setRoleCode(roleCode);
				addEntity.setVersionNo(UUIDUtil.getVersion());
				addEntity.setActive(MiserConstants.ACTIVE);
				addEntity.setResourceCode(addResourceCode);
				addEntity.setCreateUser(operUserCode);
				addEntity.setCreateDate(new Date());
				addEntity.setModifyDate(new Date());
				addEntity.setModifyUser(operUserCode);
				roleResourceDao.addRoleResource(addEntity);
			}
		}
		// 作废权限到数据库：
		for (String deleteResourceCode : deleteResourceCodes) {
			RoleResourceEntity deleteEntity = new RoleResourceEntity();
			deleteEntity.setRoleCode(roleCode);
			deleteEntity.setResourceCode(deleteResourceCode);
			deleteEntity.setVersionNo(UUIDUtil.getVersion());
			deleteEntity.setActive(MiserConstants.INACTIVE);
			deleteEntity.setModifyUser(operUserCode);
			deleteEntity.setModifyDate(new Date());
			roleResourceDao.deleteRoleResourceByRoleResource(deleteEntity);
		}
		refreshCache(roleCode);
	}

	@SuppressWarnings("unchecked")
	private void refreshCache(String roleCode) {
		// 失效角色缓存中的角色信息
		ICache<String, RoleEntity> roleCache = CacheManager.getInstance()
				.getCache(RoleCache.ROLE_CACHE_UUID);
		roleCache.invalid(roleCode);
		// 失效用户缓存中拥有该角色的用户
		ICache<String, IUser> userCache = CacheManager.getInstance().getCache(
				UserCache.USER_CACHE_UUID);
		List<String> userNames = userService.queryUserNameByRoleCode(roleCode);
		userCache.invalidMulti(userNames.toArray(new String[userNames.size()]));
		ICache<String, List<Set<String>>> userOrgRoleResCache = CacheManager
				.getInstance().getCache(
						UserOrgRoleResCache.USER_ORG_ROLE_RES_CACHE_UUID);
		List<String> userOrgCodes = userService
				.queryUserAndOrgCodesByRoleCodeForCache(roleCode);
		userOrgRoleResCache.invalidMulti(userOrgCodes
				.toArray(new String[userOrgCodes.size()]));
	}
}
