package com.hoau.miser.module.biz.extrafee.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.miser.module.biz.extrafee.server.cache.PriceUpstairsCache;
import com.hoau.miser.module.biz.extrafee.server.dao.PriceUpstairsDao;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceUpstairsService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceUpstairsEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.exception.PriceUpstairsException;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceUpstairsVo;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * ClassName: PriceUpstairsService 
 * @Description: 上楼费逻辑处理类 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月5日
 * @version V1.0   
 */
@Service
public class PriceUpstairsService implements IPriceUpstairsService {

	@Resource
	PriceUpstairsDao priceUpstairsDao;
	
	@Override
	public List<PriceUpstairsEntity> queryUpstairsPrices(PriceUpstairsVo priceUpstairsVo,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		PriceUpstairsEntity entity = priceUpstairsVo.getPriceUpstairsEntity();
		return priceUpstairsDao.queryUpstairsPrices(entity, rowBounds);
	}

	@Override
	public Long queryUpstairsPricesCount(PriceUpstairsVo priceUpstairsVo) {
		PriceUpstairsEntity entity = priceUpstairsVo.getPriceUpstairsEntity();
		return priceUpstairsDao.queryUpstairsPricesCount(entity);
	}
	
	@Override
	public PriceUpstairsEntity queryUpstairsPriceById(PriceUpstairsVo priceUpstairsVo) {
		String id = priceUpstairsVo.getPriceUpstairsEntity().getId();
		return getUpstairsEntityById(id);
	}

	public String checkSaveData(PriceUpstairsVo priceUpstairsVo, String saveModel){
		PriceUpstairsEntity entity = priceUpstairsVo.getPriceUpstairsEntity();
		
		//修改时需要检查是否允许 修改
		if ("MODIFY".equals(saveModel)) {
			//修改时需要检查修改的数据是否允许修改
			checkCanOpr(entity.getId());
		}

		//检查是否存在待生效数据
		PriceUpstairsEntity checkResultEntity = getWaitData(entity);
		// 如果查询到待生效数据
		if (checkResultEntity != null
				&& !StringUtils.isEmpty(checkResultEntity.getId())
				&& (StringUtils.isEmpty(entity.getId()) // 本次新增
				// 或修改，但是修改的数据和待生效的不是同一条(修改的是生效中的数据)
				|| (!StringUtils.isEmpty(entity.getId()) && !checkResultEntity.getId().equals(entity.getId())))) {
			return MessageType.PRICE_UPSTAIRS_EXISTS_WAIT;
		}
		return MessageType.PRICE_UPSTAIRS_CHECK_SUCCESS;
	}

	@Override
	@Transactional
	public void saveUpstairsPrice(PriceUpstairsVo priceUpstairsVo) {
		PriceUpstairsEntity entity = priceUpstairsVo.getPriceUpstairsEntity();
		
		//修改前判断是否可修改
		if (!StringUtils.isEmpty(entity.getId())) {
			checkCanOpr(entity.getId());
		}
		
		//验证生效时间是否在当前时间之后
		Date now = new Date();
		Date effectiveDate = entity.getEffectiveTime();
		if (now.compareTo(effectiveDate) > 0) {
			throw new PriceUpstairsException(PriceUpstairsException.EFFECTIVETIME_TOO_EARLY);
		}
		
		//作废待生效数据
		destoryWaitData(entity, now);
		
		//查询是否有生效中的数据,使用本次修改数据的生效时间设置生效中数据的失效时间
		setInvalidTime(entity, now, entity.getEffectiveTime());
		
		//插入一条新数据
		insertNewData(entity, now);
	}

	@Override
	public String checkDeleteData(PriceUpstairsVo priceUpstairsVo) {
		PriceUpstairsEntity entity = priceUpstairsVo.getPriceUpstairsEntity();
		//检查删除的数据是否是过期的数据，如果是，直接抛出异常
		checkCanOpr(entity.getId());
		
		//检查是否存在待生效数据
		PriceUpstairsEntity checkResultEntity = getUpstairsEntityById(entity.getId());
		checkResultEntity = getWaitData(checkResultEntity);
		//如果当前删除的数据的id和待生效数据的id不同，说明删除的是生效中的数据，提示是否同时删除待生效数据s
		if (checkResultEntity != null 
				&& !StringUtils.isEmpty(checkResultEntity.getId())
				&& !checkResultEntity.getId().equals(entity.getId())) {
			return MessageType.PRICE_UPSTAIRS_EXISTS_WAIT;
		}
		return MessageType.PRICE_UPSTAIRS_CHECK_SUCCESS;
	}
	
	@Override
	@Transactional
	public void deleteUpstairsPrice(PriceUpstairsVo priceUpstairsVo) {
		PriceUpstairsEntity entity = priceUpstairsVo.getPriceUpstairsEntity();
		Date now = new Date();
		//检查是否允许删除
		checkCanOpr(entity.getId());
		
		//查询是否存在待生效数据
		PriceUpstairsEntity checkResultEntity = getUpstairsEntityById(entity.getId());
		//删除的数据生效时间在当前时间之后的，说明是待生效数据，直接作废，
		if (now.compareTo(checkResultEntity.getEffectiveTime()) < 0) {
			checkResultEntity.setActive(MiserConstants.INACTIVE);
			checkResultEntity.setModifyDate(now);
			checkResultEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			priceUpstairsDao.deleteUpstairsPrice(checkResultEntity);
			
			//查询是否存在生效中的数据，存在则清空失效时间
			checkResultEntity.setInvalidTime(null);
			setInvalidTime(checkResultEntity, now, null);
		} else { //删除的是生效中的数据
			//查询是否有待生效，有的话作废掉
			destoryWaitData(checkResultEntity, now);
			
			//设置当前删除的数据的失效时间为当前时间
			checkResultEntity.setActive(MiserConstants.INACTIVE);
			checkResultEntity.setInvalidTime(now);
			checkResultEntity.setModifyDate(now);
			checkResultEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			priceUpstairsDao.updateInvalidTime(checkResultEntity);
		}
	}
	
	/**
	 * @Description:	根据ID查询上楼费定义
	 * @param id	查询参数id
	 * @return PriceUpstairsEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月7日
	 */
	public PriceUpstairsEntity getUpstairsEntityById(String id) {
		ICache<String, PriceUpstairsEntity> priceUpstairsCache = CacheManager.getInstance().getCache(PriceUpstairsCache.PRICEUPSTAIRS_CACHE_UUID);
		return priceUpstairsCache.get(id);
	}
	
	/**
	 * @Description: 检查是否可以操作
	 * @param id   操作的数据主键
	 * @throws	如不允许操作抛出PriceUpstairsException异常
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	public void checkCanOpr(String id){
		//检查删除的数据是否是过期的数据，如果是，直接抛出异常
		PriceUpstairsEntity checkResultEntity = getUpstairsEntityById(id);
		if (checkResultEntity != null && !StringUtils.isEmpty(checkResultEntity.getId())) {
			//重复删除数据
			String active = checkResultEntity.getActive();
			if (MiserConstants.INACTIVE.equals(active)) {
				throw new PriceUpstairsException(PriceUpstairsException.OPR_DELETED_DATA);
			}

			//删除已过期数据
			Date invalidTime = checkResultEntity.getInvalidTime();
			if (invalidTime != null && new Date().compareTo(invalidTime) > 0) {
				throw new PriceUpstairsException(PriceUpstairsException.CAN_NOT_OPR_PASSED_DATA);
			}
		} else {
			throw new PriceUpstairsException(PriceUpstairsException.MISS_OPR_DATE);
		}
	}

	
	/**
	 * @Description: 获取待生效数据
	 * @param upstairType	上楼类型
	 * @return PriceUpstairsEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	public PriceUpstairsEntity getWaitData(PriceUpstairsEntity entity) {
		PriceUpstairsEntity checkEntity = new PriceUpstairsEntity();
		checkEntity.setType(entity.getType());
		checkEntity.setTransportType(entity.getTransportType());
		checkEntity.setActive(MiserConstants.ACTIVE);
		return priceUpstairsDao.checkExistsWaitUpstairsPrice(checkEntity);
	}

	/**
	 * @Description: 作废待生效数据(如果存在就作废，不存在不用操作)
	 * @param entity   	前台传入的需要作废的相关数据
	 * @param now   	操作时间
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	@Transactional
	public void destoryWaitData(PriceUpstairsEntity entity, Date now){
		PriceUpstairsEntity checkResultEntity = getWaitData(entity);
		if (checkResultEntity != null && !StringUtils.isEmpty(checkResultEntity.getId())) {
			//作废旧的待生效数据
			checkResultEntity.setActive(MiserConstants.INACTIVE);
			checkResultEntity.setModifyDate(now);
			checkResultEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			priceUpstairsDao.deleteUpstairsPrice(checkResultEntity);
		}
	}
	
	/**
	 * @Description: 设置生效中数据的失效时间(如果存在生效中的数据则设置，如果不存在则忽略)
	 * @param entity   	前台传入的需要设置失效时间的相关数据
	 * @param now   	操作时间
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	@Transactional
	public void setInvalidTime(PriceUpstairsEntity entity, Date now, Date invalidTime) {
		PriceUpstairsEntity checkEntity = new PriceUpstairsEntity();
		checkEntity.setType(entity.getType());
		checkEntity.setActive(MiserConstants.ACTIVE);
		checkEntity.setTransportType(entity.getTransportType());
		PriceUpstairsEntity checkResultEntity = priceUpstairsDao.checkExistsEffectiveUpstairsPrice(checkEntity);
		if (checkResultEntity != null && !StringUtils.isEmpty(checkResultEntity.getId())) {
			//设置失效时间
			checkResultEntity.setInvalidTime(invalidTime);
			checkResultEntity.setModifyDate(now);
			checkResultEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			priceUpstairsDao.updateInvalidTime(checkResultEntity);
		}
	}
	
	/**
	 * @Description: 插入新的上楼费数据
	 * @param entity	前台传入的需要插入的相关数据
	 * @param now   	插入时间
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	@Transactional
	public void insertNewData(PriceUpstairsEntity entity, Date now) {
		entity.setId(UUIDUtil.getUUID());
        entity.setActive(MiserConstants.ACTIVE);
		entity.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		entity.setCreateDate(now);
		entity.setModifyDate(now);
		entity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		priceUpstairsDao.addUpstairsPrice(entity);
	}

	@Override
	public PriceUpstairsEntity queryUpstairsDetails(PriceUpstairsVo priceUpstairsVo) {
		return priceUpstairsDao.queryUpstairsEntityWithSectionDetailsById(priceUpstairsVo.getPriceUpstairsEntity().getId());
	}
	

}
