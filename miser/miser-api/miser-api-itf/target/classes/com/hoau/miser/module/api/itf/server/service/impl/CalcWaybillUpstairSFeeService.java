package com.hoau.miser.module.api.itf.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.api.itf.api.server.IDeliveryUpstairsService;
import com.hoau.miser.module.api.itf.api.shared.define.WaybillCalculateType;
import com.hoau.miser.module.api.itf.api.shared.dto.QuerySurchargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 计算上楼费Service
 * 
 * @author 蒋落琛
 * @date 2016-6-1下午5:15:21
 */
@Service
public class CalcWaybillUpstairSFeeService extends
		AbstractCalculateBaseService<QuerySurchargeDto, SurchargeDto> {

	/**
	 * 上楼费定义service
	 */
	@Resource
	private IDeliveryUpstairsService deliveryUpstairsService;

	@Override
	public SurchargeDto calculateCharge(
			QuerySurchargeDto queryUpstairsChargeDto) {
		return deliveryUpstairsService.calculateDeliveryUpstairsCharge(
				queryUpstairsChargeDto.getDeliveryMethod(),
				queryUpstairsChargeDto.getProductType(),
				queryUpstairsChargeDto.getWeight(),
				queryUpstairsChargeDto.getVolume(),
				queryUpstairsChargeDto.getOrderOrign(),
				queryUpstairsChargeDto.getOmsBizType(),
				queryUpstairsChargeDto.getCustomerCode(),
				queryUpstairsChargeDto.isInternalBelt(),
				queryUpstairsChargeDto.isHistory(),
				queryUpstairsChargeDto.getBillTime());
	}

	@Override
	public WaybillCalculateType getCalculateType() {
		return WaybillCalculateType.CALC_UPSTAIRSFEE;
	}

	@Override
	public SurchargeDto calculateStandardCharge(QuerySurchargeDto queryUpstairsChargeDto) {
		return deliveryUpstairsService.calculateStandardDeliveryUpstairsCharge(
				queryUpstairsChargeDto.getDeliveryMethod(),
				queryUpstairsChargeDto.getProductType(),
				queryUpstairsChargeDto.getWeight(),
				queryUpstairsChargeDto.getVolume(),
				queryUpstairsChargeDto.getOrderOrign(),
				queryUpstairsChargeDto.getOmsBizType(),
				queryUpstairsChargeDto.getCustomerCode(),
				queryUpstairsChargeDto.isInternalBelt(),
				queryUpstairsChargeDto.isHistory(),
				queryUpstairsChargeDto.getBillTime());
	}
}
