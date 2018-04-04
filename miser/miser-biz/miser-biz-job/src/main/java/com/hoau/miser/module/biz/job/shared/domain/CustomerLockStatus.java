package com.hoau.miser.module.biz.job.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * @ClassName:
 * @Description: 客户价格锁定状态
 * @author: Chenyl yulin.chen@hoau.net
 * @date: 2016/3/11 22:04
 */
public class CustomerLockStatus extends BaseEntity {

    /**
     * 客户编号
     */
    private String customerCode;

    /**
     * 运输类型
     */
    private String transType;

    /**
     * 价格锁定状态
     */
    private String lockStatus;

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

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
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
