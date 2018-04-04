package com.hoau.miser.module.biz.pricecard.server.action;

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
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionSubVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;

/**
 * 优惠分段
 * ClassName: PriceSectionAction 
 * @author 王茂
 * @date 2015年12月22日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PriceSectionAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource 
	private IPriceSectionService priceSectionService;
	
	private PriceSectionVo priceSectionVo;
	private PriceSectionSubVo priceSectionSubVo;
	private String priceSectionSubList;
	public String execute(){
		return "index";
	}
	
	public String queryPriceSection(){
		 List<PriceSectionEntity> entities = priceSectionService.queryPriceSection(priceSectionVo);
	        totalCount = entities == null ? 0l : entities.size();
	        priceSectionVo.setPriceSectionList(entities);
	        return this.returnSuccess();
	}
	/**
	 * 
	 * @Description: 优惠分段主表查询
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月23日
	 */
	@JSON
	public String queryListByParam() {
		try {
			if(priceSectionVo==null)priceSectionVo=new PriceSectionVo();
			List<PriceSectionEntity> priceSectionList=priceSectionService.queryListByParam(priceSectionVo, limit, start);
			totalCount=priceSectionService.queryCountByParam(priceSectionVo);
			priceSectionVo.setPriceSectionList(priceSectionList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 根据id得到优惠分段详情
	 * @return String  
	 * @Author 292078
	 * @Time 2015年12月17日下午7:09:24
	 */
	@JSON
	public String queryPriceSectionById() {
		try {
			priceSectionVo.setPriceSectionEntity(priceSectionService.queryPriceSectionById(priceSectionVo.getPriceSectionEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}
	/**
	 * 增加优惠分段
	 * @Description: addPriceSection
	 * @return String  
	 * @Author 292078
	 * @Time 2015年12月15日下午5:56:54
	 */
	public String addPriceSection(){
		try{
			priceSectionService.addPriceSection(priceSectionVo.getPriceSectionEntity(),priceSectionSubList);
			priceSectionSubList=null;
			priceSectionVo=null;
			return returnSuccess(MessageType.ADD_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 修改优惠分段
	 * @Description: updatePriceSection
	 * @return String  
	 * @Author 292078
	 * @Time 2015年12月15日下午5:57:10
	 */
	@JSON
	public String updatePriceSection(){
		try{
			priceSectionService.updatePriceSection(priceSectionVo.getPriceSectionEntity(),priceSectionSubList);
			priceSectionSubList=null;
			priceSectionVo=null;
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 删除优惠分段
	 * @Description: deletePriceSectionSub
	 * @return String  
	 * @Author 292078
	 * @Time 2015年12月15日下午5:57:20
	 */
	@JSON
	public String deletePriceSection(){
		try{
			priceSectionService.delPriceSection(priceSectionVo.getPriceSectionEntity());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 优惠分段明细查询
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月23日
	 */
	@JSON
	public String queryListByParamSub() {
		try {
			if(priceSectionSubVo==null)priceSectionSubVo=new PriceSectionSubVo();
			List<PriceSectionSubEntity> priceSectionSubList=priceSectionService.queryListByParamSub(priceSectionSubVo, limit, start);
			totalCount=priceSectionService.queryCountByParam(priceSectionSubVo);
			priceSectionSubVo.setPriceSectionSubList(priceSectionSubList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 根据id得到优惠分段明细
	 * @return String  
	 * @Author 292078
	 * @Time 2015年12月17日下午7:09:24
	 */
	@JSON
	public String queryPriceSectionSubById() {
		try {
			priceSectionSubVo.setPriceSectionSubEntity(priceSectionService.queryPriceSectionSubById(priceSectionSubVo.getPriceSectionSubEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}
	/**
	 * 增加优惠分段明细
	 * @Description: addPriceSectionSub
	 * @return String  
	 * @Author 292078
	 * @Time 2015年12月15日下午5:56:54
	 */
	@JSON
	public String addPriceSectionSub(){
		try{
			priceSectionService.addPriceSectionSub(priceSectionSubVo.getPriceSectionSubEntity());
			return returnSuccess(MessageType.ADD_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 修改优惠分段明细
	 * @Description: updatePriceSectionSub
	 * @return String  
	 * @Author 292078
	 * @Time 2015年12月15日下午5:57:10
	 */
	@JSON
	public String updatePriceSectionSub(){
		try{
			priceSectionService.updatePriceSectionSub(priceSectionSubVo.getPriceSectionSubEntity());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 删除优惠分段明细
	 * @Description: deletePriceSectionSub
	 * @return String  
	 * @Author 292078
	 * @Time 2015年12月15日下午5:57:20
	 */
	@JSON
	public String deletePriceSectionSub(){
		try{
			priceSectionService.delPriceSectionSub(priceSectionSubVo.getPriceSectionSubEntity());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * @Description: TODO导出功能
	 * @param @return
	 * @param @throws Exception   
	 * @return String 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月5日
	 */
	public String excelExport() throws Exception{
		try {
			
			if(priceSectionVo==null)priceSectionVo=new PriceSectionVo();
			List<PriceSectionEntity> priceSectionList=priceSectionService.excelQueryListByParam(priceSectionVo);
			
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String  filePath= priceSectionService.createExcelFile(priceSectionList);
			JSONObject json=new JSONObject();
			//filePath:错误的信息的文件地址
			json.put("filePath", filePath); 
			json.put("count",priceSectionList!=null? priceSectionList.size():0); 
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	public PriceSectionVo getPriceSectionVo() {
		return priceSectionVo;
	}
	public void setPriceSectionVo(PriceSectionVo priceSectionVo) {
		this.priceSectionVo = priceSectionVo;
	}
	public PriceSectionSubVo getPriceSectionSubVo() {
		return priceSectionSubVo;
	}
	public void setPriceSectionSubVo(PriceSectionSubVo priceSectionSubVo) {
		this.priceSectionSubVo = priceSectionSubVo;
	}
	public String getPriceSectionSubList() {
		return priceSectionSubList;
	}
	public void setPriceSectionSubList(String priceSectionSubList) {
		this.priceSectionSubList = priceSectionSubList;
	}
	
}
