package com.hoau.miser.module.api.itf.server.util;

import com.hoau.miser.module.api.itf.server.constants.CplbConstant;

/**
 * 产品类别（运输类型）实用类，可用于客户端和服务端代码
 * 
 * @author hsw
 */
public class CplbUtils implements CplbConstant {

	/**
	 * 判断指定的编号是否为定日达类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为定日达
	 */
	public static boolean isDRD(String cplb) {
		return TYPE_DRD.equals(cplb);
	}

	/**
	 * 判断指定的编号是否为定日达类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为定日达
	 */
	public static boolean isDRD_Name(String cplb) {
		return cplb != null && cplb.equals("定日达");
	}

	/**
	 * 判断指定的编号是否为整车类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为整车
	 */
	public static boolean isZC(String cplb) {
		return TYPE_ZC.equals(cplb);
	}

	/**
	 * 判断指定的编号是否为整车类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为整车
	 */
	public static boolean isZC_Name(String cplb) {
		return cplb != null && cplb.contains("整车");
	}

	/**
	 * 判断指定的编号是否为零担类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为零担
	 */
	public static boolean isLD(String cplb) {
		return TYPE_LD.equals(cplb);
	}

	/**
	 * 判断指定的编号是否为零担类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为零担
	 */
	public static boolean isLD_Name(String cplb) {
		return cplb != null && cplb.contains("经济快运");
	}

	/**
	 * 判断指定的编号是否为经济快运类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为经济快运
	 */
	public static boolean isJJKY_Name(String cplb) {
		return cplb != null && cplb.contains("经济快运");
	}

	/**
	 * @Description: 是否经济拼车产品
	 * @param cplb
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isJJPC(String cplb) {
		return TYPE_JJPC.equals(cplb);
	}

	/**
	 * @Description: 是否经济拼车产品名称
	 * @param cplb
	 * @return boolean 返回类型
	 */
	public static boolean isJJPC_Name(String cplb) {
		return cplb != null && cplb.contains("经济拼车");
	}

	/**
	 * 判断指定的编号是否为偏线类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为易-入户
	 */
	public static boolean isYRH(String cplb) {
		return TYPE_YRH.equals(cplb);
	}

	/**
	 * 判断指定的编号是否为偏线类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为偏线
	 */
	public static boolean isYRH_Name(String cplb) {
		return cplb != null && cplb.equals("易-入户");
	}

	/**
	 * 判断指定的编号是否为易安装类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为易安装
	 */
	public static boolean isYAZ(String cplb) {
		return TYPE_YAZ.equals(cplb);
	}

	/**
	 * 判断指定的编号是否为易-安装类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为易-安装
	 */
	public static boolean isYAZ_Name(String cplb) {
		return cplb != null && cplb.equals("易-安装");
	}

	/**
	 * 判断指定的编号是否为偏线类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为偏线
	 */
	public static boolean isYBG(String cplb) {
		return TYPE_YBG.equals(cplb);
	}

	/**
	 * 判断指定的编号是否为易包裹类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为易包裹
	 */
	public static boolean isYBG_Name(String cplb) {
		return cplb != null && cplb.equals("易-包裹");
	}

	/**
	 * 判断指定的编号是否为偏线类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为偏线
	 */
	public static boolean isPX_Name(String cplb) {
		return cplb != null && cplb.equals("偏线");
	}

	/**
	 * 判断指定的编号是否为偏线类型的
	 * 
	 * @param cplb
	 *            编号字符串
	 * @return 是否为偏线
	 */
	public static boolean isPX(String cplb) {
		return TYPE_PX.equals(cplb);
	}

	/**
	 * 把产品类别编号转换为名称
	 * 
	 * @param cplb
	 *            产品类别编号
	 * @return 产品类别名称
	 */
	public static String toCplbmc(String lbbh) {
		if (lbbh == null || lbbh.length() == 0) {
			return "";
		}
		if (lbbh.equals(TYPE_DRD)) {
			return "定日达";
		} else if (lbbh.equals(TYPE_ZC)) {
			return "整车";
		} else if (lbbh.equals(TYPE_LD)) {
			return "经济快运";
		} else if (lbbh.equals(TYPE_PX)) {
			return "偏线";
		} else if (lbbh.equals(TYPE_YAZ)) {
			return "易安装";
		} else if (lbbh.equals(TYPE_JJPC)) {
			return "经济拼车";
		} else if (lbbh.equals(TYPE_YBG)) {

			return "易包裹";
		} else if (lbbh.equals(TYPE_YRH)) {

			return "易入户";
		} else {
			return "";
		}
	}

	/**
	 * 解析运输类型，在Excel导入的时候，根据Excel中的运输类型名称来反向解析编号
	 * 
	 * @param cplbmc
	 *            运输类型名称
	 * @return 运输类型编号，为null表示解析错误
	 */
	public static String analysisCplb(String cplbmc) {
		if (isLD_Name(cplbmc)) // 零担
		{
			return TYPE_LD;
		}
		if (isDRD_Name(cplbmc)) // 定日达
		{
			return TYPE_DRD;
		}
		if (isZC_Name(cplbmc)) // 整车
		{
			return TYPE_ZC;
		}
		if (isPX_Name(cplbmc)) // 偏线
		{
			return TYPE_PX;
		}
		if (isJJPC_Name(cplbmc)) // 经济拼车
		{
			return TYPE_JJPC;
		}
		return null;
	}

	/**
	 * 把迪辰中所用到的产品类别转换成条码系统所使用的产品类别 1定日达 2整车 3零担 4经济快运 5偏线
	 * 
	 * @param cplb
	 *            迪辰系统中的产品类别
	 * @return 条码系统所使用的产品类别
	 */
	public static int toBarCodeCplb(String cplb) {
		if (TYPE_DRD.equals(cplb)) {
			return 1;
		} else if (TYPE_ZC.equals(cplb)) {
			return 2;
		} else if (TYPE_LD.equals(cplb)) {
			return 3;
		} else if (TYPE_PX.equals(cplb)) {
			return 5;
		}
		// 其他都认为是零担的类型
		return 3;
	}

	/**
	 * 获取产品类型编码给条码系统，需要通过此函数来进行转换
	 * 
	 * @param lbbh
	 *            产品类别编号所在的字段
	 * @return String 从数据获取的转换语句
	 */
	public static String getSQLForBC(String field) {
		return "DECODE(" + field + ", '" + TYPE_DRD + "', '1', '" + TYPE_ZC
				+ "', '2', '" + TYPE_LD + "', '3', '" + TYPE_PX
				+ "', '5', '3')";
	}

	/**
	 * 把条码系统中所用到的产品类别转换成迪辰系统所使用的产品类别 1定日达 2整车 3零担 4经济快运
	 * 
	 * @param cplb
	 *            条码系统中的产品类别
	 * @return 迪辰系统所使用的产品类别
	 */
	public static String toDichenCplb(int cplb) {
		String ret = null;
		switch (cplb) {
		case 1:
			ret = TYPE_DRD;
			break;
		case 2:
			ret = TYPE_ZC;
			break;
		case 3:
			ret = TYPE_LD;
			break;
		case 5:
			ret = TYPE_PX;
			break;
		default:
			ret = TYPE_LD;
			break;
		}
		return ret;
	}
}
