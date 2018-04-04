package com.hoau.miser.module.api.itf.api.shared.define;

/**
 * 开单结算类型枚举
 *
 * @author 蒋落琛
 * @date 2016-6-16上午8:35:57
 */
public enum WaybillCalculateType {
	/**
	 * 开发费用计算定义
	 */
	CALC_PACKINGFEE("开单计算包装费"),
	CALC_PICKUPFEE("开单计算提货费"),
	CALC_DELIVERYFEE("开单计算送货费"),
	CALC_UPSTAIRSFEE("开单计算上楼费"),
	CALC_UPSTAIRSMINFEE("开单计算上楼费最小值"),
	CALC_EXTRASERVICEFEE("开单计算特服费"),
	CALC_FUELFEE("开单计算燃油费"),
	CALC_INFORMATIONFEE("开单计算信息费"),
	CALC_PRODUCTIONFEE("开单计算工本费"),
	CALC_INSUREDFEE("开单计算保价费"),
	CALC_INSUREDRATE("开单计算保价率"),
	CALC_COLLECTIONPAY_RATE("开单计算代收货款费率"),
	CALC_COLLECTIONPAY("开单计算代收货款手续费"),
	CALC_WEIGHTVOLUMNUNITPRICE("开单计算重量体积单价");

	/**
	 * 结算费用类型
	 */
	private String name;


	private WaybillCalculateType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public static void main(String[] args){

		System.out.println(WaybillCalculateType.CALC_DELIVERYFEE.getName());
	}

}
