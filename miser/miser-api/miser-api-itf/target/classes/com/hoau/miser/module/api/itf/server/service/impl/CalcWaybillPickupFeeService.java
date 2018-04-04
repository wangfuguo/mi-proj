package com.hoau.miser.module.api.itf.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.api.itf.api.server.ISurchargePriceService;
import com.hoau.miser.module.api.itf.api.shared.define.WaybillCalculateType;
import com.hoau.miser.module.api.itf.api.shared.dto.QuerySurchargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 提货费Service
 *
 * @author 蒋落琛
 * @date 2016-6-14上午9:48:02
 */
@Service
public class CalcWaybillPickupFeeService
		extends
		AbstractCalculateBaseService<QuerySurchargeDto, SurchargeDto> {

	/**
	 * 附加费service
	 */
	@Resource
	private ISurchargePriceService surchargePriceService;

	@Override
	public SurchargeDto calculateCharge(
			QuerySurchargeDto queryDeliveryChargeDto) {
		return surchargePriceService.calculatePickupPrice(
				queryDeliveryChargeDto.getProductType(),
				queryDeliveryChargeDto.getCustomerCode(),
				queryDeliveryChargeDto.getOrderOrign(),
				queryDeliveryChargeDto.getWeight(),
				queryDeliveryChargeDto.getVolume(),
				queryDeliveryChargeDto.getStartOrgCode(),
				queryDeliveryChargeDto.getArrivalOrgCode(),
				queryDeliveryChargeDto.getPickUpWay(),
				queryDeliveryChargeDto.isInternalBelt(),
				queryDeliveryChargeDto.isHistory(),
				queryDeliveryChargeDto.getBillTime(),
				queryDeliveryChargeDto.getOriginPositionInfo(),
				queryDeliveryChargeDto.getDestPositionInfo());
	}

	@Override
	public WaybillCalculateType getCalculateType() {
		return WaybillCalculateType.CALC_PICKUPFEE;
	}

	@Override
	public SurchargeDto calculateStandardCharge(
			QuerySurchargeDto queryDeliveryChargeDto) {
		return surchargePriceService.calculateStandardPickupPrice(
				queryDeliveryChargeDto.getProductType(),
				queryDeliveryChargeDto.getCustomerCode(),
				queryDeliveryChargeDto.getOrderOrign(),
				queryDeliveryChargeDto.getWeight(),
				queryDeliveryChargeDto.getVolume(),
				queryDeliveryChargeDto.getStartOrgCode(),
				queryDeliveryChargeDto.getArrivalOrgCode(),
				queryDeliveryChargeDto.getPickUpWay(),
				queryDeliveryChargeDto.isInternalBelt(),
				queryDeliveryChargeDto.isHistory(),
				queryDeliveryChargeDto.getBillTime());
	}
}
