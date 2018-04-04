package com.hoau.miser.module.biz.extrafee.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceDeliveryFeeCityTypeService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeCityTypeEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceDeliveryFeeCityTypeVo;

/**
 * 送货费管理Action
 * ClassName: PriceDeliveryFeeCityTypeAction 
 * @author 廖文强
 * @date 2016年1月4日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PriceDeliveryFeeCityTypeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource 
	private IPriceDeliveryFeeCityTypeService priceDeliveryFeeCityTypeService;
	
	private PriceDeliveryFeeCityTypeVo priceDeliveryFeeCityTypeVo;
	
	public String execute(){
		return "index";
	}
	
	
	@JSON
	public String queryListByParam() {
		try {
			if(priceDeliveryFeeCityTypeVo==null)priceDeliveryFeeCityTypeVo=new PriceDeliveryFeeCityTypeVo();
			List<PriceDeliveryFeeCityTypeEntity> PriceDeliveryFeeCityTypeList=priceDeliveryFeeCityTypeService.queryListByParam(priceDeliveryFeeCityTypeVo, limit, start);
			totalCount=priceDeliveryFeeCityTypeService.queryCountByParam(priceDeliveryFeeCityTypeVo);
			priceDeliveryFeeCityTypeVo.setPdfctList(PriceDeliveryFeeCityTypeList);
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
	public String queryPriceDeliveryFeeCityTypeById() {
		try {
			priceDeliveryFeeCityTypeVo.setPdfctEntity(priceDeliveryFeeCityTypeService.queryPriceDeliveryFeeCityTypeById(priceDeliveryFeeCityTypeVo.getPdfctEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}
	/**
	 * 
	 * @Description: 增加送货费
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	@JSON
	public String addPriceDeliveryFeeCityType(){
		try{
			Integer result=priceDeliveryFeeCityTypeService.addPriceDeliveryFeeCityType(priceDeliveryFeeCityTypeVo.getPdfctEntity(),"1".equals(priceDeliveryFeeCityTypeVo.getIsConfirm()));
			if(result.intValue()==0){
				return returnSuccess(MessageType.PRICEDELIVERYFEECITYTYPE_ISCONFIRM);
			}else if(result.intValue()==1){
				return returnSuccess(MessageType.ADD_SUCCESS);
			}else{
				return returnSuccess(MessageType.STATE_CHANGE);
			}
			
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 修改价格
	 * @Description: updatePriceDeliveryFeeCityType
	 * @return String  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午5:57:10
	 */
	@JSON
	public String updatePriceDeliveryFeeCityType(){
		try{
			Integer result=priceDeliveryFeeCityTypeService.updatePriceDeliveryFeeCityType(priceDeliveryFeeCityTypeVo.getPdfctEntity(),"1".equals(priceDeliveryFeeCityTypeVo.getIsConfirm()));
			if(result.intValue()==0){
				return returnSuccess(MessageType.PRICEDELIVERYFEECITYTYPE_ISCONFIRM);
			}else if(result.intValue()==1){
				return returnSuccess(MessageType.UPDATE_SUCCESS);
			}else{
				return returnSuccess(MessageType.STATE_CHANGE);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 删除价格
	 * @Description: deletePriceDeliveryFeeCityType
	 * @return String  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午5:57:20
	 */
	@JSON
	public String deletePriceDeliveryFeeCityType(){
		try{
			
			List<PriceDeliveryFeeCityTypeEntity> list= JsonUtils.toList(priceDeliveryFeeCityTypeVo.selects, PriceDeliveryFeeCityTypeEntity.class);
			int result=priceDeliveryFeeCityTypeService.bathDelPriceDeliveryFeeCityType(list);
			if(result==1){
				return returnSuccess(MessageType.DELETE_SUCCESS);
			}else{
				return returnSuccess(MessageType.STATE_CHANGE);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}


	public PriceDeliveryFeeCityTypeVo getPriceDeliveryFeeCityTypeVo() {
		return priceDeliveryFeeCityTypeVo;
	}


	public void setPriceDeliveryFeeCityTypeVo(
			PriceDeliveryFeeCityTypeVo priceDeliveryFeeCityTypeVo) {
		this.priceDeliveryFeeCityTypeVo = priceDeliveryFeeCityTypeVo;
	}
	
}
