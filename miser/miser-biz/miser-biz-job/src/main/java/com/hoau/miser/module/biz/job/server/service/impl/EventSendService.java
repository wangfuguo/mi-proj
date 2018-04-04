package com.hoau.miser.module.biz.job.server.service.impl;

import com.hoau.eai.pc.vo.*;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.EventSendDao;
import com.hoau.miser.module.biz.job.server.dao.PriceCitySendDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IEventSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.*;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * @ClassName:
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author: Chenyl yulin.chen@hoau.net
 * @date: 2016/3/17 22:41
 */
@Service
public class EventSendService implements IEventSendService {

    @Resource
    private EventSendDao eventSendDao;

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

    static{
        try {
            Properties properties = ConfigFileLoadUtil.getPropertiesForClasspath("config.properties");
            String maxCountStr = properties.getProperty("SENDMQ.EVENT.MAXCOUNT", "100");
            MAX_COUNT = StringUtils.toInt(maxCountStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 发送活动信息
     */
    @Override
    public void sendEvent() {
        //记录本次发送时间
        Date thisTime = new Date();
        //查询出上次发送时间
        DataSendJobTimeEntity resultEntity = dataSendJobTimeService.getLastSendTime(DataSendJobConstant.JOB_BIZ_SEND_EVENT);
        Date lastTime = null;
        if (resultEntity != null) {
            lastTime = resultEntity.getLastSendTime();
        }

        //更新失败表中存在的和本次需要发送记录重复的数据
        DataSendJobFailureEntity updateFailureRepeatEntity = new DataSendJobFailureEntity();
        updateFailureRepeatEntity.setActive(MiserConstants.ACTIVE);
        updateFailureRepeatEntity.setType(DataSendJobConstant.JOB_BIZ_SEND_EVENT);
        updateFailureRepeatEntity.setLastSendTime(lastTime);
        updateFailureRepeatEntity.setSendTime(thisTime);
        updateFailureRepeatEntity.setRemark("存在新纪录，取消失败记录的发送");
        eventSendDao.updateFailureRepeatData(updateFailureRepeatEntity);

        //查询出上次发送失败的记录
        DataSendJobFailureEntity queryFailureFilterEntity = new DataSendJobFailureEntity();
        queryFailureFilterEntity.setActive(MiserConstants.ACTIVE);
        queryFailureFilterEntity.setType(DataSendJobConstant.JOB_BIZ_SEND_EVENT);
        queryFailureFilterEntity.setRetryTimes(DataSendJobConstant.MAX_FAIL_RETRY_TIMES);
        ArrayList<PriceEventSendEntity> failureDatas = eventSendDao.queryFailureData(queryFailureFilterEntity);
        //发送上次失败数据
        sendData(failureDatas, true);

        //查询出本次需要新发送的价格城市
        DataSendJobQueryFilterEntity queryNewDataFitlerEntity = new DataSendJobQueryFilterEntity();
        queryNewDataFitlerEntity.setStartTime(lastTime);
        queryNewDataFitlerEntity.setEndTime(thisTime);
        ArrayList<PriceEventSendEntity> newDatas = eventSendDao.queryNeedSendData(queryNewDataFitlerEntity);
        //发送本次新数据
        sendData(newDatas, false);

        //更新上次发送时间
        DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
        saveEntity.setId(UUIDUtil.getUUID());
        saveEntity.setJobCode(DataSendJobConstant.JOB_BIZ_SEND_EVENT);
        saveEntity.setJobName(DataSendJobConstant.getNameByCode(DataSendJobConstant.JOB_BIZ_SEND_EVENT));
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
     * @param datas		需要发送的数据
     * @param isFailureData   是否是失败重发，失败重发的数据再发送成功后需要修改为已发送，不是失败重发的数据在发送失败后需要插入到失败表中
     * @return void
     * @author yulin.chen@newhoau.com.cn
     * @date 2016年1月25日
     */
    @Transactional
    public void sendData(ArrayList<PriceEventSendEntity> datas, boolean isFailureData) {

        ArrayList<PriceEvent> priceEventData = new ArrayList<PriceEvent>(MAX_COUNT);

        for (int i = 0; i < datas.size(); i++) {
            //将查询出来的价格城市转换成需要发送到MQ队列的对象
            PriceEventSendEntity entity = datas.get(i);
            PriceEvent event = convertEventFromPMSObject(entity);
            //活动为空继续
            if (event == null) {
                continue;
            }
            //根据活动获取活动明细
            ArrayList<PriceEventDiscountSubSendEntity> priceEventDiscountSubSendEntities = eventSendDao.queryEventDiscount(entity);
            if (priceEventDiscountSubSendEntities != null && priceEventDiscountSubSendEntities.size() > 0) {
                ArrayList<PriceEventDiscountDetail> priceEventDiscountDetails = new ArrayList<PriceEventDiscountDetail>(priceEventDiscountSubSendEntities.size());
                for (int x = 0; x < priceEventDiscountSubSendEntities.size(); x++) {
                    PriceEventDiscountDetail discountDetail = convertEventDiscountFromPMSObject(priceEventDiscountSubSendEntities.get(x));
                    if (discountDetail != null) {
                        priceEventDiscountDetails.add(discountDetail);
                    }
                }
                event.setPriceEventDiscountSubs(priceEventDiscountDetails);
            }
            //获取渠道范围明细
            ArrayList<PriceEventOrderchannelSubSendEntity> priceEventOrderchannelSubSendEntities = eventSendDao.queryEventOrderChannelCondition(entity);
            if (priceEventOrderchannelSubSendEntities != null && priceEventOrderchannelSubSendEntities.size() > 0) {
                ArrayList<PriceEventOrderchannelCondition> priceEventOrderchannelConditions = new ArrayList<PriceEventOrderchannelCondition>(priceEventOrderchannelSubSendEntities.size());
                for (int x = 0; x < priceEventOrderchannelSubSendEntities.size(); x++) {
                    PriceEventOrderchannelCondition channelCondition = convertEventOrderChannelConditionFromPMSObject(priceEventOrderchannelSubSendEntities.get(x));
                    if (channelCondition != null) {
                        priceEventOrderchannelConditions.add(channelCondition);
                    }
                }
                event.setPriceEventOrderchannelSubs(priceEventOrderchannelConditions);
            }
            //获取门店范围明细
            ArrayList<PriceEventCorpSubSendEntity> priceEventCorpSubSendEntities = eventSendDao.queryEventCorpCondition(entity);
            if (priceEventCorpSubSendEntities != null && priceEventCorpSubSendEntities.size() > 0) {
                ArrayList<PriceEventCorpCondition> priceEventCorpConditions = new ArrayList<PriceEventCorpCondition>(priceEventCorpSubSendEntities.size());
                for (int x = 0; x < priceEventCorpSubSendEntities.size(); x++) {
                    PriceEventCorpCondition corpCondition = convertEventCorpConditionFromPMSObject(priceEventCorpSubSendEntities.get(x));
                    if (corpCondition != null) {
                        priceEventCorpConditions.add(corpCondition);
                    }
                }
                event.setPriceEventCorpSubs(priceEventCorpConditions);
            }
            //获取线路范围明细
            ArrayList<PriceEventRouteSubSendEntity> priceEventRouteSubSendEntities = eventSendDao.queryEventRouteCondition(entity);
            if (priceEventRouteSubSendEntities != null && priceEventRouteSubSendEntities.size() > 0) {
                ArrayList<PriceEventRouteCondition> priceEventRouteConditions = new ArrayList<PriceEventRouteCondition>(priceEventRouteSubSendEntities.size());
                for (int x = 0; x < priceEventRouteSubSendEntities.size(); x++) {
                    PriceEventRouteCondition routeCondition = convertEventRouteConditionFromPMSObject(priceEventRouteSubSendEntities.get(x));
                    if (routeCondition != null) {
                        priceEventRouteConditions.add(routeCondition);
                    }
                }
                event.setPriceEventRouteSubs(priceEventRouteConditions);
            }

            priceEventData.add(event);
            if ((i + 1) % MAX_COUNT == 0 || i == datas.size()- 1) {
                try {
                    activeMQProducerService.postPriceEventDataToQueue(priceEventData);
                    if (isFailureData) {
                        //如果是失败数据发送成功了更新失败表状态为已发送
                        for (int j = 0; j < priceEventData.size(); j++) {
                            PriceEventSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
                        for (int j = 0; j < priceEventData.size(); j++) {
                            PriceEventSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
                            saveFailureEntity.setType(DataSendJobConstant.JOB_BIZ_SEND_EVENT);
                            dataSendJobFailureService.saveFailureData(saveFailureEntity);
                        }
                    }
                    //更新失败重发次数
                    else {
                        for (int j = 0; j < priceEventData.size(); j++) {
                            PriceEventSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
                            DataSendJobFailureEntity updateFailureTimesEntity = new DataSendJobFailureEntity();
                            updateFailureTimesEntity.setBusinessId(failureEntity.getId());
                            updateFailureTimesEntity.setFailReason(e.getMessage());
                            updateFailureTimesEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
                            updateFailureTimesEntity.setType(DataSendJobConstant.JOB_BIZ_SEND_EVENT);
                            dataSendJobFailureService.updateSendFailTimes(updateFailureTimesEntity);
                        }
                    }
                }
                priceEventData.clear();
            }
        }
    }

    /**
     * 将PMS系统活动主表对象转换成迪晨接口需要的对象
     * @param entity
     * @return
     */
    public PriceEvent convertEventFromPMSObject(PriceEventSendEntity entity) {
        if (entity == null) {
            return null;
        }
        PriceEvent event = new PriceEvent();
        event.setId(entity.getId());
        event.setEventCode(entity.getEventCode());
        event.setEventName(entity.getEventName());
        event.setEffectiveTime(entity.getEffectiveTime());
        event.setInvalidTime(entity.getInvalidTime());
        event.setIsForceColse(entity.getIsForceColse());
        event.setActive(entity.getActive());
        event.setRemark(entity.getRemark());
        event.setCreateDate(entity.getCreateDate());
        event.setCreateUser(entity.getCreateUser());
        event.setModifyDate(entity.getModifyDate());
        event.setModifyUser(entity.getModifyUser());
        return event;
    }

    /**
     * 将PMS系统活动明细对象转换成迪晨接口需要的对象
     * @param entity
     * @return
     */
    public PriceEventDiscountDetail convertEventDiscountFromPMSObject(PriceEventDiscountSubSendEntity entity) {
        if (entity == null) {
            return null;
        }
        PriceEventDiscountDetail discountDetail = new PriceEventDiscountDetail();
        discountDetail.setId(entity.getId());
        discountDetail.setEventCode(entity.getEventCode());
        discountDetail.setTransTypeCode(entity.getTransTypeCode());
        discountDetail.setActive(entity.getActive());
        discountDetail.setCreateDate(entity.getCreateDate());
        discountDetail.setCreateUser(entity.getCreateUser());
        discountDetail.setModifyDate(entity.getModifyDate());
        discountDetail.setModifyUser(entity.getModifyUser());
        discountDetail.setFreightSectionCode(entity.getFreightSectionCode());
        discountDetail.setAddSectionCode(entity.getAddSectionCode());
        discountDetail.setFuelSectionCode(entity.getFuelSectionCode());
        discountDetail.setPickupSectionCode(entity.getPickupSectionCode());
        discountDetail.setDeliverySectionCode(entity.getDeliverySectionCode());
        discountDetail.setUpstairSectionCode(entity.getUpstairSectionCode());
        discountDetail.setInsuranceSectionCode(entity.getInsuranceSectionCode());
        discountDetail.setInsuranceRateSectionCode(entity.getInsuranceRateSectionCode());
        discountDetail.setPaperSectionCode(entity.getPaperSectionCode());
        discountDetail.setSmsSectionCode(entity.getSmsSectionCode());
        discountDetail.setCollectionSectionCode(entity.getCollectionSectionCode());
        discountDetail.setCollectionRateSectionCode(entity.getCollectionRateSectionCode());
        discountDetail.setRemark(entity.getRemark());
        return discountDetail;
    }

    /**
     * 将PMS系统活动门店范围对象转换成迪晨接口需要的对象
     * @param entity
     * @return
     */
    public PriceEventCorpCondition convertEventCorpConditionFromPMSObject (PriceEventCorpSubSendEntity entity) {
        if (entity == null) {
            return null;
        }
        PriceEventCorpCondition corpCondition = new PriceEventCorpCondition();
        corpCondition.setId(entity.getId());
        corpCondition.setEventCode(entity.getEventCode());
        corpCondition.setActive(entity.getActive());
        corpCondition.setRemark(entity.getRemark());
        corpCondition.setCreateDate(entity.getCreateDate());
        corpCondition.setCreateUser(entity.getCreateUser());
        corpCondition.setModifyDate(entity.getModifyDate());
        corpCondition.setModifyUser(entity.getModifyUser());
        corpCondition.setCorpType(entity.getCorpType());
        corpCondition.setOrgCode(entity.getOrgCode());
        return corpCondition;
    }

    /**
     * 将PMS系统活动渠道范围对象转换成迪晨接口需要的对象
     * @param entity
     * @return
     */
    public PriceEventOrderchannelCondition convertEventOrderChannelConditionFromPMSObject (PriceEventOrderchannelSubSendEntity entity) {
        if (entity == null) {
            return null;
        }
        PriceEventOrderchannelCondition orderchannelCondition = new PriceEventOrderchannelCondition();
        orderchannelCondition.setId(entity.getId());
        orderchannelCondition.setEventCode(entity.getEventCode());
        orderchannelCondition.setActive(entity.getActive());
        orderchannelCondition.setRemark(entity.getRemark());
        orderchannelCondition.setCreateDate(entity.getCreateDate());
        orderchannelCondition.setCreateUser(entity.getCreateUser());
        orderchannelCondition.setModifyDate(entity.getModifyDate());
        orderchannelCondition.setModifyUser(entity.getModifyUser());
        orderchannelCondition.setChannelCode(entity.getChannelCode());
        return orderchannelCondition;
    }

    /**
     * 将PMS系统活动渠道范围对象转换成迪晨接口需要的对象
     * @param entity
     * @return
     */
    public PriceEventRouteCondition convertEventRouteConditionFromPMSObject (PriceEventRouteSubSendEntity entity) {
        if (entity == null) {
            return null;
        }
        PriceEventRouteCondition routeCondition = new PriceEventRouteCondition();
        routeCondition.setId(entity.getId());
        routeCondition.setEventCode(entity.getEventCode());
        routeCondition.setActive(entity.getActive());
        routeCondition.setRemark(entity.getRemark());
        routeCondition.setCreateDate(entity.getCreateDate());
        routeCondition.setCreateUser(entity.getCreateUser());
        routeCondition.setModifyDate(entity.getModifyDate());
        routeCondition.setModifyUser(entity.getModifyUser());
        routeCondition.setSendPriceCity(entity.getSendPriceCity());
        routeCondition.setArrivalPriceCity(entity.getArrivalPriceCity());
        return routeCondition;
    }

    /**
     * 将PMS系统活动客户范围对象转换成迪晨接口需要的对象
     * @param entity
     * @return
     */
    public PriceEventCustomerCondition convertEventCustomerConditionFromPMSObject (PriceEventCustomerSubSendEntity entity) {
        if (entity == null) {
            return null;
        }
        PriceEventCustomerCondition customerCondition = new PriceEventCustomerCondition();
        customerCondition.setId(entity.getId());
        customerCondition.setEventCode(entity.getEventCode());
        customerCondition.setActive(entity.getActive());
        customerCondition.setRemark(entity.getRemark());
        customerCondition.setCreateDate(entity.getCreateDate());
        customerCondition.setCreateUser(entity.getCreateUser());
        customerCondition.setModifyDate(entity.getModifyDate());
        customerCondition.setModifyUser(entity.getModifyUser());
        customerCondition.setCustomerCode(entity.getCustomerCode());
        customerCondition.setCustomerName(entity.getCustomerName());
        return customerCondition;
    }
}
