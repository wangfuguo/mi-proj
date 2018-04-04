package com.hoau.miser.module.sys.base.server.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.components.security.SecurityNonCheckRequired;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.common.server.interceptor.CookieNonCheckRequired;
import com.hoau.miser.module.sys.base.api.server.service.IDistrictService;
import com.hoau.miser.module.sys.base.api.shared.domain.DistrictEntity;
import com.hoau.miser.module.sys.base.api.shared.exception.ResourceException;
import com.hoau.miser.module.sys.base.api.shared.vo.DistrictTreeNode;
import com.hoau.miser.module.sys.base.api.shared.vo.DistrictVo;

/**
 * 价格城市Action
 * @author 289612
 *@DATE 2015年12月08日
 */
@Controller
@Scope("prototype")
public class DistrictAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private IDistrictService districtService;
	private DistrictVo districtVo;

	private String node;

	// 菜单树展开路径集合
	private Set<String> pathList;

	private List<DistrictTreeNode<DistrictEntity>> nodes;
	/**
	 * 用于菜单树展开路径设置
	 */
	private String path = "";

	/**
	 *  跳转到价格信息界面
	 * @author 289612
	 * @return
	 * @date 2015年12月08日
	 * @select
	 */
	public String district(){

		return  this.returnSuccess();
	}

	/**
	 * 加载左侧部门菜单树
	 * 
	 * @return
	 * @author 刘海飞
	 * @date 2015年12月9日
	 * @update
	 */
	@JSON
	public String loadDistrictTree(){
		return this.queryDistrictByParentRes();
	}
	/**
	 * 加载菜单
	 * 
	 * @return
	 * @author 刘海飞
	 * @date 2015年12月9日
	 * @update
	 */
	@JSON
	private String queryDistrictByParentRes() {
		// 根据父节点查询资源权限
		List<DistrictEntity> resources = districtService
				.queryDistrictInfoEntityInfoByParentCode(node.equals("Root") ? ""
						: node);
		this.nodes = new ArrayList<DistrictTreeNode<DistrictEntity>>();
		// 树结点：
		for (DistrictEntity res : resources) {
			DistrictTreeNode<DistrictEntity> treeNode = new DistrictTreeNode<DistrictEntity>();
			treeNode.setId(res.getDistrictCode());
			treeNode.setText(res.getDistrictName());
			if (res.getChildren() == null || res.getChildren().size() == 0) {
				treeNode.setLeaf(true);
			}
			if (res.getParentDistrictCode() != null) {
				treeNode.setParentId(res.getParentDistrictCode());
			} else {
				treeNode.setParentId(null);
			}
			nodes.add(treeNode);
		}
		return this.returnSuccess();
	}

	/**
	 * 根据名称查询组织路径
	 * 
	 * @return
	 * @author 刘海飞
	 * @date 2015年12月10日
	 * @update
	 */
	@JSON
	public String queryTreePathForName() {
		try {
			List<DistrictEntity> resList = districtService.queryDistrictInfoByName(node);

			pathList = new HashSet<String>();
			for (DistrictEntity res : resList) {
				node = res.getDistrictCode();
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
			DistrictEntity parentRes = new DistrictEntity();
			String parentNode = node;
			while (parentNode != null && !"".equals(parentNode)) {
				path = "/" + parentNode + path;
				parentRes = districtService.queryDistrictInfoByCode(parentNode);
				if (parentRes == null) {
					path = "";
					break;
				}
				parentNode = parentRes.getParentDistrictCode();
			}
			path = "/Root" + path;
			return returnSuccess();
		} catch (ResourceException e) {
			return returnError(e);
		}
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

	public List<DistrictTreeNode<DistrictEntity>> getNodes() {
		return nodes;
	}

	public void setNodes(List<DistrictTreeNode<DistrictEntity>> nodes) {
		this.nodes = nodes;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String queryDistrictByName(){
		districtVo.setDistrictList(districtService.queryDistrictByEntity(districtVo.getDistrict(),limit,start));
		return returnSuccess();
	}
	
	/**
	 * 查询所有国家信息
	 * @return
	 * @author 高佳
	 * @date 2015年7月9日
	 * @update 
	 */
    @SecurityNonCheckRequired
    @CookieNonCheckRequired
	public String queryAllNation(){
		districtVo = new DistrictVo();
		districtVo.setDistrictList(districtService.queryAllNation(limit,start));
		return returnSuccess();
	}
	
	/**
	 * 根据编号拿到实体
	 * @Description: TODO描述该方法是做什么的
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 廖文强
	 * @date 2016年3月16日
	 */
    @SecurityNonCheckRequired
    @CookieNonCheckRequired    
	public String queryDistrictByCode(){
		districtVo.setDistrict(districtService.queryDistrictInfoByCode(districtVo.getDistrict().getDistrictCode()));
		return returnSuccess();
	}
	
	/**
	 * 查询省份
	 * @return
	 * @author 高佳
	 * @date 2015年7月9日
	 * @update 
	 */
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String queryProvince(){
		districtVo.getDistrict().setDistrictType("PROVINCE");
		districtVo.setDistrictList(districtService.queryDistrictByEntity(districtVo.getDistrict(), limit, start));
		return returnSuccess();
	}
	
	@SecurityNonCheckRequired
	@CookieNonCheckRequired	
	public String queryCity(){
		districtVo.getDistrict().setDistrictType("CITY");
		districtVo.setDistrictList(districtService.queryDistrictByEntity(districtVo.getDistrict(), limit, start));
		return returnSuccess();
	}
	
	@SecurityNonCheckRequired
	@CookieNonCheckRequired	
	public String queryArea(){
		districtVo.getDistrict().setDistrictType("AREA");
		districtVo.setDistrictList(districtService.queryDistrictByEntity(districtVo.getDistrict(), limit, start));
		return returnSuccess();
	}
	public DistrictVo getDistrictVo() {
		return districtVo;
	}

	public void setDistrictVo(DistrictVo districtVo) {
		this.districtVo = districtVo;
	}

}
