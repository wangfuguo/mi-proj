package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.PricePickupFeeTyEntity;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PricePickupFeeTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 提货费dao
 * @date 2016/6/6 17:35
 */
@Repository
public interface PricePickupFeeTyDao {

    /**
     *
     * @param paramInSide
     * @return
     * @Description: 查询提货费
     * @author 廖文强
     * @date 2016年06月06日
     */
    List<PricePickupFeeTyEntity> queryPricePickupFeeByParamInSide(PriceQueryParamInSide paramInSide);
}
