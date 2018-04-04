package com.hoau.miser.module.biz.discount.server.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.discount.api.server.service.IPrivilegeContractService;
import com.hoau.miser.module.biz.discount.api.shared.vo.CouponAcceptorVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeCouponCheckVo;
import com.hoau.miser.module.util.DateUtils;

/**
 * 
 * ClassName: PrivilegeCouponCheckAction
 * 
 * @Description: TODO(越发越惠返券审核)
 * @author 付于令
 * @date 2016年1月19日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PrivilegeCouponCheckAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	@Resource
	private IPrivilegeContractService privilegeContractService;

	private List<PrivilegeCouponCheckVo> privilegeCouponCheckVoList;
	private PrivilegeCouponCheckVo privilegeCouponCheckVo;
	private CouponAcceptorVo couponAcceptorVo;

	public String execute() {
		return "index";
	}

	/**
	 * @Description: 查询
	 * @param @return
	 * @return String
	 * @throws
	 * @author 付于令
	 * @date 2016年1月19日
	 */
	@JSON
	public String queryListByParam() {
		try {
			if (privilegeCouponCheckVo == null) {
				privilegeCouponCheckVo = new PrivilegeCouponCheckVo();				
			} else {
				Date recordMonth = privilegeCouponCheckVo.getRecordMonth();
				if(recordMonth != null) {
					recordMonth = DateUtils.strToDate(DateUtils.convert(recordMonth).substring(0, 8) + "01 00:00:00");
					privilegeCouponCheckVo.setRecordMonth(recordMonth);					
				}
			}
			privilegeCouponCheckVoList = privilegeContractService.queryListByParamForCheck(
					privilegeCouponCheckVo, limit, start);
			totalCount = privilegeContractService.queryCountByParamForCheck(privilegeCouponCheckVo,
					limit, start);
			privilegeCouponCheckVo = null;
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * @Description: 审核
	 * @param @return
	 * @return String
	 * @throws
	 * @author 286330付于令
	 * @date 2016年1月19日
	 */
	@JSON
	public String couponCheck() {
		String result = privilegeContractService.updateCouponState(privilegeCouponCheckVo);
		if (result == null) {
			return returnSuccess(MessageType.OPERATE_SUCCESS);
		}
		return returnError(result);
	}

	public List<PrivilegeCouponCheckVo> getPrivilegeCouponCheckVoList() {
		return privilegeCouponCheckVoList;
	}

	public void setPrivilegeCouponCheckVoList(
			List<PrivilegeCouponCheckVo> privilegeCouponCheckVoList) {
		this.privilegeCouponCheckVoList = privilegeCouponCheckVoList;
	}

	public PrivilegeCouponCheckVo getPrivilegeCouponCheckVo() {
		return privilegeCouponCheckVo;
	}

	public void setPrivilegeCouponCheckVo(PrivilegeCouponCheckVo privilegeCouponCheckVo) {
		this.privilegeCouponCheckVo = privilegeCouponCheckVo;
	}

	public CouponAcceptorVo getCouponAcceptorVo() {
		return couponAcceptorVo;
	}

	public void setCouponAcceptorVo(CouponAcceptorVo couponAcceptorVo) {
		this.couponAcceptorVo = couponAcceptorVo;
	}

}
