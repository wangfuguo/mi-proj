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

import com.hoau.eai.pc.vo.PricePackageFeePcInfo;
import com.hoau.miser.module.biz.job.server.dao.PricePackageFeePcSendEntityDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IPricePackageFeePcService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.job.shared.domain.PricePackageFeePcSendEntity;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PricePackageFeePcSendService implements IPricePackageFeePcService {
	/**
	 * 
	 */
	private static Logger logger = LoggerFactory
			.getLogger(PricePackageFeePcSendService.class);

	@Resource
	private IActiveMQProducerService activeMQProducerService;

	@Resource
	private PricePackageFeePcSendEntityDao pricePackageFeePcSendEntityDao;

	@Resource
	private IDataSendJobTimeService dataSendJobTimeService;

	@Resource
	private IDataSendJobFailureService dataSendJobFailureService;

	private static PropertiesConfiguration packageFeePcConfig;

	static {
		try {
			setPackageFeePcConfig(new PropertiesConfiguration(
					"config.properties"));
		} catch (ConfigurationException cexception) {
			logger.error("read adserver Conf properties file error.",
					cexception);
			throw new RuntimeException(cexception);
		}
	}

	/**
	 * 
	 * @Description: TODO获取上次失败数据
	 * @param
	 * @return void
	 * @throws
	 * @author 李玮琰
	 * @date 2016年2月26日
	 */
	private void searchFailureData() {
		DataSendJobFailureEntity dsjfe = new DataSendJobFailureEntity();
		dsjfe.setActive(MiserConstants.ACTIVE);
		dsjfe.setType(DataSendJobConstant.JOB_CODE_PRICE_PACKAGE_FEE_PC);

		List<PricePackageFeePcSendEntity> listFailure = pricePackageFeePcSendEntityDao
				.searchPackageFeePcFailure(dsjfe);
		send(listFailure, true);
	}

	/**
	 * 
	 * @Description: TODO获取最新数据
	 * @param
	 * @return void
	 * @throws
	 * @author 李玮琰
	 * @date 2016年2月26日
	 */
	private void searchNewData() {
		DataSendJobQueryFilterEntity queryNewDataFitlerEntity = new DataSendJobQueryFilterEntity();

		DataSendJobTimeEntity resultEntity = dataSendJobTimeService
				.getLastSendTime(DataSendJobConstant.JOB_CODE_PRICE_PACKAGE_FEE_PC);

		if (resultEntity != null) {
			queryNewDataFitlerEntity.setStartTime(resultEntity
					.getLastSendTime());
		}
		queryNewDataFitlerEntity.setEndTime(new Date());

		List<PricePackageFeePcSendEntity> listNew = pricePackageFeePcSendEntityDao
				.searchPackageFeePcNew(queryNewDataFitlerEntity);

		send(listNew, false);
	}

	/**
	 * 更新发送时间
	 * 
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author 李玮琰
	 * @date 2016年2月29日
	 */
	private void updateSendTime() {
		// 更新上次发送时间
		DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
		saveEntity.setId(UUIDUtil.getUUID());
		saveEntity
				.setJobCode(DataSendJobConstant.JOB_CODE_PRICE_PACKAGE_FEE_PC);
		saveEntity
				.setJobName(DataSendJobConstant
						.getNameByCode(DataSendJobConstant.JOB_CODE_PRICE_PACKAGE_FEE_PC));
		saveEntity.setLastSendTime(new Date());
		saveEntity.setActive(MiserConstants.ACTIVE);
		saveEntity.setCreateUser("SYSTEM-JOB");
		saveEntity.setCreateDate(new Date());
		saveEntity.setModifyDate(new Date());
		saveEntity.setModifyUser("SYSTEM-JOB");
		dataSendJobTimeService.saveLastSendTime(saveEntity);
	}

	private void send(List<PricePackageFeePcSendEntity> list, boolean isFailure) {
		/**
		 * 计算最大值
		 */
		int MAX_COUNT = Integer.parseInt(packageFeePcConfig
				.getString("SENDMQ.PRICEPACKAGEFEEPC.MAXCOUNT"));
		ArrayList<PricePackageFeePcInfo> pricePackageFeePcInfo = new ArrayList<PricePackageFeePcInfo>(
				MAX_COUNT);

		for (int i = 0; i < list.size(); i++) {
			PricePackageFeePcSendEntity pmsd = list.get(i);

			PricePackageFeePcInfo oa = new PricePackageFeePcInfo();
			oa.setPriceCity(pmsd.getPriceCity());
			oa.setCode(pmsd.getCode());
			oa.setMoney(pmsd.getMoney());
			oa.setMinMoney(pmsd.getMinMoney());
			oa.setLockType(pmsd.getLockType());
			oa.setRemark(pmsd.getRemark());
			oa.setSfyx(MiserConstants.ACTIVE.equals(pmsd.getActive()) ? "1"
					: "0");
			oa.setRecordDate(pmsd.getModifyTime());
			oa.setTransportType(pmsd.getTransTypeCode());
			oa.setRate(pmsd.getRate() == null ? null : pmsd.getRate()
					.toString());
			pricePackageFeePcInfo.add(oa);
			if ((i + 1) % MAX_COUNT == 0 || i == list.size() - 1) {
				try {
					// 发送数据
					activeMQProducerService
							.postPricePackageFeePcDataToQueue(pricePackageFeePcInfo);
					if (isFailure) {
						for (int j = 0; j < pricePackageFeePcInfo.size(); j++) {
							PricePackageFeePcSendEntity failureEntity = list
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
						for (int j = 0; j < pricePackageFeePcInfo.size(); j++) {
							PricePackageFeePcSendEntity failureEntity = list
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
									.setType(DataSendJobConstant.JOB_CODE_PRICE_PACKAGE_FEE);

							dataSendJobFailureService
									.saveFailureData(saveFailureEntity);
						}
					} else {
						for (int j = 0; j < pricePackageFeePcInfo.size(); j++) {
							PricePackageFeePcSendEntity failureEntity = list
									.get((i / MAX_COUNT) * MAX_COUNT + j); // 获取此次发送的数据
							DataSendJobFailureEntity updateFailureTimesEntity = new DataSendJobFailureEntity();
							updateFailureTimesEntity
									.setBusinessId(failureEntity.getId());
							updateFailureTimesEntity.setFailReason(e
									.getMessage());
							updateFailureTimesEntity
									.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
							updateFailureTimesEntity
									.setType(DataSendJobConstant.JOB_CODE_PRICE_PACKAGE_FEE);
							dataSendJobFailureService
									.updateSendFailTimes(updateFailureTimesEntity);

						}
					}
				}
				pricePackageFeePcInfo.clear();
			}
		}
	}

	@Override
	public void sendPackageFeePc() {
		searchFailureData();
		searchNewData();
		updateSendTime();
	}

	public static PropertiesConfiguration getPackageFeePcConfig() {
		return packageFeePcConfig;
	}

	public static void setPackageFeePcConfig(
			PropertiesConfiguration packageFeePcConfig) {
		PricePackageFeePcSendService.packageFeePcConfig = packageFeePcConfig;
	}

}
