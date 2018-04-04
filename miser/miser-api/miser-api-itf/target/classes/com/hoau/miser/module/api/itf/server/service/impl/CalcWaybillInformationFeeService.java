package com.hoau.miser.module.api.itf.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.api.itf.api.server.ISurchargePriceService;
import com.hoau.miser.module.api.itf.api.shared.define.WaybillCalculateType;
import com.hoau.miser.module.api.itf.api.shared.dto.QuerySurchargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 信息费
 *
 * @author 蒋落琛
 * @date 2016-6-13下午2:35:36
 */
@Service
public class CalcWaybillInformationFeeService extends AbstractCalculateBaseService<QuerySurchargeDto, SurchargeDto> {

	/**
	 * 附加费service
	 */
	@Resource
	private ISurchargePriceService surchargePriceService;

	@Override
	public SurchargeDto calculateCharge(
			QuerySurchargeDto queryDeliveryChargeDto) {
		return surchargePriceService.calculateInformationCharge(
				queryDeliveryChargeDto.getProductType(),
				queryDeliveryChargeDto.getCustomerCode(),
				queryDeliveryChargeDto.getOrderOrign(),
				queryDeliveryChargeDto.getWeight(),
				queryDeliveryChargeDto.getVolume(),
				queryDeliveryChargeDto.getStartOrgCode(),
				queryDeliveryChargeDto.getArrivalOrgCode(),
				queryDeliveryChargeDto.isSmsService(),
				queryDeliveryChargeDto.getOmsBizType(),
				queryDeliveryChargeDto.isInternalBelt(),
				queryDeliveryChargeDto.isHistory(),
				queryDeliveryChargeDto.getBillTime(),
				queryDeliveryChargeDto.getOriginPositionInfo(),
				queryDeliveryChargeDto.getDestPositionInfo());
	}

	@Override
	public WaybillCalculateType getCalculateType() {
		return WaybillCalculateType.CALC_INFORMATIONFEE;
	}

	@Override
	public SurchargeDto calculateStandardCharge(
			QuerySurchargeDto queryDeliveryChargeDto) {
		return surchargePriceService.calculateStandardInformationCharge(
				queryDeliveryChargeDto.getProductType(),
				queryDeliveryChargeDto.getCustomerCode(),
				queryDeliveryChargeDto.getOrderOrign(),
				queryDeliveryChargeDto.getWeight(),
				queryDeliveryChargeDto.getVolume(),
				queryDeliveryChargeDto.getStartOrgCode(),
				queryDeliveryChargeDto.getArrivalOrgCode(),
				queryDeliveryChargeDto.isSmsService(),
				queryDeliveryChargeDto.getOmsBizType(),
				queryDeliveryChargeDto.isInternalBelt(),
				queryDeliveryChargeDto.isHistory(),
				queryDeliveryChargeDto.getBillTime());
	}
}
