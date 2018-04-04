package com.hoau.miser.module.api.facade.server.dao;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import com.hoau.miser.module.api.facade.shared.vo.PriceAgingConditionVo;
import com.hoau.miser.module.api.facade.shared.domain.PriceAgingResultEntity;

public interface PriceAgingDao {

	public List<PriceAgingResultEntity> queryPriceAgingListByParams(
			PriceAgingConditionVo entity, RowBounds rowBounds);
}
