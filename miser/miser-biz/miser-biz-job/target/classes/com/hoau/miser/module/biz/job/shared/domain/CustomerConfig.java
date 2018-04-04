package com.hoau.miser.module.biz.job.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

import java.util.Date;

/**
 * @ClassName:
 * @Description: 客户配置信息
 * @author: Chenyl yulin.chen@hoau.net
 * @date: 2016/3/11 22:04
 */
public class CustomerConfig extends BaseEntity {

    /**
     * 客户编号
     */
    private String customerCode;

    /**
     * 价格开始时间
     */
    private Date pcStartTime;

    /**
     * 价格结束时间
     */
    private Date pcEndTime;

    /**
     * 是否启用客户价格
     */
    private String useCustomerPc;

    /**
     * 是否启用客户折扣
     */
    private String useCustomerDiscount;

    /**
     * 签收单是否显示运费价格
     */
    private String signbillShowMomey;

    /**
     * 是否有效
     */
    private String active;

    /**
     * 备注
     */
    private String remark;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Date getPcEndTime() {
        return pcEndTime;
    }

    public void setPcEndTime(Date pcEndTime) {
        this.pcEndTime = pcEndTime;
    }

    public Date getPcStartTime() {
        return pcStartTime;
    }

    public void setPcStartTime(Date pcStartTime) {
        this.pcStartTime = pcStartTime;
    }

    public String getUseCustomerPc() {
        return useCustomerPc;
    }

    public void setUseCustomerPc(String useCustomerPc) {
        this.useCustomerPc = useCustomerPc;
    }

    public String getUseCustomerDiscount() {
        return useCustomerDiscount;
    }

    public void setUseCustomerDiscount(String useCustomerDiscount) {
        this.useCustomerDiscount = useCustomerDiscount;
    }

    public String getSignbillShowMomey() {
        return signbillShowMomey;
    }

    public void setSignbillShowMomey(String signbillShowMomey) {
        this.signbillShowMomey = signbillShowMomey;
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
