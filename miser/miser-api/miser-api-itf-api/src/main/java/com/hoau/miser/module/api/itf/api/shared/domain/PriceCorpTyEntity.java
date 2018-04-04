package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BasePriceTyEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 网点价格
 * @date 2016/6/6 15:12
 */
public class PriceCorpTyEntity extends PriceBaseTyEntity {

    /** 行政组织编码 **/
    private String corpCode;
    /** 门店名称**/
    private String corpName;

    /** 运费专属折扣 **/
    private String freightOwnDiscountSectCode;

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getFreightOwnDiscountSectCode() {
        return freightOwnDiscountSectCode;
    }

    public void setFreightOwnDiscountSectCode(String freightOwnDiscountSectCode) {
        this.freightOwnDiscountSectCode = freightOwnDiscountSectCode;
    }

    public int getType() {
        return PCTyConstans.PC_TYPE_CORP;
    }


}
