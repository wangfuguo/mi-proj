package com.hoau.miser.module.biz.extrafee.shared.exception;

import com.hoau.hbdp.framework.exception.BusinessException;

/**
 * ClassName: PriceUpstairsException 
 * @Description: 上楼费异常处理类 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月5日
 * @version V1.0   
 */
public class PriceUpstairsException extends BusinessException {

	/**
	 * @Fields serialVersionUID : 序列号
	 */
	private static final long serialVersionUID = 6346346452931312021L;
	
	/**
	 * @Fields MISS_OPR_DATE : 操作数据丢失异常
	 */
	public static final String MISS_OPR_DATE = "pricecard.upstairs.MissOprDataException";

	/**
	 * @Fields EFFECTIVETIME_TOO_OLD : 生效时间小于当前时间异常
	 */
	public static final String EFFECTIVETIME_TOO_EARLY = "pricecard.upstairs.EffectiveTooEarlyException";
	
	/**
	 * @Fields CAN_NOT_OPR_PASSED_DATA : 操作已过期数据异常
	 */
	public static final String CAN_NOT_OPR_PASSED_DATA = "pricecard.upstairs.CannotOprPassedDataException";
	
	/**
	 * @Fields OPR_DELETED_PASSED_DATA : 操作已删除数据异常
	 */
	public static final String OPR_DELETED_DATA = "pricecard.upstairs.OprDeletedDataException";
	
	
    public PriceUpstairsException(String code) {
        super(code);
        super.errCode = code;
    }

    public PriceUpstairsException(String code, String msg) {
        super(code, msg);
    }
}
