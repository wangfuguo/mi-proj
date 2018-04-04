package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountPrivilegeTyEntity;

import java.util.Date;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: IDiscountPrivilegeService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 客户越发越惠折扣
 * @date 2016/6/13 10:45
 */
public interface IDiscountPrivilegeTyService {
    /**
     * 根据客户编号和执行年月查询客户越发越惠折扣
     * @Param  [custNo, recordMonth]
     * @Return com.hoau.miser.module.api.itf.api.shared.domain.DiscountPrivilegeTyEntity
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/13 10:48
     * @Version v1
     */
    public DiscountPrivilegeTyEntity queryDiscountCustomerByCustNoAndrecordMonth(String custNo, Date recordMonth);
}
