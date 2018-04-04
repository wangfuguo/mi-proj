package com.hoau.miser.module.api.itf.server.dao;

import java.util.Map;

import com.hoau.miser.module.api.itf.api.shared.domain.CustPriceCityEntity;
import org.springframework.stereotype.Repository;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: CustPriceCityDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 客户价格城市
 * @date 2016/6/8 14:00
 */
@Repository
public interface CustPriceCityDao {
    /**
     * @Description: 查询价格城市
     * @param orgCode
     * @return CorpPriceCityEntity
     * @author 廖文强
     * @date 2016年06月08日
     */
    CustPriceCityEntity queryPriceCityByOrgCode(Map<String, Object> map);
}
