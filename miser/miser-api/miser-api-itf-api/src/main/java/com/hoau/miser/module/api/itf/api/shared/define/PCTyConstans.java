package com.hoau.miser.module.api.itf.api.shared.define;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PCConstans
 * @Package com.hoau.miser.module.api.itf.api.shared.define
 * @Description: 价格常量
 * @date 2016/6/6 15:22
 */
public class PCTyConstans {

    /** 标准价卡 */
    public static final int PC_TYPE_STAND = 1;
    /** 客户价卡 */
    public static final int PC_TYPE_CUST = 2;
    /** 网点价卡 */
    public static final int PC_TYPE_CORP = 3;

    /** 提货方式->客户自送 **/
    public static final String TAKE_DELIVERY_TYPE_0="0";
    /** 提货方式->上门提货 **/
    public static final String TAKE_DELIVERY_TYPE_1="1";
    /**
     * 按包装体积
     */
    public static final String CALCULATION_TYPE_VOLUMN = "VOLUMN";
    /**
     * 按包装重量
     */
    public static final String CALCULATION_TYPE_WEIGHT = "WEIGHT";
    /**  0：父运输类型 */
    public static final int STGY_TRANSTYPE_0 = 0;
    /** 1：子类型策越 */
    public static final int STGY_TRANSTYPE_1 = 1;
    /** 2：子不存在选父 */
    public static final int STGY_TRANSTYPE_2 = 2;
}
