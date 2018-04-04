package com.hoau.miser.module.biz.extrafee.server.action;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.hoau.miser.module.biz.extrafee.server.service.IPricePickupFeeService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PricePickupFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.vo.PricePickupFeeVo;

/**
 * 提货费管理
 * ClassName: PricePickupFeeAction 
 * @author 刘海飞
 * @date 2015年12月28日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PricePickupFeeAction extends AbstractAction{


    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	IPricePickupFeeService pricePickupFeeService;
	
	PricePickupFeeVo pricePickupFeeVo;
	
	/**
     * 首页
     */
    public String execute() {
        return "index";
    }
    
    /**
     * @Description: TODO查询提货费
     * @param @return   
     * @return String 
     * @throws
     * @author 刘海飞
     * @date 2015年12月28日
     */
    @JSON
    public String queryPricePickupFee(){
    	
    	List<PricePickupFeeEntity> items = pricePickupFeeService.queryPricePickupFeeByParam(pricePickupFeeVo.getPricePickupFeeEntity(), limit, start);
    	pricePickupFeeVo.setPricePickupFeeList(items);
    	totalCount=pricePickupFeeService.queryCountByParam(pricePickupFeeVo.getPricePickupFeeEntity());
    	return this.returnSuccess();
    }
    
    /**
     * @Description: TODO修改提货费
     * @param @return   
     * @return String 
     * @throws
     * @author 刘海飞
     * @date 2016年1月4日
     */
    @JSON
	public String updatePricePickupFee() {
		try {
			Integer result=pricePickupFeeService.updatePricePickupFeeByEntity(pricePickupFeeVo.getPricePickupFeeEntity(),"1".equals(pricePickupFeeVo.getIsConfirm()));
			if(result.intValue()==0){
				return returnSuccess(MessageType.DELIVERY_ISCONFIRM);
			}else if(result.intValue()==1){
				return returnSuccess(MessageType.ADD_SUCCESS);
			}else if(result.intValue()==2){
				return returnSuccess(MessageType.PRICE_PICK_UP_DO_NOT_MODIFY);
			}else{
				return returnSuccess(MessageType.DELIVERY_DOWN_SYSDATE);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
   /**
    * @Description: TODO 删除提货费
    * @param @return   
    * @return String 
    * @throws
    * @author 刘海飞
    * @date 2016年1月4日
    */
    @JSON
    public String deletePricePickupFee() {
    	try {
    		pricePickupFeeService.deletePricePickupFeeByEntity(pricePickupFeeVo.getPricePickupFeeEntity());
    		return returnSuccess(MessageType.DELETE_SUCCESS);
    	} catch (BusinessException e) {
    		return returnError(e);
    	}
    }
   /**
    * @Description: TODO 增加提货费
    * @param @return   
    * @return String 
    * @throws
    * @author 刘海飞
    * @date 2016年1月4日
    */
    @JSON
    public String addPricePickupFee() {
    	try {
    		Integer result=pricePickupFeeService.addPricePickupFeeByEntity(pricePickupFeeVo.getPricePickupFeeEntity(),"1".equals(pricePickupFeeVo.getIsConfirm()));
    		if(result.intValue()==0){
				return returnSuccess(MessageType.DELIVERY_ISCONFIRM);
			}else if(result.intValue()==1){
				return returnSuccess(MessageType.ADD_SUCCESS);
			}else if(result.intValue()==2){
				return returnSuccess(MessageType.PRICE_PICK_UP_DO_NOT_MODIFY);
			}else{
				return returnSuccess(MessageType.DELIVERY_DOWN_SYSDATE);
			}
    	} catch (BusinessException e) {
    		return returnError(e);
    	}
    }

    @JSON
    public String queryPricePickupFeeByEntity()
    {
    	try {
    	pricePickupFeeVo.setPricePickupFeeEntity(pricePickupFeeService.queryPricePickupFeeByEntity(pricePickupFeeVo.getPricePickupFeeEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
    }
    
    /**
     * @Description: 导出提货费
     * @param    
     * @return String 
     * @author 286330付于令
     * @throws IOException 
     * @date 2016年4月5日
     */
    public String doExport() throws IOException {
    	HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		List<PricePickupFeeEntity> list = pricePickupFeeService.queryPricePickupFeeByParam(
				pricePickupFeeVo.getPricePickupFeeEntity());
		String filePath= pricePickupFeeService.createExcel(list);
		JSONObject json = new JSONObject();
		//filePath:错误的信息的文件地址
		json.put("result", filePath != null);
		json.put("filePath", filePath);
		json.put("count", list == null ? 0 : list.size());
		out.println(json.toString());
		out.flush();
		out.close();
    	return returnSuccess();
    }

	public PricePickupFeeVo getPricePickupFeeVo() {
		return pricePickupFeeVo;
	}

	public void setPricePickupFeeVo(PricePickupFeeVo pricePickupFeeVo) {
		this.pricePickupFeeVo = pricePickupFeeVo;
	} 
    
    
}
