package com.hoau.eai.pc.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 折扣分段
 * @author Leslie
 *
 */
public class DiscountSectionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对应的折扣分段或燃油附加费唯一编号（折扣编号或燃油附加费编号）
	 */
	private String id;
	/**
	 * 传输类型：DS优惠分段；FE燃油附加费
	 */
	private String type;
	/**
	 * 1)当传输类型为DS时：折扣分段名称 ；2）当传输类型为FE时：燃油附加费名称
	 */
	private String name;
	/**
	 * 适用产品：(D定日达 L零担 A共用
	 */
	private String suitProduct;
	/**
	 * 价格说明
	 */
	private String remark;
	/**
	 * 删除状态0正常
	 */
	private Integer recStatus;
	/**
	 * 折扣明细
	 */
	private List<DiscountDetailInfo> discountDetailInfos;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the suitProduct
	 */
	public String getSuitProduct() {
		return suitProduct;
	}

	/**
	 * @param suitProduct the suitProduct to set
	 */
	public void setSuitProduct(String suitProduct) {
		this.suitProduct = suitProduct;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the discountDetailInfos
	 */
	public List<DiscountDetailInfo> getDiscountDetailInfos() {
		return discountDetailInfos;
	}

	/**
	 * @param discountDetailInfos the discountDetailInfos to set
	 */
	public void setDiscountDetailInfos(List<DiscountDetailInfo> discountDetailInfos) {
		this.discountDetailInfos = discountDetailInfos;
	}

	public Integer getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(Integer recStatus) {
		this.recStatus = recStatus;
	}
	
}
