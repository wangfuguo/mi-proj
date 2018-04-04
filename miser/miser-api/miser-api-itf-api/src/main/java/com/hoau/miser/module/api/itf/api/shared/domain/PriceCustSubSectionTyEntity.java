package com.hoau.miser.module.api.itf.api.shared.domain;

import java.math.BigDecimal;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceCustSubSectionTyEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 分段客户价格明细
 * @date 2016/07/05
 */
public class PriceCustSubSectionTyEntity extends PriceSectionBaseTyEntity {

    /** 主表主键 **/
    private String parentId;

    /** 运费分段编号 **/
    private String  freightSectionCode;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFreightSectionCode() {
        return freightSectionCode;
    }

    public void setFreightSectionCode(String freightSectionCode) {
        this.freightSectionCode = freightSectionCode;
    }
}
