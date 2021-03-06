package com.hoau.miser.module.sys.job.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * @author：何巍
 * @create：2015年6月3日 下午1:02:07
 * @description：
 */
public class DistrictEntity extends BaseEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 行政区域编码
	 */
	private String districtCode;

	/**
	 * 行政区域名称
	 */
	private String districtName;

	/**
	 * 行政区域名称简称
	 */
	private String districtNameShort;

	/**
	 * 行政区域类型 COUNTRY(国家) PROVINCE(省份) CITY(城市) AREA(区县)
	 */
	private String districtType;

	/**
	 * 上级行政区域编码
	 */
	private String parentDistrictCode;

	/**
	 * 拼音
	 */
	private String pinyin;

	/**
	 * 拼音简称
	 */
	private String pinyinShort;

	/**
	 * 版本号
	 */
	private Long versionNo;

	private String active;

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictType() {
		return districtType;
	}

	public void setDistrictType(String districtType) {
		this.districtType = districtType;
	}

	public String getParentDistrictCode() {
		return parentDistrictCode;
	}

	public void setParentDistrictCode(String parentDistrictCode) {
		this.parentDistrictCode = parentDistrictCode;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getDistrictNameShort() {
		return districtNameShort;
	}

	public void setDistrictNameShort(String districtNameShort) {
		this.districtNameShort = districtNameShort;
	}

	public String getPinyinShort() {
		return pinyinShort;
	}

	public void setPinyinShort(String pinyinShort) {
		this.pinyinShort = pinyinShort;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}


}
