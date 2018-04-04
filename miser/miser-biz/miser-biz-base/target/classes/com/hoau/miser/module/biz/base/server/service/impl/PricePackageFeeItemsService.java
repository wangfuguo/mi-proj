package com.hoau.miser.module.biz.base.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.IPricePackageFeeItemsService;
import com.hoau.miser.module.biz.base.api.shared.domain.PricePackageFeeItemsEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.server.dao.PricePackageFeeItemsDao;
import com.hoau.miser.module.common.server.service.ISerialNumberService;
import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PricePackageFeeItemsService implements
		IPricePackageFeeItemsService {

	@Resource
	private ISerialNumberService serialNumberService;
	@Resource
	private PricePackageFeeItemsDao pricePackageFeeItemsDao;
	public List<PricePackageFeeItemsEntity> queryPricePackageFeeItemsByParam(PricePackageFeeItemsEntity ppfItemsEntity,int limit,int start){
		RowBounds rowBounds=new RowBounds(start, limit);
		return pricePackageFeeItemsDao.queryPricePackageFeeItemsByParam(ppfItemsEntity,rowBounds);
	}
	public Long queryCountByParam(PricePackageFeeItemsEntity ppfItemsEntity) {
		return pricePackageFeeItemsDao.queryCountByParam(ppfItemsEntity);
	}
	@Transactional
	public void updatePricePackageFeeItemsByEntity(PricePackageFeeItemsEntity ppfItemsEntity) {
		// name唯一的处理
		PricePackageFeeItemsEntity temEntity = new PricePackageFeeItemsEntity();
		temEntity.setActive(MiserConstants.YES);
		temEntity.setName(ppfItemsEntity.getName());
		temEntity.setIsNoFuzzy("1");// 不模糊查询
		temEntity.setInvalid(MiserConstants.NO);
		List<PricePackageFeeItemsEntity> dbList=this.queryPricePackageFeeItemsByParam(temEntity, 1, 0);
		if (dbList!=null &&dbList.size()>0) {
			if(!dbList.get(0).getId().equals(ppfItemsEntity.getId()))
			throw new BusinessException(MessageType.ERROR_ISEXIST, "name");
		}
		//作废旧的信息
		PricePackageFeeItemsEntity dbEntity=queryPricePackageFeeItemsById(ppfItemsEntity.getId());
		this.deletePricePackageFeeItemsByEntity(dbEntity,MiserConstants.NO);
		//生成新的信息
		ppfItemsEntity.setId(null);
		this.addPricePackageFeeItemsByEntity(ppfItemsEntity);
	}
	@Transactional
	public void deletePricePackageFeeItemsByEntity(PricePackageFeeItemsEntity ppfItemsEntity,String active) {
		beforOperDeal(ppfItemsEntity);
		ppfItemsEntity.setActive(active);
		ppfItemsEntity.setInvalid(MiserConstants.YES);
		pricePackageFeeItemsDao.deletePricePackageFeeItemsByEntity(ppfItemsEntity);
	}
	@Transactional
	public void addPricePackageFeeItemsByEntity(PricePackageFeeItemsEntity ppfItemsEntity) {
		//name唯一的处理
		PricePackageFeeItemsEntity temEntity=new PricePackageFeeItemsEntity();
		temEntity.setActive(MiserConstants.YES);
		temEntity.setName(ppfItemsEntity.getName());
		temEntity.setIsNoFuzzy("1");//不模糊查询
		temEntity.setInvalid(MiserConstants.NO);
		if(this.queryCountByParam(temEntity)>0){
			throw new BusinessException(MessageType.ERROR_ISEXIST,"name");
		}
		beforOperDeal(ppfItemsEntity);
		pricePackageFeeItemsDao.addPricePackageFeeItemsByEntity(ppfItemsEntity);
	}
	public PricePackageFeeItemsEntity queryPricePackageFeeItemsById(String id) {
		PricePackageFeeItemsEntity ppfiEntity=new PricePackageFeeItemsEntity();
		ppfiEntity.setId(id);
		List<PricePackageFeeItemsEntity> list= this.queryPricePackageFeeItemsByParam(ppfiEntity, 1, 0);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	
	/**
	 * 
	 * @Description: 变更数据操作之前要做的处理
	 * @param @param ppie   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	private void beforOperDeal(PricePackageFeeItemsEntity ppie){
		Date dt = new Date();
		if(StringUtil.isBlank(ppie.getId())){
			ppie.setId(UUIDUtil.getUUID());
			ppie.setActive(MiserConstants.YES);
			ppie.setInvalid(MiserConstants.NO);
			ppie.setCreateDate(dt);
			if(StringUtil.isEmpty(ppie.getCode())){
				ppie.setCode(serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_PACKAGE_FEE_ITEMS_CODE, new String[]{}));
			}
			ppie.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		}
		ppie.setModifyDate(dt);
		ppie.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	}

}
