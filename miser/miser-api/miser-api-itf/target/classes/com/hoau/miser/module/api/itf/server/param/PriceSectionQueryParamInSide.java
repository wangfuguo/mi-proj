package com.hoau.miser.module.api.itf.server.param;

import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: TransTypeFyParam
 * @Package com.hoau.miser.module.api.itf.shared.vo
 * @Description: ${TODO}(基础分段价格查询实体（内部实体） 接口使用)
 * @date 2016/6/1 11:15
 */
public class PriceSectionQueryParamInSide {

    /** 价格查询参数**/
    public PriceQueryParam baseTyParam;
    /**
     * 出发价格城市
     **/
    private String sendPriceCityCode;
    /**
     * 到达价格城市
     **/
    private String arrivePriceCityCode;

    public PriceQueryParam getBaseTyParam() {
        return baseTyParam;
    }

    public void setBaseTyParam(PriceQueryParam baseTyParam) {
        this.baseTyParam = baseTyParam;
    }

    public String getSendPriceCityCode() {
        return sendPriceCityCode;
    }

    public void setSendPriceCityCode(String sendPriceCityCode) {
        this.sendPriceCityCode = sendPriceCityCode;
    }

    public String getArrivePriceCityCode() {
        return arrivePriceCityCode;
    }

    public void setArrivePriceCityCode(String arrivePriceCityCode) {
        this.arrivePriceCityCode = arrivePriceCityCode;
    }
}
