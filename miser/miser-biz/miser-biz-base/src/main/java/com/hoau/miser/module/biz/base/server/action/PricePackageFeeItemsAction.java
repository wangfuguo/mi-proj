package com.hoau.miser.module.biz.base.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.server.service.IPricePackageFeeItemsService;
import com.hoau.miser.module.biz.base.api.shared.domain.PricePackageFeeItemsEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.api.shared.vo.PricePackageFeeItemsVo;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 包装费的Action
 * ClassName: PricePackageFeeItemsAction 
 * @author 292078
 * @date 2015年12月22日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PricePackageFeeItemsAction extends AbstractAction {

    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Resource
    IPricePackageFeeItemsService pricePackageFeeItemsService;

    PricePackageFeeItemsVo ppfItemsVo;

    /**
     * 首页
     */
    public String execute() {
        return "index";
    }

    /**
     * 
     * @Description: 查询包装列表
     * @param @return   
     * @return String 
     * @throws
     * @author 292078
     * @date 2015年12月22日
     */
    @JSON
    public String queryPricePackageFeeItems() {
    	List<PricePackageFeeItemsEntity> items = pricePackageFeeItemsService.queryPricePackageFeeItemsByParam(ppfItemsVo.getPpfItemsEntity(), limit, start);
    	ppfItemsVo.setPpfItemsList(items);
    	totalCount=pricePackageFeeItemsService.queryCountByParam(ppfItemsVo.getPpfItemsEntity());
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
	public String updatePricePackageFeeItems() {
		try {
			pricePackageFeeItemsService.updatePricePackageFeeItemsByEntity(ppfItemsVo.getPpfItemsEntity());
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
    public String deletePricePackageFeeItems() {
    	try {
    		pricePackageFeeItemsService.deletePricePackageFeeItemsByEntity(ppfItemsVo.getPpfItemsEntity(),MiserConstants.YES);
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
    public String addPricePackageFeeItems() {
    	try {
    		pricePackageFeeItemsService.addPricePackageFeeItemsByEntity(ppfItemsVo.getPpfItemsEntity());
    		return returnSuccess(MessageType.ADD_SUCCESS);
    	} catch (BusinessException e) {
    		return returnError(e);
    	}
    }

    public String queryPricePackageFeeItemsById()
    {
    	try {
    		ppfItemsVo.setPpfItemsEntity(pricePackageFeeItemsService.queryPricePackageFeeItemsById(ppfItemsVo.getPpfItemsEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
    }
	public PricePackageFeeItemsVo getPpfItemsVo() {
		return ppfItemsVo;
	}

	public void setPpfItemsVo(PricePackageFeeItemsVo ppfItemsVo) {
		this.ppfItemsVo = ppfItemsVo;
	}

    
}
