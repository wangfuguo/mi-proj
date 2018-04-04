package com.hoau.miser.module.api.itf.api.server;

import java.math.BigDecimal;
import java.util.Date;

import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 计算送货费接口
 *
 * @author 蒋落琛
 * @version V1.0
 * @Description:
 * @date 2016/8/2 13:42:57
 */
public interface IDeliveryChargeService {

    /**
     * 计算标准送货费
     *
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param productType
     * @param subProductType
     * @param deliveryMethod
     * @param weight
     * @param volume
     * @param customerCode
     * @param omsBizType
     * @param orderOrign
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    SurchargeDto calculateDeliveryCharge(String startOrgCode, String arrivalOrgCode, String productType, String subProductType,
                                         String deliveryMethod, BigDecimal weight, BigDecimal volume, String customerCode, String omsBizType, String orderOrign, boolean isInternalBelt, boolean isHistory, Date billTime,
                                         OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);

    /**
     * 计算标准送货费
     *
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param productType
     * @param subProductType
     * @param deliveryMethod
     * @param weight
     * @param volume
     * @param customerCode
     * @param omsBizType
     * @param orderOrign
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    SurchargeDto calculateStandardDeliveryCharge(String startOrgCode, String arrivalOrgCode, String productType, String subProductType,
                                                 String deliveryMethod, BigDecimal weight, BigDecimal volume, String customerCode, String omsBizType, String orderOrign, boolean isInternalBelt, boolean isHistory, Date billTime,
                                                 OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);
}
