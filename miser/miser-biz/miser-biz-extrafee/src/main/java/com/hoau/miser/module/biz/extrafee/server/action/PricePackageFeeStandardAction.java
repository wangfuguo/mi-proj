package com.hoau.miser.module.biz.extrafee.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.extrafee.server.service.IPricePackageFeeStandardService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PricePackageFeeStandardEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.vo.PricePackageFeeStandardVO;

/**
 * 包装费标准
 * ClassName: PricePackageFeeStandardAction 
 * @author 廖文强
 * @date 2016年1月13日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PricePackageFeeStandardAction extends AbstractAction {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	public String execute(){
		return "index";
	}
	
	private PricePackageFeeStandardVO ppfeeStandardVO;
	
	@Resource
	private IPricePackageFeeStandardService pricePackageFeeStandardService;
	
	@JSON
	public String queryListByParam() {
		try {
			List<PricePackageFeeStandardEntity> ppFeeStandardList=pricePackageFeeStandardService.queryListByParam(ppfeeStandardVO, limit, start);
			totalCount=pricePackageFeeStandardService.queryCountByParam(ppfeeStandardVO);
			ppfeeStandardVO.setPpFeeStandardList(ppFeeStandardList); 
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
	 * @date 2015年12月28日
	 */
	@JSON
	public String queryFeeStandardById() {
		try {
			ppfeeStandardVO.setPpFeeStandardEntity(pricePackageFeeStandardService.queryPpFeeStandardById(ppfeeStandardVO.getPpFeeStandardEntity().getId()));
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
	 * @date 2015年12月28日
	 */
	@JSON
	public String addFeeStandard(){
		try{
			pricePackageFeeStandardService.addPpFeeStandard(ppfeeStandardVO.getPpFeeStandardEntity());
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
	 * @date 2015年12月28日
	 */
	@JSON
	public String updateFeeStandard(){
		try{
			pricePackageFeeStandardService.updatePpFeeStandard(ppfeeStandardVO.getPpFeeStandardEntity());
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
	 * @date 2015年12月28日
	 */
	@JSON
	public String deleteFeeStandard(){
		try{
			pricePackageFeeStandardService.delFeeStandard(ppfeeStandardVO.getPpFeeStandardEntity());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	public PricePackageFeeStandardVO getPpfeeStandardVO() {
		return ppfeeStandardVO;
	}
	public void setPpfeeStandardVO(PricePackageFeeStandardVO ppfeeStandardVO) {
		this.ppfeeStandardVO = ppfeeStandardVO;
	}
	
}
