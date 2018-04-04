package com.hoau.miser.module.biz.job.server.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.DiscountCorpSendEntity;
import com.hoau.miser.module.biz.job.shared.domain.PriceEventSendEntity;
@Repository
public interface DiscountCorpSendDao {
	public ArrayList<DiscountCorpSendEntity> queryListByParam(DataSendJobQueryFilterEntity entity);
	public ArrayList<DiscountCorpSendEntity> queryFailureData(DataSendJobFailureEntity entity);
	public ArrayList<DiscountCorpSendEntity> queryListByParamByEvent(PriceEventSendEntity pse);
	public ArrayList<PriceEventSendEntity> queryListByParamByEventDiscountCorp(PriceEventSendEntity entity);
	public ArrayList<PriceEventSendEntity> queryListByParamByEventFORDiscountCorp(PriceEventSendEntity entity);
	public void failureInfoUniqByDiscountCorp(DataSendJobFailureEntity queryEnity);
}