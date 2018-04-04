package com.hoau.miser.module.api.itf.server.exception;

import com.hoau.hbdp.framework.exception.BusinessException;

/**
 * 增值费异常
 *
 * @author 蒋落琛
 * @date 2016-6-2上午10:56:54
 */
public class ChargeException  extends BusinessException{

	private static final long serialVersionUID = 1L;
	
	// 省不能为空
	public static final String CHARGE_CITYTYPE_PROVINCECODE_ISNULL_EXCEPTION = "charge.citytype.provincecodeIsNullException";
	
	// 市不能为空
	public static final String CHARGE_CITYTYPE_CITYCODE_ISNULL_EXCEPTION = "charge.citytype.citycodeIsNullException";
	
	// 区不能为空
	public static final String CHARGE_CITYTYPE_AREACODE_ISNULL_EXCEPTION = "charge.citytype.areacodeIsNullException";
	
	// 产品类型不能为空
	public static final String SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION = "surchargePrice.bse.productTypeIsNullException";
	
	// 出发公司不能为空
	public static final String SURCHARGEPRICE_STARTORGCODE_ISNULL_EXCEPTION = "surchargePrice.bse.startOrgCodeIsNullException";
	
	// 到达公司不能为空
	public static final String SURCHARGEPRICE_ARRIVALORGCODE_ISNULL_EXCEPTION = "surchargePrice.bse.arrivalOrgCodeIsNullException";
	
	// 请求参数不能为空
	public static final String SURCHARGEPRICE_PARAMS_ISNULL_EXCEPTION = "surchargePrice.bse.paramsIsNullException";
	
	// 特服费项目编号不能为空
	public static final String SURCHARGEPRICE_SPECIAL_ITEMCODE_ISNULL_EXCEPTION = "surchargePrice.bse.specialItemCodeIsNullException";
	
	// 重量不能为空
	public static final String SURCHARGEPRICE_GOODSWEIGHT_ISNULL_EXCEPTION = "surchargePrice.bse.goodsWeightIsNullException";
	
	// 体积不能为空
	public static final String SURCHARGEPRICE_GOODSVOLUME_ISNULL_EXCEPTION = "surchargePrice.bse.goodsVolumeIsNullException";
		
	// 重量单价不能为空
	public static final String SURCHARGEPRICE_WEIGHTUNITPRICE_ISNULL_EXCEPTION = "surchargePrice.bse.weightUnitPriceIsNullException";
		
	// 体积单价不能为空
	public static final String SURCHARGEPRICE_VOLUMEUNITPRICE_ISNULL_EXCEPTION = "surchargePrice.bse.volumeUnitPriceIsNullException";
	
	// 送货方式不能为空
	public static final String SURCHARGEPRICE_DELIVERYMETHOD_ISNULL_EXCEPTION = "surchargePrice.bse.deliveryMethodIsNullException";
	
	// 包装费类型不能为空
	public static final String SURCHARGEPRICE_PACKITEMCODE_ISNULL_EXCEPTION = "surchargePrice.bse.packItemCodeIsNullException";
	
	// 数量不能为空
	public static final String SURCHARGEPRICE_NUM_ISNULL_EXCEPTION = "surchargePrice.bse.numIsNullException";
	
	// 城市类别不能为空
	public static final String SURCHARGEPRICE_CITYTYPE_ISNULL_EXCEPTION = "surchargePrice.bse.cityTypeIsNullException"; 
	
	// 附加费类型不能为空
	public static final String SURCHARGEPRICE_EXTRAFEETYPE_ISNULL_EXCEPTION = "surchargePrice.bse.extrafeeTypeIsNullException"; 
	
	// 价格城市不能为空
	public static final String SURCHARGEPRICE_PRICECITY_ISNULL_EXCEPTION = "surchargePrice.bse.priceCityIsNullException"; 
	
	// 上楼类型不能为空
	public static final String SURCHARGEPRICE_UPSTAIRSTYPE_ISNULL_EXCEPTION = "surchargePrice.bse.upstairsTypeIsNullException";
	
	// 代收货款类型不能为空
	public static final String SURCHARGEPRICE_COLLECTIONTYPE_ISNULL_EXCEPTION = "surchargePrice.bse.collectionTypeIsNullException";
	
	// 提货方式不能为空
	public static final String SURCHARGEPRICE_PICKUPWAY_ISNULL_EXCEPTION = "surchargePrice.bse.pickUpWayIsNullException";
	
	// 轻重货类型不能为空
	public static final String SURCHARGEPRICE_GOODSTYPE_ISNULL_EXCEPTION = "surchargePrice.bse.goodsTypeIsNullException";
	
	// 单项服务项目个数不能为空
	public static final String SURCHARGEPRICE_UNITERMCOUN_ISNULL_EXCEPTION = "surchargePrice.bse.unitermCounIsNullException";

	// 出发价格城市不能为空
	public static final String SURCHARGEPRICE_SEND_PRICE_CITY_ISNULL_EXCEPTION = "surchargePrice.bse.sendPriceCityIsNullException";

	public ChargeException(String errCode) {
		super(errCode);
		super.errCode = errCode;
	}

	public ChargeException(String errCode, Object... para) {
		super(errCode, para);
		super.errCode = errCode;
	}
}