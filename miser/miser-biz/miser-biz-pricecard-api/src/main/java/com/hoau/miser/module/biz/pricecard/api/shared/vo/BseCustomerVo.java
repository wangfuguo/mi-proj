/**   
 * @Title: BseCustomerVo.java 
 * @Package com.hoau.miser.module.biz.pricecard.shared.vo 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月6日 下午5:35:29 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.BseCustomerEntity;

/**
 * ClassName: BseCustomerVo
 * 
 * @author dengyin
 * @date 2016年1月6日
 * @version V1.0
 */
public class BseCustomerVo implements Serializable {

	private static final long serialVersionUID = -8877548279351763964L;

	private BseCustomerEntity bseCustomerEntity;

	private List<BseCustomerEntity> bseCustomerEntityList;
	private String active;

	public BseCustomerEntity getBseCustomerEntity() {
		return bseCustomerEntity;
	}

	public void setBseCustomerEntity(BseCustomerEntity bseCustomerEntity) {
		this.bseCustomerEntity = bseCustomerEntity;
	}

	public List<BseCustomerEntity> getBseCustomerEntityList() {
		return bseCustomerEntityList;
	}

	public void setBseCustomerEntityList(
			List<BseCustomerEntity> bseCustomerEntityList) {
		this.bseCustomerEntityList = bseCustomerEntityList;
	}

	// 客户主键
	private String id;
	// 客户编码
	private String code;
	// 客户名称
	private String name;
	// 客户所属门店编码
	private String orgCode;
	// 客户所属路区编码
	private String roadAreaCode;

	// 客户所属大区编码
	private String bigRegionCode;

	// 客户所属事业部
	private String divisionCode;

	public String getRoadAreaCode() {
		return roadAreaCode;
	}

	public void setRoadAreaCode(String roadAreaCode) {
		this.roadAreaCode = roadAreaCode;
	}

	public String getBigRegionCode() {
		return bigRegionCode;
	}

	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
