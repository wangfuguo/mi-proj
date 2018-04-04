package com.hoau.miser.module.api.itf.api.server;


import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;

/**
 * @ClassName:
 * @Description: 价格城市维护
 * @author: 279716
 * @date: 2016/3/9 17:28
 */
public interface ICorpPriceCityService {

    /**
     * @param orgCode 组织编码
     * @return CorpPriceCityEntity
     * @throws
     * @Description: 查询物流标准价格城市映射
     * @author 廖文强
     * @date 2016年06月02日
     */
    public CorpPriceCityEntity queryPriceCity(OrgPositionInfoTyEntity entity, String priceCityType);

}
