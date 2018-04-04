package com.hoau.miser.module.biz.discount.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.discount.api.server.service.IPrivilegeContractService;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractDetailEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeContractDetailVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeContractVo;
import com.hoau.miser.module.biz.pricecard.api.shared.exception.MessageType;

/**
 * 越发越惠客户合同 
 * ClassName: PrivilegeContractAction
 * @author 付于令
 * @date 2016年01月13日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PrivilegeContractAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	@Resource
	private IPrivilegeContractService privilegeContractService;

	private PrivilegeContractVo privilegeContractVo;
	private PrivilegeContractDetailVo privilegeContractDetailVo;

	private String privilegeContractDetailList;

	/**
	 * @Description: 页面
	 * @return String
	 * @author 286330付于令
	 * @date 2016年1月13日
	 */
	public String execute() {
		return "index";
	}

	/**
	 * @Description: 越发越惠客户合同主表查询
	 * @return String
	 * @author 286330付于令
	 * @date 2016年1月13日
	 */
	@JSON
	public String queryListByParam() {
		if (privilegeContractVo == null)
			privilegeContractVo = new PrivilegeContractVo();
		List<PrivilegeContractEntity> PrivilegeContractList = privilegeContractService
				.queryListByParam(privilegeContractVo, limit, start);
		totalCount = privilegeContractService.queryCountByParam(privilegeContractVo);
		privilegeContractVo.setPrivilegeContractList(PrivilegeContractList);
		return returnSuccess();
	}

	/**
	 * @Description: 增加越发越惠客户合同
	 * @return String
	 * @Author 286330付于令
	 * @date 2016年1月13日
	 */
	@JSON
	public String addPrivilegeContract() {
		String result = privilegeContractService.addPrivilegeContract(
				privilegeContractVo.getPrivilegeContractEntity(), privilegeContractDetailList);
		privilegeContractDetailList = null;
		privilegeContractVo = null;
		if(result == null) {
			return returnSuccess(MessageType.ADD_SUCCESS);			
		} else {
			return returnError(result);
		}
	}

	/**
	 * @Description: 修改越发越惠客户合同
	 * @return String
	 * @Author 286330付于令
	 * @date 2016年1月13日
	 */
	@JSON
	public String updatePrivilegeContract() {
		String result = privilegeContractService.updatePrivilegeContract(
				privilegeContractVo.getPrivilegeContractEntity(), privilegeContractDetailList);
		privilegeContractDetailList = null;
		privilegeContractVo = null;
		if(result == null) {
			return returnSuccess(MessageType.ADD_SUCCESS);			
		} else {
			return returnError(result);
		}
	}

	/**
	 * @Description: 删除越发越惠客户合同
	 * @return String
	 * @Author 286330付于令
	 * @date 2016年1月13日
	 */
	@JSON
	public String deletePrivilegeContract() {
		privilegeContractService.delPrivilegeContract(privilegeContractVo
				.getPrivilegeContractEntity());
		return returnSuccess(MessageType.DELETE_SUCCESS);
	}

	/**
	 * @Description: 越发越惠客户合同明细查询
	 * @return String
	 * @author 286330付于令
	 * @date 2016年1月13日
	 */
	@JSON
	public String queryListByParamSub() {
		if (privilegeContractDetailVo == null)
			privilegeContractDetailVo = new PrivilegeContractDetailVo();
		List<PrivilegeContractDetailEntity> PrivilegeContractDetailList = privilegeContractService
				.queryListByParamSub(privilegeContractDetailVo, limit, start);
		totalCount = privilegeContractService.queryCountByParam(privilegeContractDetailVo);
		privilegeContractDetailVo.setPrivilegeContractDetailList(PrivilegeContractDetailList);
		
		return returnSuccess();
	}

	public PrivilegeContractVo getPrivilegeContractVo() {
		return privilegeContractVo;
	}

	public void setPrivilegeContractVo(PrivilegeContractVo privilegeContractVo) {
		this.privilegeContractVo = privilegeContractVo;
	}

	public PrivilegeContractDetailVo getPrivilegeContractDetailVo() {
		return privilegeContractDetailVo;
	}

	public void setPrivilegeContractDetailVo(PrivilegeContractDetailVo privilegeContractDetailVo) {
		this.privilegeContractDetailVo = privilegeContractDetailVo;
	}

	public String getPrivilegeContractDetailList() {
		return privilegeContractDetailList;
	}

	public void setPrivilegeContractDetailList(String privilegeContractDetailList) {
		this.privilegeContractDetailList = privilegeContractDetailList;
	}

}
