package com.hoau.miser.module.biz.pricecard.api.shared.define;

/**
 * ClassName: ReunitePriceSectionConstant 
 * @Description: 费用分段融合需要使用的相关常亮 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月28日
 * @version V1.0   
 */
public class ReunitePriceSectionConstant {
	
	/**
	 * @Fields REUNITE_TYPE_UNREUNITED_CANNOT : 分段参考对象不同等原因导致的不能进行融合
	 */
	public static final String REUNITE_TYPE_UNREUNITED_CANNOT = "UN_REUNITED_CANNOT";

	/**
	 * @Fields REUNITE_TYPE_UNREUNITED_BEFORE : 整个分段前置于融合前的分段导致的不需要融合
	 */
	public static final String REUNITE_TYPE_UNREUNITED_BEFORE = "UN_REUNITED_BEFORE";

	/**
	 * @Fields REUNITE_TYPE_UNREUNITED_AFTER : 整个分段后置于融合前的分段导致不需要融合
	 */
	public static final String REUNITE_TYPE_UNREUNITED_AFTER = "UN_REUNITED_AFTER";
	
	/**
	 * @Fields REUNITE_TYPE_PERFECT_REUNITED : 待分段段止不超过融合前段止的完美融合
	 */
	public static final String REUNITE_TYPE_PERFECT_REUNITED = "PREFECT_REUNITED";
	
	/**
	 * @Fields REUNITE_TYPE_IMPERFECT_REUNITED : 待分段段止超过融合前段止的不完美融合，需要重置待分段的段起为融合前的段止后继续想后面的分段进行融合
	 */
	public static final String REUNITE_TYPE_IMPERFECT_REUNITED = "IMPERFECT_REUNITED";
	
	/**
	 * @Fields REUNITE_SYSTEM_USER : 产生新费用分段的统一系统用户名
	 */
	public static final String REUNITE_SYSTEM_USER = "SYSTEM_REUNITE";
}
