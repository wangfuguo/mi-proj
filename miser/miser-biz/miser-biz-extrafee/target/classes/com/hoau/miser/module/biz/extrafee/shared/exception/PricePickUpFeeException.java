package com.hoau.miser.module.biz.extrafee.shared.exception;

import com.hoau.hbdp.framework.exception.BusinessException;

/**
 * ClassName: PricePickUpFeeException 
 * @author 刘海飞
 * @date 2016年1月14日
 * @version V1.0
 */
public class PricePickUpFeeException extends BusinessException {

	/**
	 * @Fields serialVersionUID : 序列号
	 */
	private static final long serialVersionUID = 6346346452931312021L;
	
	public  static final String CAN_NOT_MODIFY="price_pickUp_fee_can_not_modify";
	
	
    public PricePickUpFeeException(String code) {
        super(code);
        super.errCode = code;
    }

    public PricePickUpFeeException(String code, String msg) {
        super(code, msg);
    }
}
