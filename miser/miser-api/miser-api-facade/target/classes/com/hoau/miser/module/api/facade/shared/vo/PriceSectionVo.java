package com.hoau.miser.module.api.facade.shared.vo;

import org.apache.commons.lang3.StringUtils;


/**
 * @ClassName: PriceSectionVo
 * @Description: 易到家（易入户，易到家，易安装）分段价格Vo，查询易到家分段价格
 * @author ZOUYU
 * @date 2016年5月4日 下午1:41:41
 */
public class PriceSectionVo {
	/**
	 *@Fields 网店-迪辰公司编码-网点价格
	 */
	private String corpCode;
	/**
	 * @Fields 客户编码-迪辰客户编码-客户价格
	 */
    private String customerCode;
	/**
	 * @Fields transTypeCode : 运输类型-迪辰运单产品类型
	 */
	private String transTypeCode;
	/**
	 * @Fields sendPriceCity : 出发价格城市-迪辰公司出发价卡城市编号
	 */
	private String sendPriceCityCode;
	/**
	 * @Fields arrivePriceCity : 到达价格城市-迪辰公司到达价卡城市编号
	 */
	private String arrivePriceCityCode;
	
	public String getCorpCode() {
		return corpCode;
	}
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getTransTypeCode() {
		return transTypeCode;
	}
	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
	}
	public String getSendPriceCityCode() {
		return sendPriceCityCode;
	}
	public void setSendPriceCityCode(String sendPriceCityCode) {
		this.sendPriceCityCode = sendPriceCityCode;
	}
	public String getArrivePriceCityCode() {
		return arrivePriceCityCode;
	}
	public void setArrivePriceCityCode(String arrivePriceCityCode) {
		this.arrivePriceCityCode = arrivePriceCityCode;
	}
}
