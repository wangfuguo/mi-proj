package com.hoau.miser.module.util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.hoau.miser.module.util.define.MiserConstants;


public class DataTrans {
    public static String boolToYes(boolean bool){
	return bool?MiserConstants.YES:MiserConstants.NO;
    }

    public static boolean flagToBool(String flag){
	return StringUtils.equals(MiserConstants.YES, flag);
    }
    
    public static double bigDecimalToDouble(BigDecimal bigDecimal){
	return bigDecimal==null?0:bigDecimal.doubleValue();
    }
}
