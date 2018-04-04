package com.hoau.miser.module.biz.pricecard.server.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceStandardSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;

/**
 * @ClassName: PriceStandardSectionAction
 * @Description: 易入户标准价格管理Action
 * @author zhangqingfu
 * @date 2016年5月4日 下午4:18:07
 *
 */
@Controller
@Scope("prototype")
public class PriceStandardSectionAction extends AbstractAction {

	private static final long serialVersionUID = 8138446890167894317L;

	@Resource
	IPriceStandardSectionService priceStandardSectionService;

	PriceStandardSectionVo priceStandardSectionVo;

	/**
	 * @Title: index
	 * @Description: 易入户标准价格主页
	 * @return String
	 */
	public String index() {
		return "index";
	}

	/**
	 * @Title: queryPageByCondition
	 * @Description: 易入户标准价格分页结果集
	 * @return String
	 */
	@JSON
	public String queryPageByCondition() {
		try {
			if (null == priceStandardSectionVo) {
				priceStandardSectionVo = new PriceStandardSectionVo();
			}
			List<PriceStandardSectionEntity> priceStandardSectionList = priceStandardSectionService
					.queryPageByCondition(priceStandardSectionVo, limit, start);
			setTotalCount(priceStandardSectionService.queryCountByCondition(priceStandardSectionVo));
			priceStandardSectionVo.setPriceStandardSectionList(priceStandardSectionList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * @Title: queryById
	 * @Description: 根据ID查询易入户标准价格结果
	 * @return String
	 */
	@JSON
	public String queryById() {
		try {
			priceStandardSectionVo
					.setPriceStandardSectionEntity(this.priceStandardSectionService.queryById(priceStandardSectionVo));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * @Title: checkRecord
	 * @Description: 检查是否存在一条相同的有效记录
	 * @return String
	 */
	@JSON
	public String checkRecord() {
		try {
			priceStandardSectionVo
					.setPriceStandardSectionList(this.priceStandardSectionService.checkRecord(priceStandardSectionVo));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * @Title: add
	 * @Description: 新增
	 * @return String
	 */
	@JSON
	public String add() {
		try {
			Integer i = this.priceStandardSectionService.update(priceStandardSectionVo, MiserUserContext.getCurrentUser().getEmpNameAndUserName(), new Date());
			if (i.intValue() > 0) {
				return returnSuccess(MessageType.ADD_SUCCESS);
			} else {
				return returnSuccess(MessageType.STATE_CHANGE);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * @Title: update
	 * @Description: 修改
	 * @return String
	 */
	@JSON
	public String update() {
		try {
			Integer i = this.priceStandardSectionService.update(priceStandardSectionVo,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),new Date());
			if (i.intValue() > 0) {
				return returnSuccess(MessageType.UPDATE_SUCCESS);
			} else {
				return returnSuccess(MessageType.STATE_CHANGE);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * @Title: delete
	 * @Description: 删除
	 * @return String
	 */
	@JSON
	public String delete() {
		try {
			Integer i = this.priceStandardSectionService.delete(priceStandardSectionVo,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),new Date());
			if (i > 0) {
				return returnSuccess(MessageType.DELETE_SUCCESS);
			} else {
				return returnSuccess(MessageType.STATE_CHANGE);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * @Title: export
	 * @Description: 导出
	 * @return void
	 */
	public void export() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			try {
				if (priceStandardSectionVo == null)
					priceStandardSectionVo = new PriceStandardSectionVo();
				ExcelExportResultEntity resultEntity = priceStandardSectionService.export(priceStandardSectionVo);
				JSONObject json = new JSONObject();
				json.put("filePath", resultEntity.getFilePath());
				json.put("count", resultEntity.getRecordCount());
				out.println(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: importExcel
	 * @Description: 导入
	 * @return void
	 */
	public void importExcel() {
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			try {
				String uploadPath = ServletActionContext.getServletContext()
						.getRealPath(priceStandardSectionVo.getFilePath());
				Map<String, Object> returnMap = this.priceStandardSectionService.importExcel(uploadPath);
				JSONObject json = new JSONObject();
				//addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
				json.put("addSize", returnMap.get("addSize"));
				json.put("coverSize", returnMap.get("coverSize"));
				json.put("sumSize", returnMap.get("sumSize"));
				json.put("errorSize", returnMap.get("errorSize"));
				json.put("filePath", returnMap.get("filePath"));
				out.println(json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PriceStandardSectionVo getPriceStandardSectionVo() {
		return priceStandardSectionVo;
	}

	public void setPriceStandardSectionVo(PriceStandardSectionVo priceStandardSectionVo) {
		this.priceStandardSectionVo = priceStandardSectionVo;
	}

}
