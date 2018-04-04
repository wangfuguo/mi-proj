package com.hoau.miser.module.biz.base.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.server.service.IPriceAddvalueFeeItemsService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceAddvalueFeeItemsEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceAddvalueFeeItemsVo;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 特服费Action
 * ClassName: PriceAddvalueFeeItemsAction 
 * @author 292078
 * @date 2015年12月23日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PriceAddvalueFeeItemsAction extends AbstractAction {

    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Resource
    IPriceAddvalueFeeItemsService priceAddvalueFeeItemsService;

	PriceAddvalueFeeItemsVo pafItemsVo;

    /**
     * 首页
     */
    public String execute() {
        return "index";
    }

    
    @JSON
    public String queryPriceAddvalueFeeItems() {
    	List<PriceAddvalueFeeItemsEntity> items = priceAddvalueFeeItemsService.queryPriceAddvalueFeeItemsByParam(pafItemsVo.getPafItemsEntity(), limit, start);
    	pafItemsVo.setPafItemsList(items);
    	totalCount=priceAddvalueFeeItemsService.queryCountByParam(pafItemsVo.getPafItemsEntity());
        return this.returnSuccess();
    }
    
    /**
     * 
     * @Description: 修改包装费
     * @param @return   
     * @return String 
     * @throws
     * @author 292078
     * @date 2015年12月22日
     */
    @JSON
	public String updatePriceAddvalueFeeItems() {
		try {
			priceAddvalueFeeItemsService.updatePriceAddvalueFeeItemsByEntity(pafItemsVo.getPafItemsEntity());
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
     * @date 2015年12月22日
     */
    @JSON
    public String deletePriceAddvalueFeeItems() {
    	try {
    		priceAddvalueFeeItemsService.deletePriceAddvalueFeeItemsByEntity(pafItemsVo.getPafItemsEntity(),MiserConstants.YES);
    		return returnSuccess(MessageType.DELETE_SUCCESS);
    	} catch (BusinessException e) {
    		return returnError(e);
    	}
    }
    /**
     * 增加包装费
     * @Description: TODO描述该方法是做什么的
     * @param @return   
     * @return String 
     * @throws
     * @author 292078
     * @date 2015年12月22日
     */
    @JSON
    public String addPriceAddvalueFeeItems() {
    	try {
    		priceAddvalueFeeItemsService.addPriceAddvalueFeeItemsByEntity(pafItemsVo.getPafItemsEntity());
    		return returnSuccess(MessageType.ADD_SUCCESS);
    	} catch (BusinessException e) {
    		return returnError(e);
    	}
    }

    public String queryPriceAddvalueFeeItemsById()
    {
    	try {
    		pafItemsVo.setPafItemsEntity(priceAddvalueFeeItemsService.queryPriceAddvalueFeeItemsById(pafItemsVo.getPafItemsEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
    }


	public PriceAddvalueFeeItemsVo getPafItemsVo() {
		return pafItemsVo;
	}


	public void setPafItemsVo(PriceAddvalueFeeItemsVo pafItemsVo) {
		this.pafItemsVo = pafItemsVo;
	}
	

    
}
