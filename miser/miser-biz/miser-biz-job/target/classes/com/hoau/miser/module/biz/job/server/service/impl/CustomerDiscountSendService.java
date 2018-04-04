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

import com.hoau.miser.module.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hoau.eai.pc.vo.DisctionInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.CustomerDisCountAndSurchargeDao;
import com.hoau.miser.module.biz.job.server.service.ICustomerDiscountSendService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.job.shared.domain.DiscountCustomerEntity;
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
public class CustomerDiscountSendService implements
		ICustomerDiscountSendService {
	
	@Resource
	private CustomerDisCountAndSurchargeDao disCountAndSurchargeDao;
	@Resource
	private IDataSendJobTimeService dataSendJobTimeService;
	
	@Resource
	private IDataSendJobFailureService dataSendJobFailureService;
	
	@Resource
	private IActiveMQProducerService activeMQProducerService;
	
	private static int MAX_COUNT = 1;

	private Logger logg=Logger.getLogger(CustomerDiscountSendService.class);
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
	public void sendCustomerDiscount() {

		logg.info("--------------------------客户折扣同步开始");
		//记录本次发送时间
		Date thisTime = new Date();
		// 查询出本次需要新发送的信息
		DataSendJobQueryFilterEntity queryNewDataParam = new DataSendJobQueryFilterEntity();
		// 查询出上次发送时间
		DataSendJobTimeEntity resultEntity = dataSendJobTimeService.getLastSendTime(DataSendJobConstant.JOB_CUSTOMER_CODE_DISCOUNT);
		
		// 组装查询条件
		Map<String, Object> newMapParam = new HashMap<String, Object>();
		newMapParam.put("endTime", thisTime);
		if (resultEntity != null) {
			queryNewDataParam.setStartTime(resultEntity.getLastSendTime());
			newMapParam.put("startTime", resultEntity.getLastSendTime());
		}
		queryNewDataParam.setEndTime(thisTime);
		
		//查询本次的客户折扣信息
		List<DiscountCustomerEntity> newDiscountCustomerDatas=disCountAndSurchargeDao.queryNeedDiscountCustomerFreightData(newMapParam);
		
		//发送本次新数据
		sendDataDiscount(newDiscountCustomerDatas, false);
		logg.info("--------------------------客户折扣同步数据条数-"+newDiscountCustomerDatas.size());
		//更新上次发送时间
		DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
		saveEntity.setId(UUIDUtil.getUUID());
		saveEntity.setJobCode(DataSendJobConstant.JOB_CUSTOMER_CODE_DISCOUNT);
		saveEntity.setJobName(DataSendJobConstant.getNameByCode(DataSendJobConstant.JOB_CUSTOMER_CODE_DISCOUNT));
		saveEntity.setLastSendTime(thisTime);
		saveEntity.setActive(MiserConstants.ACTIVE);
		saveEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
		saveEntity.setCreateDate(new Date());
		saveEntity.setModifyDate(new Date());
		saveEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
		dataSendJobTimeService.saveLastSendTime(saveEntity);
		logg.info("--------------------------客户折扣同步结束");
	}

	private void sendDataDiscount(List<DiscountCustomerEntity> datas, boolean isFailureData) {

		
		ArrayList<DisctionInfo> priceCityData = new ArrayList<DisctionInfo>(MAX_COUNT);
		Map<String,DisctionInfo> map=new HashMap<String,DisctionInfo>();
		for (int i = 0; i < datas.size(); i++) {
			//将查询出来的标准附加费转换成需要发送到MQ队列的对象
			DiscountCustomerEntity entity = datas.get(i);
			DisctionInfo pci=new DisctionInfo();
			try{
			pci.setTransportType(entity.getTransTypeCode());
			pci.setType("C");
			pci.setSectionId(entity.getFreightSectionCode());
			pci.setStandardNo(entity.getCustomerCode());
			pci.setIsDisctionPrice(entity.getDefaultShowDiscountValue()); //是否显示折扣后价格
			pci.setEffectiveDate(entity.getEffectiveTime());
			pci.setInvalidDate(entity.getInvalidTime());
			pci.setRemark(entity.getRemark());
			pci.setRecStatus("EFFECTIVE".equals(entity.getState())?0:1);
			if(StringUtils.isEmpty(entity.getFreightSectionCode())){
				pci.setRecStatus(1);
			}
			//如果相同唯一主键的数据，则不同步
			boolean flag=true;
			String onlyCode=entity.getTransTypeCode()+"_"+entity.getCustomerCode();
			DisctionInfo a=map.get(onlyCode);
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
			}catch(NullPointerException e){
			}
			if (datas.size()>0&&((i + 1) % MAX_COUNT == 0 || i == datas.size()-1)) {
				try {
					activeMQProducerService.postDiscountDataToQueue(priceCityData);
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
							saveFailureEntity.setType(DataSendJobConstant.JOB_CUSTOMER_CODE_DISCOUNT);
							dataSendJobFailureService.saveFailureData(saveFailureEntity);
						}
					}
				}
				priceCityData.clear();
			}
		}
	}
}
