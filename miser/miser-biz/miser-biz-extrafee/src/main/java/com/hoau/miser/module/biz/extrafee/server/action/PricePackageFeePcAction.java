package com.hoau.miser.module.biz.extrafee.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.extrafee.server.service.IPricePackageFeePcService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PricePackageFeePcEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.vo.PricePackageFeePcVo;

/**
 *价格城市包装费标准
 * ClassName: PricePackageFeePcAction 
 * @author 廖文强
 * @date 2016年1月19日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PricePackageFeePcAction extends AbstractAction {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	public String execute(){
		return "index";
	}
	
	private PricePackageFeePcVo ppFeePcVo;
	
	@Resource
	private IPricePackageFeePcService pricePackageFeePcService;
	
	@JSON
	public String queryListByParam() {
		try {
			List<PricePackageFeePcEntity> ppFeePcList=pricePackageFeePcService.queryListByParam(ppFeePcVo, limit, start);
			totalCount=pricePackageFeePcService.queryCountByParam(ppFeePcVo);
			ppFeePcVo.setPpFeePcList(ppFeePcList); 
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 根据id得到包装费表
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 292078
	 * @date 2016年01月19日
	 */
	@JSON
	public String queryFeePcById() {
		try {
			ppFeePcVo.setPpFeePcEntity(pricePackageFeePcService.queryPpFeePcById(ppFeePcVo.getPpFeePcEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}
	/**
	 * 
	 * @Description: 增加包装费表
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 292078
	 * @date 2016年01月19日
	 */
	@JSON
	public String addFeePc(){
		try{
			pricePackageFeePcService.addPpFeePc(ppFeePcVo.getPpFeePcEntity());
			return returnSuccess(MessageType.ADD_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @Description: 修改包装费
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 292078
	 * @date 2016年01月19日
	 */
	@JSON
	public String updateFeePc(){
		try{
			pricePackageFeePcService.updatePpFeePc(ppFeePcVo.getPpFeePcEntity());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @Description: 删除包装费
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 292078
	 * @date 2016年01月19日
	 */
	@JSON
	public String deleteFeePc(){
		try{
			pricePackageFeePcService.delFeePc(ppFeePcVo.getPpFeePcEntity());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	public PricePackageFeePcVo getPpFeePcVo() {
		return ppFeePcVo;
	}
	public void setPpFeePcVo(PricePackageFeePcVo ppFeePcVo) {
		this.ppFeePcVo = ppFeePcVo;
	}
	
	
	
	
}
