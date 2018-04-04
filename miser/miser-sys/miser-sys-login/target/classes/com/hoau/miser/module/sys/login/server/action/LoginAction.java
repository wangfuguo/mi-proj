package com.hoau.miser.module.sys.login.server.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.exception.security.UserNotLoginException;
import com.hoau.hbdp.framework.server.components.security.SecurityNonCheckRequired;
import com.hoau.hbdp.framework.server.context.SessionContext;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.common.server.interceptor.CookieNonCheckRequired;
import com.hoau.miser.module.sys.base.api.server.service.IResourceMonitorService;
import com.hoau.miser.module.sys.base.api.shared.domain.OrgAdministrativeInfoEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.UserEntity;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.sys.frameworkimpl.shared.domain.Cookie;
import com.hoau.miser.module.sys.frameworkimpl.shared.domain.CurrentInfo;
import com.hoau.miser.module.sys.login.server.service.ILoginService;
import com.hoau.miser.module.sys.login.shared.exception.LoginException;

/**
 * @author：高佳
 * @create：2015年6月8日 下午3:17:30
 * @description：登录Action
 */
@Controller
@Scope("prototype")
public class LoginAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private ILoginService loginService;
	@Resource
	private IResourceMonitorService resourceMonitorService;

	/**
	 * 当前用户
	 */
	private UserEntity currentUser;

	/**
	 * 当前部门
	 */
	private OrgAdministrativeInfoEntity currentDept;

	/**
	 * 当前用户管理部门编码集合
	 */
	private List<String> currentUserDeptCodes;

	/**
	 * 当前服务器端时间
	 */
	private Date currentServerTime;

	/**
	 * 待切换的当前部门编码
	 */
	private String currenUserDeptCode;

	/**
	 * 查询部门名
	 */
	private String deptName;

	/**
	 * 是否开发环境
	 */
	private boolean dev;

	/**
	 * 加密TOKEN
	 */
	private String requestToken = null;

	private String mac;

	// 当前用户所管理的所有部门
	private List<OrgAdministrativeInfoEntity> userManagerDepts;
	
	//add by dengyin 2016-7-15 11:24:24 pms 接入 sso 当用户没有角色时返回统一登录页面
	private String userToPmsByCasErrCode = null;
	
	public String getUserToPmsByCasErrCode() {
		return userToPmsByCasErrCode;
	}

	public void setUserToPmsByCasErrCode(String userToPmsByCasErrCode) {
		this.userToPmsByCasErrCode = userToPmsByCasErrCode;
	}
	
	//end by dengyin 2016-7-15 11:24:24 pms 接入 sso 当用户没有角色时返回统一登录页面



	/**
	 * 跳转登录界面
	 * 
	 * @return
	 * @author 高佳
	 * @date 2015年5月13日
	 * @update
	 */
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String index() {
		try {
			MiserUserContext.getCurrentUser();
		} catch(UserNotLoginException e) {
			return "login";
		}			
		return this.returnSuccess();
	}

	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String login() throws Exception {
		try {
			loginService.userLogin(currentUser.getUserName(),
					currentUser.getPassword());
			// 这时跳转到main.jsp 根据session生成cookie
			Cookie.saveCookie();
			return this.returnSuccess();
		} catch (BusinessException e) {
			return this.returnError(e);
		}
	}
	
	/**
	 *  add by dengyin sso 接入
	 * @return
	 */
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String toPmsByCas(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		try {
			
			SessionContext.getSession().setObject("pmsLogInType","sso");
			request.setAttribute("loginType", "sso");
			 
			currentUser = new UserEntity();
			
			AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
			String userName = principal.getName();
			
			currentUser.setUserName(userName);
			
			//初始化用户信息
			loginService.toPmsByCas(currentUser);
			
			Cookie.saveCookie();
			
			Cookie.cookieToSession();
			
			request.setAttribute("userToPmsByCasErrCode", "success");
			
		} catch (BusinessException e) {
			
			userToPmsByCasErrCode = e.getErrorCode();
			request.setAttribute("userToPmsByCasErrCode", userToPmsByCasErrCode);
			
			return returnError(e);
		}
		return returnSuccess();
		
	}

	/**
	 * 获取当前登录用户信息
	 * 
	 * @return
	 * @author 高佳
	 * @date 2015年6月15日
	 * @update
	 */
	@JSON
	public String currentLoginUserInfo() {
		// 在线信息加入到缓存
		online();
		// 获得当前登录用户
		currentUser = MiserUserContext.getCurrentUser();
		currentUser.setOrgResCodes(null);
		currentUser.setOrgResUris(null);
		// 获得当前部门
		currentDept = MiserUserContext.getCurrentDept();
		// 当前用户管理部门编码集合
		currentUserDeptCodes = MiserUserContext.getCurrentUserManagerDeptCodes();
		// 服务器当前时间
		currentServerTime = new Date();
		return returnSuccess();
	}

	/**
	 * 查询当前可切换部门
	 * 
	 * @return
	 * @author 高佳
	 * @date 2015年6月17日
	 * @update
	 */
	@JSON
	public String currentUserChangeDepts() {
		userManagerDepts = loginService.queryCurrentUserChangeDepts(deptName,
				start, limit);
		if (userManagerDepts.size() > 0) {
			totalCount = (long) userManagerDepts.get(0).getVersionNo();
		} else {
			totalCount = 0L;
		}
		return returnSuccess();
	}

	/**
	 * 切换部门
	 * 
	 * @return
	 * @author 高佳
	 * @date 2015年6月17日
	 * @update
	 */
	@JSON
	public String changeCurrentDept() {
		try {
			// 切换部门前 清除改用户在当前部门的登陆信息
			offline();
			// 修改部门
			loginService.changeCurrentDept(currenUserDeptCode);
			// 修改了session中存储的当前部门,生成cookie
			Cookie.saveCookie();
			// 切换部门后 统计登陆的用户信息 存入缓存中
			online();
			return this.returnSuccess();
		} catch (BusinessException e) {
			return this.returnError(e);
		}
	}

	/**
	 * 注销缓存信息
	 * 
	 * @author 高佳
	 * @date 2015年6月17日
	 * @update
	 */
	private void offline() {
		CurrentInfo info = MiserUserContext.getCurrentInfo();
		resourceMonitorService.offline(info);
	}

	/**
	 * 登入的时候将信息存到缓存中去
	 */
	private void online() {
		CurrentInfo info = MiserUserContext.getCurrentInfo();
		resourceMonitorService.online(info, info.getUserName());
	}

	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String main() {
		return returnSuccess();
	}

	public UserEntity getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserEntity currentUser) {
		this.currentUser = currentUser;
	}

	public OrgAdministrativeInfoEntity getCurrentDept() {
		return currentDept;
	}

	public void setCurrentDept(OrgAdministrativeInfoEntity currentDept) {
		this.currentDept = currentDept;
	}

	public List<String> getCurrentUserDeptCodes() {
		return currentUserDeptCodes;
	}

	public void setCurrentUserDeptCodes(List<String> currentUserDeptCodes) {
		this.currentUserDeptCodes = currentUserDeptCodes;
	}

	public Date getCurrentServerTime() {
		return currentServerTime;
	}

	public void setCurrentServerTime(Date currentServerTime) {
		this.currentServerTime = currentServerTime;
	}

	public String getCurrenUserDeptCode() {
		return currenUserDeptCode;
	}

	public void setCurrenUserDeptCode(String currenUserDeptCode) {
		this.currenUserDeptCode = currenUserDeptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public boolean isDev() {
		return dev;
	}

	public void setDev(boolean dev) {
		this.dev = dev;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public List<OrgAdministrativeInfoEntity> getUserManagerDepts() {
		return userManagerDepts;
	}

	public void setUserManagerDepts(
			List<OrgAdministrativeInfoEntity> userManagerDepts) {
		this.userManagerDepts = userManagerDepts;
	}

}
