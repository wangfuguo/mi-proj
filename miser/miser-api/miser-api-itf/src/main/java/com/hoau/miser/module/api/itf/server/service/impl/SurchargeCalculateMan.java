package com.hoau.miser.module.api.itf.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.hoau.miser.module.api.itf.api.server.*;
import com.hoau.miser.module.api.itf.api.shared.domain.*;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceEventTyQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.util.CplbUtils;
import com.hoau.miser.module.api.itf.server.util.PmsUtils;
import com.hoau.miser.module.api.itf.server.util.StrUtil;

/**
 * 附加费管理类
 *
 * @author 蒋落琛
 * @date 2016-6-7下午4:35:51
 */
@Service
public class SurchargeCalculateMan {

    /**
     * 标准附加费
     */
    @Resource
    private IPriceExtrafeeStandardTyService priceExtrafeeStandardTyService;

    @Resource
    private IBseCustomerTyService bseCustomerTyService;

    @Resource
    private IPriceEventTyService priceEventTyService;

    @Resource
    private IPriceSectionTyService priceSectionTyService;

    @Resource
    private IDiscountCustomerTyService discountCustomerTyService;

    @Resource
    private ICityTypeTyService cityTypeTyService;

    @Resource
    private IPriceDeliveryFeeCityTypeTyService priceDeliveryFeeCityTypeTyService;

    @Resource
    private ICorpPriceCityService corpPriceCityService;

    @Resource
    private IDistributionFeeCityTypeTyService distributionFeeCityTypeTyService;

    @Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;

    @Resource
    private ICollectDeliveryFeeTyService collectDeliveryFeeTyService;

    /**
     * 判断是否为有效期内的签约客户
     *
     * @return
     * @author 蒋落琛
     * @date 2016-6-2下午3:03:53
     * @update
     */
    public boolean isContractedCustomer(String customerCode) {
        boolean isEffective = false;
        if (!StringUtil.isEmpty(customerCode)) {
            // 查询客户信息
            BseCustomerTyEntity customer = bseCustomerTyService.queryBseCustomerByCustNo(customerCode);
            if (customer != null) {
                if (PmsUtils.isBetween(customer.getContractStartTime(), customer.getContractEndTime())) {
                    isEffective = true;
                }
            }
        }
        return isEffective;
    }

    /**
     * 根据客户编号，客户的价卡规则有效性判断客户是否有合同
     *
     * @return
     * @author 蒋落琛
     * @date 2016-6-2下午3:03:53
     * @update
     */
    public boolean isContractedCustomerAndPc(String customerCode) {
        boolean isEffective = false;
        if (!StringUtil.isEmpty(customerCode)) {
            // 查询客户信息
            BseCustomerTyEntity customer = bseCustomerTyService.queryBseCustomerByCustNo(customerCode);
            if (customer != null) {
                if (PmsUtils.isBetween(customer.getContractStartTime(), customer.getContractEndTime(), customer.getPcStartTime(), customer.getPcEndTime())) {
                    isEffective = true;
                }
            }
        }
        return isEffective;
    }

    /**
     * 查询客户的折扣优惠分段信息
     *
     * @param feeType
     * @param productType
     * @param customerCode
     * @return
     * @author 蒋落琛
     * @date 2016-6-8下午7:09:35
     * @update
     */
    public List<PriceSectionSubEntity> queryCustomerDiscountPriceSection(String feeType, String productType, String customerCode, boolean isHistory, Date billTime) {
        // 优惠分段信息
        List<PriceSectionSubEntity> priceSEctionSubList = null;
        // 查询客户折扣信息
        PriceQueryParam baseTyParam = new PriceQueryParam();
        baseTyParam.setCustNo(customerCode);
        baseTyParam.setTransTypeCode(productType);
        baseTyParam.setHistory(isHistory);
        baseTyParam.setBillTime(billTime);
        DiscountCustomerTyEntity discountCustomer = discountCustomerTyService.queryDiscountCustomerByParam(baseTyParam);
        // 查询优惠分段明细
        if (discountCustomer != null) {
            if (StringUtil.isNotEmpty(discountCustomer.getSectionByType(feeType))) {
                priceSEctionSubList = priceSectionTyService.querySectionDetailByCode(discountCustomer.getSectionByType(feeType));
            }
        }
        return priceSEctionSubList;
    }

    /**
     * @param transTypeCode 产品编码
     * @param feeType       需要获取的费用类型
     * @return ArrayList<Tjzlfdzk[]>    返回类型
     * @throws
     * @Title: getTjzlfdzksFromEvent
     * @Description: 从所有满足条件的活动中获取给定费用的费用分段列表
     */
    public List<List<PriceSectionSubEntity>> querySectionsFromEvent(String feeType, String startOrgCode, String arrivalOrgCode, String transTypeCode, String customerCode, String orderOrign, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        //获取活动信息
        List<PriceEventEntity> events = getEvents(startOrgCode, arrivalOrgCode, transTypeCode, customerCode, orderOrign, isHistory, billTime, originPositionInfo, destPositionInfo);
        //如果没有活动或者没有产品，返回空
        if (events == null || StrUtil.isEmpty(transTypeCode)) {
            return null;
        }
        List<String> sectionCodes = new ArrayList<String>(events.size());
        List<List<PriceSectionSubEntity>> sections = new ArrayList<List<PriceSectionSubEntity>>();
        for (int i = 0; i < events.size(); i++) {
            PriceEventEntity event = events.get(i);
            List<PriceEventDiscountSubEntity> eventDetails = event.getPriceEventDiscountSubs();
            if (eventDetails != null && eventDetails.size() > 0) {
                for (int j = 0; j < eventDetails.size(); j++) {
                    PriceEventDiscountSubEntity eventDetail = eventDetails.get(j);
                    if (transTypeCode.equals(eventDetail.getTransTypeCode())) {
                        String sectionCode = eventDetail.getSectionByType(feeType);
                        if (!StringUtil.isEmpty(sectionCode) && !sectionCodes.contains(sectionCode)) {
                            List<PriceSectionSubEntity> priceSectionList = priceSectionTyService.querySectionDetailByCode(sectionCode);
                            if (priceSectionList != null && priceSectionList.size() > 0) {
                                sections.add(priceSectionList);
                                sectionCodes.add(sectionCode);
                            }
                            //一个活动中的同一种产品只能定义一条明细,如果有多个，只取一个
                            break;
                        }
                    }
                }
            }
        }
        return sections;
    }

    /**
     * 查询标准附加费
     *
     * @param feeType
     * @param productType
     * @return
     * @author 蒋落琛
     * @date 2016-6-8下午7:29:15
     * @update
     */
    public List<PriceExtrafeeStandardEntity> queryPriceExtrafeeStandard(String feeType, String productType, boolean isHistory, Date billTime) {
        PriceExtrafeeStandardEntity params = new PriceExtrafeeStandardEntity();
        // 附加费类型
        params.setType(feeType);
        // 运输类型
        params.setTransTypeCode(productType);
        params.setHistory(isHistory);
        params.setBillTime(billTime);
        List<PriceExtrafeeStandardEntity> pList = priceExtrafeeStandardTyService.queryListByParam(params);
        return pList;
    }

    /**
     * 获取此运单参加的所有活动信息
     *
     * @param startOrgCode
     * @param arrivalOrgCode
     * @param productType
     * @param customerCode
     * @param orderOrign
     * @return
     * @author 蒋落琛
     * @date 2016-6-7下午4:32:20
     * @update
     */
    public List<PriceEventEntity> getEvents(String startOrgCode, String arrivalOrgCode, String productType, String customerCode, String orderOrign, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        //必须要有线路及产品
        if (StrUtil.isEmptyAny(startOrgCode, arrivalOrgCode, productType)) {
            return null;
        }
        // 发货区域活动
        PriceEventTyQueryParam queryParam = new PriceEventTyQueryParam();
        queryParam.setSendOrgCode(startOrgCode);
        queryParam.setArrivalOrgCode(arrivalOrgCode);
        queryParam.setCustomerCode(customerCode);
        queryParam.setTransportType(productType);
        queryParam.setOrderChannel(orderOrign);
        queryParam.setHistory(isHistory);
        queryParam.setQueryTime(billTime);
        queryParam.setOriginPositionInfo(originPositionInfo);
        queryParam.setDestPositionInfo(destPositionInfo);
        List<PriceEventEntity> sendPriceEvnetList = priceEventTyService.queryPriceEventWithDetailByCondition(queryParam);
        return sendPriceEvnetList;
    }

    /**
     * @param transTypeCode 产品编码
     * @param feeType       需要获取的费用类型
     * @return ArrayList<Tjzlfdzk[]>    返回类型
     * @throws
     * @Title: getTjzlfdzksFromEvent
     * @Description: 从所有满足条件的活动中获取给定费用的费用分段列表
     */
    public List<List<PriceSectionSubEntity>> getSectionsFromEvent(String feeType, String startOrgCode, String arrivalOrgCode, String transTypeCode, String customerCode, String orderOrign, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        // 易安装使用定日达活动折扣
        if (CplbUtils.isYAZ(transTypeCode) || CplbUtils.isYRH(transTypeCode) || CplbUtils.isYBG(transTypeCode)) {
            transTypeCode = CplbUtils.TYPE_DRD;
        }
        //偏线使用经济快运活动折扣
        if (CplbUtils.isPX(transTypeCode)) {
            transTypeCode = CplbUtils.TYPE_LD;
        }
        List<List<PriceSectionSubEntity>> sections = querySectionsFromEvent(feeType, startOrgCode, arrivalOrgCode, transTypeCode, customerCode, orderOrign, isHistory, billTime, originPositionInfo, destPositionInfo);
        return sections;
    }

    /**
     * @param feeType 费用类型
     * @param weight  重量
     * @param volumn  体积
     * @return Double   活动内最低收费，如果没有则返回null
     * @throws
     * @Title: getWightAndVolumnMinInSections
     * @Description: 根据体积重量获取活动最低收费
     */
    public BigDecimal getWightAndVolumnMinInSections(String feeType, String startOrgCode, String arrivalOrgCode, String transTypeCode, String customerCode, String orderOrign, BigDecimal weight, BigDecimal volumn, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        BigDecimal finalMinValue = PmsUtils.getMinInSections(getSectionsFromEvent(feeType, startOrgCode, arrivalOrgCode, transTypeCode, customerCode, orderOrign, isHistory, billTime, originPositionInfo, destPositionInfo), weight, volumn);
        return finalMinValue;
    }

    /**
     * 根据分段编码查询费用信息
     *
     * @param section
     * @param weight
     * @return
     * @author 蒋落琛
     * @date 2016-6-15上午11:17:58
     * @update
     */
    public BigDecimal querySectionPrice(String section, BigDecimal weight, BigDecimal volume) {
        BigDecimal price = null;
        // 优惠分段信息
        List<PriceSectionSubEntity> priceSEctionSubList = priceSectionTyService.querySectionDetailByCode(section);
        if (priceSEctionSubList != null && priceSEctionSubList.size() > 0) {
            price = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
        }
        return price;
    }

    /**
     * 根据目的地与产品类别以及重量体积查询城市类别送货费信息
     *
     * @param arrivalOrgCode
     * @param productType
     * @param weight
     * @param volume
     * @return
     * @author 蒋落琛
     * @date 2016-6-29上午10:50:35
     * @update
     */
    public BigDecimal queryDeliveryFeeCityType(String arrivalOrgCode,
                                               String productType, BigDecimal weight, BigDecimal volume, OrgPositionInfoTyEntity orgPositionInfoTyEntity) {
        BigDecimal fee = null;
        // 查询目的地城市城市信息
        // 目的地
        OrgPositionInfoTyEntity originPositionInfo = null;
        OrgPositionInfoTyEntity currOrgPositionInfoTyEntity = orgPositionInfoTyEntity;
        if (currOrgPositionInfoTyEntity != null
                && !StringUtil.isEmpty(currOrgPositionInfoTyEntity
                .getProvinceCode())
                && !StringUtil.isEmpty(currOrgPositionInfoTyEntity.getCityCode()) && !StringUtil
                .isEmpty(currOrgPositionInfoTyEntity.getCountyCode())) {
            originPositionInfo = currOrgPositionInfoTyEntity;
        } else if (!StringUtil.isEmpty(arrivalOrgCode)) {
            originPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(arrivalOrgCode);
            if (originPositionInfo == null) {
                throw new ChargeException("目的地门店省市区信息为空！");
            }
        } else {
            throw new ChargeException("目的地不能为空！");
        }
        /*CorpPriceCityEntity arrivalPriceCity = corpPriceCityService
                .queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_ARRIVAL);*/
        if (originPositionInfo != null) {
            // 查询城市类别信息
            CityTypeEntity cityTypeEntity = new CityTypeEntity();
            cityTypeEntity.setProvinceCode(originPositionInfo.getProvinceCode());
            cityTypeEntity.setCityCode(originPositionInfo.getCityCode());
            cityTypeEntity.setAreaCode(originPositionInfo.getCountyCode());
            List<CityTypeEntity> cityTypeList = cityTypeTyService
                    .queryCityTypeByEntity(cityTypeEntity);
            if (cityTypeList != null && cityTypeList.size() > 0 && StringUtil.isNotEmpty(cityTypeList.get(0).getCityType())) {
                // 查询城市类别对应的送货费信息
                PriceDeliveryFeeCityTypeEntity priceDeliveryFeeCityType = new PriceDeliveryFeeCityTypeEntity();
                priceDeliveryFeeCityType.setTransTypeCode(productType);
                priceDeliveryFeeCityType.setCityType(cityTypeList.get(0).getCityType());
                List<PriceDeliveryFeeCityTypeEntity> priceDeliveryFeeCityTypeList = priceDeliveryFeeCityTypeTyService.queryListByParam(priceDeliveryFeeCityType);
                if (priceDeliveryFeeCityTypeList != null && priceDeliveryFeeCityTypeList.size() > 0 && StringUtil.isNotEmpty(priceDeliveryFeeCityTypeList.get(0).getSectionItemCode())) {
                    // 优惠分段信息
                    List<PriceSectionSubEntity> priceSEctionSubList = priceSectionTyService.querySectionDetailByCode(priceDeliveryFeeCityTypeList.get(0).getSectionItemCode());
                    ;
                    fee = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
                }
            }
        }
        return fee;
    }

    /**
     * 根据目的地与产品类别以及重量体积查询城市类别配送费信息
     *
     * @param arrivalOrgCode
     * @param productType
     * @param weight
     * @param volume
     * @return
     * @author 蒋落琛
     * @date 2016-7-6下午3:03:52
     * @update
     */
    public BigDecimal queryDistributionFeeCityType(String arrivalOrgCode,
                                                   String productType, BigDecimal weight, BigDecimal volume, OrgPositionInfoTyEntity orgPositionInfoTyEntity) {
        BigDecimal fee = null;
        // 查询目的地城市城市信息
        // 目的地
        OrgPositionInfoTyEntity originPositionInfo = null;
        OrgPositionInfoTyEntity currOrgPositionInfoTyEntity = orgPositionInfoTyEntity;
        if (currOrgPositionInfoTyEntity != null
                && !StringUtil.isEmpty(currOrgPositionInfoTyEntity
                .getProvinceCode())
                && !StringUtil.isEmpty(currOrgPositionInfoTyEntity.getCityCode()) && !StringUtil
                .isEmpty(currOrgPositionInfoTyEntity.getCountyCode())) {
            originPositionInfo = currOrgPositionInfoTyEntity;
        } else if (!StringUtil.isEmpty(arrivalOrgCode)) {
            originPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(arrivalOrgCode);
            if (originPositionInfo == null) {
                throw new ChargeException("目的地门店省市区信息为空！");
            }
        } else {
            throw new ChargeException("目的地不能为空！");
        }
        /*CorpPriceCityEntity arrivalPriceCity = corpPriceCityService
                .queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_ARRIVAL);*/
        if (originPositionInfo != null) {
            // 查询城市类别信息
            CityTypeEntity cityTypeEntity = new CityTypeEntity();
            cityTypeEntity.setProvinceCode(originPositionInfo.getProvinceCode());
            cityTypeEntity.setCityCode(originPositionInfo.getCityCode());
            cityTypeEntity.setAreaCode(originPositionInfo.getCountyCode());
            List<CityTypeEntity> cityTypeList = cityTypeTyService
                    .queryCityTypeByEntity(cityTypeEntity);
            if (cityTypeList != null && cityTypeList.size() > 0 && StringUtil.isNotEmpty(cityTypeList.get(0).getCityType())) {
                // 查询城市类别对应的送货费信息
                PriceDeliveryFeeCityTypeEntity priceDeliveryFeeCityType = new PriceDeliveryFeeCityTypeEntity();
                priceDeliveryFeeCityType.setTransTypeCode(productType);
                priceDeliveryFeeCityType.setCityType(cityTypeList.get(0).getCityType());
                List<PriceDeliveryFeeCityTypeEntity> priceDeliveryFeeCityTypeList = distributionFeeCityTypeTyService.queryListByParam(priceDeliveryFeeCityType);
                if (priceDeliveryFeeCityTypeList != null && priceDeliveryFeeCityTypeList.size() > 0 && StringUtil.isNotEmpty(priceDeliveryFeeCityTypeList.get(0).getSectionItemCode())) {
                    // 优惠分段信息
                    List<PriceSectionSubEntity> priceSEctionSubList = priceSectionTyService.querySectionDetailByCode(priceDeliveryFeeCityTypeList.get(0).getSectionItemCode());
                    ;
                    fee = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
                }
            }
        }
        return fee;
    }

    /**
     * @return
     * @Description: 根据条件查询代收货款手续费信息
     * @params
     * @author 蒋落琛
     * @date 2016/7/14 18:36:43
     * @version V1.0
     */
    public PriceCollectDeliveryFeeEntity queryCollectionPayFeeByParams(String startOrgCode,
                                                                       String productType, String collectDeliveryType, boolean isHistory, Date billTime, OrgPositionInfoTyEntity orgPositionInfoTyEntity) {
        PriceCollectDeliveryFeeEntity priceCollectDeliveryFee = null;
        // 查询目的地城市城市信息
        // 目的地
        OrgPositionInfoTyEntity originPositionInfo = null;
        OrgPositionInfoTyEntity currOrgPositionInfoTyEntity = orgPositionInfoTyEntity;
        if (currOrgPositionInfoTyEntity != null
                && !StringUtil.isEmpty(currOrgPositionInfoTyEntity
                .getProvinceCode())
                && !StringUtil.isEmpty(currOrgPositionInfoTyEntity.getCityCode()) && !StringUtil
                .isEmpty(currOrgPositionInfoTyEntity.getCountyCode())) {
            originPositionInfo = currOrgPositionInfoTyEntity;
        } else if (!StringUtil.isEmpty(startOrgCode)) {
            originPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(startOrgCode);
            if (originPositionInfo == null) {
                throw new ChargeException("目的地门店省市区信息为空！");
            }
        } else {
            throw new ChargeException("目的地不能为空！");
        }
        CorpPriceCityEntity sendPriceCity = corpPriceCityService
                .queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_SEND);
        if (sendPriceCity != null) {
            // 查询代收货款手续费信息
            PriceCollectDeliveryFeeEntity priceCollectDeliveryFeeEntity = new PriceCollectDeliveryFeeEntity();
            priceCollectDeliveryFeeEntity.setBeginPriceCityCode(sendPriceCity.getSendPriceCityCode());
            priceCollectDeliveryFeeEntity.setTransTypeCode(productType);
            priceCollectDeliveryFeeEntity.setCollectDeliveryType(Integer.valueOf(collectDeliveryType));
            priceCollectDeliveryFeeEntity.setHistory(isHistory);
            priceCollectDeliveryFeeEntity.setBillTime(billTime);
            priceCollectDeliveryFee = collectDeliveryFeeTyService.queryListByParam(priceCollectDeliveryFeeEntity);
        }
        return priceCollectDeliveryFee;
    }
}
