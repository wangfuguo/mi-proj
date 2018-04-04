/**   
* @Title: DiscountCorpAction.java 
* @Package com.hoau.miser.module.biz.discount.server.action 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 陈启勇 
* @date 2016年1月5日 下午4:23:06 
* @version V1.0   
*/
package com.hoau.miser.module.biz.discount.server.action;

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
import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.discount.api.server.service.IDiscountCorpService;
import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCorpEntity;
import com.hoau.miser.module.biz.discount.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountCorpVo;

/**
 * ClassName: DiscountCorpAction 
 * @author 陈启勇
 * @date 2016年1月5日
 * @version V1.0   
 */

@Controller
@Scope("prototype")
public class DiscountCorpAction extends AbstractAction {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 4755611302726280475L;
	@Resource
	private IDiscountCorpService discountCorpService;
	
	private DiscountCorpVo discountCorpVo;

	public String execute(){
		return "index";
	}
	
	/**
	 * 
	 * @Description: 查询网点折扣
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月5日
	 */
	@JSON
	public String queryListByParam() {
		try {
			if(discountCorpVo == null){
				discountCorpVo=new DiscountCorpVo();
			}
			discountCorpVo.getDiscountCorpEntity().setNowDate(new Date());
			String state = discountCorpVo.getDiscountCorpEntity().getState();
			if(StringUtil.isEmpty(state)){
				discountCorpVo.getDiscountCorpEntity().setState("5");
			}
			List<DiscountCorpEntity> discountCorpList = discountCorpService.queryListByParam(discountCorpVo, limit, start);
			totalCount = discountCorpService.queryCountByParam(discountCorpVo);
			discountCorpVo.setDiscountCorpList(discountCorpList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 增加网店折扣
	 * @Description: addDiscountCorp
	 * @return String  
	 * @Author 陈启勇
	 * @Time 2016年1月6日
	 */
	@JSON
	public String addDiscountCorp(){
		try{
			DiscountCorpEntity discountCorpEntity = discountCorpVo.getDiscountCorpEntity();
			Integer result = discountCorpService.addDiscountCorp(discountCorpEntity,"1".equals(discountCorpVo.getIsConfirm()));
			if(result.intValue() == 0){
				return returnSuccess(MessageType.CORPSTANDARD_ISCONFIRM);
			}else if(result.intValue() == 2){
				return returnSuccess(MessageType.CORP_VERIFICATION);
			}else if(result.intValue() == 3){
				return returnSuccess(MessageType.TIME_ISCONFIRM);
			}else{
				return returnSuccess(MessageType.ADD_SUCCESS);
			}
			
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 根据id得到网点折扣信息
	 * @return String  
	 * @Author 陈启勇
	 * @Time 2016年1月7日
	 */
	@JSON
	public String queryById() {
		try {
			DiscountCorpEntity discountCorpEntity = new DiscountCorpEntity();
			discountCorpEntity = discountCorpService.queryById(discountCorpVo.getDiscountCorpEntity().getId());
			discountCorpVo.setDiscountCorpEntity(discountCorpEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	@JSON
	public String queryAddById() {
		try {
			DiscountCorpEntity discountCorpEntity = new DiscountCorpEntity();
			String id = discountCorpVo.getDiscountCorpEntity().getId();
			if(StringUtil.isNotEmpty(id)){
				DiscountCorpEntity bean = new DiscountCorpEntity();
				bean = discountCorpService.queryById(id);	
				discountCorpEntity.setBigRegion(bean.getBigRegion());
				discountCorpEntity.setDivision(bean.getDivision());
				discountCorpEntity.setRoadArea(bean.getRoadArea());
				discountCorpEntity.setOrgCodeName(bean.getOrgCodeName());
				discountCorpEntity.setOrgCode(bean.getOrgCode());
				discountCorpEntity.setTransTypeCode(bean.getTransTypeCode());
				discountCorpEntity.setTransTypeName(bean.getTransTypeName());
			}
			discountCorpVo.setDiscountCorpEntity(discountCorpEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 修改网点折扣
	 * @Description: updateDiscountCorp
	 * @return String  
	 * @Author 陈启勇
	 * @Time 2016年1月8日
	 */
	@JSON
	public String updateDiscountCorp(){
		try{
			DiscountCorpEntity discountCorpEntity = discountCorpVo.getDiscountCorpEntity();
			Integer result = discountCorpService.updateDiscountCorp(discountCorpEntity,"1".equals(discountCorpVo.getIsConfirm()));
			if(result.intValue() == 0){
				return returnSuccess(MessageType.CORPSTANDARD_ISCONFIRM);
			}else if(result.intValue() == 3){
				return returnSuccess(MessageType.TIME_ISCONFIRM);
			}else{
				return returnSuccess(MessageType.UPDATE_SUCCESS);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	
	/**
	 * 作废网点折扣
	 * @Description: deleteDiscountCorp
	 * @return String  
	 * @Author 陈启勇
	 * @Time 2016年1月8日
	 */
	@JSON
	public String deleteDiscountCorp(){
		try{
			
			List<DiscountCorpEntity> list = JsonUtils.toList(discountCorpVo.selects, DiscountCorpEntity.class);
			discountCorpService.bathDelDiscountCorp(list);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @Description: excel导出
	 * @param @return
	 * @param @throws Exception   
	 * @return String 
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月18日
	 */
	public String exportExcel(){
		try {
			
//			if(discountCorpVo == null){
//				discountCorpVo=new DiscountCorpVo();
//			}
//			List<DiscountCorpEntity> discountCorpList=discountCorpService.excelQueryListByParam(discountCorpVo);
			
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
			}
			//String  filePath= discountCorpService.createExcelFile(discountCorpList);
			String  filePath= discountCorpService.createExcelFile();
			JSONObject json=new JSONObject();
			//filePath:错误的信息的文件地址
			json.put("filePath", filePath); 
			//json.put("count",discountCorpList!=null? discountCorpList.size():0); 
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
	 * @Description: excel导入方法
	 * @param @return
	 * @param @throws Exception   
	 * @return String 
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月19日
	 */
	public String implExcel(){
		try {
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
			}
			String uploadPath = ServletActionContext.getServletContext().getRealPath(discountCorpVo.getFilePath());
			Map<String, Object> returnMap = null;
			try {
				returnMap = discountCorpService.bathImplDiscountCorp(uploadPath);
			} catch (Exception e) {
			}
			JSONObject json=new JSONObject();
			//addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
			json.put("addSize", returnMap.get("addSize"));
			json.put("coverSize", returnMap.get("coverSize"));
			json.put("sumSize", returnMap.get("sumSize"));
			json.put("filePath", returnMap.get("filePath"));
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
		
	}
	/**
	 * @return the discountCorpVo
	 */
	public DiscountCorpVo getDiscountCorpVo() {
		return discountCorpVo;
	}

	/**
	 * @param discountCorpVo the discountCorpVo to set
	 */
	public void setDiscountCorpVo(DiscountCorpVo discountCorpVo) {
		this.discountCorpVo = discountCorpVo;
	}
}
