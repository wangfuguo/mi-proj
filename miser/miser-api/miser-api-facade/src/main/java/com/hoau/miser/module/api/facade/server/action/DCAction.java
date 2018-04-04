package com.hoau.miser.module.api.facade.server.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.miser.module.api.facade.server.service.IPriceSectionPms2DcService;
import com.hoau.miser.module.api.facade.server.service.IPrivilegeCouponService;
import com.hoau.miser.module.api.facade.shared.define.BaseResponseEnum;
import com.hoau.miser.module.api.facade.shared.domain.PriceSectionPms2DcEntity;
import com.hoau.miser.module.api.facade.shared.vo.BaseResponseVo;
import com.hoau.miser.module.api.facade.shared.vo.CouponApplyVo;
import com.hoau.miser.module.api.facade.shared.vo.PriceSectionVo;
import com.hoau.miser.module.biz.base.api.server.service.ICustomerService;
import com.hoau.miser.module.biz.base.api.shared.domain.CustomerEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.CustomerVo;

@Controller("dcAction")
@Scope("prototype")
public class DCAction extends AbstractAction {
	private static final long serialVersionUID = 7958415478985776060L;
	
	@Resource
	private IPrivilegeCouponService privilegeCouponService;
	@Resource
	private IPriceSectionPms2DcService priceSectionPms2DcService;
	@Resource
	private ICustomerService customerService;
	/**
	 * IN参数
	 */
	private CouponApplyVo couponApplyVo;
	/**
	 *IN参数 : 查询易到家分段价格
	 */
	private PriceSectionVo priceSectionVo;
	/**
	 * out参数
	 */
	private BaseResponseVo<Object> response = new BaseResponseVo<Object>();

	public String couponApply() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try {
			privilegeCouponService.apply(couponApplyVo);
			json.put("result", true);
			json.put("message", "成功");
		} catch (Exception e) {
			json.put("result", false);
			json.put("message", e.getMessage());
		} finally {
			out.println(json.toString());
			out.flush();
			out.close();
		}
		return returnSuccess();
	}

	/**
	 * @Description: 根据条件查询易到家分段价格
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年7月5日
	 */
	public String queryPriceSections() {
		if(null == priceSectionVo){
			HttpServletRequest request = ServletActionContext.getRequest();
			if (null != request.getQueryString()) {
				try {
					setPriceSectionVo(request);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					response.setStatus(BaseResponseEnum.SYSTEM_ERROR.getStatus());
					response.setMessage(BaseResponseEnum.SYSTEM_ERROR.getMessage());
					return returnError(null, response);
				}
			}
		}
		List<PriceSectionPms2DcEntity> content = priceSectionPms2DcService.queryPriceSections(priceSectionVo);
		response.setContent(content);
		response.setStatus(BaseResponseEnum.SUCCESS.getStatus());
		response.setMessage(BaseResponseEnum.SUCCESS.getMessage());
		return returnSuccess();
	}
	
	/**
	 * @Description: 从request取参数设定查询易到家分段价格vo
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年7月5日
	 */
	private void setPriceSectionVo(HttpServletRequest request) throws UnsupportedEncodingException {
		priceSectionVo = new PriceSectionVo();
		if (StringUtils.isNotEmpty(request.getParameter("corpCode"))) {
			priceSectionVo.setCorpCode(URLDecoder.decode(request.getParameter("corpCode"), "utf-8"));
		}
		if (StringUtils.isNotEmpty(request.getParameter("customerCode"))) {
			priceSectionVo.setCustomerCode(URLDecoder.decode(request.getParameter("customerCode"), "utf-8"));
		}
		if (StringUtils.isNotEmpty(request.getParameter("transTypeCode"))) {
			priceSectionVo.setTransTypeCode(URLDecoder.decode(request.getParameter("transTypeCode"), "utf-8"));
		}
		if (StringUtils.isNotEmpty(request.getParameter("sendPriceCityCode"))) {
			priceSectionVo.setSendPriceCityCode(URLDecoder.decode(request.getParameter("sendPriceCityCode"), "utf-8"));
		}
		if (StringUtils.isNotEmpty(request.getParameter("arrivePriceCityCode"))) {
			priceSectionVo.setArrivePriceCityCode(URLDecoder.decode(request.getParameter("arrivePriceCityCode"), "utf-8"));
		}
	}
	
	/**
	 * @Description: 根据迪辰客户编码查询pms客户信息定义易到家产品是否使用定日达价格
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年7月5日
	 */
	public String queryCustomerYdjUseDrd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String code = priceSectionVo.getCustomerCode();
		if (null != request.getQueryString() && StringUtils.isEmpty(code)) {
			try {
				if (StringUtils.isNotEmpty(request.getParameter("customerCode"))) {
					code = URLDecoder.decode(request.getParameter("customerCode"), "utf-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				response.setStatus(BaseResponseEnum.SYSTEM_ERROR.getStatus());
				response.setMessage(BaseResponseEnum.SYSTEM_ERROR.getMessage());
				return returnError(null, response);
			}
		}
		
		String content = "";
		CustomerEntity queryParam = new CustomerEntity();
		CustomerVo customerVo = new CustomerVo();
		queryParam.setCode(code);
		customerVo.setQueryParam(queryParam);
		List<CustomerEntity> customers = customerService.queryCustomers(customerVo, 1, 0);
		for(CustomerEntity customer : customers){
			if(code.equals(customer.getCode())){
				content = customer.getYdjUseDrd();
				break;
			}
		}
		response.setContent(StringUtils.isEmpty(content)?"N":content);//默认客户使用易到家产品
		response.setStatus(BaseResponseEnum.SUCCESS.getStatus());
		response.setMessage(BaseResponseEnum.SUCCESS.getMessage());
		return returnSuccess();
	}
	
	public CouponApplyVo getCouponApplyVo() {
		return couponApplyVo;
	}

	public void setCouponApplyVo(CouponApplyVo couponApplyVo) {
		this.couponApplyVo = couponApplyVo;
	}

	public PriceSectionVo getPriceSectionVo() {
		return priceSectionVo;
	}

	public void setPriceSectionVo(PriceSectionVo priceSectionVo) {
		this.priceSectionVo = priceSectionVo;
	}

	public BaseResponseVo<Object> getResponse() {
		return response;
	}

	public void setResponse(BaseResponseVo<Object> response) {
		this.response = response;
	}
	
}
