package com.hoau.miser.module.biz.pricecard.server.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCorpService;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpGirdVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpVo;

@Controller
@Scope("prototype")
public class PriceCorpAction extends AbstractAction {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 5771853944341450365L;

	private PriceCorpVo priceCorpVo;
	
	/**
	 * @Param priceCorpGirdVo 接受数据（excel导入,导出时）
	 */
	private PriceCorpGirdVo priceCorpGirdVo;

	@Resource
	private IPriceCorpService priceCorpService;

	public String execute() {
		return "index";
	}
	
	/**
	 * 
	 * @Description: TODO查询网点专属价格
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月12日
	 */
	@JSON
	public String queryBySearch() {
		try {
			if (priceCorpVo == null)
				priceCorpVo = new PriceCorpVo();
			List<PriceCorpGirdVo> prcGridVoList = priceCorpService.queryListByParam(priceCorpVo, limit, start);
			priceCorpVo.setGridVoList(prcGridVoList);
			setTotalCount((long) priceCorpService.queryListByParamCount(priceCorpVo));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}

	/**
	 * 
	 * @Description: TODO保存网点信息
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月14日
	 */
	@JSON
	public String savePriceCorp(){
		try{
			if(priceCorpVo == null )
				return returnError("没有内容需要保存");
			//单条插入
			//iprcService.savePriceCorpDetialInfo(priceCorpVo.getGridVoList());
			priceCorpService.insertBatch(priceCorpVo);
			return returnSuccess("成功");
		}catch(BusinessException e){
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Description: TODO作废
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月14日
	 */
	@JSON
	public String deletePriceCorp(){
		try{
			if(priceCorpVo == null){
				return returnError("作废删除内容");
			}
			List<PriceCorpGirdVo> list= JsonUtils.toList(priceCorpVo.getSelects(), PriceCorpGirdVo.class);
			if(priceCorpService.cancelByPrimaryKey(list) == list.size()){
				return returnSuccess("成功作废:" + list.size() + "条");
			}
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @Description: TODO 获取系统时间
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月20日
	 */
	@JSON
	public String dateTimeToServer(){
		try{
			priceCorpVo.setCurrentTime(priceCorpService.queryCurrentDateTime());
			return returnSuccess();
		}catch(BusinessException ex){
			return returnError(ex);
		}
	}
	/**
	 * 
	 * @Description: TODO 单条详细信息
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月21日
	 */
	@JSON
	public String searchPriceCorp(){
		try{
			if(priceCorpVo == null){
				return returnError("没有需要修改的内容");
			}
			List<PriceCorpGirdVo> pcg= priceCorpService.findByPriceCorpGirdVo(priceCorpVo.getGridVo());
			if(pcg.size()>0){
				priceCorpVo.setGridVo(pcg.get(0));
			}
			priceCorpVo.setCurrentTime(priceCorpService.queryCurrentDateTime());
			priceCorpVo.setGridVoList(pcg);
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/**
	 * 去掉get打头方法，否则会自行get打头的
	 * @Description: TODO 获取服务器的时间(这里的服务器是数据库所有服务器)
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月21日
	 */
	@JSON
	public String currentTime(){
		try{
			if(priceCorpVo == null) priceCorpVo =  new PriceCorpVo();
			Date currentTime = priceCorpService.queryCurrentDateTime();
			Calendar ca=Calendar.getInstance();
			ca.setTime(currentTime);
			ca.add(Calendar.MINUTE,5);
			//priceCorpVo.setCurrentTime(ca.getTime());//暂不对时间进行处理
			priceCorpVo.setCurrentTime(currentTime);
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @Description: 导入excel，创建网店价格详细信息
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年1月20日
	 */
	public void excelImpl() throws Exception {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext().getRealPath(priceCorpGirdVo.getFilePath());
			priceCorpGirdVo.setFilePath(uploadPath);
			Map<String, Object> returnMap = priceCorpService.bathImplPriceCorps(priceCorpGirdVo);
			JSONObject json = new JSONObject();
			// succCount:成功数量,failCount:失败数量,list: 导入的客户价格明细
			json.put("succCount", returnMap.get("succCount"));
			json.put("failCount", returnMap.get("failCount"));
			json.put("repeatTip", returnMap.get("repeatTip"));
			json.put("list", returnMap.get("list"));
			out.println(json.toString());
			out.flush();
			out.close();
			//return returnSuccess(json.toString());
		} catch (BusinessException e) {
			//return returnError(e);
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
	public String excelExport() throws Exception {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			List<PriceCorpGirdVo> details = priceCorpService.findByPriceCorpGirdVo(priceCorpGirdVo);
			//获取中文状态用于导出显示
			for(PriceCorpGirdVo vo : details){
				vo.changeStateToCn();
			}
			String filePath = priceCorpService.createExcelFile(details);
			JSONObject json = new JSONObject();
			// filePath:错误的信息的文件地址
			json.put("filePath", filePath);
			json.put("count",CollectionUtils.isNotEmpty(details)?details.size():0); 
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess(json.toString());
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	
	public PriceCorpVo getPriceCorpVo() {
		return priceCorpVo;
	}

	public void setPriceCorpVo(PriceCorpVo priceCorpVo) {
		this.priceCorpVo = priceCorpVo;
	}
	
	public PriceCorpGirdVo getPriceCorpGirdVo() {
		return priceCorpGirdVo;
	}

	public void setPriceCorpGirdVo(PriceCorpGirdVo priceCorpGirdVo) {
		this.priceCorpGirdVo = priceCorpGirdVo;
	}

}
