package com.hoau.miser.module.biz.discount.server.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.discount.api.server.service.IPrivilegeContractService;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractDetailEntity;
import com.hoau.miser.module.biz.discount.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.discount.api.shared.vo.CouponAcceptorVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeContractDetailVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeContractVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeStateQueryVo;
import com.hoau.miser.module.util.DateUtils;

/**
 * 
 * ClassName: PrivilegeStateQueryAction 
 * @Description: TODO(越发越惠状态查询) 
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月15日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PrivilegeStateQueryAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	@Resource 
	private IPrivilegeContractService privilegeContractService;
	
	private PrivilegeContractVo privilegeContractVo;
	private PrivilegeContractDetailVo privilegeContractDetailVo;
	
	private PrivilegeStateQueryVo privilegeStateQueryVo;
	private List<PrivilegeStateQueryVo> privilegeStateQueryVoList;
	//状态查询所属门店
	private String querySalesdepartment;
	private String privilegeContractDetailList;
	
	private CouponAcceptorVo couponAcceptorVo;
	public String execute(){
		return "index";
	}
	/**
	 * @Description: 
	 * @return String 
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月15日
	 */
	@JSON
	public String queryListByParam() {
		try {
			if(privilegeStateQueryVo==null) {
				privilegeStateQueryVo=new PrivilegeStateQueryVo();
			} else {
				Date recordMonth = privilegeStateQueryVo.getRecordMonth();
				if(recordMonth != null) {
					recordMonth = DateUtils.strToDate(DateUtils.convert(recordMonth).substring(0, 8) + "01 00:00:00");
					privilegeStateQueryVo.setRecordMonth(recordMonth);					
				}
			}
			privilegeStateQueryVoList =privilegeContractService.queryListByParamForStateQuery(privilegeStateQueryVo, limit, start) ;
	        totalCount = privilegeContractService.queryCountByParamForStateQuery(privilegeStateQueryVo);
	        privilegeStateQueryVo=null;
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * @Description: 新增收券人
	 * @return String 
	 * @author 286330付于令
	 * @date 2016年4月8日
	 */
	public String  addCouponAcceptor(){
		String flag = privilegeContractService.addCouponAcceptor(couponAcceptorVo);
		if(flag == null) {
			return returnSuccess(MessageType.ADD_SUCCESS);			
		} else {
			return returnError(flag);
		}
	}
	/**
	 * @Description: 修改收券人
	 * @return String 
	 * @author 286330付于令
	 * @date 2016年4月8日
	 */
	public String  updateCouponAcceptor(){
		String flag=privilegeContractService.updateCouponAcceptor(couponAcceptorVo);
		return returnSuccess( flag);
	}
	/**
	 * @Description: 通过ID获取收券人
	 * @return String 
	 * @author 286330付于令
	 * @date 2016年4月8日
	 */
	@JSON
	public String queryCouponAcceptorById() {
		try {
			couponAcceptorVo.setCouponAcceptorEntity(privilegeContractService.queryCouponAcceptorVoById(couponAcceptorVo.getCouponAcceptorEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 根据id得到越发越惠状态详情
	 * @return String  
	 * @Author 286330
	 * @Time 2015年12月17日下午7:09:24
	 */
	@JSON
	public String queryPrivilegeContractById() {
		try {
			privilegeContractVo.setPrivilegeContractEntity(privilegeContractService.queryPrivilegeContractById(privilegeContractVo.getPrivilegeContractEntity().getId()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}
	/**
	 * 
	 * @Description: 越发越惠状态明细查询
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 付于令
	 * @date 2015年12月23日
	 */
	@JSON
	public String queryListByParamSub() {
		try {
			if(privilegeContractDetailVo==null)privilegeContractDetailVo=new PrivilegeContractDetailVo();
			List<PrivilegeContractDetailEntity> PrivilegeContractDetailList=privilegeContractService.queryListByParamSub(privilegeContractDetailVo, limit, start);
			totalCount=privilegeContractService.queryCountByParam(privilegeContractDetailVo);
			privilegeContractDetailVo.setPrivilegeContractDetailList(PrivilegeContractDetailList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
    public String doExport() throws IOException {
    	HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		List<PrivilegeStateQueryVo> list = privilegeContractService.queryListByParamForStateQuery(
				privilegeStateQueryVo, RowBounds.NO_ROW_LIMIT, RowBounds.NO_ROW_OFFSET);
		String filePath= privilegeContractService.createExcel(list);
		JSONObject json = new JSONObject();
		//filePath:错误的信息的文件地址
		json.put("result", filePath != null);
		json.put("filePath", filePath);
		json.put("count", list == null ? 0 : list.size());
		out.println(json.toString());
		out.flush();
		out.close();
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
	public String getQuerySalesdepartment() {
		return querySalesdepartment;
	}
	public void setQuerySalesdepartment(String querySalesdepartment) {
		this.querySalesdepartment = querySalesdepartment;
	}
	public PrivilegeStateQueryVo getPrivilegeStateQueryVo() {
		return privilegeStateQueryVo;
	}
	public void setPrivilegeStateQueryVo(PrivilegeStateQueryVo privilegeStateQueryVo) {
		this.privilegeStateQueryVo = privilegeStateQueryVo;
	}
	public List<PrivilegeStateQueryVo> getPrivilegeStateQueryVoList() {
		return privilegeStateQueryVoList;
	}
	public void setPrivilegeStateQueryVoList(
			List<PrivilegeStateQueryVo> privilegeStateQueryVoList) {
		this.privilegeStateQueryVoList = privilegeStateQueryVoList;
	}
	public CouponAcceptorVo getCouponAcceptorVo() {
		return couponAcceptorVo;
	}
	public void setCouponAcceptorVo(CouponAcceptorVo couponAcceptorVo) {
		this.couponAcceptorVo = couponAcceptorVo;
	}
}
