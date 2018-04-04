package com.hoau.miser.module.common.shared.define;

/**
 * @author：高佳
 * @create：2015年8月19日 上午10:09:53
 * @description：序号规则枚举
 */
/**
 * 
 * @author Liwy
 * @date 2016年7月6日
 * @version V1.0
 * 注释时效城市编号。时效城市和价格城市使用同一套生成编码
 */
public enum SerialNumberRuleEnum {
	PC_PRICE_CITY_CODE("价格城市编号", "PC_PRICE_CITY_CODE", false, false, "", false, "", false, "", true, false, "", false, 4, false, "", true),
	//PC_AGING_CITY_CODE("时效城市编号", "PC_AGING_CITY_CODE", false, false, "", false, "", false, "", true, false, "", false, 4, false, "", true),
	PC_SECTION_CODE("优惠分段编号", "PC_SECTION_CODE", false, false, "", false, "", false, "", true, false, "", false, 4, false, "", true),
	PC_PRICE_PACKAGE_FEE_ITEMS_CODE("包装费编号", "PC_PRICE_PACKAGE_FEE_ITEMS_CODE", false, false, "", false, "", false, "", true, false, "", false, 4, false, "", false),
	PC_PRICE_ADDVALUE_FEE_ITEMS("特服费编号", "PC_PRICE_ADDVALUE_FEE_ITEMS", false, false, "", false, "", false, "", true, false, "", false, 4, false, "", false),
	REUNITE_PC_SECTION_CODE("融合优惠分段编号", "REUNITE_PC_SECTION_CODE", false, false, "", false, "", false, "", true, false, "", false, 4, false, "", true),
	PRIVILEGE_CONTRACT_CODE("越发越惠合同编号", "PRIVILEGE_CONTRACT_CODE", false, false, "", false, "", false, "", true, false, "", false, 36, false, "", false)
	;
	private String name;			  //中文名称
	private String code;			  //编码
	private boolean needParams;		  //是否需要输入参数
	private boolean needTime;		  //是否需要日期prefix
	private String timeFormat;		  //日期prefix
	private boolean needDelimiter;	  //是否需要分隔符
	private String delimiter;		  //分隔符
	private boolean needLetterPrefix; //是否需要字母前缀
	private String letterPrefix;	  //字母prefix
	private boolean needNumber;		  //是否需要数字累加
	private boolean needSequence;	  //是否需要sequence
	private String sequence;		  //sequence命名
	private boolean fixedNumLen;	  //是否固定位数FIXED
	private int numLen;				  //数字位数
	private boolean needLetterSuffix; //是否需要后缀suffix
	private String letterSuffix;	  //后缀值
	private boolean isHex;			  //是否使用16进制进行转换
	
	private SerialNumberRuleEnum(String name, String code, boolean needParams,
			boolean needTime, String timeFormat, boolean needDelimiter, String delimiter, 
			boolean needLetterPrefix, String letterPrefix, boolean needNumber, boolean needSequence, String sequence,
			boolean fixedNumLen, int numLen, boolean needLetterSuffix, String letterSuffix, boolean isHex) {
		this.name = name;
		this.code = code;
		this.needParams = needParams;
		this.needTime = needTime;
		this.timeFormat = timeFormat;
		this.needDelimiter = needDelimiter;
		this.delimiter = delimiter;
		this.needLetterPrefix = needLetterPrefix;
		this.letterPrefix = letterPrefix;
		this.needNumber = needNumber;
		this.needSequence = needSequence;
		this.sequence = sequence;
		this.fixedNumLen = fixedNumLen;
		this.numLen = numLen;
		this.needLetterSuffix = needLetterSuffix;
		this.letterSuffix = letterSuffix;
		this.isHex = isHex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNeedParams() {
		return needParams;
	}

	public void setNeedParams(boolean needParams) {
		this.needParams = needParams;
	}

	public boolean isNeedTime() {
		return needTime;
	}

	public void setNeedTime(boolean needTime) {
		this.needTime = needTime;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public boolean isNeedDelimiter() {
		return needDelimiter;
	}

	public void setNeedDelimiter(boolean needDelimiter) {
		this.needDelimiter = needDelimiter;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public boolean isNeedNumber() {
		return needNumber;
	}

	public void setNeedNumber(boolean needNumber) {
		this.needNumber = needNumber;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public boolean isFixedNumLen() {
		return fixedNumLen;
	}

	public void setFixedNumLen(boolean fixedNumLen) {
		this.fixedNumLen = fixedNumLen;
	}

	public int getNumLen() {
		return numLen;
	}

	public void setNumLen(int numLen) {
		this.numLen = numLen;
	}

	public boolean isNeedLetterSuffix() {
		return needLetterSuffix;
	}

	public void setNeedLetterSuffix(boolean needLetterSuffix) {
		this.needLetterSuffix = needLetterSuffix;
	}

	public String getLetterSuffix() {
		return letterSuffix;
	}

	public void setLetterSuffix(String letterSuffix) {
		this.letterSuffix = letterSuffix;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLetterPrefix() {
		return letterPrefix;
	}

	public void setLetterPrefix(String letterPrefix) {
		this.letterPrefix = letterPrefix;
	}

	public boolean isNeedLetterPrefix() {
		return needLetterPrefix;
	}

	public void setNeedLetterPrefix(boolean needLetterPrefix) {
		this.needLetterPrefix = needLetterPrefix;
	}

	public boolean isNeedSequence() {
		return needSequence;
	}

	public void setNeedSequence(boolean needSequence) {
		this.needSequence = needSequence;
	}

	public boolean isHex() {
        return isHex;
    }

	public void setHex(boolean hex) {
        isHex = hex;
    }
}
