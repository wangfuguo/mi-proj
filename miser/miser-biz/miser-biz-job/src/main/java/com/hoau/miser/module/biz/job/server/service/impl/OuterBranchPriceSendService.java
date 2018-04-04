package com.hoau.miser.module.biz.job.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.eai.pc.vo.OuterBranchPrice;
import com.hoau.miser.module.biz.job.server.dao.OuterBranchPriceSendDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IOuterBranchPriceSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.job.shared.domain.OuterBranchPriceSendEntity;
import com.hoau.miser.module.biz.job.shared.domain.PriceStandardEntity;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class OuterBranchPriceSendService implements
		IOuterBranchPriceSendService {

	/**
	 * 
	 */
	private static Logger logger = LoggerFactory
			.getLogger(OuterBranchPriceSendService.class);

	@Resource
	private IActiveMQProducerService activeMQProducerService;

	@Resource
	private OuterBranchPriceSendDao outerBranchPriceSendDao;

	@Resource
	private IDataSendJobFailureService dataSendJobFailureService;

	@Resource
	private IDataSendJobTimeService dataSendJobTimeService;

	private static PropertiesConfiguration outerBranchPriceConfig;

	static {
		try {
			setOuterBranchPriceConfig(new PropertiesConfiguration(
					"config.properties"));
		} catch (ConfigurationException cexception) {
			logger.error("read adserver Conf properties file error.",
					cexception);
			throw new RuntimeException(cexception);
		}
	}

	public static PropertiesConfiguration getOuterBranchPriceConfig() {
		return outerBranchPriceConfig;
	}

	public static void setOuterBranchPriceConfig(
			PropertiesConfiguration outerBranchPriceConfig) {
		OuterBranchPriceSendService.outerBranchPriceConfig = outerBranchPriceConfig;
	}

	private void searchNewData() {
		DataSendJobQueryFilterEntity queryNewDataFitlerEntity = new DataSendJobQueryFilterEntity();

		DataSendJobTimeEntity resultEntity = dataSendJobTimeService
				.getLastSendTime(DataSendJobConstant.JOB_CODE_OUTER_BRANCH_PRICE);

		if (resultEntity != null) {
			queryNewDataFitlerEntity.setStartTime(resultEntity
					.getLastSendTime());
		}
		queryNewDataFitlerEntity.setEndTime(new Date());

		List<OuterBranchPriceSendEntity> listNew = outerBranchPriceSendDao
				.searchOuterBranchPriceSendNew(queryNewDataFitlerEntity);

		send(listNew, false);
	}

	private void searchFailureData() {
		DataSendJobFailureEntity queryFailDataFilter = new DataSendJobFailureEntity();
		queryFailDataFilter.setActive(MiserConstants.ACTIVE);
		queryFailDataFilter
				.setType(DataSendJobConstant.JOB_CODE_OUTER_BRANCH_PRICE);

		List<OuterBranchPriceSendEntity> listFailure = outerBranchPriceSendDao
				.searchOuterBranchPriceSendFailure(queryFailDataFilter);
		send(listFailure, true);
	}

	@Transactional
	public void send(List<OuterBranchPriceSendEntity> list, boolean isFailure) {
		/**
		 * 计算最大值
		 */
		int MAX_COUNT = Integer.parseInt(outerBranchPriceConfig
				.getString("SENDMQ.OuterBranchPrice.MAXCOUNT"));

		ArrayList<OuterBranchPrice> outerBranchPrices = new ArrayList<OuterBranchPrice>(
				MAX_COUNT);
		for (int i = 0; i < list.size(); i++) {
			OuterBranchPriceSendEntity pmsd = list.get(i);

			OuterBranchPrice oa = new OuterBranchPrice();
			oa.setProvinceCode(pmsd.getProvinceCode());
			oa.setCityCode(pmsd.getCityCode());
			oa.setAreaCode(pmsd.getAreaCode());
			oa.setLowestFee(pmsd.getLowestFee().doubleValue());
			oa.setWeightFee(pmsd.getWeightRate().doubleValue());
			oa.setVolumeFee(pmsd.getVolumeRate().doubleValue());
			oa.setActive(pmsd.getActive());
			oa.setRemark(pmsd.getRemark());
			oa.setCreateDate(pmsd.getCreateTime());
			oa.setCreateUser(pmsd.getCreateUserCode());
			oa.setModifyDate(pmsd.getModifyTime());
			oa.setModifyUser(pmsd.getModifyUserCode());
			oa.setId(pmsd.getId());
			outerBranchPrices.add(oa);
			if ((i + 1) % MAX_COUNT == 0 || i == list.size() - 1) {
				try {
					// 发送数据
					activeMQProducerService
							.postOuterBranchPriceDataToQueue(outerBranchPrices);

					if (isFailure) {
						for (int j = 0; j < outerBranchPrices.size(); j++) {
							OuterBranchPriceSendEntity failureEntity = list
									.get((i / MAX_COUNT) * MAX_COUNT + j);

							DataSendJobFailureEntity saveSuccessEntity = new DataSendJobFailureEntity();
							saveSuccessEntity.setId(failureEntity.getSendId());
							saveSuccessEntity
									.setActive(MiserConstants.INACTIVE);
							dataSendJobFailureService
									.updateSendedData(saveSuccessEntity);
						}
					}

				} catch (Exception e) {
					// 发送失败将错误信息回与到错误记录表中
					e.printStackTrace();
					if (!isFailure) {
						for (int j = 0; j < outerBranchPrices.size(); j++) {
							OuterBranchPriceSendEntity failureEntity = list
									.get((i / MAX_COUNT) * MAX_COUNT + j);
							DataSendJobFailureEntity saveFailureEntity = new DataSendJobFailureEntity();

							saveFailureEntity.setId(UUIDUtil.getUUID());
							saveFailureEntity.setBusinessId(failureEntity
									.getId());
							saveFailureEntity.setActive(MiserConstants.ACTIVE);
							saveFailureEntity.setCreateDate(new Date());
							saveFailureEntity.setCreateUser("SYSTEM-JOB");
							saveFailureEntity.setFailReason(e.getMessage());
							saveFailureEntity.setModifyDate(new Date());
							saveFailureEntity.setModifyUser("SYSTEM-JOB");
							saveFailureEntity.setSendTime(new Date());
							saveFailureEntity
									.setType(DataSendJobConstant.JOB_CODE_OUTER_BRANCH_PRICE);

							dataSendJobFailureService
									.saveFailureData(saveFailureEntity);
						}
					} else {
						for (int j = 0; j < outerBranchPrices.size(); j++) {
							OuterBranchPriceSendEntity failureEntity = list
									.get((i / MAX_COUNT) * MAX_COUNT + j); // 获取此次发送的数据
							DataSendJobFailureEntity updateFailureTimesEntity = new DataSendJobFailureEntity();
							updateFailureTimesEntity
									.setBusinessId(failureEntity.getId());
							updateFailureTimesEntity.setFailReason(e
									.getMessage());
							updateFailureTimesEntity
									.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
							updateFailureTimesEntity
									.setType(DataSendJobConstant.JOB_CODE_OUTER_BRANCH_PRICE);
							dataSendJobFailureService
									.updateSendFailTimes(updateFailureTimesEntity);

						}

					}
					outerBranchPrices.clear();
				}
			}
		}
	}

	private void updateSendTime() {
		// 更新上次发送时间
		DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
		saveEntity.setId(UUIDUtil.getUUID());
		saveEntity.setJobCode(DataSendJobConstant.JOB_CODE_OUTER_BRANCH_PRICE);
		saveEntity
				.setJobName(DataSendJobConstant
						.getNameByCode(DataSendJobConstant.JOB_CODE_OUTER_BRANCH_PRICE));
		saveEntity.setLastSendTime(new Date());
		saveEntity.setActive(MiserConstants.ACTIVE);
		saveEntity.setCreateUser("SYSTEM-JOB");
		saveEntity.setCreateDate(new Date());
		saveEntity.setModifyDate(new Date());
		saveEntity.setModifyUser("SYSTEM-JOB");
		dataSendJobTimeService.saveLastSendTime(saveEntity);
	}

	@Override
	public void SendOuterBranchPrice() {
		searchFailureData();
		searchNewData();
		updateSendTime();
	}

}
