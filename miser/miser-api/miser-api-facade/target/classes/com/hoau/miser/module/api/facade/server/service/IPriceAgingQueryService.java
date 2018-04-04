package com.hoau.miser.module.api.facade.server.service;

import java.util.List;

import com.hoau.miser.module.api.facade.shared.vo.PriceAgingConditionVo;
import com.hoau.miser.module.api.facade.shared.domain.PriceAgingResultEntity;
import com.hoau.miser.module.api.facade.shared.vo.BaseResponseVo;

public interface IPriceAgingQueryService {

	public BaseResponseVo<Object> queryPriceAgingListByParams(
			PriceAgingConditionVo entity);
}
