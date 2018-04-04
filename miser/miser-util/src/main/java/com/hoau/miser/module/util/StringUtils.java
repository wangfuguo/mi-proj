package com.hoau.miser.module.util;

import java.math.BigDecimal;

/**
 * Created by Sai on 15/12/17.
 */
public class StringUtils {

	/**
	 * 是否为空字符串,包含null和空串
	 * */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 是否为只包含空格的空字符串, 包含null\空串\只包含空格
	 * */
	public static boolean isEmptyWithBlock(String str) {
		return isEmpty(str) || str.trim().length() == 0;
	}

	/**
	 * @Description: 如果为空或不是数字的字符串会返回0
	 * @param str
	 *            需要进行转换的字符串
	 * @return int
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月15日
	 */
	public static int toInt(String str) {
		if (str == null) {
			return 0;
		}
		try {
			return Integer.parseInt(str.trim());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 
	 * @Description: 转换字符串为bigdecimal， 空或者不是数字全部为0
	 * @param @param str
	 * @param @return
	 * @return BigDecimal
	 * @throws
	 * @author 赵金义
	 * @date 2016年4月28日
	 */
	public static BigDecimal toBigDecimal(String str) {
		if (str == null) {
			return new BigDecimal("0");
		}
		try {
			return new BigDecimal(str);
		} catch (Exception e) {
			return new BigDecimal("0");
		}
	}

}
