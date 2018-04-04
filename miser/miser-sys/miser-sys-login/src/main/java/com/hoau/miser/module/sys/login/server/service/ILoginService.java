package com.hoau.miser.module.sys.login.server.service;

import java.util.List;

import org.omg.CORBA.UserException;

import com.hoau.miser.module.sys.base.api.shared.domain.OrgAdministrativeInfoEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.UserEntity;
import com.hoau.miser.module.sys.login.shared.exception.LoginException;

/**
 * @author：高佳
 * @create：2015年6月9日 下午6:46:47
 * @description：web登录service
 */
public interface ILoginService {
	/**
	 * 用户登录
	 * @param loginName
	 * @param password
	 * @author 高佳
	 * @date 2015年6月9日
	 * @update 
	 */
	UserEntity userLogin(String loginName, String password)throws LoginException, UserException;

	/**
	 * 用户注销
	 * @author 高佳
	 * @date 2015年6月17日
	 * @update 
	 */
	void logout();
	
	/**
	 * 查询用户可切换部门
	 * @param deptName
	 * @param start
	 * @param limit
	 * @return
	 * @author 高佳
	 * @date 2015年6月17日
	 * @update 
	 */
	List<OrgAdministrativeInfoEntity> queryCurrentUserChangeDepts(
			String deptName, int start, int limit);

	/**
	 * 切换部门
	 * @param currenUserDeptCode
	 * @author 高佳
	 * @date 2015年6月17日
	 * @update 
	 */
	void changeCurrentDept(String currenUserDeptCode);

	/**
	 *  add by dengyin sso 接入
	 * @param currentUser
	 */
	void toPmsByCas(UserEntity currentUser);

}
