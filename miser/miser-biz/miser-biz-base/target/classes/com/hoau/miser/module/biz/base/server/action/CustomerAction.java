package com.hoau.miser.module.biz.base.server.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityMappingVo;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;
import org.apache.ibatis.session.RowBounds;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.server.service.ICustomerService;
import com.hoau.miser.module.biz.base.api.shared.domain.CustomerEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.api.shared.vo.CustomerVo;

@Controller
@Scope("prototype")
public class CustomerAction extends AbstractAction {

	@Resource
	public ICustomerService customerService;
	
	private CustomerVo customerVo;
	
	public String index() {
		return "index";
	}
	
	/**
	 * @Description: 根据条件查询客户列表信息
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	@JSON
	public String queryCustomers() {
		ArrayList<CustomerEntity> customerEntities = customerService.queryCustomers(customerVo, limit, start);
		customerVo.setCustomerEntities(customerEntities);
		Long count = customerService.queryCustomerCount(customerVo);
		totalCount = count == null ? 0l : count;
		return returnSuccess();
	}
	
	/**
	 * @Description: 根据id查询客户信息
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	@JSON
	public String queryCustomerById() {
		CustomerEntity customerEntity = customerService.queryCustomerById(customerVo);
		customerVo.setCustomerEntity(customerEntity);
		return returnSuccess();
	}

	/**
	 * @Description: 更新客户信息
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	@JSON
	public String updateCustomer() {
		customerService.updateCustomer(customerVo);
		return returnSuccess(MessageType.UPDATE_SUCCESS);
	}

	public void excelExport() throws Exception {
		HttpServletResponse response= ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		try {
			CustomerEntity entity = customerVo.getQueryParam();
			response.setContentType("text/html;charset=utf-8");
			ExcelExportResultEntity resultEntity= customerService.createCustomerExcelFile(entity);
			if (resultEntity == null) {
				throw new BusinessException("导出失败--查询条件无效");
			}
			JSONObject json=new JSONObject();
			json.put("filePath", resultEntity.getFilePath());
			json.put("count",resultEntity.getRecordCount());
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (BusinessException e) {
			out.write(e.getMessage());
			out.flush();
			out.close();
		}
	}

	public CustomerVo getCustomerVo() {
		return customerVo;
	}

	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
	}
	
	
}
