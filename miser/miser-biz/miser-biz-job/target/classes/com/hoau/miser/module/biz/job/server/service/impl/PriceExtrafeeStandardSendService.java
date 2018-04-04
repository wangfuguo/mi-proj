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

import com.hoau.eai.pc.vo.ExtraInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.PriceExtrafeeStandardSendDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IPriceExtrafeeStandardSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.job.shared.domain.PriceExtrafeeStandardSendEntity;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;


/**
 * 
 * ClassName: PriceCardSendService 
 * @Description: TODO(标准附加费队列) 
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月21日
 * @version V1.0
 */
@Service
public class PriceExtrafeeStandardSendService implements IPriceExtrafeeStandardSendService{

	@Resource
	private PriceExtrafeeStandardSendDao priceExtrafeeStandardSendDao;
	@Resource
	private IDataSendJobTimeService dataSendJobTimeService;
	
	@Resource
	private IDataSendJobFailureService dataSendJobFailureService;
	
	@Resource
	private IActiveMQProducerService activeMQProducerService;
	private static int MAX_COUNT = 100;
	
	static{
		try {
			Properties properties = ConfigFileLoadUtil.getPropertiesForClasspath("config.properties");
			String maxCountStr = properties.getProperty("SENDMQ.EXTRA.MAXCOUNT", "100");
			MAX_COUNT = StringUtils.toInt(maxCountStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	@Transactional
	public void sendPriceExtrafeeStandard() {
		//记录本次发送时间
				Date thisTime = new Date();
				
				//查询出上次发送失败的记录
				DataSendJobFailureEntity queryFailureFilterEntity = new DataSendJobFailureEntity();
				queryFailureFilterEntity.setActive(MiserConstants.ACTIVE);
				queryFailureFilterEntity.setType(DataSendJobConstant.JOB_CODE_EXTRA);
				queryFailureFilterEntity.setRetryTimes(DataSendJobConstant.MAX_FAIL_RETRY_TIMES);
				ArrayList<PriceExtrafeeStandardSendEntity> failureDatas = priceExtrafeeStandardSendDao.queryFailureData(queryFailureFilterEntity);
				//发送上次失败数据
				sendData(failureDatas, true);
				
				//查询出本次需要新发送的标准附加费
				DataSendJobQueryFilterEntity queryNewDataFitlerEntity = new DataSendJobQueryFilterEntity();
				//查询出上次发送时间
				DataSendJobTimeEntity resultEntity = dataSendJobTimeService.getLastSendTime(DataSendJobConstant.JOB_CODE_EXTRA);
				if (resultEntity != null) {
					queryNewDataFitlerEntity.setStartTime(resultEntity.getLastSendTime());
				}
				queryNewDataFitlerEntity.setEndTime(thisTime);
				ArrayList<PriceExtrafeeStandardSendEntity> newDatas = priceExtrafeeStandardSendDao.queryListByParam(queryNewDataFitlerEntity);
				//发送本次新数据
				sendData(newDatas, false);
				
				//更新上次发送时间
				DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
				saveEntity.setId(UUIDUtil.getUUID());
				saveEntity.setJobCode(DataSendJobConstant.JOB_CODE_EXTRA);
				saveEntity.setJobName(DataSendJobConstant.getNameByCode(DataSendJobConstant.JOB_CODE_EXTRA));
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
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月26日
	 */
	@Transactional
	public void sendData(ArrayList<PriceExtrafeeStandardSendEntity> datas, boolean isFailureData) {
		
		ArrayList<ExtraInfo> priceCityData = new ArrayList<ExtraInfo>(MAX_COUNT);
		Map<String,ExtraInfo> map=new HashMap<String,ExtraInfo>();
		for (int i = 0; i < datas.size(); i++) {
			//将查询出来的标准附加费转换成需要发送到MQ队列的对象
			PriceExtrafeeStandardSendEntity entity = datas.get(i);
			ExtraInfo pci=new ExtraInfo();
			pci.setTransportType(entity.getTransTypeCode());
			pci.setType("S");
			String type=entity.getType();
			if(type.equals("PAPER")){
				type="GF";
			}else if(type.equals("PICKUP")){
				type="TF";
			}else if(type.equals("DELIVERY")){
				type="SF";
			}else if(type.equals("INSURANCE")){
				type="BL";
			}else if(type.equals("COLLECTION_RATE")){
				type="BF";
			}else if(type.equals("SMS")){
				type="XF";
			}
			pci.setFeeType(type);
			pci.setAmount(entity.getMoney());
			pci.setLockedStatus(entity.getLockType());
			pci.setRemark(entity.getRemark());
			pci.setRecStatus("EFFECTIVE".equals(entity.getState())?0:1);
			//如果相同唯一主键的数据，则不同步
			boolean flag=true;
			String onlyCode=entity.getTransTypeCode()+"_"+entity.getType();
			ExtraInfo a=map.get(onlyCode);
			if(a != null){
				if("EFFECTIVE".equals(entity.getState())){
					priceCityData.remove(a);
					map.remove(onlyCode);
				}else{
					flag=false;
				}
			}else{
				if(!"EFFECTIVE".equals(entity.getState())){
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
					activeMQProducerService.postExtrtaDataToQueue(priceCityData);
					if (isFailureData) {
						//如果是失败数据发送成功了更新失败表状态为已发送
						for (int j = 0; j < priceCityData.size(); j++) {
							PriceExtrafeeStandardSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
							PriceExtrafeeStandardSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
							saveFailureEntity.setType(DataSendJobConstant.JOB_CODE_EXTRA);
							dataSendJobFailureService.saveFailureData(saveFailureEntity);
						}
					}
					else {
						for (int j = 0; j < priceCityData.size(); j++) {
							PriceExtrafeeStandardSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
							DataSendJobFailureEntity updateFailureTimesEntity = new DataSendJobFailureEntity();
							updateFailureTimesEntity.setBusinessId(failureEntity.getId());
							updateFailureTimesEntity.setFailReason(e.getMessage());
							updateFailureTimesEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
							updateFailureTimesEntity.setType(DataSendJobConstant.JOB_CODE_EXTRA);
							dataSendJobFailureService.updateSendFailTimes(updateFailureTimesEntity);
						}
					}
				}
				priceCityData.clear();
			}
		}
	}

}
