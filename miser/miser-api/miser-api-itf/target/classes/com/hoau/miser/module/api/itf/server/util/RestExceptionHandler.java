package com.hoau.miser.module.api.itf.server.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.components.logger.ILogSender;
import com.hoau.hbdp.framework.server.components.logger.entity.LogInfo;
import com.hoau.hbdp.framework.server.components.logger.entity.LogType;
import com.hoau.hbdp.framework.server.context.AppContext;
import com.hoau.hbdp.framework.server.web.message.IMessageBundle;
import com.hoau.hbdp.webservice.components.rest.define.HttpContentTypeConstants;
import com.hoau.hbdp.webservice.components.rest.define.RestErrorCodeConstants;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;

/**
 * Rest接口调用异常处理类
 * 
 * @author 蒋落琛
 * @date 2016-6-1上午10:31:41
 */
@Provider
public class RestExceptionHandler implements ExceptionMapper<Throwable> {

	@Context
	private HttpServletRequest request;

	/**
	 * 用于国际化消息生成
	 */
	@Resource
	protected IMessageBundle messageBundle;

	/**
	 * 日志发送者
	 */
	@Autowired(required = false)
	private ILogSender logSender;

	public Response toResponse(Throwable ex) {
		ResponseBuilder rb = Response.status(Response.Status.OK);
		rb.type(HttpContentTypeConstants.JSON_UTF8);
		ResponseBaseEntity<Void> entity = new ResponseBaseEntity<Void>();
		if (ex instanceof BusinessException) {// 自定义的异常类
			ex.printStackTrace();
			BusinessException e = (BusinessException) ex;
			entity.setErrorCode(RestErrorCodeConstants.STATUS_BUSSINESS_ERROR);
			entity.setErrorMessage(messageBundle.getMessage(e.getErrorCode(),
					e.getErrorArguments()));
			rb.entity(entity);
		} else {
			if (logSender != null) {
				// 发送异常日志
				LogInfo info = getExceptionLogInfo(ex);
				List<Object> log = new ArrayList<Object>();
				log.add(info);
				logSender.send(log);
			}
			// 系统异常
			ex.printStackTrace();
			entity.setErrorCode(RestErrorCodeConstants.STATUS_SYSTEM_ERROR);
			entity.setErrorMessage(ExceptionUtils.getFullStackTrace(ex));
			rb.entity(entity);
		}
		rb.language(Locale.SIMPLIFIED_CHINESE);
		Response r = rb.build();
		return r;
	}

	/**
	 * 封装系统异常日志
	 * 
	 * @param exception
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-1上午10:32:15
	 * @update
	 */
	private LogInfo getExceptionLogInfo(Throwable exception) {
		LogInfo info = new LogInfo();
		info.setRequestId(request.getHeader("requestId"));
		final String url = request.getRequestURI();
		info.setUrl(url);
		info.setUserName(request.getHeader("userName"));
		info.setDate(info.newDate());
		info.setAppName(AppContext.getAppContext().getContextPath());
		info.setType(LogType.EXCEPTION);
		info.setMessage(ExceptionUtils.getFullStackTrace(exception));
		return info;
	}
}
