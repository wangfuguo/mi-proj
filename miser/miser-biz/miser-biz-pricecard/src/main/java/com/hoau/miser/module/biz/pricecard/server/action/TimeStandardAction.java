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
import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.miser.module.biz.pricecard.api.server.service.ITimeStandartService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.TimeStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.TimeStandardVo;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;

/**
 * 
 * @Description: 时效价格Action
 * @Author Liwy
 * @Date 2015年12月15日
 */
@Controller
@Scope("prototype")
public class TimeStandardAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4213634437004527830L;

	private TimeStandardVo timeStandardVo;

	@Resource
	private ITimeStandartService timeStandartService;

	public String execute() {
		return "index";
	}

	@JSON
	public String queryListByParam() {
		try {
			if (timeStandardVo == null)
				timeStandardVo = new TimeStandardVo();
			List<TimeStandardEntity> timeStandardList = timeStandartService
					.queryListByParam(timeStandardVo, limit, start);
			totalCount = timeStandartService.queryCountByParam(timeStandardVo);
			timeStandardVo.setTimeStandardList(timeStandardList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	@JSON
	public String addTimeStandard() {
		try {
			Integer result = timeStandartService.addTimeStandard(
					timeStandardVo.getTimeStandardEntity(),
					"1".equals(timeStandardVo.getIsConfirm()));
			if (result.intValue() == 0) {
				return returnSuccess(MessageType.PRICESTANDARD_ISCONFIRM);
			} else if (result.intValue() == 1) {
				return returnSuccess(MessageType.ADD_SUCCESS);
			} else if (result.intValue() == -1) {
				return returnSuccess(MessageType.TIME_LIMIT);
			} else {
				return returnSuccess(MessageType.STATE_CHANGE);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	@JSON
	public String queryTimeStandardById() {
		try {
			timeStandardVo.setTimeStandardEntity(timeStandartService
					.queryTimeStandardById(timeStandardVo
							.getTimeStandardEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	@JSON
	public String updateTimeStandard() {
		try {
			Integer result = timeStandartService.updateTimeStandard(
					timeStandardVo.getTimeStandardEntity(),
					"1".equals(timeStandardVo.getIsConfirm()));
			if (result.intValue() == 0) {
				return returnSuccess(MessageType.PRICESTANDARD_ISCONFIRM);
			} else if (result.intValue() == 1) {
				return returnSuccess(MessageType.UPDATE_SUCCESS);
			} else if (result.intValue() == -1) {
				return returnSuccess(MessageType.TIME_LIMIT);
			} else {
				return returnSuccess(MessageType.STATE_CHANGE);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	@JSON
	public String deleteTimeStandard() {
		try {
			List<TimeStandardEntity> list = JsonUtils.toList(
					timeStandardVo.selects, TimeStandardEntity.class);
			int result = timeStandartService.bathDelTimeStandard(list);
			if (result == 1) {
				return returnSuccess(MessageType.DELETE_SUCCESS);
			} else {
				return returnSuccess(MessageType.STATE_CHANGE);
			}

		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	public void excelExport() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			if (timeStandardVo == null) timeStandardVo = new TimeStandardVo();
			ExcelExportResultEntity resultEntity = timeStandartService.createExcelFile(timeStandardVo);
			if (resultEntity == null) {
				throw new BusinessException("导出错误--查询条件无效");
			}
			JSONObject json = new JSONObject();
			//filePath:错误的信息的文件地址
			json.put("filePath", resultEntity.getFilePath());
			json.put("count", resultEntity.getRecordCount());
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
	
	public void excelImpl() throws Exception{
		try {
			HttpServletResponse response=ServletActionContext.getResponse();
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext().getRealPath(timeStandardVo.getFilePath());
			Map<String,Object> returnMap= timeStandartService.bathImplTimeStandards(uploadPath);
			JSONObject json=new JSONObject();
			//addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
			json.put("addSize", returnMap.get("addSize"));
			json.put("coverSize", returnMap.get("coverSize"));
			json.put("sumSize", returnMap.get("sumSize"));
			json.put("filePath", returnMap.get("filePath"));
			out.println(json.toString());
			out.flush();
			out.close();
			//return returnSuccess();
		} catch (BusinessException e) {
			//return returnError(e);
		}
	}
	
	public TimeStandardVo getTimeStandardVo() {
		return timeStandardVo;
	}

	public void setTimeStandardVo(TimeStandardVo timeStandardVo) {
		this.timeStandardVo = timeStandardVo;
	}

}
