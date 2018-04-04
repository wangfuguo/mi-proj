package com.hoau.miser.module.api.itf.api.server;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 燃油费service
 *
 * @author 蒋落琛
 * @date 2016-6-14下午2:59:24
 */
public interface IFuelChargeService {

	/**
	 * 计算燃油费
	 * 
	 * @param departOrgCode
	 * @param destinationOrgCode
	 * @param productCode
	 * @param weight
	 * @param volume
	 * @param lightHeavyType
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-14下午2:59:17
	 * @update
	 */
	SurchargeDto calculateFuelCharge(String productType,
			String orderOrign, BigDecimal weight,
			BigDecimal volume, String startOrgCode, String arrivalOrgCode, String omsBizType, String customerCode,
			boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);
	
	/**
	 * 计算标准燃油费
	 * 
	 * @param departOrgCode
	 * @param destinationOrgCode
	 * @param productCode
	 * @param weight
	 * @param volume
	 * @param lightHeavyType
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-14下午2:59:17
	 * @update
	 */
	SurchargeDto calculateStandardFuelCharge(String productType,
			String orderOrign, BigDecimal weight,
			BigDecimal volume, String startOrgCode, String arrivalOrgCode, String omsBizType, String customerCode,
			boolean isInternalBelt, boolean isHistory, Date billTime);
}
