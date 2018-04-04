package com.hoau.miser.module.api.facade.server.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.miser.module.api.facade.server.service.impl.DiscountPrivilegeService;
import com.hoau.miser.module.api.facade.server.service.impl.PrivilegeContractService;
import com.hoau.miser.module.api.facade.shared.define.BaseResponseEnum;
import com.hoau.miser.module.api.facade.shared.vo.BaseResponseVo;
import com.hoau.miser.module.api.facade.shared.vo.DiscountPrivilegeVo;
import com.hoau.miser.module.api.facade.shared.vo.PrivilegeContractVo;

/**
 * OA控制器
 * ClassName: DiscountPrivilegeController 
 * @author 286330付于令
 * @date 2016年1月25日
 * @version V1.0
 */
@Controller("oAAction")
@Scope("prototype")
public class OAAction extends AbstractAction {
	private static final long serialVersionUID = 7958415478985776060L;
	@Resource
	private DiscountPrivilegeService discountPrivilegeService;
	@Resource
	private PrivilegeContractService privilegeContractService;
	/* in参数 */
	private PrivilegeContractVo privilegeContractVo;
	private DiscountPrivilegeVo discountPrivilegeVo;

	/* out参数 */
	private BaseResponseVo<Object> response = new BaseResponseVo<Object>();

	/**
	 * @Description: 请求越发越惠基础数据
	 * @return String
	 * @author 286330付于令
	 * @date 2016年1月25日
	 */
	public String queryPrivileges() {
		Object content = discountPrivilegeService.queryListByParam(discountPrivilegeVo);
		response.setContent(content);
		response.setStatus(BaseResponseEnum.SUCCESS.getStatus());
		response.setMessage(BaseResponseEnum.SUCCESS.getMessage());
		return returnSuccess();
	}
	
	/**
	 * @Description: 保存合同数据
	 * @param contract
	 * @return String
	 * @author 286330付于令
	 * @date 2016年1月26日
	 */
	public String savePrivilegeContract() {
		String result = privilegeContractService.addPrivilegeContract(privilegeContractVo);
		if(result == null) {
			BaseResponseEnum successRes = BaseResponseEnum.SUCCESS;
			response.setStatus(successRes.getStatus());
			response.setMessage(successRes.getMessage());
			return returnSuccess();
		} else {
			BaseResponseEnum parameterCheckFail = BaseResponseEnum.PARAMETER_CHECK_FAIL;
			response.setStatus(parameterCheckFail.getStatus());
			response.setMessage(parameterCheckFail.getMessage() + ": " + result);
			return returnError(result);
		}
	}
	
	public BaseResponseVo<Object> getResponse() {
		return response;
	}
	public void setResponse(BaseResponseVo<Object> response) {
		this.response = response;
	}
	public PrivilegeContractVo getPrivilegeContractVo() {
		return privilegeContractVo;
	}
	public void setPrivilegeContractVo(PrivilegeContractVo privilegeContractVo) {
		this.privilegeContractVo = privilegeContractVo;
	}
	public DiscountPrivilegeVo getDiscountPrivilegeVo() {
		return discountPrivilegeVo;
	}
	public void setDiscountPrivilegeVo(DiscountPrivilegeVo discountPrivilegeVo) {
		this.discountPrivilegeVo = discountPrivilegeVo;
	}
}
