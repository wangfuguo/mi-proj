package com.hoau.miser.module.api.facade.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.facade.server.service.IPriceCustomerSyncService;
import com.hoau.miser.module.api.facade.shared.domain.SyncCustomerPrice;
import com.hoau.miser.module.biz.pricecard.api.server.service.IBseCustomerService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerSubService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.BseCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSubEntity;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PriceCustomerSyncService implements IPriceCustomerSyncService{
	
	private static final Logger log = LoggerFactory.getLogger(PriceCustomerSyncService.class);
	
	@Resource
	private IPriceCustomerService priceCustomerService;

	@Resource
	private IPriceCustomerSubService priceCustomerSubService;
	
	@Resource
	private IBseCustomerService bseCustomerService;
	

	@Override
	public String syncCustomerPrice(List<SyncCustomerPrice> scpBeanList) {
		
		log.info("PriceCustomerSyncService OA对接新PMS接口进入 1");
		
		SyncCustomerPrice syncCustomer = scpBeanList.get(0);
		
		log.info("PriceCustomerSyncService OA对接新PMS接口 customerCode:"+syncCustomer.getCustomerCode());
		
		PriceCustomerEntity checkEntity = new PriceCustomerEntity();
		checkEntity.setCustomerCode(syncCustomer.getCustomerCode());
		checkEntity.setEffectiveTime(syncCustomer.getEffectiveTime());
		checkEntity.setInvalidTime(syncCustomer.getInvalidTime());
		
		BseCustomerEntity bseCustomerEntity = new BseCustomerEntity();
		bseCustomerEntity.setCode(syncCustomer.getCustomerCode());
		bseCustomerEntity.setModifyUserCode(syncCustomer.getUserCode());
		
		bseCustomerEntity.setPcStartTime(syncCustomer.getEffectiveTime());
		bseCustomerEntity.setPcEndTime(syncCustomer.getInvalidTime());
		
		//1. 根据客户价格，获取最近一条非作废状态的客户价格主表及明细数据，作为原记录，如果没有原记录，则不进行任何处理返回。
		PriceCustomerEntity priceCustomerEntity = priceCustomerService.selectPriceCustomerListForLastByCode(syncCustomer.getCustomerCode());
		if(priceCustomerEntity != null){
			
			bseCustomerEntity.setUseCustomerPc("Y");
			String curFreightSectionPrice = syncCustomer.getFreightSectionPrice();
			
			if(!StringUtil.isEmpty(curFreightSectionPrice)){
				log.info("PriceCustomerSyncService syncCustomerPrice customerCode:"+syncCustomer.getCustomerCode()+" 运费分段不为空时， 不再需要处理客户价格");
				bseCustomerService.updateFromOaPmsInterface(bseCustomerEntity);
				return "success";
			}
			
			String parentId = priceCustomerEntity.getId();
			
			//当前时间
			Date curDateTime = new Date();
			
			//原失效时间
			Date oldInvalidTime = priceCustomerEntity.getInvalidTime();
			
			//OA新生效时间
			Date newEffectiveTime = syncCustomer.getEffectiveTime();
			
			//OA新失效时间
			Date newInvalidTime = syncCustomer.getInvalidTime();
			
			/*
			 * 1、OA的新失效时间<当前时间 ：不处理
			 * 2、OA的新失效时间 > 当前时间：
			 *    1）OA的新生效时间 > 原失效：直接复制应用新增
			 *    2）OA的新生效时间 < 原失效：
			 *      （1）OA的新生效时间 > 当前时间：原记录失效时间置为新OA生效时间，新记录的生效，失效以OA为准
			 *      （2）OA的新生效时间 < 当前时间：原记录失效时间置为当前时间，新记录的生效置为当前时间，失效以OA为准
			 */
			
			//1、OA的新失效时间<当前时间 ：不处理
			int timeResult = newInvalidTime.compareTo(curDateTime);
			
			if(timeResult < 0 || timeResult == 0){
				log.info("PriceCustomerSyncService OA对接新PMS接口 customerCode:"+priceCustomerEntity.getCustomerCode()+" 1、OA的新失效时间<当前时间 ：不处理：不处理.oa新失效时间:"+newInvalidTime+" 当前时间:"+curDateTime);
				return "OA的新失效时间<当前时间 ：不处理";
			}
			
			String newParentId = null;
			
			//2、OA的新失效时间 > 当前时间：
			if(timeResult > 0){
				
				//1）OA的新生效时间 > 原失效：直接复制应用新增
				timeResult = newEffectiveTime.compareTo(oldInvalidTime);
				
				if(timeResult > 0 || timeResult == 0){
					log.info("PriceCustomerSyncService OA对接新PMS接口 customerCode:"+priceCustomerEntity.getCustomerCode()+" 2、1）OA的新生效时间 > 原失效：直接复制应用新增 "
				              +" oldInvalidTime:" + oldInvalidTime
				              +" newEffectiveTime:"+newEffectiveTime);
					
					newParentId = operateForPriceCustomer(priceCustomerEntity,syncCustomer);
				}
				//2）OA的新生效时间 < 原失效
				else{
					
					timeResult = newEffectiveTime.compareTo(curDateTime);
					
					//（1）OA的新生效时间 > 当前时间：原记录失效时间置为新OA生效时间，新记录的生效，失效以OA为准
					if(timeResult > 0 || timeResult == 0){
						
						log.info("PriceCustomerSyncService OA对接新PMS接口 customerCode:"+priceCustomerEntity.getCustomerCode()+" 2、2）（1）OA的新生效时间 > 当前时间：原记录失效时间置为新OA生效时间，新记录的生效，失效以OA为准"
					              +" curDateTime:" + curDateTime
					              +" newEffectiveTime:"+newEffectiveTime);
						
						operateForOldPriceCustomer(parentId,newEffectiveTime,syncCustomer);
						newParentId = operateForPriceCustomer(priceCustomerEntity,syncCustomer);
					}
					//（2）OA的新生效时间 < 当前时间：原记录失效时间置为当前时间，新记录的生效置为当前时间，失效以OA为准
					else {
						
						log.info("PriceCustomerSyncService OA对接新PMS接口 customerCode:"+priceCustomerEntity.getCustomerCode()+" 2、2）（2）OA的新生效时间 < 当前时间：原记录失效时间置为当前时间，新记录的生效置为当前时间，失效以OA为准"
					              +" curDateTime:" + curDateTime
					              +" newEffectiveTime:"+newEffectiveTime);
						
						operateForOldPriceCustomer(parentId,curDateTime,syncCustomer);
						syncCustomer.setEffectiveTime(curDateTime);
						newParentId = operateForPriceCustomer(priceCustomerEntity,syncCustomer);
					}
				}
			}
			
			
			log.info("PriceCustomerSyncService OA对接新PMS接口 customerCode:"+syncCustomer.getCustomerCode()+" newParentId:"+newParentId);
			
			if(newParentId != null){
				
	            //循环对 不同的运输类型对应的浮动金额 进行处理
	            for(SyncCustomerPrice scpBean :  scpBeanList){
	                operateForPriceCustomerSub(parentId, newParentId, scpBean);
	            }
			}
			
		} else {
			log.info("PriceCustomerSyncService 根据客户编号:"+syncCustomer.getCustomerCode()+" 获取最近一条非作废记录为空 ");
			bseCustomerEntity.setUseCustomerPc("N");
		}
		
		bseCustomerService.updateFromOaPmsInterface(bseCustomerEntity);
		
		return "success";
	}
	
	/**
	 * 对老记录(当前最近的一条非作废的记录)进行更新操作
	 * @param id
	 * @param invalidTime
	 * @param syncCustomer
	 */
	public void operateForOldPriceCustomer(String id,Date invalidTime,SyncCustomerPrice syncCustomer){
		PriceCustomerEntity priceCustomerEntity = new PriceCustomerEntity();
		priceCustomerEntity.setId(id);
		priceCustomerEntity.setInvalidTime(invalidTime);
		priceCustomerEntity.setModifyUserCode(syncCustomer.getUserCode());
		priceCustomerEntity.setModifyTime(new Date());
		priceCustomerService.updateForEffectiveById(priceCustomerEntity);
	}



	/**
	 * @param parentId
	 * @param id
	 * @param scpBean
	 */
	private void operateForPriceCustomerSub(String parentId, String newParentId,
			SyncCustomerPrice scpBean) {
		//2. 在原记录基础上，浮动金额/比例后，复制新数据写入客户价格主表及明细表，生效时间与失效时间以OA接口参数为准，原记录不动。
		PriceCustomerSubEntity tmpSubObj = new PriceCustomerSubEntity();  
		tmpSubObj.setParentId(parentId);
		tmpSubObj.setTransTypeCode(scpBean.getTransTypeCode());
		
		//根据 parentId + transTypeCode 查询对应的明细记录
		List<PriceCustomerSubEntity> subEntityList = priceCustomerSubService.selectCustomerSubListByParam(tmpSubObj);
		
		/*副表  需要根据接口更新 重货单价 体积单价(轻货单价)
		 * 浮动百分比 与 具体的 浮动金额 只能选择一个
		 */
		if(scpBean.getFloatPercentage() == null){
			weightVolumnPriceUpdateByFloatPrice(scpBean, subEntityList, newParentId);
		}
		else{
			weightVolumnPriceUpdateByFloatPercent(scpBean,subEntityList, newParentId);					
		}
		priceCustomerSubService.batchInsertCustomerSub(subEntityList);
	}



	/**
	 * @param priceCustomerEntity
	 * @return
	 */
	private String operateForPriceCustomer(
			PriceCustomerEntity priceCustomerEntity,SyncCustomerPrice syncCustomer) {
		Date curDateTime = new Date();
		String curUserCode = syncCustomer.getUserCode();
 
 
		//主表:接口主要更新 生效时间 失效时间
		PriceCustomerEntity newPriceCustomerEntity = new PriceCustomerEntity();
		String id = UUIDUtil.getUUID();
		newPriceCustomerEntity.setId(id);
		newPriceCustomerEntity.setCustomerCode(syncCustomer.getCustomerCode());
		newPriceCustomerEntity.setCustomerName(priceCustomerEntity.getCustomerName());
		
		newPriceCustomerEntity.setEffectiveTime(syncCustomer.getEffectiveTime());
		newPriceCustomerEntity.setInvalidTime(syncCustomer.getInvalidTime());
		newPriceCustomerEntity.setRemark(priceCustomerEntity.getRemark());

		
		newPriceCustomerEntity.setCreateTime(curDateTime);
		newPriceCustomerEntity.setCreateUserCode(curUserCode);
		newPriceCustomerEntity.setModifyTime(curDateTime);
		newPriceCustomerEntity.setModifyUserCode(curUserCode);
		newPriceCustomerEntity.setActive(MiserConstants.YES);
		newPriceCustomerEntity.setDataOrign(DataOrignEnum.OA.getCode());
		newPriceCustomerEntity.setCustomerType(priceCustomerEntity.getCustomerType());
		
		priceCustomerService.insertSelective(newPriceCustomerEntity);
		return id;
	}

 

	/**
	 * 根据具体的浮动金额值来更新 重货单价 体积单价
	 * 浮动百分比为空 则按具体的浮动金额计算
	 * 重货浮动金额和轻货浮动金额，分别加到原记录的明细表的重货单价及轻货单价上。
	 *
	 * @param scpBean
	 * @param subEntityList
	 * @param id
	 */
	private void weightVolumnPriceUpdateByFloatPrice(SyncCustomerPrice scpBean,
			List<PriceCustomerSubEntity> subEntityList, String id) {
		if (subEntityList != null && subEntityList.size() > 0) {
			for (PriceCustomerSubEntity curSubEntity : subEntityList) {
				curSubEntity.setParentId(id);
				
				BigDecimal weightPrice = curSubEntity.getWeightPrice();
				weightPrice = weightPrice.add(scpBean.getHeavyFloatPrice());
				curSubEntity.setWeightPrice(weightPrice);
				
				BigDecimal volumePrice = curSubEntity.getVolumePrice();
				volumePrice = volumePrice.add(scpBean.getLightFloatPrice());
				curSubEntity.setVolumePrice(volumePrice);
				
				beforeOperDeal(curSubEntity,scpBean);
			}
		}
	}
	
	/**根据具体浮动的百分比来计算重货单价 轻货单价
	 * @param scpBean
	 * @param subEntityList
	 * @param id
	 */
	private void weightVolumnPriceUpdateByFloatPercent(
			SyncCustomerPrice scpBean,
			List<PriceCustomerSubEntity> subEntityList, String id) {
		/**
		 * 浮动比例，新纪录的
		 * 重货单价=原记录的重货单价+重货单价（原记录的重货单价）*浮动比例，
		 * 轻货单价=原记录的轻货单价+重货单价（原记录的重货单价）*浮动比例*220
		 */
		
		if (subEntityList != null && subEntityList.size() > 0) {
			
			BigDecimal weightPrice  = null;
			BigDecimal floatPrice = null;
			BigDecimal volumePrice = null;
			
			BigDecimal oldWeightPrice = null;
			BigDecimal multiplicand = new BigDecimal(220);
			
			
			for (PriceCustomerSubEntity curSubEntity : subEntityList) {
				curSubEntity.setParentId(id);
				
				oldWeightPrice =  curSubEntity.getWeightPrice();
				weightPrice = curSubEntity.getWeightPrice();
				
				//重货单价（原记录的重货单价）*浮动比例
				floatPrice = weightPrice.multiply(scpBean.getFloatPercentage());
				
				//原记录的重货单价+重货单价（原记录的重货单价）*浮动比例
				weightPrice = weightPrice.add(floatPrice);
				curSubEntity.setWeightPrice(weightPrice);
				
				volumePrice = curSubEntity.getVolumePrice();
				
				//重货单价（原记录的重货单价）*浮动比例
				floatPrice = oldWeightPrice.multiply(scpBean.getFloatPercentage());
				
				//*220
				floatPrice = floatPrice.multiply(multiplicand);
				
				//原记录的轻货单价+重货单价（原记录的重货单价）*浮动比例*220
				volumePrice = volumePrice.add(floatPrice);
				curSubEntity.setVolumePrice(volumePrice);
				
				beforeOperDeal(curSubEntity,scpBean);
				
			}
		}
	}
	
	public void beforeOperDeal(PriceCustomerSubEntity curSubEntity ,SyncCustomerPrice scpBean){
		Date dt = new Date();
		
		curSubEntity.setId(UUIDUtil.getUUID());
		curSubEntity.setActive(MiserConstants.YES);
		curSubEntity.setCreateTime(dt);
		curSubEntity.setCreateUserCode(scpBean.getUserCode());
		curSubEntity.setModifyTime(dt);
		curSubEntity.setModifyUserCode(scpBean.getUserCode());
		curSubEntity.setDataOrign(DataOrignEnum.OA.getCode());
		
	}

}
