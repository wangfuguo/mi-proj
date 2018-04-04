package com.hoau.miser.module.api.itf.api.shared.domain;

import java.math.BigDecimal;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: FreightFeeCalcEntity
 * @Package com.hoau.miser.module.api.itf.api.shared.domain
 * @Description: 运费计算实体
 * @date 2016/6/21 17:00
 */
public class FreightFeeCalcEntity {

    /** 重货单价**/
    private BigDecimal weightPrice;
    /** 轻货单价**/
    private BigDecimal volumePrice;
    /** 重量**/
    private BigDecimal weightNo;
    /** 体积**/
    private BigDecimal volumeNo;


    public BigDecimal getWeightPrice() {
        return setDefaultVale(weightPrice);
    }

    public void setWeightPrice(BigDecimal weightPrice) {
        this.weightPrice = weightPrice;
    }

    public BigDecimal getVolumePrice() {
        return setDefaultVale(volumePrice);
    }

    public void setVolumePrice(BigDecimal volumePrice) {
        this.volumePrice = volumePrice;
    }

    public BigDecimal getWeightNo() {
        return setDefaultVale(weightNo);
    }

    public void setWeightNo(BigDecimal weightNo) {
        this.weightNo = weightNo;
    }

    public BigDecimal getVolumeNo() {
        return setDefaultVale(volumeNo);
    }

    public void setVolumeNo(BigDecimal volumeNo) {
        this.volumeNo = volumeNo;
    }


    private  BigDecimal setDefaultVale(BigDecimal bigDecimal){
        if(bigDecimal==null){
            bigDecimal=BigDecimal.valueOf(0);
        }
        return bigDecimal;
    }
}
