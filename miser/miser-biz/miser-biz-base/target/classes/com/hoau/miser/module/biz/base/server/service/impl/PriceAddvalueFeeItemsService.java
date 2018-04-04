package com.hoau.miser.module.biz.base.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.IPriceAddvalueFeeItemsService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceAddvalueFeeItemsEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.server.dao.PriceAddvalueFeeItemsDao;
import com.hoau.miser.module.common.server.service.ISerialNumberService;
import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PriceAddvalueFeeItemsService implements
		IPriceAddvalueFeeItemsService {
	@Resource
	private ISerialNumberService serialNumberService;
	@Resource
	private PriceAddvalueFeeItemsDao priceAddvalueFeeItemsDao;
	public List<PriceAddvalueFeeItemsEntity> queryPriceAddvalueFeeItemsByParam(
			PriceAddvalueFeeItemsEntity pafItemsEntity, int limit, int start) {
		RowBounds rowBounds=new RowBounds(start, limit);
		return priceAddvalueFeeItemsDao.queryPriceAddvalueFeeItemsByParam(pafItemsEntity,rowBounds);
	}

	
	public Long queryCountByParam(PriceAddvalueFeeItemsEntity pafItemsEntity) {
		return priceAddvalueFeeItemsDao.queryCountByParam(pafItemsEntity);
	}

	
	public void updatePriceAddvalueFeeItemsByEntity(
			PriceAddvalueFeeItemsEntity pafItemsEntity) {
		PriceAddvalueFeeItemsEntity temEntity = new PriceAddvalueFeeItemsEntity();
		temEntity.setActive(MiserConstants.YES); 
		temEntity.setName(pafItemsEntity.getName());
		temEntity.setIsNoFuzzy("1");//不模糊查询
		temEntity.setInvalid(MiserConstants.NO);
		List<PriceAddvalueFeeItemsEntity> dbList=this.queryPriceAddvalueFeeItemsByParam(temEntity, 1, 0);
		if (dbList!=null &&dbList.size()>0) {
			if(!dbList.get(0).getId().equals(pafItemsEntity.getId()))
				throw new BusinessException(MessageType.ERROR_ISEXIST,"name");
		}
		//查出旧的信息，作废掉
		PriceAddvalueFeeItemsEntity dbEntity=this.queryPriceAddvalueFeeItemsById(pafItemsEntity.getId());
		deletePriceAddvalueFeeItemsByEntity(dbEntity,MiserConstants.NO);
		//新增一条新的信息
		pafItemsEntity.setId(null);
		addPriceAddvalueFeeItemsByEntity(pafItemsEntity);
		
	}

	
	
	public void deletePriceAddvalueFeeItemsByEntity(PriceAddvalueFeeItemsEntity pafItemsEntity,String active){
		pafItemsEntity.setActive(active);
		pafItemsEntity.setInvalid(MiserConstants.YES);
		beforOperDeal(pafItemsEntity);
		priceAddvalueFeeItemsDao.deletePriceAddvalueFeeItemsByEntity(pafItemsEntity);
	}

	
	public void addPriceAddvalueFeeItemsByEntity(
			PriceAddvalueFeeItemsEntity pafItemsEntity) {
		// name唯一的处理
		PriceAddvalueFeeItemsEntity temEntity = new PriceAddvalueFeeItemsEntity();
		temEntity.setActive(MiserConstants.YES); 
		temEntity.setName(pafItemsEntity.getName());
		temEntity.setIsNoFuzzy("1");//不模糊查询
		temEntity.setInvalid(MiserConstants.NO);
		List<PriceAddvalueFeeItemsEntity> dbList=this.queryPriceAddvalueFeeItemsByParam(temEntity, 1, 0);
		if (dbList!=null &&dbList.size()>0) {
			throw new BusinessException(MessageType.ERROR_ISEXIST,"name");
		}
		beforOperDeal(pafItemsEntity);
		priceAddvalueFeeItemsDao.addPriceAddvalueFeeItemsByEntity(pafItemsEntity);
	}

	
	public PriceAddvalueFeeItemsEntity queryPriceAddvalueFeeItemsById(String id) {
		PriceAddvalueFeeItemsEntity pafi=new PriceAddvalueFeeItemsEntity();
		pafi.setId(id);
		List<PriceAddvalueFeeItemsEntity> list= this.queryPriceAddvalueFeeItemsByParam(pafi, 1, 0);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	/**
	 * 
	 * @Description: 变更数据操作之前要做的处理
	 * @param @param paie   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月23日
	 */
	private void beforOperDeal(PriceAddvalueFeeItemsEntity paie){
		Date dt = new Date();
		if(StringUtil.isBlank(paie.getId())){
			paie.setId(UUIDUtil.getUUID());
			paie.setActive(MiserConstants.YES);
			paie.setInvalid(MiserConstants.NO);
			paie.setCreateDate(dt);
			if(StringUtil.isEmpty(paie.getCode())){
				paie.setCode(serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_ADDVALUE_FEE_ITEMS, new String[]{}));
			}
			paie.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		}
		paie.setModifyDate(dt);
		paie.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	}


	/* (non-Javadoc)
	 * @see com.hoau.miser.module.biz.base.server.service.IPriceAddvalueFeeItemsService#queryPriceAddvalueFeeItems(com.hoau.miser.module.biz.base.shared.domain.PriceAddvalueFeeItemsEntity)
	 */
	@Override
	public List<PriceAddvalueFeeItemsEntity> queryPriceAddvalueFeeItems(
			PriceAddvalueFeeItemsEntity pafItemsEntity) {
		// TODO Auto-generated method stub
		return priceAddvalueFeeItemsDao.queryPriceAddvalueFeeItems(pafItemsEntity);
	}
}
