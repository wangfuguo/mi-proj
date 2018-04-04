package com.hoau.miser.module.biz.base.api.shared.exception;

import com.hoau.hbdp.framework.exception.BusinessException;

/**
 * @ClassName:
 * @Description: 价格城市维护相关异常
 * @author: Chenyl yulin.chen@hoau.net
 * @date: 2016/3/9 19:20
 */
public class PriceCityException extends BusinessException {

    public static final String TRANSTYPE_NAME_CONFLICT = "bizbase.pricecity.NameConflictException";

    public PriceCityException(String code) {
        super(code);
        super.errCode = code;
    }

    public PriceCityException(String code, String msg) {
        super(code, msg);
    }
}
