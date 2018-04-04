package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceCustSectionTyEntity;
import com.hoau.miser.module.api.itf.server.param.PriceSectionQueryParamInSide;
import org.springframework.stereotype.Component;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceCustSectionTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 分段客户价格
 * @date 2016/7/5 14:49
 */
@Component
public interface PriceCustSectionTyDao {
   public  PriceCustSectionTyEntity queryPriceCustSectionTyByParamInSide(PriceSectionQueryParamInSide paramInSide);
}
