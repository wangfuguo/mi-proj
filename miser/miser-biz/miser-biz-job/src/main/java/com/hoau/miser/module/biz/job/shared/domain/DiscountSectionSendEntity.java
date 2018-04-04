package com.hoau.miser.module.biz.job.shared.domain;

import java.util.List;


/**
 * ClassName: DiscountSectionSendEntity 
 * @author 刘海飞
 * @date 2016年1月23日
 * @version V1.0
 */
public class DiscountSectionSendEntity {
	/**
	 * 对应的折扣分段或燃油附加费唯一编号（折扣编号或燃油附加费编号）
	 */
	private String id;
	/**
	 * 分段项目编号
	 */
	private String code;
	/**
	 *分段项目名称
	 */
	private String name;
	/**
	 * 适用产品：(D定日达 L零担 A共用
	 * 适用产品 空代表使用全部产品 其他产品来源 T_BSE_TRANS_TYPE表CODE字段
	 */
	private String suitProduct;
	/**
	 * 价格说明
	 */
	private String remark;
	/**
	 * 分段计算的项目：
		FREIGHT:运费
		FUEL:燃油费
		DELIVERY:送货费
		PICK:提货费
	 */
	private String sectionedItem;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 折扣明细
	 */
	private List<DiscountDetailEntity> discountDetailEntity;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSuitProduct() {
		return suitProduct;
	}
	public void setSuitProduct(String suitProduct) {
		this.suitProduct = suitProduct;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<DiscountDetailEntity> getDiscountDetailEntity() {
		return discountDetailEntity;
	}
	public void setDiscountDetailEntity(
			List<DiscountDetailEntity> discountDetailEntity) {
		this.discountDetailEntity = discountDetailEntity;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSectionedItem() {
		return sectionedItem;
	}
	public void setSectionedItem(String sectionedItem) {
		this.sectionedItem = sectionedItem;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	
}
