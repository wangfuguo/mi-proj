package com.hoau.miser.module.biz.discount.server.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventOrgEntity;
import com.hoau.miser.module.biz.discount.server.dao.PriceEvnetDao;
import com.hoau.miser.module.sys.base.api.shared.vo.OrgTreeNode;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 树节点缓存
 * ClassName: PriceSectionCacheProvider 
 * @author 王茂
 * @date 2016年1月7日
 * @version V1.0
 */
@Component
public class PriceEventOrgCacheProvider implements ITTLCacheProvider<List<OrgTreeNode<PriceEventOrgEntity>>> {
	@Resource
	private PriceEvnetDao priceEvnetDao;
	@Override
	public List<OrgTreeNode<PriceEventOrgEntity>> get(String oldEntity) {
			List<OrgTreeNode<PriceEventOrgEntity>> list=new ArrayList<OrgTreeNode<PriceEventOrgEntity>>();
		    PriceEventOrgEntity orgEntity=JSON.parseObject(oldEntity, PriceEventOrgEntity.class);
		    List<PriceEventOrgEntity> orgList =priceEvnetDao.queryOrgListByParam(orgEntity);
			for (PriceEventOrgEntity entity : orgList) {
				OrgTreeNode<PriceEventOrgEntity> treeNode = new OrgTreeNode<PriceEventOrgEntity>();
				treeNode.setId(entity.getCode());
				treeNode.setText(entity.getName());
				if (entity.getChildren() == null
						|| entity.getChildren().size() == 0){
					treeNode.setLeaf(true);
					treeNode.setExpanded(false);
				}else{
					treeNode.setExpanded(true);
				}
				treeNode.setChecked(MiserConstants.YES.equals(entity.getIsCheck()));
				if (entity.getParentCode() != null) {
					treeNode.setParentId(entity.getParentCode());
				} else {
					treeNode.setParentId(null);
				}
				treeNode.setEntity(entity);
				list.add(treeNode);
			}
			return list;
	}

}
