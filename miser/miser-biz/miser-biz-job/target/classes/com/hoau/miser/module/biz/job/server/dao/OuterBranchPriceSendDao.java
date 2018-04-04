package com.hoau.miser.module.biz.job.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.OuterBranchPriceSendEntity;

/**
 * 偏线外发配置 ClassName: OuterBranchPriceSendDao
 * 
 * @author 赵金义
 * @date 2016年3月17日
 * @version V1.0
 */
@Repository
public interface OuterBranchPriceSendDao {

	List<OuterBranchPriceSendEntity> searchOuterBranchPriceSendFailure(
			DataSendJobFailureEntity dataSendJobFailureEntity);

	List<OuterBranchPriceSendEntity> searchOuterBranchPriceSendNew(
			DataSendJobQueryFilterEntity dataSendJobFailureEntity);
}
