package com.hoau.miser.module.biz.base.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.miser.module.biz.base.api.server.service.ITranstypeService;
import com.hoau.miser.module.biz.base.api.shared.define.BizBaseConstant;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.TranstypeException;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.biz.base.server.cache.TranstypeCache;
import com.hoau.miser.module.biz.base.server.dao.TranstypeDao;
import com.hoau.miser.module.sys.base.api.shared.exception.MessageType;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @Description	运输类型后台处理类 
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0   
 */
@Service
public class TranstypeService implements ITranstypeService {

    @Resource
    TranstypeDao transtypeDao;

    /**
     * @Description 根据查询条件查询运输类型列表
     * @param transtypeVo   查询参数
     * @return  查询结果列表
     */
    public List<TranstypeEntity> queryTranstypes(TranstypeVo transtypeVo, int start, int limit) {
    	RowBounds rowBounds = new RowBounds(start, limit);
        TranstypeEntity entity = transtypeVo.getTranstypeEntity();
        return transtypeDao.queryTranstypes(entity, rowBounds);
    }

    /**
     * @Description     新增/修改运输类型,根据是否有id进行判断
     * @param transtypeVo   新增/修改数据
     * @return          新增/修改成功后提示内容
     */
    @Override
    @Transactional
    public String postTranstype(TranstypeVo transtypeVo) {
        Date date = new Date();
        TranstypeEntity entity = transtypeVo.getTranstypeEntity();
        entity.setActive(MiserConstants.ACTIVE);
        entity.setInvalid(MiserConstants.NO);
        String id = entity.getId();
        if (StringUtils.isEmptyWithBlock(id)){
            //校验编码是否重复
            TranstypeEntity old_entity = queryTranstypeByCode(entity.getCode());
            if (old_entity != null) {
                throw new TranstypeException(TranstypeException.TRANSTYPE_CODE_CONFLICT, "编码重复!");
            }
            //校验名称是否重复
            old_entity = queryTranstypeByName(entity.getName());
            if (old_entity != null) {
                throw new TranstypeException(TranstypeException.TRANSTYPE_NAME_CONFLICT, "名称重复!");
            }

            entity.setId(UUIDUtil.getUUID());
            entity.setInvalid(MiserConstants.NO);
            entity.setActive(MiserConstants.ACTIVE);
            entity.setCreateDate(date);
            entity.setCreateUser(MiserUserContext.getCurrentUser().getUserName());
            entity.setModifyDate(date);
            entity.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
            if (StringUtils.isEmpty(entity.getParentCode())) {
				entity.setParentCode(BizBaseConstant.ROOT_TRANSTYPE_CODE);
			}
            transtypeDao.addTranstype(entity);
            return MessageType.ADD_SUCCESS;
        } else {
            //检查是否可以修改
        	checkCanModify(entity.getId());
            //校验名称是否重复
            TranstypeEntity old_entity = queryTranstypeByName(entity.getName());
            if (old_entity != null && !old_entity.getId().equals(entity.getId())) {
                throw new TranstypeException(TranstypeException.TRANSTYPE_NAME_CONFLICT, "名称重复!");
            }
            //历史记录设置为无效
            entity.setInvalid(MiserConstants.YES);
            entity.setActive(MiserConstants.INACTIVE);
            entity.setModifyDate(date);
            entity.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
            transtypeDao.deleteTranstype(entity);

            //插入新纪录
            entity.setId(UUIDUtil.getUUID());
            entity.setInvalid(MiserConstants.NO);
            entity.setActive(MiserConstants.ACTIVE);
            entity.setCreateDate(date);
            entity.setCreateUser(MiserUserContext.getCurrentUser().getUserName());
            entity.setModifyDate(date);
            entity.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
            transtypeDao.addTranstype(entity);
            if (StringUtils.isEmpty(entity.getParentCode())) {
				entity.setParentCode(BizBaseConstant.ROOT_TRANSTYPE_CODE);
			}
            return MessageType.UPDATE_SUCCESS;
        }
    }

    /**
     * @Description 作废运输类型
     * @param transtypeVo   需要作废的运输类型
     */
    @Override
    public void deleteTranstype(TranstypeVo transtypeVo) {
        TranstypeEntity entity = transtypeVo.getTranstypeEntity();
        //检查是否可进行作废，不可进行作废则会直接抛出异常
    	checkCanDelete(entity.getId());
    	entity.setInvalid(MiserConstants.YES);
        entity.setActive(MiserConstants.ACTIVE);
        entity.setModifyDate(new Date());
        entity.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
        transtypeDao.deleteTranstype(entity);
    }

    /**
     * @Description     根据id查询运输类型
     * @param transtypeVo   修改时加载数据使用
     * @return
     */
    @Override
    public TranstypeEntity queryTranstypeById(TranstypeVo transtypeVo) {
        TranstypeEntity entity = transtypeVo.getTranstypeEntity();
        if (entity != null) {
            return transtypeDao.queryTranstypeById(entity.getId());
		} else {
			return null;
		}
    }

    /**
     * @Description 使用缓存，根据code查询运输类型,用于判断是否重复
     * @param code  用于判断的code
     * @return      如果存在则返回此code对应的有效的运输类型
     */
    private TranstypeEntity queryTranstypeByCode(String code) {
		ICache<String, TranstypeEntity> transtypeCache = this.getTranstypeCache();
		TranstypeEntity entity = transtypeCache.get(code);
		return entity;		
    }

	/**
	 * @Description: 获取查询运输类型缓存
	 * @return ICache<String,TranstypeEntity> 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2015年12月26日
	 */
	@SuppressWarnings("unchecked")
	private ICache<String, TranstypeEntity> getTranstypeCache(){
		ICache<String, TranstypeEntity> roleCache = CacheManager.getInstance().getCache(TranstypeCache.TRANSTYPE_CACHE_UUID);
		return roleCache;
	}

    /**
     * @Description 根据name查询运输类型,用于判断是否重复
     * @param name  用于判断的name
     * @return      如果存在则返回此name对应的有效的运输类型
     */
    private TranstypeEntity queryTranstypeByName(String name) {
        TranstypeEntity entity = new TranstypeEntity();
        entity.setInvalid(MiserConstants.NO);
        entity.setActive(MiserConstants.ACTIVE);
        entity.setName(name);
        return transtypeDao.queryTranstypeByName(entity);
    }
    
    /**
     * @Description: 判断是否可进行操作
     * @param  entity
     * @return boolean 
     * @author yulin.chen@newhoau.com.cn
     * @date 2015年12月26日
     */
    private void checkCanModify(String id) {
    	TranstypeEntity entity = transtypeDao.queryTranstypeById(id);
    	if (entity == null || MiserConstants.YES.equals(entity.getInvalid())) {
    		throw new TranstypeException(TranstypeException.TRANSTYPE_CANT_MODIFY_INACTIVE, "不可修改状态为无效的记录!");
		}
    }
    
    /**
     * @Description: 判断是否可进行操作
     * @param  entity
     * @return boolean 
     * @author yulin.chen@newhoau.com.cn
     * @date 2015年12月26日
     */
    private void checkCanDelete(String id) {
    	TranstypeEntity entity = transtypeDao.queryTranstypeById(id);
    	if (entity == null || MiserConstants.YES.equals(entity.getInvalid())) {
    		throw new TranstypeException(TranstypeException.TRANSTYPE_CANT_DELETE_INACTIVE, "不可重复作废");	
		}
    }

	public List<TranstypeEntity> queryTranstypes(TranstypeVo transtypeVo) {
		TranstypeEntity entity = transtypeVo.getTranstypeEntity();
		entity.setActive(MiserConstants.ACTIVE);
		entity.setInvalid(MiserConstants.NO);
		return transtypeDao.queryTranstypes(entity);
	}

}
