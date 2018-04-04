package com.hoau.miser.module.biz.pricecard.server.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCorpSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCorpSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpSectionVo;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;

@Controller
@Scope("prototype")
public class PriceCorpSectionAction extends AbstractAction{

	private static final long serialVersionUID = 6457064035725041030L;
	
	@Resource
	private IPriceCorpSectionService priceCorpSectionService; 
	
	private PriceCorpSectionVo vo;
	
	public String execute() {
		return "index";
	}
	
	//查询
	@JSON
	public String query(){
		try{
			vo.getPriceCorpSection().setDataorign(DataOrignEnum.PMS.getCode());
			vo.setList(priceCorpSectionService.queryGroup(vo.getPriceCorpSection(),limit,start));
			setTotalCount(priceCorpSectionService.queryGroupCount(vo.getPriceCorpSection()));
			return returnSuccess();
		}catch(BusinessException ex){
			return returnError(ex);
		}
	}

	//保存
	@JSON
	public String save(){
		try{
			if(vo == null )
				return returnError("没有内容需要保存");
			priceCorpSectionService.insertBatch(vo);
			return returnSuccess("成功");
		}catch(BusinessException e){
			return returnError(e.getMessage());
		}
	}
	
	//作废
	@JSON
	public String revoke(){
		try{
			if(vo == null )
				return returnError("没有任何数据需要操作");
			priceCorpSectionService.revoke(vo.getList());
			return returnSuccess("成功");
		}catch(BusinessException e){
			return returnError(e.getMessage());
		}
	}
	
	@JSON
	public String search(){
		try{
			vo.getPriceCorpSection().setDataorign(DataOrignEnum.PMS.getCode());
			vo.setList(priceCorpSectionService.search(vo.getPriceCorpSection()));
			vo.setOld(vo.getList().get(0));
			vo.setPriceCorpSection(vo.getList().get(0));
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	@JSON
	public void export() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			if (vo == null) vo = new PriceCorpSectionVo();
			vo.getPriceCorpSection().setDataorign(DataOrignEnum.PMS.getCode());
			List<PriceCorpSectionEntity> list = priceCorpSectionService.search(vo.getPriceCorpSection());
			if (list == null) {
				throw new BusinessException("导出错误--没有需要导出的数据");
			}
			String filePath = priceCorpSectionService.exportExcel(list);
			JSONObject json = new JSONObject();
			json.put("filePath", filePath);
			json.put("count", list.size());
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (BusinessException e) {
			e.printStackTrace();
			out.write(e.getMessage());
			out.flush();
			out.close();
		}
	}
	
	public void downFormPage() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			if (vo == null) vo = new PriceCorpSectionVo();
			vo.getPriceCorpSection().setDataorign(DataOrignEnum.PMS.getCode());
			List<PriceCorpSectionEntity> list = priceCorpSectionService.reform(vo);
			if (vo.getList() == null) {
				throw new BusinessException("导出错误--没有需要导出的数据");
			}
			String filePath = priceCorpSectionService.exportExcel(list);
			JSONObject json = new JSONObject();
			json.put("filePath", filePath);
			json.put("count", vo.getList().size());
			json.put("success", true);
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (BusinessException e) {
			e.printStackTrace();
			out.write(e.getMessage());
			out.flush();
			out.close();
		}
	}
	
	public void impl() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			String uploadPath = ServletActionContext.getServletContext().getRealPath(vo.getFilePath());
			vo.setFilePath(uploadPath);
			Map<String, Object> returnMap = priceCorpSectionService.impl(vo.getFilePath());
			JSONObject json = new JSONObject();
			
			json.put("succCount", returnMap.get("succCount"));
			json.put("failCount", returnMap.get("failCount"));
			json.put("repeatTip", returnMap.get("repeatTip"));
			json.put("list", returnMap.get("list"));
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (BusinessException e) {
			out.flush();
			out.close();
			throw e;
		}
	}
	
	
	public PriceCorpSectionVo getVo() {
		return vo;
	}

	public void setVo(PriceCorpSectionVo vo) {
		this.vo = vo;
	}
}
