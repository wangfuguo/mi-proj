package com.hoau.miser.module.api.itf.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.api.itf.api.server.ISurchargePriceService;
import com.hoau.miser.module.api.itf.api.shared.define.WaybillCalculateType;
import com.hoau.miser.module.api.itf.api.shared.dto.QueryPackChargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 计算包装费Service
 * 
 * @author 蒋落琛
 * @date 2016-6-1下午5:14:38
 */
@Service
public class CalcWaybillPackingFeeService extends
		AbstractCalculateBaseService<QueryPackChargeDto, SurchargeDto> {

	/**
	 * 附加费service
	 */
	@Resource
	private ISurchargePriceService surchargePriceService;

	@Override
	public SurchargeDto calculateCharge(QueryPackChargeDto params) {
		return surchargePriceService.calculatePackPrice(params);
	}

	@Override
	public WaybillCalculateType getCalculateType() {
		return WaybillCalculateType.CALC_PACKINGFEE;
	}

	@Override
	public SurchargeDto calculateStandardCharge(QueryPackChargeDto params) {
		return surchargePriceService.calculateStandardPackPrice(params);
	}
}
