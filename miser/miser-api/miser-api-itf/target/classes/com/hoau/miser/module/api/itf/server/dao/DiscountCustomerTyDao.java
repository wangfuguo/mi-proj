package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: DiscountCustomerTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 客户折扣dao
 * @date 2016/6/12 16:49
 */
@Repository
public interface DiscountCustomerTyDao {
    /**
     * 查询客户折扣
     * @Param  [queryEnity]
     * @Return java.util.List<com.hoau.miser.module.api.itf.api.shared.domain.DiscountCustomerTyEntity>
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/12 16:50
     * @Version v1
     */
    List<DiscountCustomerTyEntity> queryListByParam(PriceQueryParam queryEnity);
}
