package com.hoau.miser.module.biz.job.server.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.PriceEventSendEntity;
import com.hoau.miser.module.biz.job.shared.domain.PriceStandardEntity;

/**
 * 
 * ClassName: PriceStandardDao 
 * @Description: 价格dao
 * @author mao.wang@newHoau.com.cn
 * @date 2016年1月21日
 * @version V1.0
 */
@Repository
public interface PriceStandardSendDao {

	/**
	 * 
	 * @Description: mq同步价格数据
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceStandardEntity> 
	 * @throws
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月21日
	 */
	public ArrayList<PriceStandardEntity> queryListByParam(DataSendJobQueryFilterEntity entity);
	public ArrayList<PriceStandardEntity> queryFailureData(DataSendJobFailureEntity entity);
	public ArrayList<PriceStandardEntity> queryListByParamByEvent(PriceEventSendEntity pse);
	
	public ArrayList<PriceEventSendEntity> queryListByParamByEventStandard(PriceEventSendEntity entity);
	public ArrayList<PriceEventSendEntity> queryListByParamByEventFORStandard(PriceEventSendEntity entity);
	public void failureInfoUniqByPriceStandard(DataSendJobFailureEntity queryEnity);
	
}
