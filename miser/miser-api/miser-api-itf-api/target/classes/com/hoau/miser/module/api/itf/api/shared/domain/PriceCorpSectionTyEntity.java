package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;


/**
 * 分段网点价格实体
 * @Author 廖文强
 * @date 2016/7/5
 */
public class PriceCorpSectionTyEntity extends PriceSectionBaseTyEntity{
    /** 行政组织编码 **/
    private String corpCode;

    private String freightSectionCode;

    public int getType() {
        return PCTyConstans.PC_TYPE_STAND;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getFreightSectionCode() {
        return freightSectionCode;
    }

    public void setFreightSectionCode(String freightSectionCode) {
        this.freightSectionCode = freightSectionCode;
    }
}