package com.hoau.miser.module.api.itf.server.util;

import java.math.BigDecimal;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BigDecimalUtil
 * @Package com.hoau.miser.module.api.itf.server.util
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/17 14:53
 */
public class BigDecimalUtil {
    public  static  BigDecimal setDefaultVale(BigDecimal bigDecimal){
        if(bigDecimal==null){
            bigDecimal=BigDecimal.valueOf(0);
        }
        return bigDecimal;
    }
    public  static  BigDecimal setDefaultVale(BigDecimal bigDecimal,double paramNullval){
        if(bigDecimal==null){
            bigDecimal=BigDecimal.valueOf(paramNullval);
        }
        return bigDecimal;
    }

    /**
     * 取最小值 （空默认为1）
     * @param first
     * @param two
     * @param paramNullval 参数为null时赋的值
     * @return
     */
    public static BigDecimal min(BigDecimal first,BigDecimal two,double paramNullval){
        if(first==null){
            first=BigDecimal.valueOf(paramNullval);
        }
        if(two==null){
            two=BigDecimal.valueOf(paramNullval);
        }
        return first.min(two);
    }
}
