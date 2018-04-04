package com.hoau.miser.module.sys.base.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.sys.base.api.server.service.IResourceService;
import com.hoau.miser.module.sys.base.api.shared.domain.ResourceEntity;
import com.hoau.miser.module.sys.base.api.shared.exception.ResourceException;
import com.hoau.miser.module.sys.base.server.cache.ResourceCodeCache;
import com.hoau.miser.module.sys.base.server.cache.ResourceMenuCache;
import com.hoau.miser.module.sys.base.server.cache.ResourceUriCache;
import com.hoau.miser.module.sys.base.server.dao.ResourceDao;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：高佳
 * @create：2015年6月8日 下午7:40:07
 * @description：权限资源service
 */
@Service
public class ResourceService implements IResourceService{
	@Resource
	private ResourceDao resourceDao;

	@Override
	@SuppressWarnings("unchecked")
	public List<ResourceEntity> queryResourcesByParentCode(String parentCode) {
		ICache<String, List<ResourceEntity>> resMenuCache = CacheManager
				.getInstance().getCache(ResourceMenuCache.MENU_CACHE_UUID);
		List<ResourceEntity> resMenus = resMenuCache.get(parentCode);
		if (resMenus == null) {
			throw new ResourceException(ResourceException.RESOURCE_URI_NULL);
		}
		return resMenus;
	}

	@Override
	public List<ResourceEntity> queryResourceBatchByCode(String[] array) {
		List<ResourceEntity> resources = new ArrayList<ResourceEntity>();
		for (String code : array) {
			ResourceEntity resource = this.queryResourceByCode(code);
			resources.add(resource);
		}
		return resources;
	}

	@Override
	public List<ResourceEntity> queryResourceByEntity(ResourceEntity entity, int limit, int start) {
		entity.setActive(MiserConstants.ACTIVE);
		RowBounds rowBounds=new RowBounds(start,limit);
		List<ResourceEntity> entityResults = resourceDao.queryResourceByResourceEntity(entity,rowBounds);
		return entityResults;
	}
	
	@Override
	public List<ResourceEntity> queryResourceByEntity(ResourceEntity entity) {
		entity.setActive(MiserConstants.ACTIVE);
		List<ResourceEntity> entityResults = resourceDao.queryResourceByEntity(entity);
		return entityResults;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResourceEntity queryResourceRoot(String belongSystemType) {
		ICache<String, ResourceEntity> resUriCache = CacheManager.getInstance()
				.getCache(ResourceUriCache.RESOURCE_URI_CACHE_UUID);
		ResourceEntity entityResults = resUriCache.get(belongSystemType);
		return entityResults;
	}
	
	
	/**
	 * 根据code查询权限
	 * @param node
	 * @return
	 * @author 高佳
	 * @date 2015年7月13日
	 * @update 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResourceEntity queryResourceByCode(String code) {
		if (StringUtil.isBlank(code)) {
			throw new ResourceException(ResourceException.RESOURCE_CODE_NULL);
		}
		ICache<String, ResourceEntity> resCache = CacheManager.getInstance()
				.getCache(ResourceCodeCache.RESOURCE_CODE_CACHE_UUID);
		ResourceEntity res = null;
		if (resCache != null) {
			res = resCache.get(code);
		}
		return res;
	}

	@Override
	@Transactional
	public void addResourceEntity(ResourceEntity resourceEntity) {
		Date dt = new Date();
		resourceEntity.setId(UUIDUtil.getUUID());
		resourceEntity.setCreateDate(dt);
		resourceEntity.setVersionNo(UUIDUtil.getVersion());
		resourceEntity.setCreateUser(MiserUserContext.getCurrentUser().getUserName());
		resourceEntity.setModifyDate(dt);
		resourceEntity.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
		resourceEntity.setActive(MiserConstants.YES);
		resourceDao.addResourceEntity(resourceEntity);
	}

	@Override
	@Transactional
	public void updateResourceEntity(ResourceEntity resourceEntity) {
		Date dt = new Date();
		resourceEntity.setVersionNo(UUIDUtil.getVersion());
		resourceEntity.setModifyDate(dt);
		resourceEntity.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
		resourceEntity.setActive(MiserConstants.NO);
		
		
		
		ResourceEntity resourcelin = new ResourceEntity();
		resourcelin.setId(resourceEntity.getId());
		resourcelin.setActive(MiserConstants.YES);
		ResourceEntity resource = resourceDao.queryResourceByEntity(resourcelin).get(0);
		
		resourceDao.deleteResourceEntity(resourceEntity);
		
		resource.setId(UUIDUtil.getUUID());
		resource.setCreateDate(dt);
		resource.setCreateUser(MiserUserContext.getCurrentUser().getUserName());
		resource.setActive(MiserConstants.YES);
		resource.setVersionNo(UUIDUtil.getVersion());
		
		resourceDao.addResourceEntity(resource);
		resourceEntity.setId(resource.getId());
		resourceEntity.setCreateDate(dt);
		resourceEntity.setCreateUser(MiserUserContext.getCurrentUser().getUserName());
		resourceEntity.setVersionNo(UUIDUtil.getVersion());
		resourceEntity.setActive(MiserConstants.YES);
		resourceDao.updateResourceEntity(resourceEntity);
	}

	@Override
	@Transactional
	public void deleteResourceEntity(List<ResourceEntity> resourceEntity) {
		for(ResourceEntity resource:resourceEntity){
			List<ResourceEntity> list = queryResourcesByParentCode(resource.getCode());
			if(list.size()>0)
				deleteResourceEntity(list);
			resource.setVersionNo(UUIDUtil.getVersion());
			resource.setModifyDate(new Date());
			resource.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
			resource.setActive(MiserConstants.NO);
			resourceDao.deleteResourceEntity(resource);
		}
	}

	@Override
	public Long queryCountByResource(ResourceEntity resourceEntity) {
		resourceEntity.setActive(MiserConstants.YES);
		return resourceDao.queryCountByRserouce(resourceEntity);
	}

}
