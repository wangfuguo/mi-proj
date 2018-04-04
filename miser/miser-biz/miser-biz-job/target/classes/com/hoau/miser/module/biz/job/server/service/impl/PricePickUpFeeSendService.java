package com.hoau.miser.module.biz.job.server.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.eai.pc.vo.PricePickUpFeeInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.PricePickUpFeeSendDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IPricePickUpFeeSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.job.shared.domain.PricePickUpFeeEntity;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * ClassName: PricePickUpFeeSendService
 * 
 * @Description: 提货费同步服务 sync PricePickUpFee Service
 * @author ZOUYU
 * @date 2016年1月15日
 * @version V1.0
 */
@Service("pricePickUpFeeSendService")
public class PricePickUpFeeSendService implements IPricePickUpFeeSendService {

	@Resource
	private PricePickUpFeeSendDao pricePickUpFeeSendDao;

	@Resource
	private IDataSendJobTimeService dataSendJobTimeService;

	@Resource
	private IDataSendJobFailureService dataSendJobFailureService;

	@Resource
	private IActiveMQProducerService activeMQProducerService;

	/**
	 * @Fields MAX_COUNT : 每次提交MQ的条数
	 */
	private static int MAX_COUNT = 100;

	/**
	 * reload parameter MAX_COUNT
	 */
	static {
		try {
			Properties properties = ConfigFileLoadUtil
					.getPropertiesForClasspath("config.properties");
			String maxCountStr = properties.getProperty(
					"SENDMQ.PRICEPICKUPFEE.MAXCOUNT", "100");
			MAX_COUNT = StringUtils.toInt(maxCountStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * send PricePickUpFee
	 */
	@Override
	@Transactional
	public void sendPricePickUpFee() {
		// 记录本次发送时间
		Date thisTime = new Date();

		// 查询出上次发送时间
		DataSendJobTimeEntity resultEntity = dataSendJobTimeService
				.getLastSendTime(DataSendJobConstant.JOB_CODE_PRICE_PICK_UP_FEE);
		Date lastTime = null;
		if (resultEntity != null) {
			lastTime = resultEntity.getLastSendTime();
		}

		// 更新失败表中存在的和本次需要发送记录重复的数据
		DataSendJobFailureEntity updateFailureRepeatEntity = new DataSendJobFailureEntity();
		updateFailureRepeatEntity.setActive(MiserConstants.ACTIVE);
		updateFailureRepeatEntity
				.setType(DataSendJobConstant.JOB_CODE_PRICE_PICK_UP_FEE);
		updateFailureRepeatEntity.setLastSendTime(lastTime);
		updateFailureRepeatEntity.setSendTime(thisTime);
		updateFailureRepeatEntity.setRemark("存在新纪录，取消失败记录的发送");
		pricePickUpFeeSendDao
				.updateFailureRepeatData(updateFailureRepeatEntity);

		// 查询出上次发送失败的记录
		DataSendJobFailureEntity queryFailureFilterEntity = new DataSendJobFailureEntity();
		queryFailureFilterEntity.setActive(MiserConstants.ACTIVE);
		queryFailureFilterEntity
				.setType(DataSendJobConstant.JOB_CODE_PRICE_PICK_UP_FEE);
		queryFailureFilterEntity
				.setRetryTimes(DataSendJobConstant.MAX_FAIL_RETRY_TIMES);
		ArrayList<PricePickUpFeeEntity> failureDatas = pricePickUpFeeSendDao
				.queryFailureData(queryFailureFilterEntity);
		// 发送上次失败数据
		sendData(failureDatas, true);

		// 查询出本次需要新发送的提货费
		DataSendJobQueryFilterEntity queryNewDataFitlerEntity = new DataSendJobQueryFilterEntity();
		queryNewDataFitlerEntity.setStartTime(lastTime);
		queryNewDataFitlerEntity.setEndTime(thisTime);
		ArrayList<PricePickUpFeeEntity> newDatas = pricePickUpFeeSendDao
				.queryNeedSendData(queryNewDataFitlerEntity);
		// 发送本次新数据
		sendData(newDatas, false);

		// 更新上次发送时间
		DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
		saveEntity.setId(UUIDUtil.getUUID());
		saveEntity.setJobCode(DataSendJobConstant.JOB_CODE_PRICE_PICK_UP_FEE);
		saveEntity.setJobName(DataSendJobConstant
				.getNameByCode(DataSendJobConstant.JOB_CODE_PRICE_PICK_UP_FEE));
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
	 * @author ZOUYU
	 * @date 2016年1月25日
	 */
	@Transactional
	public void sendData(ArrayList<PricePickUpFeeEntity> datas,
			boolean isFailureData) {

		ArrayList<PricePickUpFeeInfo> pricePickUpFeeData = new ArrayList<PricePickUpFeeInfo>(
				MAX_COUNT);

		for (int i = 0; i < datas.size(); i++) {
			// 将查询出来的提货费转换成需要发送到MQ队列的对象
			PricePickUpFeeEntity entity = datas.get(i);
			// MQ队列对象
			PricePickUpFeeInfo info = new PricePickUpFeeInfo();

			info.setPriceCityCode(entity.getPriceCityCode());
			// 运输类型:运输类型 D-定日达;Z-整车;P-普通零担
			info.setTransportType(entity.getTransportType());
			info.setMinMoney(entity.getMinMoney());
			info.setWeightPrice(entity.getWeightPrice());
			info.setVolumnPrice(entity.getVolumnPrice());
			info.setSfyx(MiserConstants.ACTIVE.equals(entity.getActive()) ? "1"
					: "0");// 是否有效y->1,n->0
			info.setRemark(entity.getRemark());

			pricePickUpFeeData.add(info);
			if ((i + 1) % MAX_COUNT == 0 || (i + 1) == datas.size()) {
				try {
					activeMQProducerService
							.postPricePickUpFeeDataToQueue(pricePickUpFeeData);
					if (isFailureData) {
						// 如果是失败数据发送成功了更新失败表状态为已发送
						for (int j = 0; j < pricePickUpFeeData.size(); j++) {
							PricePickUpFeeEntity failureEntity = datas
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
						for (int j = 0; j < pricePickUpFeeData.size(); j++) {
							PricePickUpFeeEntity failureEntity = datas
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
									.setType(DataSendJobConstant.JOB_CODE_PRICE_PICK_UP_FEE);
							dataSendJobFailureService
									.saveFailureData(saveFailureEntity);
						}
					} else {
						for (int j = 0; j < pricePickUpFeeData.size(); j++) {
							PricePickUpFeeEntity failureEntity = datas
									.get((i / MAX_COUNT) * MAX_COUNT + j); // 获取此次发送的数据
							DataSendJobFailureEntity updateFailureTimesEntity = new DataSendJobFailureEntity();
							updateFailureTimesEntity
									.setBusinessId(failureEntity.getId());
							updateFailureTimesEntity.setFailReason(e
									.getMessage());
							updateFailureTimesEntity
									.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
							updateFailureTimesEntity
									.setType(DataSendJobConstant.JOB_CODE_PRICE_PICK_UP_FEE);
							dataSendJobFailureService
									.updateSendFailTimes(updateFailureTimesEntity);

						}
					}
				}
				pricePickUpFeeData.clear();
			}
		}
	}

}
