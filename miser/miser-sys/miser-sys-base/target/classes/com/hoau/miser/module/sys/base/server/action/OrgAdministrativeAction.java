package com.hoau.miser.module.sys.base.server.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.sys.base.api.server.service.IOrgAdministrativeInfoService;
import com.hoau.miser.module.sys.base.api.shared.domain.OrgAdministrativeInfoEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.PlatformEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.SalesDepartmentEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.TransferCenterEntity;
import com.hoau.miser.module.sys.base.api.shared.exception.ResourceException;
import com.hoau.miser.module.sys.base.api.shared.vo.OrgAdministrativeVo;
import com.hoau.miser.module.sys.base.api.shared.vo.OrgTreeNode;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 行政组织管理
 * 
 * @author 张贞献
 * @date 2015-7-16
 */
@Controller
@Scope("prototype")
public class OrgAdministrativeAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private OrgAdministrativeVo orgAdministrativeVo;

	private OrgAdministrativeInfoEntity orgInfoEntity;

	public OrgAdministrativeInfoEntity getOrgInfoEntity() {
		return orgInfoEntity;
	}

	public void setOrgInfoEntity(OrgAdministrativeInfoEntity orgInfoEntity) {
		this.orgInfoEntity = orgInfoEntity;
	}

	private List<OrgTreeNode<OrgAdministrativeInfoEntity>> nodes;

	private String node;

	// 菜单树展开路径集合
	private Set<String> pathList;

	/**
	 * 用于菜单树展开路径设置
	 */
	private String path = "";

	@Resource
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 跳转到行政组织管理界面
	 * 
	 * @return
	 * @author 张贞献
	 * @date 2015年7月16日
	 * @update
	 */
	public String orgAdministrative() {
		return this.returnSuccess();
	}

	/**
	 * 加载左侧部门菜单树
	 * 
	 * @return
	 * @author 张贞献
	 * @date 2015-7-20
	 * @update
	 */
	@JSON
	public String loadDepartmentTree() {
		return this.queryOrgByParentRes();
	}

	/**
	 * 加载菜单
	 * 
	 * @return
	 * @author 张贞献
	 * @date 2015-7-20
	 * @update
	 */
	@JSON
	private String queryOrgByParentRes() {
		// 根据父节点查询资源权限
		List<OrgAdministrativeInfoEntity> resources = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByParentCode(node.equals("Root") ? ""
						: node);
		this.nodes = new ArrayList<OrgTreeNode<OrgAdministrativeInfoEntity>>();

		// 树结点：
		for (OrgAdministrativeInfoEntity res : resources) {
			OrgTreeNode<OrgAdministrativeInfoEntity> treeNode = new OrgTreeNode<OrgAdministrativeInfoEntity>();
			treeNode.setId(res.getCode());
			treeNode.setText(res.getName());
			if (res.getChildren() == null || res.getChildren().size() == 0) {
				treeNode.setLeaf(true);
			}
			if (res.getParentCode() != null) {
				treeNode.setParentId(res.getParentCode());
			} else {
				treeNode.setParentId(null);
			}
			nodes.add(treeNode);
		}
		return this.returnSuccess();
	}

	/**
	 * 根据部门信息查询部门
	 * 
	 * @return
	 * @author 张贞献
	 * @date 2015-7-20
	 * @update
	 */
	@JSON
	public String queryTreePathForName() {
		try {
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = new OrgAdministrativeInfoEntity();
			orgAdministrativeInfoEntity.setActive(MiserConstants.ACTIVE);
			// 名称
			orgAdministrativeInfoEntity.setName(node);
			// 拼音全拼

			// 首字母大写

			List<OrgAdministrativeInfoEntity> resList = orgAdministrativeInfoService
					.queryOrgAdministrativeInfo(orgAdministrativeInfoEntity);

			pathList = new HashSet<String>();
			for (OrgAdministrativeInfoEntity res : resList) {
				node = res.getCode();
				this.expendTreePath();
				if (!"/Root".equals(path.trim())) {
					pathList.add(new String(path));
				}
				path = "";
			}
			return returnSuccess();
		} catch (ResourceException e) {
			return returnError(e);
		}
	}

	/**
	 * 加载组织所有信息
	 * 
	 * @return
	 * @author 张贞献
	 * @date 2015-7-24
	 * @update
	 */
	@JSON
	public String loadOrgAllInfo() {
		try {
			OrgAdministrativeInfoEntity administrativeInfo = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(node);
			orgAdministrativeVo = new OrgAdministrativeVo();
			if (administrativeInfo != null) {
				orgAdministrativeVo
						.setOrgAdministrativeInfoEntity(administrativeInfo);
				if (administrativeInfo.getIsSalesDepartment().equals(
						MiserConstants.YES)) {
					SalesDepartmentEntity salesDepartment = orgAdministrativeInfoService
							.querySalesDepartmentEntityByCode(node);
					orgAdministrativeVo
							.setSalesDepartmentEntity(salesDepartment);
				}
				if (administrativeInfo.getIsPlatform().equals(
						MiserConstants.YES)) {
					PlatformEntity platform = orgAdministrativeInfoService
							.queryPlatformEntityByCode(node);
					orgAdministrativeVo.setPlatformEntity(platform);
				}
				if (administrativeInfo.getIsTransferCenter().equals(
						MiserConstants.YES)) {
					TransferCenterEntity transferCenter = orgAdministrativeInfoService
							.queryTransferCenterEntityByCode(node);
					orgAdministrativeVo.setTransferCenterEntity(transferCenter);
				}
			}
		} catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 菜单树的展开路径加载
	 * 
	 * @return
	 * @author 高佳
	 * @date 2015年7月13日
	 * @update
	 */
	@JSON
	private String expendTreePath() {
		try {
			OrgAdministrativeInfoEntity parentRes = new OrgAdministrativeInfoEntity();
			String parentNode = node;
			while (parentNode != null && !"".equals(parentNode)) {
				path = "/" + parentNode + path;
				parentRes = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(parentNode);
				if (parentRes == null) {
					path = "";
					break;
				}
				parentNode = parentRes.getParentCode();
			}
			path = "/Root" + path;
			return returnSuccess();
		} catch (ResourceException e) {
			return returnError(e);
		}
	}

	/**
	 * 
	 * @Description: 获取组织完整路径，包括门店、路区、大区、事业部的名称代码等等，主要用于门店，需求来源网点折扣、网点价格业务.
	 * @param @return
	 * @return String
	 * @throws
	 * @author 赵金义
	 * @date 2016年3月1日
	 */
	@JSON
	public String queryOrgAdministrativeInfoByCode() {
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(orgAdministrativeVo.getOrgCode());
		setOrgInfoEntity(orgInfo);
		return returnSuccess();
	}

	public OrgAdministrativeVo getOrgAdministrativeVo() {
		return orgAdministrativeVo;
	}

	public void setOrgAdministrativeVo(OrgAdministrativeVo orgAdministrativeVo) {
		this.orgAdministrativeVo = orgAdministrativeVo;
	}

	public List<OrgTreeNode<OrgAdministrativeInfoEntity>> getNodes() {
		return nodes;
	}

	public void setNodes(List<OrgTreeNode<OrgAdministrativeInfoEntity>> nodes) {
		this.nodes = nodes;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public Set<String> getPathList() {
		return pathList;
	}

	public void setPathList(Set<String> pathList) {
		this.pathList = pathList;
	}

}
