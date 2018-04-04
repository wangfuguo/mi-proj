/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.server.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceCollectDeliveryFeeService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceCollectDeliveryFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceCollectDeliveryFeeVo;

/**
 * 代收货款手续费管理
 * @author dengyin
 */
@Controller
@Scope("prototype")
public class PriceCollectDeliveryFeeAction extends AbstractAction{

	private static final long serialVersionUID = 4447011212955096825L;
	
	
	private PriceCollectDeliveryFeeVo priceCollectDeliveryFeeVo;
	
	@Resource
	private IPriceCollectDeliveryFeeService priceCollectDeliveryFeeService;
	
	@JSON
	public String index(){
		return "index";
	}
	
	@JSON
	public String query(){
		
		try {
			
			if(priceCollectDeliveryFeeVo == null){
				PriceCollectDeliveryFeeEntity entity = new PriceCollectDeliveryFeeEntity();
				List<PriceCollectDeliveryFeeEntity> entityList = new ArrayList<PriceCollectDeliveryFeeEntity>();
				priceCollectDeliveryFeeVo = new PriceCollectDeliveryFeeVo();
				priceCollectDeliveryFeeVo.setEntity(entity);
				priceCollectDeliveryFeeVo.setEntityList(entityList);
			}
			
			List<PriceCollectDeliveryFeeEntity> entityList = priceCollectDeliveryFeeService.queryListByEntity(priceCollectDeliveryFeeVo.getEntity(), start, limit);
			priceCollectDeliveryFeeVo.setEntityList(entityList);
			
			Long totalCount = priceCollectDeliveryFeeService.queryCountByEntity(priceCollectDeliveryFeeVo.getEntity());
			setTotalCount(totalCount);
			
		} catch (Exception e) {
			return returnError(e.toString());
		}
		return returnSuccess();
	}
	
	@JSON
	public String queryEntityById(){
		try{
			List<PriceCollectDeliveryFeeEntity> entityList = priceCollectDeliveryFeeService.queryListByEntity(priceCollectDeliveryFeeVo.getEntity(), 0, 1);
			priceCollectDeliveryFeeVo.setEntity(entityList == null && entityList.size() == 0 ? null:entityList.get(0));
		} catch (Exception e) {
			return returnError(e.toString());
		}
		return returnSuccess();
    }
	
	@JSON
	public String add(){
		try {
			Integer result = priceCollectDeliveryFeeService.add(priceCollectDeliveryFeeVo.getEntity(),priceCollectDeliveryFeeVo.getConfirm() == 1 ? true : false);
			
			if(result.intValue()==0){
				return returnSuccess(MessageType.COLLECT_DELIVERY_ISCONFIRM);
			}else if(result.intValue()==1){
				return returnSuccess(MessageType.ADD_SUCCESS);
			}else{
				return returnSuccess(MessageType.STATE_CHANGE);
			}
		} catch (Exception e) {
			return returnError(e.toString());
		}
	}
	
	
	@JSON
	public String update(){
		try {
			Integer result = priceCollectDeliveryFeeService.update(priceCollectDeliveryFeeVo.getEntity(),priceCollectDeliveryFeeVo.getConfirm() == 1 ? true :false);
			
			
			if(result.intValue()==0){
				return returnSuccess(MessageType.COLLECT_DELIVERY_ISCONFIRM);
			}else if(result.intValue()==1){
				return returnSuccess(MessageType.ADD_SUCCESS);
			}else{
				return returnSuccess(MessageType.STATE_CHANGE);
			}
			
		} catch (Exception e) {
			return returnError(e.toString());
		}
	}	
	
	
	@JSON
	public String delete(){
		try {
			priceCollectDeliveryFeeService.delete(priceCollectDeliveryFeeVo.getSelectedIdStr());
		} catch (Exception e) {
			return returnError(e.toString());
		}
		return returnSuccess(MessageType.DELETE_SUCCESS);
	}
	
	@JSON
	public String excelExport(){
		PrintWriter out = null;
		try {

			List<PriceCollectDeliveryFeeEntity> entityList = priceCollectDeliveryFeeService.queryListByEntity2(priceCollectDeliveryFeeVo.getEntity(), RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String filePath = priceCollectDeliveryFeeService.createExcelFile(entityList);
			JSONObject json = new JSONObject();
			
			json.put("filePath", filePath);
			json.put("count", entityList != null ? entityList.size() : 0);
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();

		} catch (Exception e) {
			return returnError(e.toString());
		} finally{
			if(null != out){
				out.close();
			}
		}
	}
	
	@JSON
	public String excelImport() throws Exception{
		
		PrintWriter out = null;
		boolean isClosed = false;
		try {
			 HttpServletResponse response=ServletActionContext.getResponse();
			 response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext().getRealPath(priceCollectDeliveryFeeVo.getFilePath());
			Map<String,Object> returnMap= priceCollectDeliveryFeeService.excelImport(uploadPath);
			JSONObject json=new JSONObject();

			json.put("error", returnMap.get("error"));
			json.put("success", returnMap.get("success"));
			out.println(json.toString());
			out.flush();
			out.close();
			isClosed = true;
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		} finally {
			if( out != null && isClosed == false){
				out.close();
			}
		}
	}

	public PriceCollectDeliveryFeeVo getPriceCollectDeliveryFeeVo() {
		return priceCollectDeliveryFeeVo;
	}

	public void setPriceCollectDeliveryFeeVo(
			PriceCollectDeliveryFeeVo priceCollectDeliveryFeeVo) {
		this.priceCollectDeliveryFeeVo = priceCollectDeliveryFeeVo;
	}
}
