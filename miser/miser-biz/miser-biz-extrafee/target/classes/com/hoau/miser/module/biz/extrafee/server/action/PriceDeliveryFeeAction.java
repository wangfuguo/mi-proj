package com.hoau.miser.module.biz.extrafee.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceDeliveryFeeService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeCityTypeEntity;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceDeliveryFeeVo;

/**
 * add by dengyin 2016-7-6 14:53:32 配送费管理
 * 
 * @author dengyin
 *
 */
@Controller
@Scope("prototype")
public class PriceDeliveryFeeAction extends AbstractAction {

	private static final long serialVersionUID = 7202271963850309410L;

	@Resource
	private IPriceDeliveryFeeService priceDeliveryFeeService;

	private PriceDeliveryFeeVo priceDeliveryFeeVo;

	public String index() {
		return "index";
	}

	@JSON
	public String queryListByParam() {
		try {
			if (priceDeliveryFeeVo == null) {
				priceDeliveryFeeVo = new PriceDeliveryFeeVo();
			}
			List<PriceDeliveryFeeEntity> entityList = priceDeliveryFeeService
					.queryListByParam(priceDeliveryFeeVo, limit, start);
			
			totalCount = priceDeliveryFeeService
					.queryCountByParam(priceDeliveryFeeVo);
			priceDeliveryFeeVo.setPriceDeliveryFeeEntityList(entityList);

			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 根据id得到价格表
	 * 
	 * @return String
	 * @Author dengyin
	 */
	@JSON
	public String queryPriceDeliveryFeeById() {
		try {

			PriceDeliveryFeeEntity entity = priceDeliveryFeeService
					.queryPriceDeliveryFeeById(priceDeliveryFeeVo
							.getPriceDeliveryFeeEntity().getId());
			priceDeliveryFeeVo.setPriceDeliveryFeeEntity(entity);

			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}

	/**
	 * 
	 * @Description: 增加配送费
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 */
	@JSON
	public String addPriceDeliveryFee() {
		try {
			Integer result = priceDeliveryFeeService
					.addPriceDeliveryFee(priceDeliveryFeeVo
							.getPriceDeliveryFeeEntity(),priceDeliveryFeeVo.getConfirm() == 1 ? true:false);
	       
			if(result.intValue()==0){
				return returnSuccess(MessageType.PRICEDELIVERYFEE_ISCONFIRM);
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
	 * 
	 * @Description: updatePriceDeliveryFeeCityType
	 * @return String
	 * @Author dengyin
	 */
	@JSON
	public String updatePriceDeliveryFee() {
		try {
			Integer result = priceDeliveryFeeService
					.updatePriceDeliveryFee(priceDeliveryFeeVo
							.getPriceDeliveryFeeEntity(),priceDeliveryFeeVo.getConfirm() == 1 ? true:false);

			if(result.intValue()==0){
				return returnSuccess(MessageType.PRICEDELIVERYFEE_ISCONFIRM);
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
	 * 
	 * @Description: deletePriceDeliveryFee
	 * @return String
	 * @Author dengyin
	 */
	@JSON
	public String deletePriceDeliveryFee() {
		try {

			List<PriceDeliveryFeeEntity> list= JsonUtils.toList(priceDeliveryFeeVo.selects, PriceDeliveryFeeEntity.class);
			int result=priceDeliveryFeeService.batchDelPriceDeliveryFee(list);
			if(result==1){
				return returnSuccess(MessageType.DELETE_SUCCESS);
			}else{
				return returnSuccess(MessageType.STATE_CHANGE);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	public PriceDeliveryFeeVo getPriceDeliveryFeeVo() {
		return priceDeliveryFeeVo;
	}

	public void setPriceDeliveryFeeVo(PriceDeliveryFeeVo priceDeliveryFeeVo) {
		this.priceDeliveryFeeVo = priceDeliveryFeeVo;
	}
}
