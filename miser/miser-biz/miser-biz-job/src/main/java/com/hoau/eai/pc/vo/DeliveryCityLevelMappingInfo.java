package com.hoau.eai.pc.vo;

import java.io.Serializable;

/**
 * @ClassName:
 * @Description: 送货城市类型配置信息
 * @author: Chenyl yulin.chen@hoau.net
 * @date: 2016/4/9 15:57
 */
public class DeliveryCityLevelMappingInfo implements Serializable {

    private static final long serialVersionUID = 7108080496165863258L;
    /**
     * 省份编码
     */
    private String provinceCode;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 区县编码
     */
    private String areaCode;

    /**
     * 城市等级
     */
    private String cityType;

    /**
     * 是否有效
     */
    private String active;

    /**
     * 备注
     */
    private String remark;

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityType() {
        return cityType;
    }

    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
