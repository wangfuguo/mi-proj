package com.hoau.miser.module.api.itf.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.api.itf.api.server.IFuelChargeService;
import com.hoau.miser.module.api.itf.api.shared.define.WaybillCalculateType;
import com.hoau.miser.module.api.itf.api.shared.dto.QuerySurchargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 燃油费Service
 *
 * @author 蒋落琛
 * @date 2016-6-14下午2:58:16
 */
@Service
public class CalcWaybillFuelFeeService extends
		AbstractCalculateBaseService<QuerySurchargeDto, SurchargeDto> {

	/**
	 * 燃油费定义service
	 */
	@Resource
	private IFuelChargeService fuelChargeService;

	@Override
	public SurchargeDto calculateCharge(
			QuerySurchargeDto queryDeliveryChargeDto) {
		return fuelChargeService.calculateFuelCharge(
				queryDeliveryChargeDto.getProductType(),
				queryDeliveryChargeDto.getOrderOrign(),
				queryDeliveryChargeDto.getWeight(),
				queryDeliveryChargeDto.getVolume(),
				queryDeliveryChargeDto.getStartOrgCode(),
				queryDeliveryChargeDto.getArrivalOrgCode(),
				queryDeliveryChargeDto.getOmsBizType(),
				queryDeliveryChargeDto.getCustomerCode(),
				queryDeliveryChargeDto.isInternalBelt(),
				queryDeliveryChargeDto.isHistory(),
				queryDeliveryChargeDto.getBillTime(),
				queryDeliveryChargeDto.getOriginPositionInfo(),
				queryDeliveryChargeDto.getDestPositionInfo());
	}

	@Override
	public WaybillCalculateType getCalculateType() {
		return WaybillCalculateType.CALC_FUELFEE;
	}

	@Override
	public SurchargeDto calculateStandardCharge(
			QuerySurchargeDto queryDeliveryChargeDto) {
		return fuelChargeService.calculateStandardFuelCharge(
				queryDeliveryChargeDto.getProductType(),
				queryDeliveryChargeDto.getOrderOrign(),
				queryDeliveryChargeDto.getWeight(),
				queryDeliveryChargeDto.getVolume(),
				queryDeliveryChargeDto.getStartOrgCode(),
				queryDeliveryChargeDto.getArrivalOrgCode(),
				queryDeliveryChargeDto.getOmsBizType(),
				queryDeliveryChargeDto.getCustomerCode(),
				queryDeliveryChargeDto.isInternalBelt(),
				queryDeliveryChargeDto.isHistory(),
				queryDeliveryChargeDto.getBillTime());
	}
}
