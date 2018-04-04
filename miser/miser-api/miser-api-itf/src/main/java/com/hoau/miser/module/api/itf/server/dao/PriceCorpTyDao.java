package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpTyEntity;
import com.hoau.miser.module.api.itf.server.param.PriceQueryParamInSide;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceCorpTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 网点价格dao
 * @date 2016/6/7 11:05
 */
@Repository
public interface PriceCorpTyDao {
    /**
     * 查询网点价格
     * @Param  [baseTyParam]
     * @Return java.util.List<com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpTyEntity>
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/7 11:08
     * @Version v1
     */
    List<PriceCorpTyEntity> queryPriceCorpTyByParamInSide(PriceQueryParamInSide baseTyParam);
}
