package com.hoau.miser.module.sys.login.shared.exception;

import com.hoau.hbdp.framework.exception.BusinessException;

public class LoginException extends BusinessException {

	private static final long serialVersionUID = -4375232641764945199L;
	
	/**
	 * 登录密码不能为空
	 */
	public static final String USER_PASSWORD_NULL = "miser.login.UserPasswordNullException";

	/**
	 * 登录密码错误
	 */
	public static final String USER_PASSWORD_ERROR = "miser.login.UserPasswordErrorException";

	/**
	 * 该用户不存在
	 */
	public static final String USER_NULL = "miser.login.UserNullException";

	/**
	 * 用户名不能为空
	 */
	public static final String USER_NAME_NULL = "miser.login.UserNameNullException";

	/**
	 * 当前用户已经被禁用
	 */
	public static final String USER_DISABLE = "miser.login.UserIsDisableException";

	/**
	 * 当前用户部门编码不能为空
	 */
	public static final String CURRENT_USER_DEPT_CODE_NULL = "miser.login.CurrentUserDeptCodeException";

	/**
	 * 当前部门不存在
	 */
	public static final String CURRENT_DEPT_ISNOT_EXIST = "miser.login.CurrentDeptIsNotExistException";
	
	/**
	 * 所属部门未配置角色信息
	 */
	public static final String CURRENT_DEPT_NO_ROLE = "miser.login.currentDeptNoRoleInfo";
	
	/**
	 * 当前用户未配置角色信息
	 */
	public static final String CURRENT_USER_NO_ROLE = "miser.login.currentUserNoRoleInfo";
	
	public LoginException(String errCode){
		super();
		super.errCode = errCode;
	}
}
