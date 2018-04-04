/**   
 * @Title: BseCustomerAction.java 
 * @Package com.hoau.miser.module.biz.pricecard.server.action 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 陈启勇 
 * @date 2016年1月13日 下午4:29:51 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.pricecard.api.server.service.IBseCustomerService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.BseCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.BseCustomerVo;

/**
 * ClassName: BseCustomerAction
 * 
 * @author 陈启勇
 * @date 2016年1月13日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class BseCustomerAction extends AbstractAction {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private IBseCustomerService bseCustomerService;
	private BseCustomerVo bseCustomerVo;

	@Override
	public String execute() throws Exception {
		return "index";
	}

	/**
	 * 
	 * @Description: 查询客户信息
	 * @param @return
	 * @return String
	 * @throws
	 * @date 2016年1月13日
	 */
	@JSON
	public String queryBseCustomer() {
		List<BseCustomerEntity> items = bseCustomerService
				.queryBseCustomerByParam(bseCustomerVo, limit, start);
		bseCustomerVo.setBseCustomerEntityList(items);
		totalCount = bseCustomerService.queryCountByParam(bseCustomerVo);
		return this.returnSuccess();
	}

	/**
	 * @return the bseCustomerVo
	 */
	public BseCustomerVo getBseCustomerVo() {
		return bseCustomerVo;
	}

	/**
	 * @param bseCustomerVo
	 *            the bseCustomerVo to set
	 */
	public void setBseCustomerVo(BseCustomerVo bseCustomerVo) {
		this.bseCustomerVo = bseCustomerVo;
	}

}
