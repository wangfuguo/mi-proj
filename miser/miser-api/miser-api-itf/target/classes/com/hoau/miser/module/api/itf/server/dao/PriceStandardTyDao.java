package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardTyEntity;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceStandardTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 查询标准价格
 * @date 2016/6/7 11:04
 */
@Repository
public interface PriceStandardTyDao {
    /**
     * 查询标准价格
     * @Param  [baseTyParam]
     * @Return java.util.List<com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardTyEntity>
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/7 11:09
     * @Version v1
     */
    List<PriceStandardTyEntity> queryPriceStandardTyByParamInSide(PriceQueryParamInSide baseTyParam);
}
