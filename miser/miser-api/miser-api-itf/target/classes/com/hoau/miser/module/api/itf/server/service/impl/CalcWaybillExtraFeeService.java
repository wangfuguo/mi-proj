package com.hoau.miser.module.api.itf.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.api.itf.api.server.ISpecialServerPriceService;
import com.hoau.miser.module.api.itf.api.shared.define.WaybillCalculateType;
import com.hoau.miser.module.api.itf.api.shared.dto.SpecialServerCalculateResultDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SpecialServerPriceConditionDto;

/**
 * 特服费单项费用计算Service
 *
 * @author 蒋落琛
 * @date 2016-6-15下午2:01:59
 */
@Service
public class CalcWaybillExtraFeeService
		extends
		AbstractCalculateBaseService<SpecialServerPriceConditionDto, SpecialServerCalculateResultDto> {

	/**
	 * 特服费定义service
	 */
	@Resource
	private ISpecialServerPriceService specialServerPriceService;

	@Override
	public SpecialServerCalculateResultDto calculateCharge(
			SpecialServerPriceConditionDto specialServerPriceConditionDto) {
		return specialServerPriceService
				.calculateUnitermSpecialPrice(specialServerPriceConditionDto);
	}

	@Override
	public WaybillCalculateType getCalculateType() {
		return WaybillCalculateType.CALC_EXTRASERVICEFEE;
	}

	@Override
	public SpecialServerCalculateResultDto calculateStandardCharge(
			SpecialServerPriceConditionDto specialServerPriceConditionDto) {
		return specialServerPriceService
				.calculateStandardUnitermSpecialPrice(specialServerPriceConditionDto);
	}
}
