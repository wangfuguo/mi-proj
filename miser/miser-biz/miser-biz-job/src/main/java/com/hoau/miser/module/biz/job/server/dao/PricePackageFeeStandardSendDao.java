package com.hoau.miser.module.biz.job.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.PricePackageFeeStandardEntity;

/**
 * 包装费
 * ClassName: PricePackageFeeStandardDao 
 * @author 李玮琰
 * @date 2016年2月26日
 * @version V1.0
 */
@Repository
public interface PricePackageFeeStandardSendDao {   
    
    List<PricePackageFeeStandardEntity> searchPackageFeeStandFailure(DataSendJobFailureEntity dataSendJobFailureEntity);
    
    List<PricePackageFeeStandardEntity> searchPackageFeeStandNew(DataSendJobQueryFilterEntity dataSendJobFailureEntity);
}