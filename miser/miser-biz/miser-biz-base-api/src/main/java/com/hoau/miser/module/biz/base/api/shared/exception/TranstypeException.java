package com.hoau.miser.module.biz.base.api.shared.exception;

import com.hoau.hbdp.framework.exception.BusinessException;

/**
 * @author Chenyl.sai@gmail.com
 * @version V1.0
 * @Title: TranstypeException
 * @Package com.hoau.miser.module.biz.base.shared.exception
 * @Description: 运输类型界面异常
 * @date 15/12/25 09:18
 */
public class TranstypeException extends BusinessException {

    private static final long serialVersionUID = -519680898918705540L;

    /**
     * @Fields TRANSTYPE_NAME_CONFLICT : 名称重复异常
     */
    public static final String TRANSTYPE_NAME_CONFLICT = "bizbase.transtype.NameConflictException";

    /**
     * @Fields TRANSTYPE_CODE_CONFLICT : 编码重复异常
     */
    public static final String TRANSTYPE_CODE_CONFLICT = "bizbase.transtype.CodeConflictException";
    
    /**
     * @Fields TRANSTYPE_CANT_MODIFY_INACTIVE : 不可修改无效记录异常
     */
    public static final String TRANSTYPE_CANT_MODIFY_INACTIVE = "bizbase.transtype.CantModifyInactiveException";
    
    /**
     * @Fields TRANSTYPE_CANT_DELETE_INACTIVE : 不可重复作废
     */
    public static final String TRANSTYPE_CANT_DELETE_INACTIVE = "bizbase.transtype.CantDeleteInactiveException";

    public TranstypeException(String code) {
        super(code);
        super.errCode = code;
    }

    public TranstypeException(String code, String msg) {
        super(code, msg);
    }
}
