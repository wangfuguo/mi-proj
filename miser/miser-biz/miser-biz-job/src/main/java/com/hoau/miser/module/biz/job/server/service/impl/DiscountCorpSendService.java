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

import com.hoau.eai.pc.vo.DisctionInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.DiscountCorpSendDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IDiscountCorpSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.job.shared.domain.DiscountCorpSendEntity;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;


/**
 * ClassName: PriceCorpSendService
 *
 * @author mao.wang@newHoau.com.cn
 * @version V1.0
 * @Description: TODO(价格队列)
 * @date 2016年1月21日
 */
@Service
public class DiscountCorpSendService implements IDiscountCorpSendService {

    @Resource
    private DiscountCorpSendDao discountCorpSendDao;
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
    public void sendDiscountCorp() {
        //记录本次发送时间
        Date thisTime = new Date();
      //查询出本次需要新发送的标准附加费
        DataSendJobQueryFilterEntity queryNewDataFitlerEntity = new DataSendJobQueryFilterEntity();
        //查询出上次发送时间
        DataSendJobTimeEntity resultEntity = dataSendJobTimeService.getLastSendTime(DataSendJobConstant.JOB_CODE_DISCOUNT_CORP);
        if (resultEntity != null) {
            queryNewDataFitlerEntity.setStartTime(resultEntity.getLastSendTime());
        }
        queryNewDataFitlerEntity.setEndTime(thisTime);
        //查询出上次发送失败的记录
        DataSendJobFailureEntity queryFailureFilterEntity = new DataSendJobFailureEntity();
        queryFailureFilterEntity.setActive(MiserConstants.ACTIVE);
        queryFailureFilterEntity.setType(DataSendJobConstant.JOB_CODE_DISCOUNT_CORP);
        
        queryFailureFilterEntity.setSendTime(thisTime);
        if (resultEntity != null) {
		queryFailureFilterEntity.setLastSendTime(resultEntity.getLastSendTime());
        }
        //取消重复数据发送
        discountCorpSendDao.failureInfoUniqByDiscountCorp(queryFailureFilterEntity);
        queryFailureFilterEntity.setRetryTimes(DataSendJobConstant.MAX_FAIL_RETRY_TIMES);
        ArrayList<DiscountCorpSendEntity> failureDatas = discountCorpSendDao.queryFailureData(queryFailureFilterEntity);
        //发送上次失败数据
        sendData(failureDatas, true);

        ArrayList<DiscountCorpSendEntity> newDatas = discountCorpSendDao.queryListByParam(queryNewDataFitlerEntity);
        //发送本次新数据
        sendData(newDatas, false);

        //更新上次发送时间
        DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
        saveEntity.setId(UUIDUtil.getUUID());
        saveEntity.setJobCode(DataSendJobConstant.JOB_CODE_DISCOUNT_CORP);
        saveEntity.setJobName(DataSendJobConstant.getNameByCode(DataSendJobConstant.JOB_CODE_DISCOUNT_CORP));
        saveEntity.setLastSendTime(thisTime);
        saveEntity.setActive(MiserConstants.ACTIVE);
        saveEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
        saveEntity.setCreateDate(new Date());
        saveEntity.setModifyDate(new Date());
        saveEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
        dataSendJobTimeService.saveLastSendTime(saveEntity);


//		resultEntity = dataSendJobTimeService.getLastSendTime(DataSendJobConstant.JOB_CODE_DISCOUNT_CORP);
//		//发送活动相关网点价格
//		PriceEventSendEntity entity1=new PriceEventSendEntity();
//		entity1.setStartTime(resultEntity.getLastSendTime());
//		entity1.setEndTime(thisTime);
//		ArrayList<PriceEventSendEntity> PriceEventSendEntityList = discountCorpSendDao.queryListByParamByEventFORDiscountCorp(entity1);
//		for(PriceEventSendEntity pese:PriceEventSendEntityList){
//			List<PriceEventDiscountSubSendEntity> preseList=pese.getPriceEventDiscountSubs();
//			List<PriceEventCorpSubSendEntity> pecssl=pese.getPriceEventCorpSubs();
//			for(PriceEventCorpSubSendEntity pecsse:pecssl){
//					for(PriceEventDiscountSubSendEntity peds:preseList){
//						//获取所有匹配的标准价格
//						PriceEventSendEntity pesenew=new PriceEventSendEntity();
//						pesenew.setTransTypeCode(peds.getTransTypeCode());
//						pesenew.setCorpCode(pecsse.getOrgCode());
//						//queryListByParam获取到标准价格
//						ArrayList<DiscountCorpSendEntity> pseList=discountCorpSendDao.queryListByParamByEvent(pesenew);
//						sendData(pseList,false);
//					}
//			}
//			
//		}

    }

    @Transactional
    public void sendData(ArrayList<DiscountCorpSendEntity> datas, boolean isFailureData) {

        ArrayList<DisctionInfo> priceCityData = new ArrayList<DisctionInfo>(MAX_COUNT);
        Map<String, DisctionInfo> map = new HashMap<String, DisctionInfo>();
        for (int i = 0; i < datas.size(); i++) {
            //将查询出来的标准附加费转换成需要发送到MQ队列的对象
            DiscountCorpSendEntity entity = datas.get(i);
            DisctionInfo pci = new DisctionInfo();

//			//获取相关活动
//			PriceEventSendEntity pese=new PriceEventSendEntity();
//			pese.setTransTypeCode(entity.getTransTypeCode());
//			pese.setCorpCode(entity.getOrgCode());
//			//获取相关活动信息
//			ArrayList<PriceEventSendEntity> PriceEventSendList=discountCorpSendDao.queryListByParamByEventDiscountCorp(pese);
//			ArrayList<String> strFreightSectionCode=new ArrayList<String>();
//			for(PriceEventSendEntity psee:PriceEventSendList){
//			//	psee.getPriceEventDiscountSubs()
//				List<PriceEventDiscountSubSendEntity> PEDSList=psee.getPriceEventDiscountSubs();
//				for(PriceEventDiscountSubSendEntity pes:PEDSList){
//					if (!StringUtils.isEmptyWithBlock(pes.getFreightSectionCode())) {
//						strFreightSectionCode.add(pes.getFreightSectionCode());
//					}
//				}
//			}
//			PriceSectionEntity priceSectionEntity=null;
//			//融合运费分段
//			strFreightSectionCode.add(entity.getFreightSectionCode());
//			priceSectionEntity=priceSectionService.reuniteSectionsList(strFreightSectionCode);
//			if(null!=priceSectionEntity){
//				pci.setSectionId(priceSectionEntity.getCode());
//			}
            pci.setSectionId(entity.getFreightSectionCode());
            pci.setType("N");
            pci.setTransportType(entity.getTransTypeCode());
            pci.setStandardNo(entity.getLogistCode());
            //运费
            pci.setEffectiveDate(entity.getEffectiveTime());//生效日期
            pci.setInvalidDate(entity.getInvalidTime());//失效日期
            pci.setRemark(entity.getRemark());
            pci.setRecStatus(entity.getState().equals("EFFECTIVE") ? 0 : 1);
            //如果相同唯一主键的数据，则不同步
            boolean flag = true;
            String onlyCode = entity.getTransTypeCode() + "_" + entity.getOrgCode();
            DisctionInfo a = map.get(onlyCode);
            if (a != null) {
                if (entity.getState().equals("EFFECTIVE")) {
                    priceCityData.remove(a);
                    map.remove(onlyCode);
                } else {
                    flag = false;
                }
            } else {
                if (!entity.getState().equals("EFFECTIVE")) {
                    map.put(onlyCode, pci);
                    if (i == datas.size() - 1 && i != 0) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                priceCityData.add(pci);
            }
            if ((i + 1) % MAX_COUNT == 0 || i == datas.size() - 1) {
                try {
                    activeMQProducerService.postDiscountDataToQueue(priceCityData);
                    if (isFailureData) {
                        //如果是失败数据发送成功了更新失败表状态为已发送
                        for (int j = 0; j < priceCityData.size(); j++) {
                            DiscountCorpSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
                            DiscountCorpSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
                            saveFailureEntity.setType(DataSendJobConstant.JOB_CODE_DISCOUNT_CORP);
                            dataSendJobFailureService.saveFailureData(saveFailureEntity);
                        }
                    } else {
                        for (int j = 0; j < priceCityData.size(); j++) {
                            DiscountCorpSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
                            DataSendJobFailureEntity updateFailureTimesEntity = new DataSendJobFailureEntity();
                            updateFailureTimesEntity.setBusinessId(failureEntity.getId());
                            updateFailureTimesEntity.setFailReason(e.getMessage());
                            updateFailureTimesEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
                            updateFailureTimesEntity.setType(DataSendJobConstant.JOB_CODE_DISCOUNT_CORP);
                            dataSendJobFailureService.updateSendFailTimes(updateFailureTimesEntity);
                        }
                    }
                }
                priceCityData.clear();
            }
        }
    }

}
