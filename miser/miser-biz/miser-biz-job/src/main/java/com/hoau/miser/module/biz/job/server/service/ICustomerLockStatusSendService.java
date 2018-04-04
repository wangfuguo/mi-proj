package com.hoau.miser.module.biz.job.server.service;

/**
 * @ClassName:
 * @Description: 客户价格锁定状态同步
 * @author: Chenyl yulin.chen@hoau.net
 * @date: 2016/3/11 21:16
 */
public interface ICustomerLockStatusSendService {

    /**
     * 发送客户配置数据
     */
    void sendCustomerLockStatus();

}
