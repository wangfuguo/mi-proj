package com.hoau.miser.module.biz.extrafee.server.action;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.extrafee.server.service.IExtrafeeAddValueFeeService;
import com.hoau.miser.module.biz.extrafee.shared.domain.ExtrafeeAddValueFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.ExtrafeeAddValueFeeVo;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardVo;

/**
 * 增值费
 * ClassName: ExtrafeeAddValueFeeAction 
 * @author 王茂
 * @date 2016年1月13日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ExtrafeeAddValueFeeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource 
	private IExtrafeeAddValueFeeService ExtrafeeAddValueFeeService;
	
	private ExtrafeeAddValueFeeVo extrafeeAddValueFeeVo;
	
	public String execute(){
		return "index";
	}
	
	
	/**
	 * 
	 * @Description: excel导出
	 * @param @return
	 * @param @throws Exception   
	 * @return String 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	public String excelExport() throws Exception{
		try {
			
			if(extrafeeAddValueFeeVo==null)extrafeeAddValueFeeVo=new ExtrafeeAddValueFeeVo();
			List<ExtrafeeAddValueFeeEntity> ExtrafeeAddValueFeeList=ExtrafeeAddValueFeeService.excelListByParam(extrafeeAddValueFeeVo);
			totalCount=ExtrafeeAddValueFeeService.queryCountByParam(extrafeeAddValueFeeVo);
			
			HttpServletResponse response=ServletActionContext.getResponse();
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out = response.getWriter();
			String  filePath= ExtrafeeAddValueFeeService.createExcelFile(ExtrafeeAddValueFeeList);
			JSONObject json=new JSONObject();
			//filePath:错误的信息的文件地址
			json.put("filePath", filePath); 
			json.put("count",ExtrafeeAddValueFeeList!=null? ExtrafeeAddValueFeeList.size():0); 
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @Description: 查询增值费
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String queryListByParam() {
		try {
			if(extrafeeAddValueFeeVo==null)extrafeeAddValueFeeVo=new ExtrafeeAddValueFeeVo();
			List<ExtrafeeAddValueFeeEntity> ExtrafeeAddValueFeeList=ExtrafeeAddValueFeeService.queryListByParam(extrafeeAddValueFeeVo, limit, start);
			totalCount=ExtrafeeAddValueFeeService.queryCountByParam(extrafeeAddValueFeeVo);
			extrafeeAddValueFeeVo.setExtrafeeAddValueFeeList(ExtrafeeAddValueFeeList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 根据id得到增值费表
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String queryExtrafeeAddValueFeeById() {
		try {
			extrafeeAddValueFeeVo.setExtrafeeAddValueFeeEntity(ExtrafeeAddValueFeeService.queryExtrafeeAddValueFeeById(extrafeeAddValueFeeVo.getExtrafeeAddValueFeeEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}
	/**
	 * 
	 * @Description: 增加增值费
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String addExtrafeeAddValueFee(){
		try{
			String flag=ExtrafeeAddValueFeeService.addExtrafeeAddValueFee(extrafeeAddValueFeeVo.getExtrafeeAddValueFeeEntity());
			return returnSuccess(flag);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 修改增值费
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String updateExtrafeeAddValueFee(){
		try{
			String flag=ExtrafeeAddValueFeeService.updateExtrafeeAddValueFee(extrafeeAddValueFeeVo.getExtrafeeAddValueFeeEntity());
			return returnSuccess(flag);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
/**
 * 
 * @Description: 删除增值费
 * @param @return   
 * @return String 
 * @throws
 * @author 王茂
 * @date 2015年12月30日
 */
	@JSON
	public String deleteExtrafeeAddValueFee(){
		try{
			String flag=ExtrafeeAddValueFeeService.delExtrafeeAddValueFee(extrafeeAddValueFeeVo.getExtrafeeAddValueFeeEntity());
			return returnSuccess(flag);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
public ExtrafeeAddValueFeeVo getExtrafeeAddValueFeeVo() {
	return extrafeeAddValueFeeVo;
}
public void setExtrafeeAddValueFeeVo(ExtrafeeAddValueFeeVo extrafeeAddValueFeeVo) {
	this.extrafeeAddValueFeeVo = extrafeeAddValueFeeVo;
}
	
	
}
