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
import com.hoau.miser.module.biz.extrafee.server.service.IPriceExtrafeeStandardService;
import com.hoau.miser.module.biz.extrafee.shared.domain.ExtrafeeAddValueFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceExtrafeeStandardEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceExtrafeeStandardVo;

/**
 * 标准附加费
 * ClassName: PriceExtrafeeStandardAction 
 * @author 王茂
 * @date 2016年1月13日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PriceExtrafeeStandardAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private IPriceExtrafeeStandardService PriceExtrafeeStandardService;

	private PriceExtrafeeStandardVo priceExtrafeeStandardVo;

	public String execute() {
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
			
			if(priceExtrafeeStandardVo==null)priceExtrafeeStandardVo=new PriceExtrafeeStandardVo();
			List<PriceExtrafeeStandardEntity> ExtrafeeAddValueFeeList=PriceExtrafeeStandardService.excelListByParam(priceExtrafeeStandardVo);
			totalCount=PriceExtrafeeStandardService.queryCountByParam(priceExtrafeeStandardVo);
			
			HttpServletResponse response=ServletActionContext.getResponse();
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out = response.getWriter();
			String  filePath= PriceExtrafeeStandardService.createExcelFile(ExtrafeeAddValueFeeList);
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
	 * @Description: 查询标准附加费
	 * @param @return
	 * @return String
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String queryListByParam() {
		try {
			if (priceExtrafeeStandardVo == null)
				priceExtrafeeStandardVo = new PriceExtrafeeStandardVo();
			List<PriceExtrafeeStandardEntity> PriceExtrafeeStandardList = PriceExtrafeeStandardService.queryListByParam(priceExtrafeeStandardVo, limit, start);
			totalCount = PriceExtrafeeStandardService.queryCountByParam(priceExtrafeeStandardVo);
			priceExtrafeeStandardVo.setPriceExtrafeeStandardList(PriceExtrafeeStandardList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 根据id得到标准附加费表
	 * @param @return
	 * @return String
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String queryPriceExtrafeeStandardById() {
		try {
			priceExtrafeeStandardVo.setPriceExtrafeeStandardEntity(PriceExtrafeeStandardService.queryPriceExtrafeeStandardById(priceExtrafeeStandardVo.getPriceExtrafeeStandardEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}
	/**
	 * 
	 * @Description: 增加标准附加费
	 * @param @return
	 * @return String
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String addPriceExtrafeeStandard() {
		try {
			String flag=PriceExtrafeeStandardService.addPriceExtrafeeStandard(priceExtrafeeStandardVo.getPriceExtrafeeStandardEntity());
			return returnSuccess(flag);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 * @Description: 修改标准附加费
	 * @param @return
	 * @return String
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String updatePriceExtrafeeStandard() {
		try {
			String flag=PriceExtrafeeStandardService.updatePriceExtrafeeStandard(priceExtrafeeStandardVo.getPriceExtrafeeStandardEntity());
			return returnSuccess(flag);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 删除标准附加费
	 * @param @return
	 * @return String
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String deletePriceExtrafeeStandard() {
		try {
			String flag=PriceExtrafeeStandardService.delPriceExtrafeeStandard(priceExtrafeeStandardVo.getPriceExtrafeeStandardEntity());
			return returnSuccess(flag);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	public PriceExtrafeeStandardVo getPriceExtrafeeStandardVo() {
		return priceExtrafeeStandardVo;
	}
	public void setPriceExtrafeeStandardVo(PriceExtrafeeStandardVo priceExtrafeeStandardVo) {
		this.priceExtrafeeStandardVo = priceExtrafeeStandardVo;
	}
	

}
