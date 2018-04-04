package com.hoau.miser.module.biz.job.server.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.eai.pc.vo.ExtraInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.CustomerDisCountAndSurchargeDao;
import com.hoau.miser.module.biz.job.server.dao.CustomerPriceSendDao;
import com.hoau.miser.module.biz.job.server.service.ICustomerExtraFeeSendService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.job.shared.domain.DiscountCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 
 * ClassName: CustomerDiscountSendService 
 * @Description: TODO(标准附加费Service) 
 * @author 275636
 * @date 2016年3月2日
 * @version V1.0
 */
@Service
public class CustomerExtraFeeSendService implements
		ICustomerExtraFeeSendService {
	
	@Resource
	private CustomerDisCountAndSurchargeDao disCountAndSurchargeDao;
	@Resource
	private CustomerPriceSendDao customerPriceSendDao;
	@Resource
	private IDataSendJobTimeService dataSendJobTimeService;
	
	@Resource
	private IDataSendJobFailureService dataSendJobFailureService;
	
	@Resource
	private IActiveMQProducerService activeMQProducerService;
	
	@Resource
	private IPriceSectionService priceSectionService;
	private static int MAX_COUNT = 100;
	
	static{
		try {
			Properties properties = ConfigFileLoadUtil.getPropertiesForClasspath("config.properties");
			String maxCountStr = properties.getProperty("SENDMQ.EXTRA.MAXCOUNT", "100");
			MAX_COUNT = Integer.parseInt((maxCountStr));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendCustomerSurcharge() {

		//记录本次发送时间
		Date thisTime = new Date();
		// 查询出本次需要新发送的信息
		DataSendJobQueryFilterEntity queryNewDataParam = new DataSendJobQueryFilterEntity();
		// 查询出上次发送时间
		DataSendJobTimeEntity resultEntity = dataSendJobTimeService.getLastSendTime(DataSendJobConstant.JOB_CUSTOMER_CODE_EXTRA);
		
		// 组装查询条件
		Map<String, Object> newMapParam = new HashMap<String, Object>();
		newMapParam.put("endTime", thisTime);
		if (resultEntity != null) {
			queryNewDataParam.setStartTime(resultEntity.getLastSendTime());
			newMapParam.put("startTime", resultEntity.getLastSendTime());
		}
		queryNewDataParam.setEndTime(thisTime);
		
		//查询本次的客户折扣信息
		List<DiscountCustomerEntity> newDiscountCustomerDatas=disCountAndSurchargeDao.queryNeedDiscountCustomerData(newMapParam);
//		
//		//查出来客户正生效的活动信息，然后统一融合
//		List<Map<String,Object>> currDiscountCustomerDatas=disCountAndSurchargeDao.queryAllVaildEventData();
//		
//		Map<String,List<String>> preferentialMap=new HashMap<String, List<String>>();
//		for(Map<String,Object> map:currDiscountCustomerDatas){
//			String transTypeCode=(String)map.get("TRANS_TYPE_CODE");
//			String customerCode=(String)map.get("CUSTOMER_CODE");
//			
//			String freight_Section_Code = (String)map.get("freight_Section_Code");
//			String add_Section_Code = (String)map.get("add_Section_Code");
//			String fuel_Section_Code = (String)map.get("fuel_Section_Code");
//			String pickup_Section_Code = (String)map.get("pickup_Section_Code");
//			String delivery_Section_Code = (String)map.get("delivery_Section_Code");
//			String upstair_Section_Code = (String)map.get("upstair_Section_Code");
//			String insurance_Rate_Section_Code = (String)map.get("insurance_Rate_Section_Code");
//			String insurance_Section_Code = (String)map.get("insurance_Section_Code");
//			String paper_Section_Code = (String)map.get("paper_Section_Code");
//			String sms_Section_Code = (String)map.get("sms_Section_Code");
//			String collection_Rate_Section_Code = (String)map.get("collection_Rate_Section_Code");
//			String collection_Section_Code = (String)map.get("collection_Section_Code");
//			
//			//运费key=客户编号_运输类型
//			String _freight=customerCode+"_"+transTypeCode+"_freight";
//			String _add=customerCode+"_"+transTypeCode+"_add";
//			String _fuel=customerCode+"_"+transTypeCode+"_fuel";
//			String _pickup=customerCode+"_"+transTypeCode+"_pickup";
//			String _delivery=customerCode+"_"+transTypeCode+"_delivery";
//			String _upstair=customerCode+"_"+transTypeCode+"_upstair";
//			String _insurance_Rate=customerCode+"_"+transTypeCode+"_insurance_Rate";
//			String _insurance_Section=customerCode+"_"+transTypeCode+"_insurance_Section";
//			String _paper=customerCode+"_"+transTypeCode+"_paper";
//			String _sms=customerCode+"_"+transTypeCode+"_sms";
//			String _collection_Rate=customerCode+"_"+transTypeCode+"_collection_Rate";
//			String _collection_Section=customerCode+"_"+transTypeCode+"_collection_Section";
//			
//			List<String> freightList=preferentialMap.get(_freight);
//			if(freightList==null)freightList=new ArrayList<String>();
//			freightList.add(freight_Section_Code);
//			preferentialMap.put(_freight, freightList);
//			
//			List<String> addList=preferentialMap.get(_add);
//			if(addList==null)addList=new ArrayList<String>();
//			addList.add(add_Section_Code);
//			preferentialMap.put(_add, addList);
//			
//			List<String> fuelList=preferentialMap.get(_fuel);
//			if(fuelList==null)fuelList=new ArrayList<String>();
//			fuelList.add(fuel_Section_Code);
//			preferentialMap.put(_fuel, fuelList);
//			
//			List<String> pickupList=preferentialMap.get(_pickup);
//			if(pickupList==null)pickupList=new ArrayList<String>();
//			pickupList.add(pickup_Section_Code);
//			preferentialMap.put(_pickup, pickupList);
//			
//			List<String> deliveryList=preferentialMap.get(_delivery);
//			if(deliveryList==null)deliveryList=new ArrayList<String>();
//			deliveryList.add(delivery_Section_Code);
//			preferentialMap.put(_delivery, deliveryList);
//			
//			List<String> upstairList=preferentialMap.get(_upstair);
//			if(upstairList==null)upstairList=new ArrayList<String>();
//			upstairList.add(upstair_Section_Code);
//			preferentialMap.put(_upstair, upstairList);
//			
//			List<String> insurance_RateList=preferentialMap.get(_insurance_Rate);
//			if(insurance_RateList==null)insurance_RateList=new ArrayList<String>();
//			insurance_RateList.add(insurance_Rate_Section_Code);
//			preferentialMap.put(_insurance_Rate, insurance_RateList);
//			
//			List<String> insurance_SectionList=preferentialMap.get(_insurance_Section);
//			if(insurance_SectionList==null)insurance_SectionList=new ArrayList<String>();
//			insurance_SectionList.add(insurance_Section_Code);
//			preferentialMap.put(_insurance_Section, insurance_SectionList);
//			
//			List<String> paperList=preferentialMap.get(_paper);
//			if(paperList==null)paperList=new ArrayList<String>();
//			paperList.add(paper_Section_Code);
//			preferentialMap.put(_paper, paperList);
//			
//			List<String> smsList=preferentialMap.get(_sms);
//			if(smsList==null)smsList=new ArrayList<String>();
//			smsList.add(sms_Section_Code);
//			preferentialMap.put(_sms, smsList);
//			
//			List<String> collection_RateList=preferentialMap.get(_collection_Rate);
//			if(collection_RateList==null)collection_RateList=new ArrayList<String>();
//			collection_RateList.add(collection_Rate_Section_Code);
//			preferentialMap.put(_collection_Rate, collection_RateList);
//			
//			List<String> collection_SectionList=preferentialMap.get(_collection_Section);
//			if(collection_SectionList==null)collection_SectionList=new ArrayList<String>();
//			collection_SectionList.add(collection_Section_Code);
//			preferentialMap.put(_collection_Section, collection_SectionList);
//		}
//		
//		//最后跟活动的信息相融合
//		for(DiscountCustomerEntity newEntity :newDiscountCustomerDatas){
//			
//			//运费key=客户编号_运输类型
//			String _freight=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_freight";
//			String _add=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_add";
//			String _fuel=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_fuel";
//			String _pickup=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_pickup";
//			String _delivery=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_delivery";
//			String _upstair=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_upstair";
//			String _insurance_Rate=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_insurance_Rate";
//			String _insurance_Section=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_insurance_Section";
//			String _paper=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_paper";
//			String _sms=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_sms";
//			String _collection_Rate=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_collection_Rate";
//			String _collection_Section=newEntity.getCustomerCode()+"_"+newEntity.getTransTypeCode()+"_collection_Section";
//			
////			List<String> freightList=preferentialMap.get(_freight);
////			if(freightList==null)freightList=new ArrayList<String>();
////			if(freightList.size()>0 && newEntity.getFreightSectionCode() != null){
////				freightList.add(newEntity.getFreightSectionCode());
////				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) freightList);
////				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
////					newEntity.setFreightSectionCode(priceSectionEntity.getSubEntities().get(0).getPrice()+"");
////			}
//			//附件费
//			List<String> addList=preferentialMap.get(_add);
//			if(addList==null)addList=new ArrayList<String>();
//			if(addList.size()>0 && newEntity.getAddSectionCode() != null){
//				addList.add(newEntity.getAddSectionCode());
//				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) addList);
//				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
//					newEntity.setSubcharge(priceSectionEntity.getSubEntities().get(0).getPrice());
//			}
//			//燃油费
//			List<String> fuelList=preferentialMap.get(_fuel);
//			if(fuelList==null)fuelList=new ArrayList<String>();
//			if(fuelList.size()>0 && newEntity.getFuelSectionCode() != null){
//				fuelList.add(newEntity.getFuelSectionCode());
//				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) fuelList);
//				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
//					newEntity.setFuelSubcharge(priceSectionEntity.getCode());
//			}
//			//提货费
//			List<String> pickupList=preferentialMap.get(_pickup);
//			if(pickupList==null)pickupList=new ArrayList<String>();
//			if(pickupList.size()>0 && newEntity.getPickupSectionCode() != null){
//				pickupList.add(newEntity.getPickupSectionCode());
//				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) pickupList);
//				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
//					newEntity.setPickFees(priceSectionEntity.getSubEntities().get(0).getPrice());
//			}
//			//送货费
//			List<String> deliveryList=preferentialMap.get(_delivery);
//			if(deliveryList==null)deliveryList=new ArrayList<String>();
//			if(deliveryList.size()>0 && newEntity.getDeliverySectionCode() != null){
//				deliveryList.add(newEntity.getDeliverySectionCode());
//				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) deliveryList);
//				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
//					newEntity.setDeliverFees(priceSectionEntity.getSubEntities().get(0).getPrice());
//			}
//			//
////			List<String> upstairList=preferentialMap.get(_upstair);
////			if(upstairList==null)upstairList=new ArrayList<String>();
////			if(upstairList.size()>0 && newEntity.getUpstairsSectionCode() != null){
////				upstairList.add(newEntity.getUpstairsSectionCode());
////				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) upstairList);
////				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
////					newEntity.setUpstairsSectionCode(priceSectionEntity.getSubEntities().get(0).getPrice()+"");
////			}
//			//保价费率
//			List<String> insurance_RateList=preferentialMap.get(_insurance_Rate);
//			if(insurance_RateList==null)insurance_RateList=new ArrayList<String>();
//			if(insurance_RateList.size()>0 && newEntity.getInsuranceRateSectionCode() != null){
//				insurance_RateList.add(newEntity.getInsuranceRateSectionCode());
//				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) insurance_RateList);
//				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
//					newEntity.setInsuranceRate(priceSectionEntity.getSubEntities().get(0).getPrice());
//			}
//			//最低保价
//			List<String> insurance_SectionList=preferentialMap.get(_insurance_Section);
//			if(insurance_SectionList==null)insurance_SectionList=new ArrayList<String>();
//			if(insurance_SectionList.size()>0 && newEntity.getInsuranceSectionCode() != null){
//				insurance_SectionList.add(newEntity.getInsuranceSectionCode());
//				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) insurance_SectionList);
//				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
//					newEntity.setLowestInsuranceFees(priceSectionEntity.getSubEntities().get(0).getPrice());
//			}
//			//工本费
//			List<String> paperList=preferentialMap.get(_paper);
//			if(paperList==null)paperList=new ArrayList<String>();
//			if(paperList.size()>0 && newEntity.getPaperSectionCode() != null){
//				paperList.add(newEntity.getPaperSectionCode());
//				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) paperList);
//				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
//					newEntity.setFlatCost(priceSectionEntity.getSubEntities().get(0).getPrice());
//			}
//			//信息费
//			List<String> smsList=preferentialMap.get(_sms);
//			if(smsList==null)smsList=new ArrayList<String>();
//			if(smsList.size()>0 && newEntity.getSmsSectionCode() != null){
//				smsList.add(newEntity.getSmsSectionCode());
//				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) smsList);
//				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
//					newEntity.setInformationFees(priceSectionEntity.getSubEntities().get(0).getPrice());
//			}
//			//代收手续费率
//			List<String> collection_RateList=preferentialMap.get(_collection_Rate);
//			if(collection_RateList==null)collection_RateList=new ArrayList<String>();
//			if(collection_RateList.size()>0 && newEntity.getCollectionRateSectionCode() != null){
//				collection_RateList.add(newEntity.getCollectionRateSectionCode());
//				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) collection_RateList);
//				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
//					newEntity.setCollectionRateSectionCode(priceSectionEntity.getSubEntities().get(0).getPrice()+"");
//			}
//			//代收款
//			List<String> collection_SectionList=preferentialMap.get(_collection_Section);
//			if(collection_SectionList==null)collection_SectionList=new ArrayList<String>();
//			if(collection_SectionList.size()>0 && newEntity.getCollectionSectionCode() != null){
//				collection_SectionList.add(newEntity.getCollectionSectionCode());
//				PriceSectionEntity priceSectionEntity=priceSectionService.reuniteSectionsList((ArrayList<String>) collection_SectionList);
//				if(priceSectionEntity.getSubEntities() != null && priceSectionEntity.getSubEntities().size() > 0)
//					newEntity.setPayableServiceRate(priceSectionEntity.getSubEntities().get(0).getPrice());
//			}
//		}
//		
		//发送本次新数据
		sendData(newDiscountCustomerDatas, false);
		
		//更新上次发送时间
		DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
		saveEntity.setId(UUIDUtil.getUUID());
		saveEntity.setJobCode(DataSendJobConstant.JOB_CUSTOMER_CODE_EXTRA);
		saveEntity.setJobName(DataSendJobConstant.getNameByCode(DataSendJobConstant.JOB_CUSTOMER_CODE_EXTRA));
		saveEntity.setLastSendTime(thisTime);
		saveEntity.setActive(MiserConstants.ACTIVE);
		saveEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
		saveEntity.setCreateDate(new Date());
		saveEntity.setModifyDate(new Date());
		saveEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
		dataSendJobTimeService.saveLastSendTime(saveEntity);
	}
	
	/**
	 * 
	 * @Description: 发送数据到MQ
	 * @param @param datas
	 * @param @param isFailureData   
	 * @return void 
	 * @throws
	 * @author 275636
	 * @date 2016年1月26日
	 */
	@Transactional
	public void sendData(List<DiscountCustomerEntity> datas, boolean isFailureData) {
		
		ArrayList<ExtraInfo> priceCityData = new ArrayList<ExtraInfo>(MAX_COUNT);
		Map<String,ExtraInfo> map=new HashMap<String, ExtraInfo>();
		for (int i = 0; i < datas.size(); i++) {
			//将查询出来的标准附加费转换成需要发送到MQ队列的对象
			DiscountCustomerEntity entity = datas.get(i);
			ExtraInfo pci=new ExtraInfo();
			try{
			//标识作废数据
			pci.setTransportType(entity.getTransTypeCode());
			pci.setType("C");
			pci.setSubcharge(entity.getSubcharge());
			pci.setFuelSubcharge(entity.getFuelSectionCode());
			pci.setPickFees(entity.getPickFees());
			pci.setDeliverFees(entity.getDeliverFees());
			pci.setInsuranceRate(entity.getInsuranceRate());
			pci.setLowestInsuranceFees(entity.getLowestInsuranceFees());
			pci.setPayableServiceRate(entity.getPayableServiceRate());
			pci.setFlatCost(entity.getFlatCost());
			pci.setInformationFees(entity.getInformationFees());
			pci.setRemark(entity.getRemark());
			pci.setRecStatus(entity.getState().equals("EFFECTIVE")?0:1);
			pci.setCustomerId(entity.getCustomerCode());
			//如果相同唯一主键的数据，则不同步
			boolean flag=true;
			String onlyCode=entity.getTransTypeCode()+"_"+entity.getCustomerCode();
			ExtraInfo a=map.get(onlyCode);
			if(a != null){
				if(entity.getState().equals("EFFECTIVE")){
					priceCityData.remove(a);
					map.remove(onlyCode);
				}else{
					flag=false;
				}
			}else{
				if(!entity.getState().equals("EFFECTIVE")){
					map.put(onlyCode, pci);
					if(i == datas.size()-1&&i!=0){
						flag=false;
					}
				}
			}
			if(flag){
				priceCityData.add(pci);
			}

			}catch(NullPointerException e){
			}
			if (datas.size()>0&&((i + 1) % MAX_COUNT == 0 || i == datas.size()-1)) {
				try {
					activeMQProducerService.postExtrtaDataToQueue(priceCityData);
					if (isFailureData) {
						//如果是失败数据发送成功了更新失败表状态为已发送
						for (int j = 0; j < priceCityData.size(); j++) {
							DiscountCustomerEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
							DataSendJobFailureEntity saveSuccessEntity = new DataSendJobFailureEntity();
							saveSuccessEntity.setId(failureEntity.getId());
							saveSuccessEntity.setActive(MiserConstants.INACTIVE);
							dataSendJobFailureService.updateSendedData(saveSuccessEntity);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (!isFailureData) {
						//如果是新数据发送失败了，插入到失败表
						for (int j = 0; j < priceCityData.size(); j++) {
							DiscountCustomerEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
							DataSendJobFailureEntity saveFailureEntity = new DataSendJobFailureEntity();
							saveFailureEntity.setId(UUIDUtil.getUUID());
							saveFailureEntity.setBusinessId(failureEntity.getId());
							saveFailureEntity.setActive(MiserConstants.ACTIVE);
							saveFailureEntity.setCreateDate(new Date());
							saveFailureEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
							saveFailureEntity.setFailReason(e.getMessage());
							saveFailureEntity.setModifyDate(new Date());
							saveFailureEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
							saveFailureEntity.setSendTime(new Date());
							saveFailureEntity.setType(DataSendJobConstant.JOB_CUSTOMER_CODE_EXTRA);
							dataSendJobFailureService.saveFailureData(saveFailureEntity);
						}
					}
				}
				priceCityData.clear();
			}
		}
	}

}
