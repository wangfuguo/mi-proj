package com.hoau.miser.module.api.facade.shared.define;

/**
 * 易到家类型，用于区分返回迪辰数据的list中的元素是易到家的那一类分段价格PriceStandardSection/PriceCorpSection/PriceCustomerSection
 * @author zouyu
 */
public enum PriceSectionType {

	Price_Standard_Section("PriceStandardSection","易到家标准分段价格"),
	Price_Corp_Section("PriceCorpSection","易到家网点分段价格"),
	Price_Customer_Section("PriceCustomerSection","易到家客户分段价格");
	
	private PriceSectionType(String produce, String content){
		this.produce = produce;
		this.content = content;
	}

	/**
	 * 产品名
	 */
	private String produce;
	/**
	 * 中文名
	 */
	private String content;
	
	public String getProduce() {
		return produce;
	}
	public String getContent() {
		return content;
	}
	
}
