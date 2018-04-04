package com.hoau.miser.module.biz.base.server.action;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityVo;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * @ClassName:
 * @Description: 价格城市维护
 * @author Chenyl yulin.chen@hoau.net
 * @date: 2016/3/9 18:04
 */
@Controller
@Scope("prototype")
public class PriceCityAction extends AbstractAction {

	@Resource
	IPriceCityService priceCityService;

	private PriceCityVo priceCityVo;

	public String index() {
		return "index";
	}

	/**
	 * @Title: queryPriceCities
	 * @Description: 查询价格城市列表
	 * @return String 返回类型
	 */
	@JSON
	public String queryPriceCities() {
		ArrayList<PriceCityEntity> priceCityEntities = priceCityService
				.queryPriceCities(priceCityVo, limit, start);
		priceCityVo.setPriceCityEntities(priceCityEntities);
		Long count = priceCityService.queryPriceCityCount(priceCityVo);
		totalCount = count == null ? 0l : count;
		return returnSuccess();
	}

	/**
	 * @Title: queryPriceCityById
	 * @Description: 查询价格城市列表
	 * @return String 返回类型
	 */
	public String queryPriceCityById() {
		PriceCityEntity priceCityEntity = priceCityService
				.queryPriceCityById(priceCityVo);
		priceCityVo.setPriceCityEntity(priceCityEntity);
		return returnSuccess();
	}

	/**
	 * @Title: updatePriceCity
	 * @Description: 更新价格城市
	 * @return String 返回类型
	 */
	public String updatePriceCity() {
		try {
			priceCityVo.getQueryParam().setPriceCityScope("STANDARD");
			priceCityService.updatePriceCity(priceCityVo);
		} catch (BusinessException exception) {
			return returnError(exception);
		}
		return returnSuccess(MessageType.UPDATE_SUCCESS);
	}
	
	/**
	 * 
	 * @Description: TODO价格城市到出
	 * @param @return   
	 * @return String 
	 * @throws IOException 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年4月11日
	 */
	public String excelExport() throws IOException {
		try{
			ArrayList<PriceCityEntity> priceCityEntities = priceCityService.excelPriceCity(priceCityVo);
			priceCityVo.setPriceCityEntities(priceCityEntities);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String filePath = priceCityService.createExcelFile(priceCityEntities);
			JSONObject json = new JSONObject();
			// filePath:错误的信息的文件地址
			json.put("filePath", filePath);
			json.put("count",priceCityEntities != null ? priceCityEntities.size(): 0);
			out.println(json.toString());
			out.flush();
			out.close();
		return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	public void excelImpl() throws Exception{
		PrintWriter out  = null;
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext()
					.getRealPath(priceCityVo.getFilePath());
			Map<String, Object> returnMap = priceCityService.bathImplPriceCity(uploadPath,"STANDARD");;
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

	/**
	 * @Title: invalidPriceCity
	 * @Description: 删除价格城市
	 * @return String 返回类型
	 */
	public String invalidPriceCity() {
		priceCityService.invalidPriceCity(priceCityVo);
		return returnSuccess(MessageType.DELETE_SUCCESS);
	}

	public PriceCityVo getPriceCityVo() {
		return priceCityVo;
	}

	public void setPriceCityVo(PriceCityVo priceCityVo) {
		this.priceCityVo = priceCityVo;
	}
}
