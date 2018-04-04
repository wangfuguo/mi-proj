package com.hoau.miser.module.biz.discount.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.discount.api.server.service.IDiscountPrivilegeService;
import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountPrivilegeEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountPrivilegeVo;

/**
 * 越发越惠
 * ClassName: DiscountPrivilegeAction 
 * @author 王茂
 * @date 2016年1月13日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class DiscountPrivilegeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource 
	private IDiscountPrivilegeService DiscountPrivilegeService;
	
	private DiscountPrivilegeVo discountPrivilegeVo;
	
	public String execute(){
		return "index";
	}
	/**
	 * 
	 * @Description: 查询越发越惠
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String queryListByParam() {
		try {
			if(discountPrivilegeVo==null)discountPrivilegeVo=new DiscountPrivilegeVo();
			List<DiscountPrivilegeEntity> DiscountPrivilegeList=DiscountPrivilegeService.queryListByParam(discountPrivilegeVo, limit, start);
			totalCount=DiscountPrivilegeService.queryCountByParam(discountPrivilegeVo);
			discountPrivilegeVo.setDiscountPrivilegeList(DiscountPrivilegeList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 * @Description: 根据id得到越发越惠表
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String queryDiscountPrivilegeById() {
		try {
			discountPrivilegeVo.setDiscountPrivilegeEntity(DiscountPrivilegeService.queryDiscountPrivilegeById(discountPrivilegeVo.getDiscountPrivilegeEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}

	}
	/**
	 * 
	 * @Description: 增加越发越惠
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String addDiscountPrivilege(){
		try{
			String flag=DiscountPrivilegeService.addDiscountPrivilege(discountPrivilegeVo.getDiscountPrivilegeEntity());
			return returnSuccess(flag);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 * @Description: 修改越发越惠
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@JSON
	public String updateDiscountPrivilege(){
		try{
			String flag=DiscountPrivilegeService.updateDiscountPrivilege(discountPrivilegeVo.getDiscountPrivilegeEntity());
			return returnSuccess(flag);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
/**
 * 
 * @Description: 删除越发越惠
 * @param @return   
 * @return String 
 * @throws
 * @author 王茂
 * @date 2015年12月30日
 */
	@JSON
	public String deleteDiscountPrivilege(){
		try{
			String flag=DiscountPrivilegeService.delDiscountPrivilege(discountPrivilegeVo.getDiscountPrivilegeEntity());
			return returnSuccess(flag);
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
			return returnError(e.getMessage());
		}
	}
public DiscountPrivilegeVo getDiscountPrivilegeVo() {
	return discountPrivilegeVo;
}
public void setDiscountPrivilegeVo(DiscountPrivilegeVo discountPrivilegeVo) {
	this.discountPrivilegeVo = discountPrivilegeVo;
}
	
	
}
