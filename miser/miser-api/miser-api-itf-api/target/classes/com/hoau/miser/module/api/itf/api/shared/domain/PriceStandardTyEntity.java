package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;

import java.math.BigDecimal;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BasePriceTyEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 标准价格
 * @date 2016/6/6 15:12
 */
public class PriceStandardTyEntity extends PriceBaseTyEntity {



    public int getType() {
        return PCTyConstans.PC_TYPE_STAND;
    }

}
