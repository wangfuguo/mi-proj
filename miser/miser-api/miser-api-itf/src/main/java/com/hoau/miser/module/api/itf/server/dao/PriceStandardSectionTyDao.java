package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardSectionTyEntity;
import com.hoau.miser.module.api.itf.server.param.PriceSectionQueryParamInSide;
import org.springframework.stereotype.Component;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceStandardSectionTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 分段价格dao
 * @date 2016/7/5 10:52
 */
@Component
public interface PriceStandardSectionTyDao {
   public PriceStandardSectionTyEntity queryByPriceSectionQueryParamInSide(PriceSectionQueryParamInSide paramInSide);
}
