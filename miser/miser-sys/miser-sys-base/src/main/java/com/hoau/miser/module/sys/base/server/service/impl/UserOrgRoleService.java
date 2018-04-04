package com.hoau.miser.module.sys.base.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;








import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.sys.base.api.server.service.IUserOrgRoleService;
import com.hoau.miser.module.sys.base.api.shared.domain.UserOrgRoleEntity;
import com.hoau.miser.module.sys.base.server.dao.UserOrgRoleDao;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：涂婷婷
 * @create：2015年7月16日 上午9:24:54
 * @description：用户部门角色service
 */
@Service
public class UserOrgRoleService implements IUserOrgRoleService {

	@Resource
	private UserOrgRoleDao userOrgRoleDao;

	/**
	 * 删除用户部门全部角色信息
	 * 
	 * @param userName
	 * @param orgCode
	 * @author 高佳
	 * @date 2015年7月21日
	 * @update
	 */
	@Override
	@Transactional
	public void deleteUserAllOrgRole(String userName, String orgCode) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("orgCode", orgCode);
		map.put("active", MiserConstants.INACTIVE);
		map.put("versionNo", UUIDUtil.getVersion());
		map.put("modifyUser", MiserUserContext.getCurrentUser().getUserName());
		map.put("modifyDate", new Date());
		map.put("conditionActive", MiserConstants.ACTIVE);
		userOrgRoleDao.deleteUserAllOrgRole(map);
	}

	/**
	 * 更新用户组织角色信息
	 * 
	 * @param userName
	 *            用户名
	 * @param orgCode
	 *            部门编码
	 * @param roleCodes
	 *            角色编码
	 * @author 高佳
	 * @date 2015年7月21日
	 * @update
	 */
	@Override
	@Transactional
	public void updateUserOrgRole(String userName, String orgCode,
			Set<String> roleCodes) {
		if (StringUtil.isBlank(userName) || StringUtil.isBlank(orgCode)) {
			return;
		}
		if (CollectionUtils.isEmpty(roleCodes)) {
			// 角色编码为空则删除用户部门角色信息
			deleteUserAllOrgRole(userName, orgCode);
			return;
		}
		// 查询用户部门已有角色信息
		Set<String> roleExistSet = queryRoleCodeByUserOrg(userName, orgCode);
		Set<String> addRoleCodes = new HashSet<String>();
		Set<String> deleteRoleCodes = new HashSet<String>();
		// 获得新增角色信息
		for (String roleCode : roleCodes) {
			if (!roleExistSet.contains(roleCode)) {
				addRoleCodes.add(roleCode);
			}
		}
		// 获得删除角色信息
		for (String roleExist : roleExistSet) {
			if (!roleCodes.contains(roleExist)) {
				deleteRoleCodes.add(roleExist);
			}
		}
		// 新增角色信息
		for (String s : addRoleCodes) {
			addUserOrgRole(userName, orgCode, s);
		}
		// 删除角色信息
		for (String s : deleteRoleCodes) {
			deleteUserOrgRole(userName, orgCode, s);
		}

	}

	/**
	 * 根据用户名和组织查询角色编码
	 * 
	 * @param userName
	 *            用户名
	 * @param orgCode
	 *            组织编码
	 * @return
	 * @author 高佳
	 * @date 2015年7月21日
	 * @update
	 */
	private Set<String> queryRoleCodeByUserOrg(String userName, String orgCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("orgCode", orgCode);
		map.put("active", MiserConstants.ACTIVE);
		return userOrgRoleDao.queryRoleCodeByUserOrg(map);
	}
	@Transactional
	private void addUserOrgRole(String userName, String orgCode, String roleCode) {
		UserOrgRoleEntity entity = new UserOrgRoleEntity();
		entity.setId(UUIDUtil.getUUID());
		entity.setOrgCode(orgCode);
		entity.setRoleCode(roleCode);
		entity.setUserName(userName);
		entity.setActive(MiserConstants.ACTIVE);
		entity.setVersionNo(UUIDUtil.getVersion());
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
		entity.setCreateUser(MiserUserContext.getCurrentUser().getUserName());
		entity.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
		userOrgRoleDao.addUserOrgRole(entity);
	}
	@Transactional
	private void deleteUserOrgRole(String userName, String orgCode,
			String roleCode) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("orgCode", orgCode);
		map.put("roleCode", roleCode);
		map.put("active", MiserConstants.INACTIVE);
		map.put("versionNo", UUIDUtil.getVersion());
		map.put("modifyUser", MiserUserContext.getCurrentUser().getUserName());
		map.put("modifyDate", new Date());
		map.put("conditionActive", MiserConstants.ACTIVE);
		userOrgRoleDao.deleteUserOrgRole(map);
	}
}
