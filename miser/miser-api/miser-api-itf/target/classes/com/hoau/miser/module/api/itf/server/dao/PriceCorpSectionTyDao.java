package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpSectionTyEntity;
import com.hoau.miser.module.api.itf.server.param.PriceSectionQueryParamInSide;
import org.springframework.stereotype.Component;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceCorpSectionTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 分段网点价格Dao
 * @date 2016/7/5 11:30
 */
@Component
public interface PriceCorpSectionTyDao {
    /**
     * 查询分段网点价格
     * @Param  [paramInSide]
     * @Return com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpSectionTyEntity
     * @Throws
     * @Author 廖文强
     * @Date 2016/7/5 11:31
     * @Version v1
     */
    public PriceCorpSectionTyEntity queryPriceSectionQueryParamInSide(PriceSectionQueryParamInSide paramInSide);
}
