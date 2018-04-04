package com.hoau.miser.module.sys.base.api.shared.vo;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.TreeNode;

/**
 * @author：张贞献
 * @create：2015-7-20 上午10:30:32
 * @description：部门树菜单
 */
@SuppressWarnings("rawtypes")
public class OrgTreeNode <T extends BaseEntity> extends TreeNode<T,OrgTreeNode>{
	
	
private Boolean expanded;

public Boolean getExpanded() {
	return expanded;
}

public void setExpanded(Boolean expanded) {
	this.expanded = expanded;
}

}
