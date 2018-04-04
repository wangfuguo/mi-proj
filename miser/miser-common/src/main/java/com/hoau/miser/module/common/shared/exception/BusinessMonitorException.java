package com.hoau.miser.module.common.shared.exception;

import com.hoau.hbdp.framework.exception.BusinessException;

/**
 * @author：高佳
 * @create：2015年6月17日 下午8:09:19
 * @description：
 */
public class BusinessMonitorException extends BusinessException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public BusinessMonitorException(String errCode) {
		super(errCode);
		this.errCode = errCode;
	}

}
