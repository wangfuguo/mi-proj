package com.hoau.miser.module.sys.base.api.shared.exception;

import com.hoau.hbdp.framework.exception.BusinessException;

/**
 * 组织异常类
 * @author刘海飞
 * @date 2015年12月09日
 */
public class DistrictInfoException extends BusinessException{

	private static final long serialVersionUID = 8777134714614032746L;

	/**
     * 组织编码为null异常
     */
    public static final String DISTRICTINFO_CODE_IS_NULL = "bse.org.DistrictInfoCodeNullException";
    /**
     * 组织编码为null异常
     */
    public static final String DISTRICTINFO_LOGISTCODE_IS_NULL = "bse.org.DistrictInfoLogistCodeNullException";
    
    /**
     * 组织不存在异常
     */
    public static final String DISTRICTINFO_NOT_EXIST = "bse.org.DistrictInfoNotExist";
    
    public DistrictInfoException(String code) {
    	super(code);
    	super.errCode = code;
    }
}
