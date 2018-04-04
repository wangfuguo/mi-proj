package com.hoau.miser.module.sys.base.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.components.security.SecurityNonCheckRequired;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.common.server.interceptor.CookieNonCheckRequired;
import com.hoau.miser.module.sys.base.api.server.service.ICommonOrgService;
import com.hoau.miser.module.sys.base.api.shared.domain.CommonOrgEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.OrgAdministrativeInfoEntity;
import com.hoau.miser.module.sys.base.api.shared.vo.CommonOrgVo;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;

/**
 * @author：高佳
 * @create：2015年6月30日 下午1:47:09
 * @description：公共选择器组织相关Action
 */
@Controller
@Scope("prototype")
public class CommonOrgSearchAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private CommonOrgVo commonOrgVo;

	private OrgAdministrativeInfoEntity orgInfoEntity;

	public OrgAdministrativeInfoEntity getOrgInfoEntity() {
		return orgInfoEntity;
	}

	public void setOrgInfoEntity(OrgAdministrativeInfoEntity orgInfoEntity) {
		this.orgInfoEntity = orgInfoEntity;
	}

	@Resource
	private ICommonOrgService commonOrgService;

	/**
	 * 
	 * @Description: 获取当前用户的默认组织机构，包括事业部大区路区门店
	 * @param @return
	 * @return String
	 * @throws
	 * @author 赵金义
	 * @date 2016年1月13日
	 */
	@JSON
	public String loadDefaultOrgInfo() {
		OrgAdministrativeInfoEntity org = MiserUserContext.getCurrentDept();
		// 设置路区门店编码
		if ("Y".equals(org.getIsSalesDepartment())) {
			org.setSalesDepartmentCode(org.getCode());
			org.setSalesDepartmentName(org.getName());
			org.setRoadAreaCode(org.getParentCode());
			org.setRoadAreaName(org.getParentName());
		} else if ("Y".equals(org.getIsRoadArea())) {
			org.setRoadAreaCode(org.getCode());
			org.setRoadAreaName(org.getName());
		}
		setOrgInfoEntity(org);
		return returnSuccess();
	}

	/**
	 * 根据条件查询组织信息
	 * 
	 * @return
	 * @author 高佳
	 * @date 2015年6月30日
	 * @update
	 */
	@JSON
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String seacrhOrgByParam() {
		commonOrgVo.getOrgSearchConditionEntity().setLimit(limit);
		commonOrgVo.getOrgSearchConditionEntity().setStart(start);
		List<CommonOrgEntity> commonOrgEntityList = commonOrgService
				.queryOrgByParam(commonOrgVo.getOrgSearchConditionEntity());

		setTotalCount(commonOrgService.countOrgByParam(commonOrgVo
				.getOrgSearchConditionEntity()));
		commonOrgVo.setCommonOrgEntityList(commonOrgEntityList);
		return returnSuccess();
	}

	@JSON
	public String searchDivisions() {
		commonOrgVo.getOrgSearchConditionEntity().setType("ORG");
		commonOrgVo.getOrgSearchConditionEntity().setIsDivision("Y");
		commonOrgVo.getOrgSearchConditionEntity().setActive("Y");
		commonOrgVo.getOrgSearchConditionEntity().setLimit(limit);
		commonOrgVo.getOrgSearchConditionEntity().setStart(start);
		commonOrgVo.setCommonOrgEntityList(commonOrgService
				.queryOrgByParam(commonOrgVo.getOrgSearchConditionEntity()));
		return returnSuccess();
	}

	@JSON
	public String searchBigRegion() {
		commonOrgVo.getOrgSearchConditionEntity().setType("ORG");
		commonOrgVo.getOrgSearchConditionEntity().setIsBigRegion("Y");
		commonOrgVo.getOrgSearchConditionEntity().setActive("Y");
		commonOrgVo.getOrgSearchConditionEntity().setLimit(limit);
		commonOrgVo.getOrgSearchConditionEntity().setStart(start);
		commonOrgVo.setCommonOrgEntityList(commonOrgService
				.queryOrgByParam(commonOrgVo.getOrgSearchConditionEntity()));
		return returnSuccess();
	}

	@JSON
	public String searchRoadArea() {
		commonOrgVo.getOrgSearchConditionEntity().setType("ORG");
		commonOrgVo.getOrgSearchConditionEntity().setIsRoadArea("Y");
		commonOrgVo.getOrgSearchConditionEntity().setActive("Y");
		commonOrgVo.getOrgSearchConditionEntity().setLimit(limit);
		commonOrgVo.getOrgSearchConditionEntity().setStart(start);
		commonOrgVo.setCommonOrgEntityList(commonOrgService
				.queryOrgByParam(commonOrgVo.getOrgSearchConditionEntity()));
		return returnSuccess();
	}

	@JSON
	public String searchSalesDepartment() {
		commonOrgVo.getOrgSearchConditionEntity().setType("ORG");
		commonOrgVo.getOrgSearchConditionEntity().setIsSalesDepartment("Y");
		commonOrgVo.getOrgSearchConditionEntity().setActive("Y");
		commonOrgVo.getOrgSearchConditionEntity().setLimit(limit);
		commonOrgVo.getOrgSearchConditionEntity().setStart(start);
		commonOrgVo.setCommonOrgEntityList(commonOrgService
				.queryOrgByParam(commonOrgVo.getOrgSearchConditionEntity()));
		return returnSuccess();
	}

	public CommonOrgVo getCommonOrgVo() {
		return commonOrgVo;
	}

	public void setCommonOrgVo(CommonOrgVo commonOrgVo) {
		this.commonOrgVo = commonOrgVo;
	}

}
