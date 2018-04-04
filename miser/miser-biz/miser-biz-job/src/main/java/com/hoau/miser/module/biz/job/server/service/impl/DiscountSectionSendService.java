package com.hoau.miser.module.biz.job.server.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.eai.pc.vo.DiscountDetailInfo;
import com.hoau.eai.pc.vo.DiscountSectionInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.DiscountSectionSendDao;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.server.service.IDiscountSectionSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.job.shared.domain.DiscountDetailEntity;
import com.hoau.miser.module.biz.job.shared.domain.DiscountSectionSendEntity;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;


/**
 * 发送优惠分段到MQ
 * ClassName: DiscountSectionSendService
 *
 * @author 刘海飞
 * @version V1.0
 * @date 2016年1月22日
 */
@Service
public class DiscountSectionSendService implements IDiscountSectionSendService {

    @Resource
    private DiscountSectionSendDao discountSectionSendDao;
    @Resource
    private IActiveMQProducerService activeMQProducerService;
    @Resource
    private IDataSendJobTimeService dataSendJobTimeService;
    @Resource
    private IDataSendJobFailureService dataSendJobFailureService;

    private static int MAX_COUNT = 100;

    static {
        try {
            Properties properties = ConfigFileLoadUtil.getPropertiesForClasspath("config.properties");
            String maxCountStr = properties.getProperty("SENDMQ.DISCOUNTSECTION.MAXCOUNT", "100");
            MAX_COUNT = StringUtils.toInt(maxCountStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void sendDiscountSection() {
        //记录本次发送时间
        Date thisTime = new Date();
        //查询出上次发送失败的记录
        DataSendJobFailureEntity queryFailureFilterEntity = new DataSendJobFailureEntity();
        queryFailureFilterEntity.setActive(MiserConstants.ACTIVE);
        queryFailureFilterEntity.setType("DISCOUNT_SECTION");
        ArrayList<DiscountSectionSendEntity> failureDatas = discountSectionSendDao.queryFailureData(queryFailureFilterEntity);
        //发送上次失败数据
        sendData(failureDatas, true);

        //查询出本次需要新发送的价格城市
        DataSendJobQueryFilterEntity queryNewDataFitlerEntity = new DataSendJobQueryFilterEntity();
        //查询出上次发送时间
        DataSendJobTimeEntity resultEntity = dataSendJobTimeService.getLastSendTime(DataSendJobConstant.JOB_CODE_DISCOUNT_SECTION);
        if (resultEntity != null) {
            queryNewDataFitlerEntity.setStartTime(resultEntity.getLastSendTime());
        }
        queryNewDataFitlerEntity.setEndTime(thisTime);
        ArrayList<DiscountSectionSendEntity> newDatas = discountSectionSendDao.queryListByParam(queryNewDataFitlerEntity);
        //发送本次新数据
        sendData(newDatas, false);

        //更新上次发送时间
        DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
        saveEntity.setId(UUIDUtil.getUUID());
        saveEntity.setJobCode(DataSendJobConstant.JOB_CODE_DISCOUNT_SECTION);
        saveEntity.setJobName(DataSendJobConstant.getNameByCode(DataSendJobConstant.JOB_CODE_DISCOUNT_SECTION));
        saveEntity.setLastSendTime(thisTime);
        saveEntity.setActive(MiserConstants.ACTIVE);
        saveEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
        saveEntity.setCreateDate(new Date());
        saveEntity.setModifyDate(new Date());
        saveEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
        dataSendJobTimeService.saveLastSendTime(saveEntity);

    }


    /**
     * @param @param datas 需要发送的数据
     * @param @param isFailureData  是否是失败重发，失败重发的数据再发送成功后需要修改为已发送，不是失败重发的数据在发送失败后需要插入到失败表中
     * @return void
     * @throws
     * @Description: 发送数据到MQ
     * @author 刘海飞
     * @date 2016年1月26日
     */
    @Transactional
    public void sendData(ArrayList<DiscountSectionSendEntity> datas, boolean isFailureData) {
        //查询出本次需要优惠分段
        ArrayList<DiscountSectionInfo> discountSectionInfoList = new ArrayList<DiscountSectionInfo>(MAX_COUNT);
        //将PMS中的数据转换为DC中的数据结构发送到MQ
        for (int i = 0; i < datas.size(); i++) {
            DiscountSectionSendEntity discountSectionSendEntity = datas.get(i);
            /***---------- 设置主表数据 -----------**/
            DiscountSectionInfo discountSectionInfo = new DiscountSectionInfo();
            discountSectionInfo.setId(discountSectionSendEntity.getCode());
            if ("FREIGHT".equals(discountSectionSendEntity.getSectionedItem())) {
                discountSectionInfo.setType("DS");
            } else if ("FUEL".equals(discountSectionSendEntity.getSectionedItem())) {
                discountSectionInfo.setType("FE");
            } else {
                discountSectionInfo.setType("AA");//优惠分段类型
            }
            discountSectionInfo.setName(discountSectionSendEntity.getName());
            if ("10000000000000000001".equals(discountSectionSendEntity.getSuitProduct())) {
                discountSectionInfo.setSuitProduct("D");//定日达
            } else if ("30000000000000000001".equals(discountSectionSendEntity.getSuitProduct())) {
                discountSectionInfo.setSuitProduct("L");//经济快运
            } else {
                discountSectionInfo.setSuitProduct("A");//全部
            }
            discountSectionInfo.setRemark(discountSectionSendEntity.getRemark());
            discountSectionInfo.setRecStatus(new Integer(MiserConstants.ACTIVE.equals(discountSectionSendEntity.getActive()) ? 0 : 1));

            /***---------- 设置明细数据 -----------**/
            List<DiscountDetailEntity> discountDetailEntityList = discountSectionSendEntity.getDiscountDetailEntity();
            ArrayList<DiscountDetailInfo> discountDetailList = new ArrayList<DiscountDetailInfo>();
            for (DiscountDetailEntity discountDetailEntity : discountDetailEntityList) {
                DiscountDetailInfo discountDetailInfo = new DiscountDetailInfo();
                discountDetailInfo.setBeginSection(Double.parseDouble(discountDetailEntity.getStartValue()));
                discountDetailInfo.setEndSection(Double.parseDouble(discountDetailEntity.getEndValue()));
                if ("WEIGHT".equals(discountDetailEntity.getSectionAccdoingItem())) {
                    discountDetailInfo.setLightWeightType("Z");
                } else if ("VOLUMN".equals(discountDetailEntity.getSectionAccdoingItem())) {
                    discountDetailInfo.setLightWeightType("Q");
                } else if ("PIECE".equals(discountDetailEntity.getSectionAccdoingItem())) {
                    discountDetailInfo.setLightWeightType("P");
                } else if ("PACKAGE".equals(discountDetailEntity.getSectionAccdoingItem())) {
                    discountDetailInfo.setLightWeightType("A");
                } else if ("INSURANCE".equals(discountDetailEntity.getSectionAccdoingItem())) {
                    discountDetailInfo.setLightWeightType("I");
                } else if ("COLLECTION".equals(discountDetailEntity.getSectionAccdoingItem())) {
                    discountDetailInfo.setLightWeightType("C");
                }
                
                if ("FUEL".equals(discountSectionSendEntity.getSectionedItem())) {
                    discountDetailInfo.setFuelFees(Double.parseDouble(discountDetailEntity.getPrice()));
                }
//                discountDetailInfo.setDiscountAmount(Double.parseDouble(discountDetailEntity.getPrice()));
                discountDetailInfo.setSectionDiscount(Double.parseDouble(discountDetailEntity.getPrice()));
//                discountDetailInfo.setSectionUnitPrice(Double.parseDouble(discountDetailEntity.getPrice()));
                if ("Y".equals(discountDetailEntity.getCalcAlone())) {
                    discountDetailInfo.setReduceRadix(Double.parseDouble(discountDetailEntity.getMinPrice()));
                } else {
                    discountDetailInfo.setReduceRadix(0.0);
                }
                discountDetailList.add(discountDetailInfo);
            }

            discountSectionInfo.setDiscountDetailInfos(discountDetailList);
            discountSectionInfoList.add(discountSectionInfo);
            if ((i + 1) % MAX_COUNT == 0 || (i + 1) == datas.size()) {
                try {
                    activeMQProducerService.postDiscountSectionDataToQueue(discountSectionInfoList);
                    if (isFailureData) {
                        //如果是失败数据发送成功了更新失败表状态为已发送
                        for (int j = 0; j < discountSectionInfoList.size(); j++) {
                            DiscountSectionSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
                        for (int j = 0; j < discountSectionInfoList.size(); j++) {
                            DiscountSectionSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
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
                            saveFailureEntity.setType(DataSendJobConstant.JOB_CODE_DISCOUNT_SECTION);
                            dataSendJobFailureService.saveFailureData(saveFailureEntity);
                        }
                    }
                }
                discountSectionInfoList.clear();
            }
        }

    }


    @Override
    @Transactional
    public void sendDiscountSectionByCode(String code) {
        // TODO Auto-generated method stub
        ArrayList<DiscountSectionInfo> discountSectionInfoList = new ArrayList<DiscountSectionInfo>();
   	 	ArrayList<DiscountDetailInfo> discountDetailList = new ArrayList<DiscountDetailInfo>();

        //1.查询出该条优惠分段
        DiscountSectionSendEntity discountSectionSendEntity = discountSectionSendDao.queryDataByCode(code);
        //1.优惠分段主
        DiscountSectionInfo discountSectionInfo = new DiscountSectionInfo();
        //2.优惠分段明细
        DiscountDetailInfo discountDetailInfo = new DiscountDetailInfo();
        List<DiscountDetailEntity> discountDetailEntityList = discountSectionSendEntity.getDiscountDetailEntity();
        discountSectionInfo.setId(discountSectionSendEntity.getCode());
        if ("FREIGHT".equals(discountSectionSendEntity.getSectionedItem())) {
            discountSectionInfo.setType("DS");
        } else if ("FUEL".equals(discountSectionSendEntity.getSectionedItem())) {
            discountSectionInfo.setType("FE");
        } else {
            discountSectionInfo.setType(discountSectionSendEntity.getSectionedItem());//优惠分段类型
        }
        discountSectionInfo.setName(discountSectionSendEntity.getName());
        discountSectionInfo.setSuitProduct(discountSectionSendEntity.getSuitProduct());//运输类型
        discountSectionInfo.setRemark(discountSectionSendEntity.getRemark());
        discountSectionInfo.setRecStatus(new Integer(MiserConstants.ACTIVE.equals(discountSectionSendEntity.getActive()) ? 0 : 1));
        for (DiscountDetailEntity discountDetailEntity : discountDetailEntityList) {
        	
            discountDetailInfo.setBeginSection(Double.parseDouble(discountDetailEntity.getStartValue()));
            discountDetailInfo.setEndSection(Double.parseDouble(discountDetailEntity.getEndValue()));
            if ("WEIGHT".equals(discountDetailEntity.getSectionAccdoingItem())) {
                discountDetailInfo.setLightWeightType("Z");
            } else if ("VOLUMN".equals(discountDetailEntity.getSectionAccdoingItem())) {
                discountDetailInfo.setLightWeightType("Q");
            } else if ("PIECE".equals(discountDetailEntity.getSectionAccdoingItem())) {
                discountDetailInfo.setLightWeightType("P");
            } else if ("PACKAGE".equals(discountDetailEntity.getSectionAccdoingItem())) {
                discountDetailInfo.setLightWeightType("A");
            } else if ("INSURANCE".equals(discountDetailEntity.getSectionAccdoingItem())) {
                discountDetailInfo.setLightWeightType("I");
            } else if ("COLLECTION".equals(discountDetailEntity.getSectionAccdoingItem())) {
                discountDetailInfo.setLightWeightType("C");
            }

            if ("FUEL".equals(discountSectionSendEntity.getSectionedItem())) {
                discountDetailInfo.setFuelFees(Double.parseDouble(discountDetailEntity.getPrice()));
            }
//            discountDetailInfo.setDiscountAmount(Double.parseDouble(discountDetailEntity.getPrice()));
            discountDetailInfo.setSectionDiscount(Double.parseDouble(discountDetailEntity.getPrice()));
//            discountDetailInfo.setSectionUnitPrice(Double.parseDouble(discountDetailEntity.getPrice()));
            if ("Y".equals(discountDetailEntity.getCalcAlone())) {
                discountDetailInfo.setReduceRadix(Double.parseDouble(discountDetailEntity.getMinPrice()));
            } else {
                discountDetailInfo.setReduceRadix(0.0);
            }
            discountDetailList.add(discountDetailInfo);
        }
        discountSectionInfo.setDiscountDetailInfos(discountDetailList);
        discountSectionInfoList.add(discountSectionInfo);
        activeMQProducerService.postDiscountSectionDataToQueue(discountSectionInfoList);

    }
}
