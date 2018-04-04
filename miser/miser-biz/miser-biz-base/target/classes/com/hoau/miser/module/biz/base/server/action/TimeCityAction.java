package com.hoau.miser.module.biz.base.server.action;

import java.io.IOException;
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
import com.hoau.miser.module.biz.base.api.server.service.ITimeCityService;
import com.hoau.miser.module.biz.base.api.shared.domain.TimeCityEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.api.shared.vo.TimeCityVo;

/**
 * 时效城市定义
 * @author 何羽
 *
 */
@Controller
@Scope("prototype")
public class TimeCityAction extends AbstractAction {

	private static final long serialVersionUID = 1642891990934701776L;
	
	@Resource
	private ITimeCityService timeCityService;
	
	private TimeCityVo timeCityVo;

	public TimeCityVo getTimeCityVo() {
		return timeCityVo;
	}

	public void setTimeCityVo(TimeCityVo timeCityVo) {
		this.timeCityVo = timeCityVo;
	}

	public String index() {
		return "index";
	}
	
	/**
	 * 查询时效城市列表
	 * @return
	 */
	@JSON
	public String query(){
		if(timeCityVo == null){
			timeCityVo = new TimeCityVo();
			timeCityVo.setTimeCityEntity(new TimeCityEntity());
		}
		TimeCityEntity entity = timeCityVo.getTimeCityEntity();
		List<TimeCityEntity> list = timeCityService.queryByEntity(entity, limit, start);
		timeCityVo.setTimeCityList(list);
		Long count = timeCityService.countOfTimeCity(entity);
		totalCount = count == null ? 0l : count;
		return returnSuccess();
	}
	
	/**
	 * 通过ID查询时效城市
	 * @return
	 */
	@JSON
	public String queryById(){
		if(timeCityVo == null){
			timeCityVo = new TimeCityVo();
			timeCityVo.setTimeCityEntity(new TimeCityEntity());
		}
		TimeCityEntity entity = timeCityService.queryById(timeCityVo.getTimeCityEntity());
		timeCityVo.setTimeCityEntity(entity);
		return returnSuccess();
	}
	
	/**
	 * 更新时效城市信息
	 * @return
	 */
	@JSON
	public String updateTimeCity(){
		try {
			timeCityService.updatetimeCity(timeCityVo.getTimeCityEntity());
		} catch (BusinessException exception) {
			return returnError(exception);
		}
		return returnSuccess(MessageType.UPDATE_SUCCESS);
	}
	
	/**
	 * 作废时效城市
	 */
	@JSON
	public String invalidTimeCity(){
		try {
			timeCityService.invalidTimeCity(timeCityVo.getTimeCityEntity());
		} catch (BusinessException exception) {
			return returnError(exception);
		}
		return returnSuccess(MessageType.DELETE_SUCCESS);
	}
	
	/**
	 * 导出时效城市
	 * @return
	 * @throws IOException
	 */
	public String excelExport() throws IOException {
		try{
			ArrayList<TimeCityEntity> list = timeCityService.excelTimeCity(timeCityVo.getTimeCityEntity());
			timeCityVo.setTimeCityList(list);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String filePath = timeCityService.createExcelFile(list);
			JSONObject json = new JSONObject();
			// filePath:错误的信息的文件地址
			json.put("filePath", filePath);
			json.put("count",list != null ? list.size(): 0);
			out.println(json.toString());
			out.flush();
			out.close();
		return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 导入时效城市
	 * @throws Exception
	 */
	public void excelImpl() throws Exception{
		PrintWriter out  = null;
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext()
					.getRealPath(timeCityVo.getFilePath());
			Map<String, Object> returnMap = timeCityService.bathImplTimeCity(uploadPath);;
			JSONObject json = new JSONObject();
			// succCount:成功数量,failCount:失败数量,list: 导入的客户价格明细
			json.put("succCount", returnMap.get("succCount"));
			json.put("failCount", returnMap.get("failCount"));
			json.put("repeatTip", returnMap.get("error"));
			json.put("filePath",returnMap.get("fileUrl"));
			out.println(json.toString());
			out.flush();
			out.close();
			//return returnSuccess();
		} catch (BusinessException e) {
			//return returnError(e);
		} finally {
			if(null != out ){
				out.close();
			}
		}
	}
}
