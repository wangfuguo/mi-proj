package com.hoau.miser.module.sys.login.server.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hoau.hbdp.framework.entity.IFunction;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.exception.security.UserNotLoginException;
import com.hoau.hbdp.framework.server.components.security.SecurityAccessor;
import com.hoau.hbdp.framework.server.context.RequestContext;
import com.hoau.hbdp.framework.server.context.WebApplicationContextHolder;
import com.hoau.hbdp.framework.server.web.interceptor.AbstractInterceptor;
import com.hoau.miser.module.sys.base.api.server.service.IResourceMonitorService;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.sys.frameworkimpl.shared.domain.CurrentInfo;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * @author：高佳
 * @create：2015年9月8日 下午4:34:18
 * @description：资源菜单拦截器
 * 应用监控-菜单功能计数
 */
public class ResourceMonitorInterceptor extends AbstractInterceptor{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Log LOGGER = LogFactory.getLog(ResourceMonitorInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		IResourceMonitorService service = WebApplicationContextHolder.getWebApplicationContext().getBean(IResourceMonitorService.class);
		final String url = RequestContext.getCurrentContext().getRemoteRequestURL();
		try {
			IFunction function = SecurityAccessor.getFunction(url);
			if(function == null){
				return invocation.invoke();
			}
			CurrentInfo currentInfo = null;
			try {
				currentInfo=MiserUserContext.getCurrentInfo();
			} catch(UserNotLoginException e) {
				return invocation.invoke();
			}
			//service.counterResource(function.getFunctionCode(), currentInfo);
		} catch(BusinessException e) {
			LOGGER.error("菜单计数失败!",e);
		}
		return invocation.invoke();
	}

}
