package com.hoau.miser.module.biz.job.server.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hoau.eai.pc.vo.PriceCardInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.PriceStandardSendDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IPriceStandSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.job.shared.domain.PriceStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;


/**
 * 
 * ClassName: PriceCardSendService 
 * @Description: TODO(价格队列)
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月21日
 * @version V1.0
 */
@Service
public class PriceStandSendService implements IPriceStandSendService{
	@Resource
	private PriceStandardSendDao priceStandardSendDao;
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
			String maxCountStr = properties.getProperty("SENDMQ.PRICECARD.MAXCOUNT", "100");
			MAX_COUNT = StringUtils.toInt(maxCountStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public void sendPriceCard() {
		//记录本次发送时间
		Date thisTime = new Date();
		//查询出本次需要新发送的标准附加费
		DataSendJobQueryFilterEntity queryNewDataFitlerEntity = new DataSendJobQueryFilterEntity();
		//查询出上次发送时间
		DataSendJobTimeEntity resultEntity = dataSendJobTimeService.getLastSendTime(DataSendJobConstant.JOB_CODE_PRICE_STANDARD);
		if (resultEntity != null) {
			queryNewDataFitlerEntity.setStartTime(resultEntity.getLastSendTime());
		}
		queryNewDataFitlerEntity.setEndTime(thisTime);
		//查询出上次发送失败的记录
		DataSendJobFailureEntity queryFailureFilterEntity = new DataSendJobFailureEntity();
		queryFailureFilterEntity.setActive(MiserConstants.ACTIVE);
		queryFailureFilterEntity.setType(DataSendJobConstant.JOB_CODE_PRICE_STANDARD);
		queryFailureFilterEntity.setSendTime(thisTime);
		if (resultEntity != null) {
		queryFailureFilterEntity.setLastSendTime(resultEntity.getLastSendTime());
		}
		//去重复
		priceStandardSendDao.failureInfoUniqByPriceStandard(queryFailureFilterEntity);
		queryFailureFilterEntity.setRetryTimes(DataSendJobConstant.MAX_FAIL_RETRY_TIMES);
		ArrayList<PriceStandardEntity> failureDatas = priceStandardSendDao.queryFailureData(queryFailureFilterEntity);
		//发送上次失败数据
		sendData(failureDatas, true);
		
		ArrayList<PriceStandardEntity> newDatas = priceStandardSendDao.queryListByParam(queryNewDataFitlerEntity);
		//发送本次新数据
		sendData(newDatas, false);
		
		//更新上次发送时间
		DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
		saveEntity.setId(UUIDUtil.getUUID());
		saveEntity.setJobCode(DataSendJobConstant.JOB_CODE_PRICE_STANDARD);
		saveEntity.setJobName(DataSendJobConstant.getNameByCode(DataSendJobConstant.JOB_CODE_PRICE_STANDARD));
		saveEntity.setLastSendTime(thisTime);
		saveEntity.setActive(MiserConstants.ACTIVE);
		saveEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
		saveEntity.setCreateDate(new Date());
		saveEntity.setModifyDate(new Date());
		saveEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
		dataSendJobTimeService.saveLastSendTime(saveEntity);
		
//		
//		resultEntity = dataSendJobTimeService.getLastSendTime(DataSendJobConstant.JOB_CODE_PRICE_STANDARD);
//		//发送活动相关标准价格
//		PriceEventSendEntity entity1=new PriceEventSendEntity();
//		entity1.setStartTime(resultEntity.getLastSendTime());
//		entity1.setEndTime(thisTime);
//		ArrayList<PriceEventSendEntity> PriceEventSendEntityList = priceStandardSendDao.queryListByParamByEventFORStandard(entity1);
//		for(PriceEventSendEntity pese:PriceEventSendEntityList){
//			List<PriceEventDiscountSubSendEntity> preseList=pese.getPriceEventDiscountSubs();
//			List<PriceEventRouteSubSendEntity> prel=pese.getPriceEventRouteSubs();
//			for(PriceEventRouteSubSendEntity pes:prel){
//				for(PriceEventDiscountSubSendEntity peds:preseList){
//					//获取所有匹配的标准价格
//					PriceEventSendEntity pesenew=new PriceEventSendEntity();
//					pesenew.setTransTypeCode(peds.getTransTypeCode());
//					pesenew.setSendPriceCity(pes.getSendPriceCity());
//					pesenew.setArrivalPriceCity(pes.getArrivalPriceCity());
//					//queryListByParam获取到标准价格
//					ArrayList<PriceStandardEntity> pseList=priceStandardSendDao.queryListByParamByEvent(pesenew);
//					sendData(pseList,false);
//				}
//			}
//			
//		}
	}
	
	@Transactional
	public void sendData(ArrayList<PriceStandardEntity> datas, boolean isFailureData) {
		
		ArrayList<PriceCardInfo> priceCityData = new ArrayList<PriceCardInfo>(MAX_COUNT);
		Map<String,PriceCardInfo> map=new HashMap<String,PriceCardInfo>();
		for (int i = 0; i < datas.size(); i++) {
			PriceCardInfo pci=new PriceCardInfo();
			PriceStandardEntity entity = datas.get(i);
			if(null==entity.getSendPriceCity()||null==entity.getArrivePriceCity()){
				continue;
			}
//			//获取相关活动
//			PriceEventSendEntity pese=new PriceEventSendEntity();
//			pese.setTransTypeCode(entity.getTransTypeCode());
//			pese.setSendPriceCity(entity.getSendPriceCity().getCode());
//			pese.setArrivalPriceCity(entity.getArrivePriceCity().getCode());
//			//获取相关活动信息
//			ArrayList<PriceEventSendEntity> PriceEventSendList=priceStandardSendDao.queryListByParamByEventStandard(pese);
//			ArrayList<String> strFreightSectionCode=new ArrayList<String>();
//			ArrayList<String> strFuelSectionCode=new ArrayList<String>();
//			for(PriceEventSendEntity psee:PriceEventSendList){
//				List<PriceEventDiscountSubSendEntity> PEDSList=psee.getPriceEventDiscountSubs();
//				for(PriceEventDiscountSubSendEntity pes:PEDSList){
//					if (!StringUtils.isEmptyWithBlock(pes.getFreightSectionCode())) {
//						strFreightSectionCode.add(pes.getFreightSectionCode());
//					}
//					if (!StringUtils.isEmptyWithBlock(pes.getFuelSectionCode())) {
//						strFuelSectionCode.add(pes.getFuelSectionCode());
//					}
//				}
//			}
//			PriceSectionEntity priceSectionEntity=null;
//			//融合运费分段
//			if(strFreightSectionCode.size() > 0){
//				priceSectionEntity=priceSectionService.reuniteSectionsList(strFreightSectionCode);
//				
//			}
//			//融合燃油分段
//			strFuelSectionCode.add(entity.getFuelFeeSection());
//			priceSectionEntity=priceSectionService.reuniteSectionsList(strFuelSectionCode);
//			if(null!=priceSectionEntity){
//								
//			}
			//活动多个活动调用接口获取到最新的优惠分段
			//将查询出来的标准附加费转换成需要发送到MQ队列的对象
			pci.setType("S");
			pci.setSectionId("");
			pci.setFuelSubchargeId(entity.getFuelFeeSection());
			pci.setTransportType(entity.getTransTypeCode());
			pci.setStartCityName(entity.getSendPriceCity().getCode());
			pci.setArriveCityName(entity.getArrivePriceCity().getCode());
			pci.setCityMapType("C");
			pci.setStartPrice(entity.getLowestFee());
			pci.setWeightPrice(entity.getWeightPrice());
			pci.setVolumePrice(entity.getVolumePrice());
			pci.setLightDiscount(1D);
			pci.setHeavyDiscount(1D);
			pci.setLineDistance(0);
			pci.setLightWeightType("A");
			pci.setColdHotType("C");
			pci.setMajorMinorType("P");
			
			pci.setExtraChargePrice(entity.getAddFee());
			//transportTime 运输时间（小时）
			pci.setEffectiveDate(entity.getEffectiveTime());//生效日期
			pci.setInvalidDate(entity.getInvalidTime());//失效日期
			pci.setRemark(entity.getRemark());
			pci.setRecStatus(entity.getState().equals("EFFECTIVE")?0:1);
			
			//如果相同唯一主键的数据，则不同步
			boolean flag=true;
			String onlyCode=entity.getTransTypeCode()+"_"+entity.getArrivePriceCity().getCode()+"_"+entity.getSendPriceCity().getCode();
			PriceCardInfo a=map.get(onlyCode);
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
			if ((i + 1) % MAX_COUNT == 0 || i == datas.size()-1) {
				try {
					activeMQProducerService.postPriceCardDataToQueue(priceCityData);
					if (isFailureData) {
						//如果是失败数据发送成功了更新失败表状态为已发送
						for (int j = 0; j < priceCityData.size(); j++) {
							PriceStandardEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
							PriceStandardEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
							saveFailureEntity.setType(DataSendJobConstant.JOB_CODE_PRICE_STANDARD);
							dataSendJobFailureService.saveFailureData(saveFailureEntity);
						}
					}
					//失败数据需要更新失败重发次数 Chenyl @20160305
					else {
						for (int j = 0; j < priceCityData.size(); j++) {
							PriceStandardEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
							DataSendJobFailureEntity updateFailureTimesEntity = new DataSendJobFailureEntity();
							updateFailureTimesEntity.setBusinessId(failureEntity.getId());
							updateFailureTimesEntity.setFailReason(e.getMessage());
							updateFailureTimesEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
							updateFailureTimesEntity.setType(DataSendJobConstant.JOB_CODE_PRICE_STANDARD);
							dataSendJobFailureService.updateSendFailTimes(updateFailureTimesEntity);
						}
					}
				}
				priceCityData.clear();
			}
		}
	}
	

}
