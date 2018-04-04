package com.hoau.miser.module.sys.base.api.server.service;

import java.util.Set;

/**
 * @author：涂婷婷
 * @create：2015年7月9日 
 * @description
 */
public interface IUserOrgRoleService {

	
	/**
	 * 删除用户部门全部角色信息
	 * @param userName
	 * @param orgCode
	 * @author 高佳
	 * @date 2015年7月21日
	 * @update 
	 */
	public void deleteUserAllOrgRole(String userName,String orgCode);


	/**
	 * 更新用户组织角色信息
	 * @param userName 用户名
	 * @param orgCode 部门编码
	 * @param roleCodes 角色编码
	 * @author 高佳
	 * @date 2015年7月21日
	 * @update 
	 */
	public void updateUserOrgRole(String userName, String orgCode,
			Set<String> roleCodes);
}
