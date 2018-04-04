package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceCustTyEntity;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceCustTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 客户价格dao
 * @date 2016/6/7 11:05
 */
@Repository
public interface PriceCustTyDao {
    /**
     *查询客户价格
     * @Param  [baseTyParam]
     * @Return java.util.List<com.hoau.miser.module.api.itf.api.shared.domain.PriceCustTyEntity>
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/7 11:06
     * @Version v1
     */
    List<PriceCustTyEntity> queryPriceCustTyByBaseTyParamInSide(PriceQueryParamInSide baseTyParam);

}
