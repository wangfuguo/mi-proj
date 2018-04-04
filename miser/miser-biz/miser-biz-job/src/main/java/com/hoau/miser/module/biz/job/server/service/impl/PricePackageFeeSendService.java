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

import com.hoau.eai.pc.vo.PricePackageFeeInfo;
import com.hoau.miser.module.biz.job.server.dao.PricePackageFeeStandardSendDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IPricePackageFeeService;
import com.hoau.miser.module.biz.job.server.service.IPriceStandSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.job.shared.domain.OuterBranchPriceSendEntity;
import com.hoau.miser.module.biz.job.shared.domain.PricePackageFeeStandardEntity;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PricePackageFeeSendService implements IPricePackageFeeService {

	/**
	 * 
	 */
	private static Logger logger = LoggerFactory
			.getLogger(PricePackageFeeSendService.class);

	@Resource
	private IActiveMQProducerService activeMQProducerService;

	@Resource
	private PricePackageFeeStandardSendDao pricePackageFeeStandardSendDao;

	@Resource
	private IDataSendJobFailureService dataSendJobFailureService;

	@Resource
	private IDataSendJobTimeService dataSendJobTimeService;

	private static PropertiesConfiguration packageFeeConfig;

	static {
		try {
			setPackageFeeConfig(new PropertiesConfiguration("config.properties"));
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
		dsjfe.setType(DataSendJobConstant.JOB_CODE_PRICE_PACKAGE_FEE);

		List<PricePackageFeeStandardEntity> listFailure = pricePackageFeeStandardSendDao
				.searchPackageFeeStandFailure(dsjfe);
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
				.getLastSendTime(DataSendJobConstant.JOB_CODE_PRICE_PACKAGE_FEE);

		if (resultEntity != null) {
			queryNewDataFitlerEntity.setStartTime(resultEntity
					.getLastSendTime());
		}
		queryNewDataFitlerEntity.setEndTime(new Date());

		List<PricePackageFeeStandardEntity> listNew = pricePackageFeeStandardSendDao
				.searchPackageFeeStandNew(queryNewDataFitlerEntity);

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
		saveEntity.setJobCode(DataSendJobConstant.JOB_CODE_PRICE_PACKAGE_FEE);
		saveEntity.setJobName(DataSendJobConstant
				.getNameByCode(DataSendJobConstant.JOB_CODE_PRICE_PACKAGE_FEE));
		saveEntity.setLastSendTime(new Date());
		saveEntity.setActive(MiserConstants.ACTIVE);
		saveEntity.setCreateUser("SYSTEM-JOB");
		saveEntity.setCreateDate(new Date());
		saveEntity.setModifyDate(new Date());
		saveEntity.setModifyUser("SYSTEM-JOB");
		dataSendJobTimeService.saveLastSendTime(saveEntity);
	}

	/**
	 * 
	 * @Description: TODO发送数据
	 * @param
	 * @return void
	 * @throws
	 * @author 李玮琰
	 * @date 2016年2月26日
	 */
	@Transactional
	private void send(List<PricePackageFeeStandardEntity> list,
			boolean isFailure) {
		/**
		 * 计算最大值
		 */
		int MAX_COUNT = Integer.parseInt(packageFeeConfig
				.getString("SENDMQ.PRICEPACKAGEFEE.MAXCOUNT"));
		ArrayList<PricePackageFeeInfo> pricePackageFeeInfo = new ArrayList<PricePackageFeeInfo>(
				MAX_COUNT);
		for (int i = 0; i < list.size(); i++) {
			PricePackageFeeStandardEntity pmsd = list.get(i);

			PricePackageFeeInfo oa = new PricePackageFeeInfo();
			oa.setCode(pmsd.getCode());
			oa.setName(pmsd.getName());
			// 修改默认按照个数收费
			if ("VOLUMN".equals(pmsd.getCalculationType())) {
				oa.setCalculationType("3");
			} else {
				oa.setCalculationType("0");
			}
			oa.setRate(pmsd.getRate().doubleValue());
			oa.setMoney(pmsd.getMoney());
			oa.setMinMoney(pmsd.getMinMoney());
			oa.setLockType(pmsd.getLockType());
			oa.setRemark(pmsd.getRemark());
			oa.setSfyx(MiserConstants.ACTIVE.equals(pmsd.getActive()) ? "1"
					: "0");
			oa.setTransportType(pmsd.getTransTypeCode());
			pricePackageFeeInfo.add(oa);
			if ((i + 1) % MAX_COUNT == 0 || i == list.size() - 1) {
				try {
					// 发送数据
					activeMQProducerService
							.postPricePackageFeeDataToQueue(pricePackageFeeInfo);
					if (isFailure) {
						for (int j = 0; j < pricePackageFeeInfo.size(); j++) {
							PricePackageFeeStandardEntity failureEntity = list
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
						for (int j = 0; j < pricePackageFeeInfo.size(); j++) {
							PricePackageFeeStandardEntity failureEntity = list
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
						for (int j = 0; j < pricePackageFeeInfo.size(); j++) {
							PricePackageFeeStandardEntity failureEntity = list
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
					pricePackageFeeInfo.clear();
				}
			}
		}
	}

	@Override
	public void sendPackageFee() {
		searchFailureData();
		searchNewData();
		updateSendTime();
	}

	public static PropertiesConfiguration getPackageFeeConfig() {
		return packageFeeConfig;
	}

	public static void setPackageFeeConfig(
			PropertiesConfiguration packageFeeConfig) {
		PricePackageFeeSendService.packageFeeConfig = packageFeeConfig;
	}

}
