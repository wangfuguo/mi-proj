package com.hoau.miser.module.biz.job.server.service.impl;

import com.hoau.eai.pc.vo.BasicInfo;
import com.hoau.eai.pc.vo.DeliveryCityLevelMappingInfo;
import com.hoau.eai.pc.vo.DisctionInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.DeliveryCityLevelMappingSendDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IDeliveryCityLevelMappingSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.*;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName:
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author: Chenyl yulin.chen@hoau.net
 * @date: 2016/4/9 14:59
 */
@Service
public class DeliveryCityLevelMappingSendService implements IDeliveryCityLevelMappingSendService {

    @Resource
    private DeliveryCityLevelMappingSendDao deliveryCityLevelMappingSendDao;

    @Resource
    private IDataSendJobTimeService dataSendJobTimeService;

    @Resource
    private IDataSendJobFailureService dataSendJobFailureService;

    @Resource
    private IActiveMQProducerService activeMQProducerService;

    private static int MAX_COUNT = 100;

    static {
        try {
            Properties properties = ConfigFileLoadUtil.getPropertiesForClasspath("config.properties");
            String maxCountStr = properties.getProperty("SENDMQ.DELEVERYCITYLEVELMAPPING.MAXCOUNT", "100");
            MAX_COUNT = StringUtils.toInt(maxCountStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送送货城市等级映射关系
     */
    @Override
    public void sendDeliveryCityLevelMapping() {
        //记录本次发送时间
        Date thisTime = new Date();

        //查询出上次发送失败的记录
        DataSendJobFailureEntity queryFailureFilterEntity = new DataSendJobFailureEntity();
        queryFailureFilterEntity.setActive(MiserConstants.ACTIVE);
        queryFailureFilterEntity.setType(DataSendJobConstant.JOB_BIZ_SEND_DELIVERY_CITYLEVEL_MAPPING);
        queryFailureFilterEntity.setRetryTimes(DataSendJobConstant.MAX_FAIL_RETRY_TIMES);
        ArrayList<DeliveryCityLevelMappingEntity> failureDatas = deliveryCityLevelMappingSendDao.queryFailureData(queryFailureFilterEntity);
        //发送上次失败数据
        sendData(failureDatas, true);

        //查询出本次需要新发送的映射关系
        DataSendJobQueryFilterEntity queryNewDataFitlerEntity = new DataSendJobQueryFilterEntity();
        //查询出上次发送时间
        DataSendJobTimeEntity resultEntity = dataSendJobTimeService.getLastSendTime(DataSendJobConstant.JOB_BIZ_SEND_DELIVERY_CITYLEVEL_MAPPING);
        if (resultEntity != null) {
            queryNewDataFitlerEntity.setStartTime(resultEntity.getLastSendTime());
        }
        queryNewDataFitlerEntity.setEndTime(thisTime);
        ArrayList<DeliveryCityLevelMappingEntity> newDatas = deliveryCityLevelMappingSendDao.queryNeedSendData(queryNewDataFitlerEntity);
        //发送本次新数据
        sendData(newDatas, false);

        //更新上次发送时间
        DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
        saveEntity.setId(UUIDUtil.getUUID());
        saveEntity.setJobCode(DataSendJobConstant.JOB_BIZ_SEND_DELIVERY_CITYLEVEL_MAPPING);
        saveEntity.setJobName(DataSendJobConstant.getNameByCode(DataSendJobConstant.JOB_BIZ_SEND_DELIVERY_CITYLEVEL_MAPPING));
        saveEntity.setLastSendTime(thisTime);
        saveEntity.setActive(MiserConstants.ACTIVE);
        saveEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
        saveEntity.setCreateDate(new Date());
        saveEntity.setModifyDate(new Date());
        saveEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
        dataSendJobTimeService.saveLastSendTime(saveEntity);
    }

    @Transactional
    public void sendData(ArrayList<DeliveryCityLevelMappingEntity> datas, boolean isFailureData) {

        ArrayList<DeliveryCityLevelMappingInfo> priceCityData = new ArrayList<DeliveryCityLevelMappingInfo>(MAX_COUNT);

        for (int i = 0; i < datas.size(); i++) {
            //将查询出来的价格城市转换成需要发送到MQ队列的对象
            DeliveryCityLevelMappingEntity entity = datas.get(i);
            DeliveryCityLevelMappingInfo info = new DeliveryCityLevelMappingInfo();
            info.setProvinceCode(entity.getProvinceCode());
            info.setCityCode(entity.getCityCode());
            info.setAreaCode(entity.getAreaCode());
            info.setCityType(entity.getCityType());
            info.setActive(entity.getActive());
            info.setRemark(entity.getRemark());
            priceCityData.add(info);
            if ((i + 1) % MAX_COUNT == 0 || i == datas.size()- 1) {
                try {
                    activeMQProducerService.postDeliveryCityLevelMappingDataToQueue(priceCityData);
                    if (isFailureData) {
                        //如果是失败数据发送成功了更新失败表状态为已发送
                        for (int j = 0; j < priceCityData.size(); j++) {
                            DeliveryCityLevelMappingEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
                            DeliveryCityLevelMappingEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
                            saveFailureEntity.setType(DataSendJobConstant.JOB_CODE_PRICE_CITY);
                            dataSendJobFailureService.saveFailureData(saveFailureEntity);
                        }
                    }
                    //更新失败重发次数
                    else {
                        for (int j = 0; j < priceCityData.size(); j++) {
                            DeliveryCityLevelMappingEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
                            DataSendJobFailureEntity updateFailureTimesEntity = new DataSendJobFailureEntity();
                            updateFailureTimesEntity.setBusinessId(failureEntity.getId());
                            updateFailureTimesEntity.setFailReason(e.getMessage());
                            updateFailureTimesEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
                            updateFailureTimesEntity.setType(DataSendJobConstant.JOB_CODE_PRICE_CITY);
                            dataSendJobFailureService.updateSendFailTimes(updateFailureTimesEntity);
                        }
                    }
                }
                priceCityData.clear();
            }
        }
    }

}
