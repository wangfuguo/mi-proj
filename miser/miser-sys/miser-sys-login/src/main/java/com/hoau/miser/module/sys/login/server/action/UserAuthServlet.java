/**
 * 
 */
package com.hoau.miser.module.sys.login.server.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.miser.module.sys.base.api.server.service.IUserService;
import com.hoau.miser.module.sys.base.api.shared.domain.UserEntity;
import com.hoau.miser.module.sys.base.server.service.impl.UserService;

/**
 * @author dengyin
 *
 */
public class UserAuthServlet extends HttpServlet{
	
	private static final long serialVersionUID = 3352380075218063322L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String userName = request.getParameter("userName");
		
		PrintWriter writer = response.getWriter();
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		try{
			
			if(null == userName || "".equals(userName)){
				throw new Exception("userName is empty");
			}
			
			IUserService userService = new UserService();
			UserEntity entity = userService.queryUserByUserName(userName);
			
			resultMap.put("errorCode", "1000");
			resultMap.put("errorMessage", "成功");
			resultMap.put("result", true);
			
			if(null == entity){
				resultMap.put("errorCode", "0000");
				resultMap.put("errorMessage", "失败");
				resultMap.put("result", false);
			}
			
			writer.write(JsonUtils.toJson(resultMap));
			 
		}catch(Exception e){
			e.printStackTrace();
			
			resultMap.put("errorCode", "0000");
			resultMap.put("errorMessage", "成功");
			resultMap.put("result",false);
			
			writer.write(JsonUtils.toJson(resultMap));
		}finally{
			writer.flush();
			writer.close();
		}
	}

}
