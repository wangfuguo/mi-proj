package com.hoau.miser.module.biz.job.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.PricePackageFeePcSendEntity;

@Repository
public interface PricePackageFeePcSendEntityDao {    
    
    List<PricePackageFeePcSendEntity> searchPackageFeePcFailure(DataSendJobFailureEntity dataSendJobFailureEntity);
    
    List<PricePackageFeePcSendEntity> searchPackageFeePcNew(DataSendJobQueryFilterEntity dataSendJobQueryFilterEntity);
 }