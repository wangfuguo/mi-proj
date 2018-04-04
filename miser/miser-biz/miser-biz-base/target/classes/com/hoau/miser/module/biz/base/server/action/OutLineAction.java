/**
 * 
 */
package com.hoau.miser.module.biz.base.server.action;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.hoau.miser.module.biz.base.api.server.service.IOutLineService;
import com.hoau.miser.module.biz.base.api.shared.domain.OutLineEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.api.shared.vo.OutLineVo;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 外发偏线系统管理 2016-3-17 20:09:07
 * 
 * @author dengyin
 *
 */
@Controller
@Scope("prototype")
public class OutLineAction extends AbstractAction {
	private static final long serialVersionUID = 3471133211181649225L;

	@Resource
	private IOutLineService outLineService;

	private OutLineVo outLineVo;

	public String index() {
		return "index";
	}

	@JSON
	public String queryOutLineList() {

		try {
			if (outLineVo == null) {
				outLineVo = new OutLineVo();
				outLineVo.setOutLineEntity(new OutLineEntity());
				outLineVo.setOutLineEntities(new ArrayList<OutLineEntity>());
			}

			OutLineEntity paramOutLineEntity = outLineVo.getOutLineEntity();
			paramOutLineEntity.setActive(MiserConstants.ACTIVE);

			List<OutLineEntity> outLineEntities = outLineService
					.queryOutLineByEntity(paramOutLineEntity, limit, start);

			if (outLineEntities != null) {
				outLineVo.setOutLineEntities(outLineEntities);
				setTotalCount(outLineService
						.countOfOutLineByEntity(paramOutLineEntity));
			}

		} catch (BusinessException e) {
			return returnError(e.toString());
		}

		return returnSuccess();
	}

	@JSON
	public String queryOutLineInUpdate() {
		try {

			List<OutLineEntity> outLineEntities = outLineService
					.queryOutLineByEntity(getOutLineVo().getOutLineEntity(), 1,
							0);
			if (outLineEntities != null && outLineEntities.size() > 0) {
				getOutLineVo().setOutLineEntity(outLineEntities.get(0));
			}

		} catch (BusinessException e) {
			return returnError(e.toString());
		}
		return returnSuccess();
	}
	
	public String updateOutLine(){
		
		try {
			
			if(getOutLineVo().getOutLineEntity() != null){
				outLineService.updateByEntity(getOutLineVo().getOutLineEntity());
			}
			
		} catch (BusinessException e) {
			return returnError(e.toString());
		}
		return returnSuccess(MessageType.UPDATE_SUCCESS);
	}

	/**
	 * 导出
	 */
	@JSON
	public String excelExport() throws Exception {
		try {

			List<OutLineEntity> outLineList = outLineService
					.excelQueryOutLineByEntity(getOutLineVo()
							.getOutLineEntity());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String filePath = outLineService.createExcelFile(outLineList);
			JSONObject json = new JSONObject();
			// filePath:错误的信息的文件地址
			json.put("filePath", filePath);
			json.put("count", outLineList != null ? outLineList.size() : 0);
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();

		} catch (BusinessException e) {
			return returnError(e.toString());
		}
	}
	
   /*
    *导入
    */
	public String outLineImport() throws Exception {
		PrintWriter out = null;
		try {
			 HttpServletResponse response=ServletActionContext.getResponse();
			 response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext().getRealPath(outLineVo.getFilePath());
			Map<String,Object> returnMap= outLineService.excelImport(uploadPath);
			JSONObject json=new JSONObject();

			json.put("error", returnMap.get("error"));
			json.put("success", returnMap.get("success"));
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		} finally {
			if( out != null){
				out.close();
			}
		}
	}

	/**
	 * 添加偏线价格作废功能
	 * @return
	 */
	@JSON
	public String destrory(){
		
		try {
			outLineService.destroy(outLineVo.getDestoryIdStr());
		} catch (Exception e) {
			return returnError(e.toString());
		}
		return returnSuccess(MessageType.DELETE_SUCCESS);
		
	}
	public OutLineVo getOutLineVo() {
		return outLineVo;
	}

	public void setOutLineVo(OutLineVo outLineVo) {
		this.outLineVo = outLineVo;
	}

}
