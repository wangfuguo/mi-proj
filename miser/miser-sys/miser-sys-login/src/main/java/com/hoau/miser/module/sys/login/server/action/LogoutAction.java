package com.hoau.miser.module.sys.login.server.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.context.SessionContext;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.sys.base.api.server.service.IResourceMonitorService;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.sys.frameworkimpl.shared.domain.Cookie;
import com.hoau.miser.module.sys.frameworkimpl.shared.domain.CurrentInfo;
import com.hoau.miser.module.sys.login.server.service.ILoginService;

/**
 * @author：高佳
 * @create：2015年6月8日 下午3:17:30
 * @description：注销Action
 */
@Controller
@Scope("prototype")
public class LogoutAction extends AbstractAction{
	
	@Resource
	private ILoginService loginService;
	@Resource
	private IResourceMonitorService resourceMonitorService;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//add by dengyin 2016-7-12 16:04:03 pms sso 接入 用以确定 pms 是普通登录还是 cas 登录
	private String pmsLogInType = null;
	//end by dengyin 2016-7-12 16:04:03 pms sso 接入 用以确定 pms 是普通登录还是 cas 登录
 
	
	
	
	/**
	 * 注销
	 * @return
	 * @author 高佳
	 * @date 2015年6月17日
	 * @update 
	 */
	public String logout(){
		
		//add by dengyin 2016-7-12 16:04:03 pms sso 接入 用以确定 pms 是普通登录还是 cas 登录
		//pmsLogInType 是在 loginAction 的 toPmsByCas 方法里设置
		
		Object loginTypeObj = SessionContext.getSession().getObject("pmsLogInType");
		pmsLogInType =  loginTypeObj == null ? "normal" : String.valueOf(loginTypeObj);
		
		//end by dengyin 2016-7-12 16:04:03 pms sso 接入 用以确定 pms 是普通登录还是 cas 登录
		
		offline();
		loginService.logout();
		//失效Cookie
		Cookie.invalidateCookie();
		
		return returnSuccess();
	}
	
	/**
	 * 注销缓存信息
	 * @author 高佳
	 * @date 2015年6月17日
	 * @update 
	 */
	private void offline() {
		CurrentInfo info = MiserUserContext.getCurrentInfo();
		resourceMonitorService.offline(info);
	}

	public String getPmsLogInType() {
		return pmsLogInType;
	}

	public void setPmsLogInType(String pmsLogInType) {
		this.pmsLogInType = pmsLogInType;
	}
 
	
}
