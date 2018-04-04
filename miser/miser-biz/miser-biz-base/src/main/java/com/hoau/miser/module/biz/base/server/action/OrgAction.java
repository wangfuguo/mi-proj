package com.hoau.miser.module.biz.base.server.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.context.UserContext;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.IOrgService;
import com.hoau.miser.module.biz.base.api.shared.domain.OrgEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.OrgVo;

/**
 * ORG组织Action
 * ClassName: OrgAction 
 * @author 286330付于令
 * @date 2016年1月18日
 * @version V1.0
 */
@Controller
public class OrgAction extends AbstractAction {
	@Resource
	private IOrgService orgService;
	
	private OrgVo orgVo;
	
	public OrgVo getOrgVo() {
		return orgVo;
	}

	public void setOrgVo(OrgVo orgVo) {
		this.orgVo = orgVo;
	}

	public String queryCurrentUserORG() {
		String userName = UserContext.getCurrentUser().getUserName();
		if(StringUtil.isNotEmpty(userName)) {
			OrgEntity orgEntity = new OrgEntity();
			orgEntity.setUserName(userName);
			OrgEntity currentUserOrg = orgService.queryCurrentUserOrg(orgEntity);
			orgVo = new OrgVo();
			orgVo.setOrgEntity(currentUserOrg);	
		}
		return returnSuccess();
	}

}
