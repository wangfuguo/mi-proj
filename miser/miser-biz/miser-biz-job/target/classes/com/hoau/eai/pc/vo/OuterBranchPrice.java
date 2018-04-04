package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 外发偏线价格 ClassName: OuterBranchPrice
 *
 * @author 廖文强
 * @version V1.0
 * @date 2016年3月17日
 */
public class OuterBranchPrice implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String id;

    /**
     * 行政省份
     */
    private String provinceCode;

    /**
     * 行政城市
     */
    private String cityCode;

    /**
     * 行政区县
     */
    private String areaCode;

    /**
     * 最低收费
     */
    private Double lowestFee;
    /**
     * 重货
     */
    private Double weightFee;
    /**
     * 轻货
     */
    private Double volumeFee;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改日期
     */
    private Date modifyDate;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 是否有效
     */
    private String active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Double getLowestFee() {
        return lowestFee;
    }

    public void setLowestFee(Double lowestFee) {
        this.lowestFee = lowestFee;
    }

    public Double getWeightFee() {
        return weightFee;
    }

    public void setWeightFee(Double weightFee) {
        this.weightFee = weightFee;
    }

    public Double getVolumeFee() {
        return volumeFee;
    }

    public void setVolumeFee(Double volumeFee) {
        this.volumeFee = volumeFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }


}
