package com.hoau.miser.module.api.itf.api.server;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.dto.PackChargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.QueryPackChargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.QuerySurchargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 附加费计算
 *
 * @author 蒋落琛
 * @date 2016-6-8下午6:27:12
 */
public interface ISurchargePriceService {

    /**
     * 计算提货费
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param pickUpWay
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    public SurchargeDto calculatePickupPrice(String productType,
                                             String customerCode, String orderOrign, BigDecimal weight,
                                             BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                             String pickUpWay, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);

    /**
     * 保价费
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param insuredRate
     * @param insuredMoney
     * @param omsBizType
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    public SurchargeDto calculateInsuredPrice(String productType,
                                              String customerCode, String orderOrign, BigDecimal weight,
                                              BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                              BigDecimal insuredRate,
                                              BigDecimal insuredMoney, String omsBizType, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);

    /**
     * 保价率
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    public SurchargeDto calculateInsuredRate(String productType,
                                             String customerCode, String orderOrign, BigDecimal weight,
                                             BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                             boolean isInternalBelt,
                                             boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);

    /**
     * 工本费
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param omsBizType
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    public SurchargeDto calculateIssuingCharge(String productType,
                                               String customerCode, String orderOrign, BigDecimal weight,
                                               BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                               String omsBizType,
                                               boolean isInternalBelt,
                                               boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);

    /**
     * 信息费
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param isSmsService
     * @param omsBizType
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    public SurchargeDto calculateInformationCharge(String productType,
                                                   String customerCode, String orderOrign, BigDecimal weight,
                                                   BigDecimal volume, String startOrgCode, String arrivalOrgCode, boolean isSmsService, String omsBizType, boolean isInternalBelt,
                                                   boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);

    /**
     * 初始加载包装费
     *
     * @param params
     * @return
     */
    public List<PackChargeDto> initialLoadPackCharge(QuerySurchargeDto params);

    /**
     * 包装费
     *
     * @param params
     * @return
     */
    public SurchargeDto calculatePackPrice(QueryPackChargeDto params);


    /**
     * 代收货款费率
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param collectionType
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    public SurchargeDto calculateCollectionPayRate(String productType,
                                                   String customerCode, String orderOrign, BigDecimal weight,
                                                   BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                   String collectionType, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);

    /**
     * 代收货款手续费
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param collectionType
     * @param collectionPayAmount
     * @param collectionPayRate
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    public SurchargeDto calculateCollectionPayCharge(String productType,
                                                     String customerCode, String orderOrign, BigDecimal weight,
                                                     BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                     String collectionType, BigDecimal collectionPayAmount, BigDecimal collectionPayRate, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);

    // TODO  以下为标准费用信息

    /**
     * 计算标准提货费
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param pickUpWay
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @return
     */
    public SurchargeDto calculateStandardPickupPrice(String productType,
                                                     String customerCode, String orderOrign, BigDecimal weight,
                                                     BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                     String pickUpWay, boolean isInternalBelt, boolean isHistory, Date billTime);

    /**
     * 标准保价费
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param insuredRate
     * @param insuredMoney
     * @param omsBizType
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @return
     */
    public SurchargeDto calculateStandardInsuredPrice(String productType,
                                                      String customerCode, String orderOrign, BigDecimal weight,
                                                      BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                      BigDecimal insuredRate,
                                                      BigDecimal insuredMoney, String omsBizType, boolean isInternalBelt, boolean isHistory, Date billTime);

    /**
     * 标准保价率
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @return
     */
    public SurchargeDto calculateStandardInsuredRate(String productType,
                                                     String customerCode, String orderOrign, BigDecimal weight,
                                                     BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                     boolean isInternalBelt,
                                                     boolean isHistory, Date billTime);

    /**
     * 标准工本费
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param omsBizType
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @return
     */
    public SurchargeDto calculateStandardIssuingCharge(String productType,
                                                       String customerCode, String orderOrign, BigDecimal weight,
                                                       BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                       String omsBizType,
                                                       boolean isInternalBelt,
                                                       boolean isHistory, Date billTime);

    /**
     * 标准信息费
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param isSmsService
     * @param omsBizType
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @return
     */
    public SurchargeDto calculateStandardInformationCharge(String productType,
                                                           String customerCode, String orderOrign, BigDecimal weight,
                                                           BigDecimal volume, String startOrgCode, String arrivalOrgCode, boolean isSmsService, String omsBizType, boolean isInternalBelt,
                                                           boolean isHistory, Date billTime);


    /**
     * 标准包装费
     *
     * @param params
     * @return
     */
    public SurchargeDto calculateStandardPackPrice(QueryPackChargeDto params);


    /**
     * 标准代收货款费率
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param collectionType
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    public SurchargeDto calculateStandardCollectionPayRate(String productType, String customerCode, String orderOrign,
                                                           BigDecimal weight, BigDecimal volume, String startOrgCode,
                                                           String arrivalOrgCode, String collectionType, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);

    /**
     * 标准代收货款手续费
     *
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @param weight
     * @param volume
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param collectionType
     * @param collectionPayAmount
     * @param collectionPayRate
     * @param isInternalBelt
     * @param isHistory
     * @param billTime
     * @param originPositionInfo
     * @param destPositionInfo
     * @return
     */
    public SurchargeDto calculateStandardCollectionPayCharge(String productType,
                                                             String customerCode, String orderOrign, BigDecimal weight,
                                                             BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                             String collectionType, BigDecimal collectionPayAmount, BigDecimal collectionPayRate, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo);
}
