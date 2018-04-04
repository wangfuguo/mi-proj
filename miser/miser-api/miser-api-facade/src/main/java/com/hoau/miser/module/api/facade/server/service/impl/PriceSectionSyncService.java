package com.hoau.miser.module.api.facade.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.api.facade.server.dao.PriceSectionSyncDao;
import com.hoau.miser.module.api.facade.server.service.IPriceSectionSyncService;
import com.hoau.miser.module.api.facade.shared.domain.PriceSectionSyncEntity;
import com.hoau.miser.module.api.facade.shared.domain.PriceSectionSubSyncEntity;
import com.hoau.miser.module.common.server.service.ISerialNumberService;
import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;
@Service
public class PriceSectionSyncService implements IPriceSectionSyncService {

	@Resource
	private PriceSectionSyncDao priceSectionSyncDao;
	@Resource
	private ISerialNumberService serialNumberService;
	
	public List<PriceSectionSyncEntity> queryPriceSection(PriceSectionSyncEntity bean) {
		List<PriceSectionSyncEntity> listPiceSection=null;
		PriceSectionSyncEntity entity = bean;
		listPiceSection=priceSectionSyncDao.queryPriceSection(entity);
	    return listPiceSection;
	}


	public void addPriceSection(PriceSectionSyncEntity pse,String priceSectionSubList) {
		
		List<PriceSectionSubSyncEntity> jsonArray = com.alibaba.fastjson.JSON.parseArray(priceSectionSubList,PriceSectionSubSyncEntity.class);
		beforAddPriceSectionDispose(pse);
		String code=serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_SECTION_CODE);
		pse.setCode(code);
		priceSectionSyncDao.addPriceSection(pse);
		for(PriceSectionSubSyncEntity npse:jsonArray){
			beforAddPriceSectionSubDispose(npse);
			npse.setSectionId(pse.getId());
			priceSectionSyncDao.addPriceSectionSub(npse);
		}
	}
	
	public void addPriceSectionSub(PriceSectionSubSyncEntity pse) {
		beforAddPriceSectionSubDispose(pse);
		priceSectionSyncDao.addPriceSectionSub(pse);
	}
	
	public void beforAddPriceSectionSubDispose(PriceSectionSubSyncEntity pse){
		Date dt = new Date();
		pse.setId(UUIDUtil.getUUID());
		pse.setCreateDate(dt);
		pse.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setModifyDate(dt);
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setActive(MiserConstants.YES);
	}
	
	public void beforAddPriceSectionDispose(PriceSectionSyncEntity pse){
		Date dt = new Date();
		pse.setId(UUIDUtil.getUUID());
		pse.setCreateDate(dt);
		pse.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setModifyDate(dt);
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setActive(MiserConstants.YES);
	}
}
