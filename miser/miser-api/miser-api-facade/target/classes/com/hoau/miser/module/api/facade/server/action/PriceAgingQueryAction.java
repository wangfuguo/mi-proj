package com.hoau.miser.module.api.facade.server.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.api.facade.server.service.IPriceAgingQueryService;
import com.hoau.miser.module.api.facade.shared.define.BaseResponseEnum;
import com.hoau.miser.module.api.facade.shared.vo.PriceAgingConditionVo;
import com.hoau.miser.module.api.facade.shared.vo.BaseResponseVo;
import com.hoau.miser.module.common.shared.util.MD5EncryptUtil;
import com.hoau.miser.module.util.StringUtils;

@Controller("PriceAgingQueryAction")
@Scope("prototype")
public class PriceAgingQueryAction extends AbstractAction {
	private static final long serialVersionUID = 7958415478985776061L;

	@Resource
	private IPriceAgingQueryService priceAgingQueryService;

	private PriceAgingConditionVo priceAgingConditionVo;

	public PriceAgingConditionVo getPriceAgingConditionVo() {
		return priceAgingConditionVo;
	}

	public void setPriceAgingConditionVo(
			PriceAgingConditionVo priceAgingConditionVo) {
		this.priceAgingConditionVo = priceAgingConditionVo;
	}

	private BaseResponseVo<Object> response = new BaseResponseVo<Object>();

	public BaseResponseVo<Object> getResponse() {
		return response;
	}

	public void setResponse(BaseResponseVo<Object> response) {
		this.response = response;
	}

	public String QueryPriceAging() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			if (null != request.getQueryString()) {
				response = VerifySign(request);
				if (!"".equals(response.getMessage())) {
					return returnError(null, response);
				}
				setQueryVO(request);
			}
			String json = JSON.toJSONString(priceAgingConditionVo);
			System.out.println(json);
			response = priceAgingQueryService
					.queryPriceAgingListByParams(priceAgingConditionVo);

			if (null != response) {
				if (BaseResponseEnum.SUCCESS.getStatus().equals(
						response.getStatus())) {
					return returnSuccess();
				} else {
					return returnError(null, response);
				}
			} else {
				response.setStatus(BaseResponseEnum.SYSTEM_ERROR.getStatus());
				response.setMessage(BaseResponseEnum.SYSTEM_ERROR.getMessage());
				return returnError(null, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.toString());
			response.setStatus(BaseResponseEnum.SYSTEM_ERROR.getStatus());
			response.setMessage(BaseResponseEnum.SYSTEM_ERROR.getMessage());
			return returnError(null, response);
		}
	}

	private void setQueryVO(HttpServletRequest request)
			throws UnsupportedEncodingException {
		PriceAgingConditionVo vo = new PriceAgingConditionVo();
		if (!StringUtils.isEmpty(request.getParameter("sendProvinceName"))) {
			vo.setSendProvinceName(URLDecoder.decode(
					request.getParameter("sendProvinceName"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("sendCityName"))) {
			vo.setSendCityName(URLDecoder.decode(
					request.getParameter("sendCityName"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("sendAreaName"))) {
			vo.setSendAreaName(URLDecoder.decode(
					request.getParameter("sendAreaName"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("arriveProviceName"))) {
			vo.setArriveProvinceName(URLDecoder.decode(
					request.getParameter("arriveProviceName"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("arriveCityName"))) {
			vo.setArriveCityName(URLDecoder.decode(
					request.getParameter("arriveCityName"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("arriveAreaName"))) {
			vo.setArriveAreaName(URLDecoder.decode(
					request.getParameter("arriveAreaName"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("sendProvinceCode"))) {
			vo.setSendProvinceCode(URLDecoder.decode(
					request.getParameter("sendProvinceCode"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("sendCityCode"))) {
			vo.setSendCityCode(URLDecoder.decode(
					request.getParameter("sendCityCode"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("sendAreaCode"))) {
			vo.setSendAreaCode(URLDecoder.decode(
					request.getParameter("sendAreaCode"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("arriveProvinceCode"))) {
			vo.setArriveProvinceCode(URLDecoder.decode(
					request.getParameter("arriveProvinceCode"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("arriveCityCode"))) {
			vo.setArriveCityCode(URLDecoder.decode(
					request.getParameter("arriveCityCode"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("arriveAreaCode"))) {
			vo.setArriveAreaCode(URLDecoder.decode(
					request.getParameter("arriveAreaCode"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("transTypeName"))) {
			vo.setTransTypeName(URLDecoder.decode(
					request.getParameter("transTypeName"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("channelCode"))) {
			vo.setChannelCode(URLDecoder.decode(
					request.getParameter("channelCode"), "utf-8"));
		}
		if (!StringUtils.isEmpty(request.getParameter("weight"))) {
			vo.setWeight(StringUtils.toBigDecimal(URLDecoder.decode(
					request.getParameter("weight"), "utf-8")));
		}
		if (!StringUtils.isEmpty(request.getParameter("volume"))) {
			vo.setVolume(StringUtils.toBigDecimal(URLDecoder.decode(
					request.getParameter("volume"), "utf-8")));
		}
		if (!StringUtils.isEmpty(request.getParameter("sign"))) {
			vo.setSign(URLDecoder.decode(request.getParameter("sign"), "utf-8"));
		}
		setPriceAgingConditionVo(vo);
	}

	private BaseResponseVo<Object> VerifySign(final HttpServletRequest request) {
		try {
			String requestSign = "";
			StringBuilder strBuilderParam = new StringBuilder();

			Map<String, String[]> map = request.getParameterMap();
			List<String> keys = new ArrayList<String>(map.keySet());
			Collections.sort(keys);
			for (int idx = 0; idx < keys.size(); idx++) {
				String key = keys.get(idx);
				String[] values = map.get(key);
				if (!"sign".equals(key) && null != values && values.length > 0) {
					strBuilderParam.append(key).append(
							URLDecoder.decode(values[0], "utf-8"));
				} else if ("sign".equals(key) && null != values
						&& values.length > 0) {
					requestSign = values[0];
				}

			}
			String strParam = strBuilderParam.append(md5Key).toString();
			String sign = MD5EncryptUtil.md5(strParam);
			if (!sign.equals(requestSign)) {
				response.setStatus(BaseResponseEnum.SIGN_CHECK_FAIL.getStatus());
				response.setMessage(BaseResponseEnum.SIGN_CHECK_FAIL
						.getMessage());
				response.setContent("sign=" + sign + ";requestSign="
						+ requestSign + ";" + strParam);
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			response.setStatus(BaseResponseEnum.SYSTEM_ERROR.getStatus());
			response.setMessage(BaseResponseEnum.SYSTEM_ERROR.getMessage());
			return response;
		}
		return response;
	}

	private static String md5Key = "";

	static {
		try {
			Properties properties = ConfigFileLoadUtil
					.getPropertiesForClasspath("config.properties");
			md5Key = properties
					.getProperty("PriceAgingQueryKey", "hoauPMS$#@!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
