package com.hoau.miser.module.biz.extrafee.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceUpstairsService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceUpstairsEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.exception.PriceUpstairsException;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceUpstairsVo;

/**
 * ClassName: PriceUpstairsAction 
 * @Description: 上楼费 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月5日
 * @version V1.0   
 */
@Controller
@Scope("prototype")
public class PriceUpstairsAction extends AbstractAction{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -7643924606825837520L;

	@Resource
	private IPriceUpstairsService priceUpstairsService;
	
	private PriceUpstairsVo priceUpstairsVo;
	
	
	/**
	 * @Description: 首次打开界面加载jsp
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	public String index(){
		return "index";
	}
	
	/**
	 * @Description: 查询所有上楼费定义(含分页)
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	@JSON
	public String queryUpstairsPrices(){
		List<PriceUpstairsEntity> entities = priceUpstairsService.queryUpstairsPrices(priceUpstairsVo, start, limit);
		priceUpstairsVo.setPriceUpstairsEntities(entities);
        totalCount = priceUpstairsService.queryUpstairsPricesCount(priceUpstairsVo);
		return this.returnSuccess();
	}
	
	/**
	 * @Description: 根据id查询上楼费
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月6日
	 */
	@JSON
	public String queryUpstairsPriceById() {
		PriceUpstairsEntity entity = priceUpstairsService.queryUpstairsPriceById(priceUpstairsVo);
		priceUpstairsVo.setPriceUpstairsEntity(entity);
		return this.returnSuccess();
	}
	
	/**
	 * @Description: 检查上楼费是否存在冲突，存在的提示
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	@JSON
	public String checkAddData(){
		try {
			String resultMsg = priceUpstairsService.checkSaveData(priceUpstairsVo, "ADD");
			priceUpstairsVo = null;
			return this.returnSuccess(resultMsg);
		} catch (PriceUpstairsException e) {
			return this.returnError(e);
		}
	}
	
	/**
	 * @Description: 检查上楼费是否存在冲突，存在的提示
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	@JSON
	public String checkModifyData(){
		try {
			String resultMsg = priceUpstairsService.checkSaveData(priceUpstairsVo, "MODIFY");
			return this.returnSuccess(resultMsg);
		} catch (PriceUpstairsException e) {
			return this.returnError(e);
		}
	}
	
	/**
	 * @Description: 保存上楼费配置，新增与修改逻辑相同
	 * @return String 
	 * @throws
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	@JSON
	public String saveUpstairsPrice() {
		try {
			priceUpstairsService.saveUpstairsPrice(priceUpstairsVo);
		} catch (PriceUpstairsException e) {
			return this.returnError(e);
		}
		return this.returnSuccess(MessageType.SAVE_SUCCESS);
	}
	
	/**
	 * @Description: 检查删除的数据
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	@JSON
	public String checkDeleteData() {
		try {
			String resultMsg = priceUpstairsService.checkDeleteData(priceUpstairsVo);
			return this.returnSuccess(resultMsg);
		} catch (PriceUpstairsException e) {
			return this.returnError(e);
		}
	}

	/**
	 * @Description: 作废上楼费
	 * @return String 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月7日
	 */
	@JSON
	public String deleteUpstairsPrice() {
		try {
			priceUpstairsService.deleteUpstairsPrice(priceUpstairsVo);
		} catch (PriceUpstairsException e) {
			return this.returnError(e);
		}
		return this.returnSuccess(MessageType.DELETE_SUCCESS);
	}
	
	@JSON
	public String queryUpstairsDetails() {
		PriceUpstairsEntity entity = priceUpstairsService.queryUpstairsDetails(priceUpstairsVo);
		priceUpstairsVo.setPriceUpstairsEntity(entity);
		return this.returnSuccess();
	}

	public PriceUpstairsVo getPriceUpstairsVo() {
		return priceUpstairsVo;
	}

	public void setPriceUpstairsVo(PriceUpstairsVo priceUpstairsVo) {
		this.priceUpstairsVo = priceUpstairsVo;
	}
	
}
