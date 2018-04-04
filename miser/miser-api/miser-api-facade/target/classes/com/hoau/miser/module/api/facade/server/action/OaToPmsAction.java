package com.hoau.miser.module.api.facade.server.action;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.miser.module.api.facade.server.service.IDiscountCustomerSyncService;
import com.hoau.miser.module.api.facade.shared.define.BaseResponseEnum;
import com.hoau.miser.module.api.facade.shared.vo.BaseResponseVo;
@Controller("oAToPmsAction")
@Scope("prototype")
public class OaToPmsAction extends AbstractAction{
	private static final long serialVersionUID = 6787539257106128007L;
	
    private static final Logger log = LoggerFactory.getLogger(OaToPmsAction.class);
    
	@Resource
	private IDiscountCustomerSyncService discountCustomerSyncService;
	/* out参数 */
	public BaseResponseVo<Object> response = new BaseResponseVo<Object>();
	
	
	public String syncDiscountCustomer() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonString = request.getParameter("data");
		//System.out.println("jsonString======================"+jsonString);
		
		log.info("OaToPmsAction syncDiscountCustomer jsonString:"+jsonString);
		response = discountCustomerSyncService.DiscountCustomerSync(jsonString);
		log.info("OaToPmsAction syncDiscountCustomer response:"+response.getMessage());
		
		if(null != response){
			if(BaseResponseEnum.SUCCESS.getStatus().equals(response.getStatus())){
				return returnSuccess();
			}else{
				return returnError(null, response);
			}
		}else{
			response.setStatus(BaseResponseEnum.SYSTEM_ERROR.getStatus());
			response.setMessage(BaseResponseEnum.SYSTEM_ERROR.getMessage());
			return returnError(null, response);
		}	
	}

	public BaseResponseVo<Object> getResponse() {
		return response;
	}

	public void setResponse(BaseResponseVo<Object> response) {
		this.response = response;
	}

}
