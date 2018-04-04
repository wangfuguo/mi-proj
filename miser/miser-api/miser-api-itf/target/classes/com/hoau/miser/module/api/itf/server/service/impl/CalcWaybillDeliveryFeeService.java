package com.hoau.miser.module.api.itf.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.api.itf.api.server.IDeliveryChargeService;
import com.hoau.miser.module.api.itf.api.shared.define.WaybillCalculateType;
import com.hoau.miser.module.api.itf.api.shared.dto.QuerySurchargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 计算送货费Service
 * 
 * @author 蒋落琛
 * @date 2016-6-1下午4:36:54
 */
@Service
public class CalcWaybillDeliveryFeeService extends
		AbstractCalculateBaseService<QuerySurchargeDto, SurchargeDto> {

	/**
	 * 送货费定义service
	 */
	@Resource
	private IDeliveryChargeService deliveryChargeService;

	@Override
	public SurchargeDto calculateCharge(QuerySurchargeDto queryDeliveryChargeDto) {
		return deliveryChargeService.calculateDeliveryCharge(
				queryDeliveryChargeDto.getStartOrgCode(),
				queryDeliveryChargeDto.getArrivalOrgCode(),
				queryDeliveryChargeDto.getProductType(),
				queryDeliveryChargeDto.getSubProductType(),
				queryDeliveryChargeDto.getDeliveryMethod(),
				queryDeliveryChargeDto.getWeight(),
				queryDeliveryChargeDto.getVolume(),
				queryDeliveryChargeDto.getCustomerCode(),
				queryDeliveryChargeDto.getOmsBizType(),
				queryDeliveryChargeDto.getOrderOrign(),
				queryDeliveryChargeDto.isInternalBelt(),
				queryDeliveryChargeDto.isHistory(),
				queryDeliveryChargeDto.getBillTime(),
				queryDeliveryChargeDto.getOriginPositionInfo(),
				queryDeliveryChargeDto.getDestPositionInfo());
	}

	@Override
	public WaybillCalculateType getCalculateType() {
		return WaybillCalculateType.CALC_DELIVERYFEE;
	}

	@Override
	public SurchargeDto calculateStandardCharge(
			QuerySurchargeDto queryDeliveryChargeDto) {
		return deliveryChargeService.calculateStandardDeliveryCharge(
				queryDeliveryChargeDto.getStartOrgCode(),
				queryDeliveryChargeDto.getArrivalOrgCode(),
				queryDeliveryChargeDto.getProductType(),
				queryDeliveryChargeDto.getSubProductType(),
				queryDeliveryChargeDto.getDeliveryMethod(),
				queryDeliveryChargeDto.getWeight(),
				queryDeliveryChargeDto.getVolume(),
				queryDeliveryChargeDto.getCustomerCode(),
				queryDeliveryChargeDto.getOmsBizType(),
				queryDeliveryChargeDto.getOrderOrign(),
				queryDeliveryChargeDto.isInternalBelt(),
				queryDeliveryChargeDto.isHistory(),
				queryDeliveryChargeDto.getBillTime(),
				queryDeliveryChargeDto.getOriginPositionInfo(),
				queryDeliveryChargeDto.getDestPositionInfo());
	}
}
