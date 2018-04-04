package com.hoau.miser.module.biz.job.server.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.PriceCorpSendEntity;
import com.hoau.miser.module.biz.job.shared.domain.PriceEventSendEntity;
@Repository
public interface PriceCorpSendDao {
	public ArrayList<PriceCorpSendEntity> queryListByParam(DataSendJobQueryFilterEntity entity);
	public ArrayList<PriceCorpSendEntity> queryFailureData(DataSendJobFailureEntity entity);
	public ArrayList<PriceCorpSendEntity> queryListByParamByEvent(PriceEventSendEntity pse);
	public ArrayList<PriceEventSendEntity> queryListByParamByEventCorp(PriceEventSendEntity entity);
	public ArrayList<PriceEventSendEntity> queryListByParamByEventFORCorp(PriceEventSendEntity entity);
	public void failureInfoUniqByPriceCorp(DataSendJobFailureEntity queryEnity);
}