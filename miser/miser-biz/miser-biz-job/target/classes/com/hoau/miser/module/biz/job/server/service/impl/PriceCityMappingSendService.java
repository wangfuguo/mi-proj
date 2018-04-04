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

import com.hoau.eai.pc.vo.PriceCityMappingInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.PriceCityMappingSendDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IPriceCityMappingSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 价格城市关系映射Service ClassName: PriceCityMappingSendService
 * 
 * @author 廖文强
 * @date 2016年2月28日
 * @version V1.0
 */
@Service
public class PriceCityMappingSendService implements
		IPriceCityMappingSendService {
	@Resource
	private IDataSendJobTimeService dataSendJobTimeService;

	@Resource
	private IDataSendJobFailureService dataSendJobFailureService;

	@Resource
	private IActiveMQProducerService activeMQProducerService;

	@Resource
	private PriceCityMappingSendDao priceCityMappingSendDao;
	/**
	 * @Fields MAX_COUNT : 每次提交MQ的条数
	 */
	private static int MAX_COUNT = 100;

	static {
		try {
			Properties properties = ConfigFileLoadUtil
					.getPropertiesForClasspath("config.properties");
			String maxCountStr = properties.getProperty(
					"SENDMQ.PRICECITYMAPPING.MAXCOUNT", "100");
			MAX_COUNT = StringUtils.toInt(maxCountStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void send() {
		// 记录本次发送时间
		Date thisTime = new Date();
		// 查询出本次需要新发送的信息
		DataSendJobQueryFilterEntity queryNewDataFitlerEntity = new DataSendJobQueryFilterEntity();
		// 查询出上次发送时间
		// 失败条件
		DataSendJobFailureEntity queryFailureFilterEntity = new DataSendJobFailureEntity();
		DataSendJobTimeEntity resultEntity = dataSendJobTimeService
				.getLastSendTime(DataSendJobConstant.JOB_BIZ_SEND_PRICE_CITY_MAPPING);
		// 组装查询条件
		Map<String, Object> uniqParam = new HashMap<String, Object>();
		uniqParam.put("endTime", thisTime);
		if (resultEntity != null) {
			queryNewDataFitlerEntity.setStartTime(resultEntity
					.getLastSendTime());
			queryFailureFilterEntity.setLastSendTime(resultEntity
					.getLastSendTime());
		}
		queryNewDataFitlerEntity.setEndTime(thisTime);
		queryFailureFilterEntity.setSendTime(thisTime);

		queryFailureFilterEntity.setActive(MiserConstants.ACTIVE);
		queryFailureFilterEntity
				.setType(DataSendJobConstant.JOB_BIZ_SEND_PRICE_CITY_MAPPING);
		// 失败信息去重
		priceCityMappingSendDao.failureInfoUniq(queryFailureFilterEntity);
		// 发送失败信息

		// 发送上次失败数据
		ArrayList<PriceCityMappingInfo> failureDatas = priceCityMappingSendDao
				.queryFailureData(queryFailureFilterEntity);
		sendData(failureDatas, true);
		// 发送本次数据
		ArrayList<PriceCityMappingInfo> needDatas = priceCityMappingSendDao
				.queryNeedSendData(queryNewDataFitlerEntity);
		sendData(needDatas, false);
		// 更新上次发送时间
		DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
		saveEntity.setId(UUIDUtil.getUUID());
		saveEntity
				.setJobCode(DataSendJobConstant.JOB_BIZ_SEND_PRICE_CITY_MAPPING);
		saveEntity
				.setJobName(DataSendJobConstant
						.getNameByCode(DataSendJobConstant.JOB_BIZ_SEND_PRICE_CITY_MAPPING));
		saveEntity.setLastSendTime(thisTime);
		saveEntity.setActive(MiserConstants.ACTIVE);
		saveEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
		saveEntity.setCreateDate(new Date());
		saveEntity.setModifyDate(new Date());
		saveEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
		dataSendJobTimeService.saveLastSendTime(saveEntity);
	}

	/**
	 * @Description: 发送数据到MQ
	 * @param datas
	 *            需要发送的数据
	 * @param isFailureData
	 *            是否是失败重发，失败重发的数据再发送成功后需要修改为已发送，不是失败重发的数据在发送失败后需要插入到失败表中
	 * @return void
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月25日
	 */
	@Transactional
	public void sendData(ArrayList<PriceCityMappingInfo> datas,
			boolean isFailureData) {
		// activeMQProducerService.postPriceCityMappingToQueue();
		ArrayList<PriceCityMappingInfo> priceCityMappingData = new ArrayList<PriceCityMappingInfo>(
				MAX_COUNT);
		for (int i = 0; i < datas.size(); i++) {
			PriceCityMappingInfo entity = datas.get(i);
			String priceCity = entity.getPriceCity();
			if (!StringUtils.isEmpty(priceCity)) {
				int lepLeng = 4 - priceCity.length();
				for (int j = 0; j < lepLeng; j++) {
					priceCity = "0" + priceCity;
				}
			}
			entity.setPriceCity(priceCity);
			priceCityMappingData.add(entity);
			if ((i + 1) % MAX_COUNT == 0 || i == datas.size() - 1) {
				try {
					activeMQProducerService
							.postPriceCityMappingToQueue(priceCityMappingData);
					if (isFailureData) {
						// 如果是失败数据发送成功了更新失败表状态为已发送
						for (int j = 0; j < priceCityMappingData.size(); j++) {
							PriceCityMappingInfo failureEntity = datas
									.get((i / MAX_COUNT) * MAX_COUNT + j); // 获取此次发送的数据
							DataSendJobFailureEntity saveSuccessEntity = new DataSendJobFailureEntity();
							saveSuccessEntity.setId(failureEntity.getId());
							saveSuccessEntity
									.setActive(MiserConstants.INACTIVE);
							dataSendJobFailureService
									.updateSendedData(saveSuccessEntity);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (!isFailureData) {
						// 如果是新数据发送失败了，插入到失败表
						for (int j = 0; j < priceCityMappingData.size(); j++) {
							PriceCityMappingInfo failureEntity = datas
									.get((i / MAX_COUNT) * MAX_COUNT + j); // 获取此次发送的数据
							DataSendJobFailureEntity saveFailureEntity = new DataSendJobFailureEntity();
							saveFailureEntity.setId(UUIDUtil.getUUID());
							saveFailureEntity.setBusinessId(failureEntity
									.getId());
							saveFailureEntity.setActive(MiserConstants.ACTIVE);
							saveFailureEntity.setCreateDate(new Date());
							saveFailureEntity
									.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
							saveFailureEntity.setFailReason(e.getMessage());
							saveFailureEntity.setModifyDate(new Date());
							saveFailureEntity
									.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
							saveFailureEntity.setSendTime(new Date());
							saveFailureEntity
									.setType(DataSendJobConstant.JOB_BIZ_SEND_PRICE_CITY_MAPPING);
							dataSendJobFailureService
									.saveFailureData(saveFailureEntity);
						}
					} else {
						for (int j = 0; j < priceCityMappingData.size(); j++) {
							PriceCityMappingInfo failureEntity = datas
									.get((i / MAX_COUNT) * MAX_COUNT + j); // 获取此次发送的数据
							DataSendJobFailureEntity updateFailureTimesEntity = new DataSendJobFailureEntity();
							updateFailureTimesEntity
									.setBusinessId(failureEntity.getId());
							updateFailureTimesEntity.setFailReason(e
									.getMessage());
							updateFailureTimesEntity
									.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
							updateFailureTimesEntity
									.setType(DataSendJobConstant.JOB_BIZ_SEND_PRICE_CITY_MAPPING);
							dataSendJobFailureService
									.updateSendFailTimes(updateFailureTimesEntity);

						}
					}
					priceCityMappingData.clear();
				}
			}
		}
	}
}
