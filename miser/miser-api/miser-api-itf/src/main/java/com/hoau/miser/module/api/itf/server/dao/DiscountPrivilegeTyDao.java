package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.DiscountPrivilegeTyEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: DiscountPrivilegeTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 越发越惠折扣
 * @date 2016/6/13 11:22
 */
@Repository
public interface DiscountPrivilegeTyDao {
    /**
     * 根据查询客户越发越惠折扣
     * @Param
     * @Return com.hoau.miser.module.api.itf.api.shared.domain.DiscountPrivilegeTyEntity
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/13 10:48
     * @Version v1
     */
    public List<DiscountPrivilegeTyEntity> queryDiscountCustomerByParam(DiscountPrivilegeTyEntity param);
}
