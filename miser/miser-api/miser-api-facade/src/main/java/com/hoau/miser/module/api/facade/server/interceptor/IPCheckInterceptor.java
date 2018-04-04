package com.hoau.miser.module.api.facade.server.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.struts2.ServletActionContext;

import com.hoau.hbdp.framework.server.web.interceptor.AbstractInterceptor;
import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.miser.module.api.facade.shared.define.BaseResponseEnum;
import com.hoau.miser.module.api.facade.shared.vo.BaseResponseVo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * 白名单IP检查 拦截器
 * ClassName: IPCheckInterceptor
 * @author 286330付于令
 * @date 2016年1月25日
 * @version V1.0
 */
public class IPCheckInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = -8940580011064200304L;
	
	private static final String CONFIG_LOCATION = "ip_white_list.properties";

	@Override
	public void init() {
		super.init();
	}

	/**
	 * 白名单IP缓存
	 */
	private Map<String, List<String>> ipMap = new HashMap<String, List<String>>();
	
	/**
	 * @Description: 验证IP
	 * @param ip 请求IP
	 * @param ipList 白名单IP
	 * @return boolean true验证通过，false验证失败
	 * @author 286330付于令
	 * @date 2016年1月25日
	 */
	private boolean verifyIP(String ip, List<String> ipList) {
		if(ip == null || "".equals(ipList) || ipList == null || ipList.size() == 0) {
			return false;
		}
		if(ipList.contains(ip)) {
			return true;
		}
		for(String whiteIp: ipList) {
			if(ip.startsWith(whiteIp)) {
				return true;
			}
		}
		return true;
	}
	
	/**
	 * @Description: 获取白名单IP
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年1月25日
	 */
	private List<String> getIpList(String orign) {
		try {
			List<String> ipList = ipMap.get(orign);
			if(ipList == null) {
				PropertiesConfiguration config = new PropertiesConfiguration(CONFIG_LOCATION);
				List<String> paramList = new ArrayList<String>();
				paramList.add(orign);
				ipList = config.getList("com.hoau.miser.module.api." + orign + ".ip");
				ipMap.put(orign, ipList);
			}
			return ipList;
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String intercept(ActionInvocation action) throws Exception {
		ActionContext context = action.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context
				.get(ServletActionContext.HTTP_REQUEST);
		String ip = request.getHeader("ORIG_CLIENT_IP");
		if(ip == null || "".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
			if(ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
				ip = ip.split("\\s*,\\s*")[0];
			}
		}
		if(ip == null || "".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		String systemOrign = request.getHeader("systemOrign");
		if(systemOrign == null || "".equals(systemOrign)) {
			systemOrign = "base";
		}
		List<String> ipList = getIpList(systemOrign);
		if(verifyIP(ip, ipList)) {
			return action.invoke();
		} else {
			
			BaseResponseVo<?> responseVo = new BaseResponseVo<Object>();
			responseVo.setStatus(BaseResponseEnum.IP_CHECK_FAIL.getStatus());
			responseVo.setMessage(BaseResponseEnum.IP_CHECK_FAIL.getMessage());
			String responseJson = JsonUtils.toJson(responseVo);
			HttpServletResponse response = (HttpServletResponse) context
					.get(ServletActionContext.HTTP_RESPONSE);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(responseJson);
			return null;
		}
	}

}
