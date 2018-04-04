package com.hoau.miser.module.biz.pricecard.server.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardVo;

/**
 * 
 * @Description: 标准价格Action
 * @Author 廖文强
 * @Date 2015年12月15日
 */
@Controller
@Scope("prototype")
public class PriceStandardAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource 
	private IPriceStandardService priceStandardService;
	
	private PriceStandardVo priceStandardVo;
	
	public String execute(){
		return "index";
	}
	
	/**
	 * 
	 * @Description: excel导入方法
	 * @param @return
	 * @param @throws Exception   
	 * @return String 
	 * @throws
	 * @author 廖文强
	 * @date 2015年12月31日
	 */
	public void excelImpl() throws Exception{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			String uploadPath = ServletActionContext.getServletContext().getRealPath(priceStandardVo.getFilePath());
			Map<String,Object> returnMap= priceStandardService.bathImplPriceStandards(uploadPath);
			JSONObject json=new JSONObject();
			//addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
			json.put("addSize", returnMap.get("addSize"));
			json.put("coverSize", returnMap.get("coverSize"));
			json.put("sumSize", returnMap.get("sumSize"));
			json.put("errorSize", returnMap.get("errorSize"));
			json.put("filePath", returnMap.get("filePath"));
			out.println(json.toString());
			out.flush();
			out.close();
			//return returnSuccess();
		} catch (BusinessException e) {
			//return returnError(e);
			out.close();
		}
		
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
	public void excelExport() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			if (priceStandardVo == null) priceStandardVo = new PriceStandardVo();
			ExcelExportResultEntity resultEntity = priceStandardService.createExcelFile(priceStandardVo);
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
	
	@JSON
	public String queryListByParam() {
		try {
			if(priceStandardVo==null)priceStandardVo=new PriceStandardVo();
			/*PriceStandardEntity pse=new PriceStandardEntity();
			pse.setActive(MiserConstants.ACTIVE);
			priceStandardVo.setPriceStandardEntity(pse);*/
			List<PriceStandardEntity> priceStandardList=priceStandardService.queryListByParam(priceStandardVo, limit, start);
			totalCount=priceStandardService.queryCountByParam(priceStandardVo);
			priceStandardVo.setPriceStandardList(priceStandardList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 根据id得到价格表
	 * @return String  
	 * @Author 廖文强
	 * @Time 2015年12月17日下午7:09:24
	 */
	@JSON
	public String queryPriceStandardById() {
		try {
			priceStandardVo.setPriceStandardEntity(priceStandardService.queryPriceStandardById(priceStandardVo.getPriceStandardEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}
	/**
	 * 增加价格
	 * @Description: addPriceStandard
	 * @return String  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午5:56:54
	 */
	@JSON
	public String addPriceStandard(){
		try{
			Integer result=priceStandardService.addPriceStandard(priceStandardVo.getPriceStandardEntity(),"1".equals(priceStandardVo.getIsConfirm()));
			if(result.intValue()==0){
				return returnSuccess(MessageType.PRICESTANDARD_ISCONFIRM);
			}else if(result.intValue()==1){
				return returnSuccess(MessageType.ADD_SUCCESS);
			}else if(result.intValue()==-1){
				return returnSuccess(MessageType.TIME_LIMIT);
			}else{
				return returnSuccess(MessageType.STATE_CHANGE);
			}
			
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 修改价格
	 * @Description: updatePriceStandard
	 * @return String  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午5:57:10
	 */
	@JSON
	public String updatePriceStandard(){
		try{
			Integer result=priceStandardService.updatePriceStandard(priceStandardVo.getPriceStandardEntity(),"1".equals(priceStandardVo.getIsConfirm()));
			if(result.intValue()==0){
				return returnSuccess(MessageType.PRICESTANDARD_ISCONFIRM);
			}else if(result.intValue()==1){
				return returnSuccess(MessageType.UPDATE_SUCCESS);
			}else if(result.intValue()==-1){
				return returnSuccess(MessageType.TIME_LIMIT);
			}else{
				return returnSuccess(MessageType.STATE_CHANGE);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 删除价格
	 * @Description: deletePriceStandard
	 * @return String  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午5:57:20
	 */
	@JSON
	public String deletePriceStandard(){
		try{
			
			List<PriceStandardEntity> list= JsonUtils.toList(priceStandardVo.selects, PriceStandardEntity.class);
			int result=priceStandardService.bathDelPriceStandard(list);
			if(result==1){
				return returnSuccess(MessageType.DELETE_SUCCESS);
			}else{
				return returnSuccess(MessageType.STATE_CHANGE);
			}
			
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	public PriceStandardVo getPriceStandardVo() {
		return priceStandardVo;
	}
	public void setPriceStandardVo(PriceStandardVo priceStandardVo) {
		this.priceStandardVo = priceStandardVo;
	}
	
	
}
