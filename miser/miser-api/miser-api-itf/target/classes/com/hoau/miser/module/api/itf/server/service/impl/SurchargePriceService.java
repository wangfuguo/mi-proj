package com.hoau.miser.module.api.itf.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.hoau.miser.module.api.itf.api.shared.domain.*;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.server.IPricePackageFeePcTyService;
import com.hoau.miser.module.api.itf.api.server.IPricePackageFeeStandardTyService;
import com.hoau.miser.module.api.itf.api.server.ISurchargePriceService;
import com.hoau.miser.module.api.itf.api.shared.dto.PackChargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.QueryPackChargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.QuerySurchargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;
import com.hoau.miser.module.api.itf.server.constants.LockTypeConstants;
import com.hoau.miser.module.api.itf.server.constants.PkpConstants;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.util.PmsUtils;

/**
 * 计算附加费
 *
 * @author 蒋落琛
 * @date 2016-6-8下午6:30:15
 */
@Service
public class SurchargePriceService implements
        ISurchargePriceService {

    @Resource
    private IPricePackageFeeStandardTyService pricePackageFeeStandardTyService;

    @Resource
    private IPricePackageFeePcTyService pricePackageFeePcTyService;

    @Resource
    private ICorpPriceCityService corpPriceCityService;

    @Resource
    private IOrgPositionInfoTyService orgPositionInfoTyService;

    /**
     * 附加费公共信息管理类
     */
    @Resource
    private SurchargeCalculateMan surchargeCalculateMan;

    /**
     * 初始加载包装费
     */
    @Override
    public List<PackChargeDto> initialLoadPackCharge(QuerySurchargeDto queryDeliveryChargeDto) {
        String startOrgCode = queryDeliveryChargeDto.getStartOrgCode();
        String productType = queryDeliveryChargeDto.getProductType();
        if (StringUtil.isEmpty(productType)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(startOrgCode)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_STARTORGCODE_ISNULL_EXCEPTION);
        }
        // 查标准包装方式
        PricePackageFeeStandardEntity params = new PricePackageFeeStandardEntity();
        params.setTransTypeCode(productType);
        params.setHistory(queryDeliveryChargeDto.isHistory());
        params.setBillTime(queryDeliveryChargeDto.getBillTime());
        List<PricePackageFeeStandardEntity> packChargeList = pricePackageFeeStandardTyService.queryListByParam(params);

        // 根据价卡城市查包装方式
        // 起运地
        OrgPositionInfoTyEntity originPositionInfo = null;
        OrgPositionInfoTyEntity currOrgPositionInfoTyEntity = queryDeliveryChargeDto.getOriginPositionInfo();
        if (currOrgPositionInfoTyEntity != null
                && !StringUtil.isEmpty(currOrgPositionInfoTyEntity
                .getProvinceCode())
                && !StringUtil.isEmpty(currOrgPositionInfoTyEntity.getCityCode()) && !StringUtil
                .isEmpty(currOrgPositionInfoTyEntity.getCountyCode())) {
            originPositionInfo = currOrgPositionInfoTyEntity;
        } else if (!StringUtil.isEmpty(startOrgCode)) {
            originPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(startOrgCode);
            if (originPositionInfo == null) {
                throw new ChargeException("起运地门店省市区信息为空！");
            }
        } else {
            throw new ChargeException("起运地不能为空！");
        }
        List<PricePackageFeePcEntity> pricePackageFeePcList = null;
        CorpPriceCityEntity cityEntity = corpPriceCityService.queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_SEND);
        if (cityEntity != null) {
            PricePackageFeePcEntity p = new PricePackageFeePcEntity();
            p.setTransTypeCode(productType);
            p.setPriceCity(cityEntity.getSendPriceCityCode());
            p.setHistory(queryDeliveryChargeDto.isHistory());
            p.setBillTime(queryDeliveryChargeDto.getBillTime());
            pricePackageFeePcList = pricePackageFeePcTyService.queryListByParam(p);
        }

        List<PackChargeDto> pList = new ArrayList<PackChargeDto>();
        for (PricePackageFeeStandardEntity entity : packChargeList) {
            PackChargeDto e = new PackChargeDto();
            e.setItemCode(entity.getCode());
            e.setItemName(entity.getName());
            e.setLowestPrice(BigDecimal.valueOf(entity.getMinMoney()));
            e.setLockType(Integer.valueOf(entity.getLockType()));
            e.setUnitPrice(BigDecimal.valueOf(entity.getMoney()));
            e.setCoefficient(BigDecimal.valueOf(entity.getRate()));
            e.setCalcuMethod(entity.getCalculationType());
            pList.add(e);
        }

        // 合并城市包装费
        if (pricePackageFeePcList != null) {
            List<PackChargeDto> pcList = new ArrayList<PackChargeDto>();
            for (int j = 0; j < pricePackageFeePcList.size(); j++) {
                PricePackageFeePcEntity entity = pricePackageFeePcList.get(0);
                boolean isHave = false;
                for (PackChargeDto e : pList) {
                    if (entity.getCode().equals(e.getItemCode())) {
                        e.setLowestPrice(BigDecimal.valueOf(entity.getMinMoney()));
                        e.setLockType(Integer.valueOf(entity.getLockType()));
                        e.setUnitPrice(BigDecimal.valueOf(entity.getMoney()));
                        e.setCoefficient(BigDecimal.valueOf(entity.getRate()));
                        e.setCalcuMethod(entity.getCalculationType());
                        isHave = true;
                    }
                }
                if (!isHave) {
                    PackChargeDto e = new PackChargeDto();
                    e.setItemCode(entity.getCode());
                    e.setItemName(entity.getName());
                    e.setLowestPrice(BigDecimal.valueOf(entity.getMinMoney()));
                    e.setLockType(Integer.valueOf(entity.getLockType()));
                    e.setUnitPrice(BigDecimal.valueOf(entity.getMoney()));
                    e.setCoefficient(BigDecimal.valueOf(entity.getRate()));
                    e.setCalcuMethod(entity.getCalculationType());
                    pcList.add(e);
                }
            }
            pList.addAll(pcList);
        }
        return pList;
    }

    /**
     * 计算包装费
     */
    @Override
    public SurchargeDto calculatePackPrice(QueryPackChargeDto queryPackChargeDto) {
        String itemCode = queryPackChargeDto.getItemCode();
        String startOrgCode = queryPackChargeDto.getStartOrgCode();
        String productType = queryPackChargeDto.getProductType();
        BigDecimal num = queryPackChargeDto.getNum();
        // 参数验证
        checkParamsByPackPrice(itemCode, startOrgCode, productType, num);
        // 免限价
        if (queryPackChargeDto.isInternalBelt()) {
            SurchargeDto surchargeDto = new SurchargeDto();
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(999999999));
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        // 标准值
        BigDecimal standardPrice = null;
        SurchargeDto surchargeDto = new SurchargeDto();
        // 查标准包装方式
        PricePackageFeeStandardEntity params = new PricePackageFeeStandardEntity();
        params.setTransTypeCode(productType);
        params.setCode(itemCode);
        params.setHistory(queryPackChargeDto.isHistory());
        params.setBillTime(queryPackChargeDto.getBillTime());
        List<PricePackageFeeStandardEntity> packChargeList = pricePackageFeeStandardTyService.queryListByParam(params);
        if (packChargeList != null && packChargeList.size() > 0) {
            PricePackageFeeStandardEntity entity = packChargeList.get(0);
            BigDecimal minMoney = BigDecimal.valueOf(entity.getMinMoney());
            BigDecimal rate = BigDecimal.valueOf(entity.getRate());
            BigDecimal money = BigDecimal.valueOf(entity.getMoney());
            BigDecimal amount = num.multiply(money).multiply(rate);
            standardPrice = minMoney.max(amount);
            surchargeDto.setHaveConfig(true);
        }
        // 根据价卡城市查包装方式
        // 起运地
        OrgPositionInfoTyEntity originPositionInfo = null;
        OrgPositionInfoTyEntity currOrgPositionInfoTyEntity = queryPackChargeDto.getOriginPositionInfo();
        if (currOrgPositionInfoTyEntity != null
                && !StringUtil.isEmpty(currOrgPositionInfoTyEntity
                .getProvinceCode())
                && !StringUtil.isEmpty(currOrgPositionInfoTyEntity.getCityCode()) && !StringUtil
                .isEmpty(currOrgPositionInfoTyEntity.getCountyCode())) {
            originPositionInfo = currOrgPositionInfoTyEntity;
        } else if (!StringUtil.isEmpty(startOrgCode)) {
            originPositionInfo = orgPositionInfoTyService.queryDistrictByOrgCode(startOrgCode);
            if (originPositionInfo == null) {
                throw new ChargeException("起运地门店省市区信息为空！");
            }
        } else {
            throw new ChargeException("起运地不能为空！");
        }
        List<PricePackageFeePcEntity> pricePackageFeePcList = null;
        CorpPriceCityEntity cityEntity = corpPriceCityService.queryPriceCity(originPositionInfo, PmsConstants.PRICE_CITY_SEND);
        if (cityEntity != null) {
            PricePackageFeePcEntity p = new PricePackageFeePcEntity();
            p.setTransTypeCode(productType);
            p.setPriceCity(cityEntity.getSendPriceCityCode());
            p.setCode(itemCode);
            p.setHistory(queryPackChargeDto.isHistory());
            p.setBillTime(queryPackChargeDto.getBillTime());
            pricePackageFeePcList = pricePackageFeePcTyService.queryListByParam(p);
        }

        String calculationType = null;
        BigDecimal rate = BigDecimal.ZERO;
        BigDecimal money = BigDecimal.ZERO;
        BigDecimal minMoney = BigDecimal.ZERO;
        BigDecimal maxMoney2 = BigDecimal.ZERO;
        String lockType = null;
        if (pricePackageFeePcList != null && pricePackageFeePcList.size() > 0) {
            PricePackageFeePcEntity entity = pricePackageFeePcList.get(0);
            calculationType = entity.getCalculationType();
            rate = BigDecimal.valueOf(entity.getRate());
            money = BigDecimal.valueOf(entity.getMoney());
            minMoney = BigDecimal.valueOf(entity.getMinMoney());
            maxMoney2 = BigDecimal.valueOf(entity.getMaxMoney2());
            lockType = entity.getLockType();
            surchargeDto.setHaveConfig(true);
        } else {
            if (packChargeList != null && packChargeList.size() > 0) {
                PricePackageFeeStandardEntity entity = packChargeList.get(0);
                calculationType = entity.getCalculationType();
                rate = BigDecimal.valueOf(entity.getRate());
                money = BigDecimal.valueOf(entity.getMoney());
                minMoney = BigDecimal.valueOf(entity.getMinMoney());
                maxMoney2 = BigDecimal.valueOf(entity.getMaxMoney2());
                lockType = entity.getLockType();
                surchargeDto.setHaveConfig(true);
            }
        }
        if (calculationType != null) {
            BigDecimal amount = num.multiply(money).multiply(rate);
            surchargeDto.setAmount(minMoney.max(amount));
            surchargeDto.setMinAmount(minMoney);
            surchargeDto.setMaxAmount(maxMoney2);
            surchargeDto.setLockType(Integer.valueOf(lockType));
        }


        // 返回值处理
        PmsUtils.procCalculateResult(surchargeDto, "PACKING", queryPackChargeDto.isInternalBelt(), null, null, null, productType, null, null);
        surchargeDto.setStandardAmount(standardPrice);

        return surchargeDto;
    }

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
     * @return
     * @author 蒋落琛
     * @date 2016-6-14上午8:43:15
     * @update
     */
    @Override
    public SurchargeDto calculatePickupPrice(String productType, String customerCode, String orderOrign, BigDecimal weight,
                                             BigDecimal volume, String startOrgCode, String arrivalOrgCode, String pickUpWay, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        weight = PmsUtils.procBigDecimalNullUtil(weight);
        volume = PmsUtils.procBigDecimalNullUtil(volume);
        // 参数验证
        checkParamsByPickupPrice(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, pickUpWay, orderOrign, isInternalBelt, isHistory, billTime);
        // 免限价
        if (isInternalBelt) {
            SurchargeDto surchargeDto = new SurchargeDto();
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(999999999));
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        SurchargeDto surchargeDto = new SurchargeDto();
        // 标准值
        BigDecimal standardPrice = null;
        // 天猫、非签约客户的直接返回
        boolean isTmall = PmsUtils.isTmall(orderOrign);
        // 非上门提货
        if (!PkpConstants.PICKUP_VISIT.equals(pickUpWay)) {
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            standardPrice = BigDecimal.ZERO;
            surchargeDto.setHaveConfig(true);
        } else {
            // 查询标准附加费
            List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_PICKUP, productType, isHistory, billTime);
            if (pList != null && pList.size() > 0) {
                standardPrice = BigDecimal.valueOf(pList.get(0).getMoney());
            }
            if ((isTmall && !surchargeCalculateMan.isContractedCustomer(customerCode))) {
                surchargeDto.setAmount(BigDecimal.ZERO);
                surchargeDto.setMinAmount(BigDecimal.ZERO);
                surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
                surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
                surchargeDto.setHaveConfig(true);
            } else {
                // 上门提货
                // 查询客户折扣中的信息费优惠分段信息
                BigDecimal customerInformation = null;
                if (StringUtil.isNotEmpty(productType) && StringUtil.isNotEmpty(customerCode)) {
                    List<PriceSectionSubEntity> priceSEctionSubList = surchargeCalculateMan.queryCustomerDiscountPriceSection(PriceEventDiscountSubEntity.FEE_TYPE_PICKUP, productType, customerCode, isHistory, billTime);
                    if (priceSEctionSubList != null && priceSEctionSubList.size() > 0) {
                        customerInformation = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
                        surchargeDto.setHaveConfig(true);
                    }
                }

                if (pList != null && pList.size() > 0) {
                    surchargeDto.setAmount(BigDecimal.valueOf(pList.get(0).getMoney()));
                    surchargeDto.setLockType(pList.get(0).getLockType().intValue());
                    surchargeDto.setHaveConfig(true);
                }

                // 客户折扣
                if (customerInformation != null) {
                    surchargeDto = new SurchargeDto();
                    surchargeDto.setAmount(customerInformation.max(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount())));//显示值取最大值
                    surchargeDto.setMinAmount(customerInformation.min(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount())));//最小值取最小值
                    surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_DOWN);//控制方式定死为向下锁定
                    surchargeDto.setHaveConfig(true);
                }

                // 活动
                BigDecimal eventMinSms = surchargeCalculateMan.getWightAndVolumnMinInSections(PriceEventDiscountSubEntity.FEE_TYPE_INSURANCE_RATE, startOrgCode, arrivalOrgCode, productType, customerCode, orderOrign, weight, volume, isHistory, billTime, originPositionInfo, destPositionInfo);
                if (eventMinSms != null) {
                    surchargeDto.setHaveConfig(true);
                    surchargeDto.setAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).max(eventMinSms));
                    if (surchargeDto.getLockType() != null && (LockTypeConstants.LOCK_TYPE_DOWN == surchargeDto.getLockType() || LockTypeConstants.LOCK_TYPE_ALL == surchargeDto.getLockType())) {
                        surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(eventMinSms));
                    }
                }

                /** 如果是天猫的订单，而且是结算客户，由于不允许调整提货费，提货费默认就是最小值 */
                if (surchargeDto.getMinAmount() != null && isTmall
                        && surchargeCalculateMan.isContractedCustomer(customerCode)) {
                    surchargeDto.setAmount(surchargeDto.getMinAmount());
                }
            }
        }

        // 返回值处理
        PmsUtils.procCalculateResult(surchargeDto, PmsConstants.ADDITIONAL_TYPE_PICKUP, isInternalBelt, null, null, null, productType, null, null);
        surchargeDto.setStandardAmount(standardPrice);

        return surchargeDto;
    }

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
     * @param insuredMoney
     * @return
     * @author 蒋落琛
     * @date 2016-6-13下午6:35:47
     * @update
     */
    @Override
    public SurchargeDto calculateInsuredPrice(String productType, String customerCode, String orderOrign, BigDecimal weight,
                                              BigDecimal volume, String startOrgCode, String arrivalOrgCode, BigDecimal insuredRate, BigDecimal insuredMoney, String omsBizType, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        // 参数验证
        checkParamsByInformationCharge(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, omsBizType, orderOrign, isInternalBelt, isHistory, billTime);
        // 免限价
        if (isInternalBelt) {
            SurchargeDto surchargeDto = new SurchargeDto();
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(999999999));
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        SurchargeDto surchargeDto = new SurchargeDto();
        weight = PmsUtils.procBigDecimalNullUtil(weight);
        volume = PmsUtils.procBigDecimalNullUtil(volume);
        insuredRate = PmsUtils.procBigDecimalNullUtil(insuredRate);
        insuredMoney = PmsUtils.procBigDecimalNullUtil(insuredMoney);
        // 标准值
        BigDecimal standardPrice = null;
        // 查询标准费率
        List<PriceExtrafeeStandardEntity> list = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_INSURANCE_RATE, productType, isHistory, billTime);
        if (list != null && list.size() > 0) {
            surchargeDto.setHaveConfig(true);
            standardPrice = BigDecimal.valueOf(list.get(0).getMoney()).multiply(insuredMoney)
                    .multiply(BigDecimal.valueOf(0.001));
        }

        // 查询最低保价手续费
        List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_INSURANCE, productType, isHistory, billTime);
        // 如果保价额大于0
        if (pList != null && pList.size() > 0) {
            surchargeDto.setHaveConfig(true);
            surchargeDto.setAmount(BigDecimal.valueOf(pList.get(0).getMoney()));
            standardPrice = PmsUtils.procBigDecimalNullUtil(standardPrice).max(BigDecimal.valueOf(pList.get(0).getMoney()));
            surchargeDto.setLockType(pList.get(0).getLockType().intValue());
        }

        // 查询客户折扣中的信息费优惠分段信息
        BigDecimal customerInformation = null;
        if (StringUtil.isNotEmpty(productType) && StringUtil.isNotEmpty(customerCode)) {
            List<PriceSectionSubEntity> priceSEctionSubList = surchargeCalculateMan.queryCustomerDiscountPriceSection(PriceEventDiscountSubEntity.FEE_TYPE_INSURANCE, productType, customerCode, isHistory, billTime);
            if (priceSEctionSubList != null && priceSEctionSubList.size() > 0) {
                surchargeDto.setHaveConfig(true);
                customerInformation = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
            }
        }
        // 客户折扣
        if (customerInformation != null) {
            surchargeDto.setAmount(customerInformation.min(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount())));//由于保价费不允许输入，所以显示也是取最小值
            surchargeDto.setMinAmount(customerInformation.min(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount())));//最小值取最小值
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_DOWN);//控制方式定死为向下锁定
        }

        // 活动
        BigDecimal eventMinSms = surchargeCalculateMan.getWightAndVolumnMinInSections(PriceEventDiscountSubEntity.FEE_TYPE_INSURANCE, startOrgCode, arrivalOrgCode, productType, customerCode, orderOrign, weight, volume, isHistory, billTime, originPositionInfo, destPositionInfo);
        if (eventMinSms != null) {
            surchargeDto.setHaveConfig(true);
            surchargeDto.setAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).max(eventMinSms));
            if (surchargeDto.getLockType() != null && (LockTypeConstants.LOCK_TYPE_DOWN == surchargeDto.getLockType() || LockTypeConstants.LOCK_TYPE_ALL == surchargeDto.getLockType())) {
                surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(eventMinSms));
            }
        }

        // 返回值处理
        PmsUtils.procCalculateResult(surchargeDto, PmsConstants.ADDITIONAL_TYPE_INSURANCE, isInternalBelt, null, insuredRate, insuredMoney, productType, PmsUtils.isMROOrder(omsBizType), null);
        surchargeDto.setStandardAmount(standardPrice);

        return surchargeDto;
    }


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
     * @return
     * @author 蒋落琛
     * @date 2016-6-13下午6:35:21
     * @update
     */
    @Override
    public SurchargeDto calculateInsuredRate(String productType, String customerCode, String orderOrign, BigDecimal weight,
                                             BigDecimal volume, String startOrgCode, String arrivalOrgCode, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        weight = PmsUtils.procBigDecimalNullUtil(weight);
        volume = PmsUtils.procBigDecimalNullUtil(volume);
        // 参数验证
        checkParamsByInformationCharge(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, null, orderOrign, isInternalBelt, isHistory, billTime);
        // 免限价
        if (isInternalBelt) {
            SurchargeDto surchargeDto = new SurchargeDto();
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(999999999));
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        // 标准值
        BigDecimal standardPrice = null;
        SurchargeDto surchargeDto = new SurchargeDto();
        // 查询客户折扣中的信息费优惠分段信息
        BigDecimal customerInformation = null;
        if (StringUtil.isNotEmpty(productType) && StringUtil.isNotEmpty(customerCode)) {
            List<PriceSectionSubEntity> priceSEctionSubList = surchargeCalculateMan.queryCustomerDiscountPriceSection(PriceEventDiscountSubEntity.FEE_TYPE_INSURANCE_RATE, productType, customerCode, isHistory, billTime);
            if (priceSEctionSubList != null && priceSEctionSubList.size() > 0) {
                surchargeDto.setHaveConfig(true);
                customerInformation = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
            }
        }

        // 查询标准附加费
        List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_INSURANCE_RATE, productType, isHistory, billTime);
        if (pList != null && pList.size() > 0) {
            surchargeDto.setHaveConfig(true);
            standardPrice = BigDecimal.valueOf(pList.get(0).getMoney());
            surchargeDto.setAmount(BigDecimal.valueOf(pList.get(0).getMoney()));
            surchargeDto.setLockType(pList.get(0).getLockType().intValue());
        }

        // 活动
        BigDecimal eventMinSms = surchargeCalculateMan.getWightAndVolumnMinInSections(PriceEventDiscountSubEntity.FEE_TYPE_INSURANCE_RATE, startOrgCode, arrivalOrgCode, productType, customerCode, orderOrign, weight, volume, isHistory, billTime, originPositionInfo, destPositionInfo);
        if (eventMinSms != null) {
            surchargeDto.setHaveConfig(true);
            surchargeDto.setAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).max(eventMinSms));
            if (surchargeDto.getLockType() != null && (LockTypeConstants.LOCK_TYPE_DOWN == surchargeDto.getLockType() || LockTypeConstants.LOCK_TYPE_ALL == surchargeDto.getLockType())) {
                surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(eventMinSms));
            }
        }

        // 客户折扣
        if (customerInformation != null) {
            surchargeDto = new SurchargeDto();
            surchargeDto.setAmount(customerInformation.max(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount())));
            surchargeDto.setMinAmount(customerInformation.min(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount())));
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_DOWN);
        }

        // 返回值处理
        PmsUtils.procCalculateResult(surchargeDto, PmsConstants.ADDITIONAL_TYPE_INSURANCE_RATE, isInternalBelt, null, null, null, productType, null, null);
        surchargeDto.setStandardAmount(standardPrice);

        return surchargeDto;
    }

    /**
     * 工本费
     */
    @Override
    public SurchargeDto calculateIssuingCharge(
            String productType, String customerCode, String orderOrign, BigDecimal weight,
            BigDecimal volume, String startOrgCode, String arrivalOrgCode, String omsBizType,
            boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        weight = PmsUtils.procBigDecimalNullUtil(weight);
        volume = PmsUtils.procBigDecimalNullUtil(volume);
        // 参数验证
        checkParamsByInformationCharge(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, null, orderOrign, isInternalBelt, isHistory, billTime);
        // 免限价
        if (isInternalBelt) {
            SurchargeDto surchargeDto = new SurchargeDto();
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(999999999));
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        SurchargeDto surchargeDto = new SurchargeDto();
        // 标准值
        BigDecimal standardPrice = null;
        // 查询标准附加费
        List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_PAPER, productType, isHistory, billTime);
        if (pList != null && pList.size() > 0) {
            surchargeDto.setHaveConfig(true);
            standardPrice = BigDecimal.valueOf(pList.get(0).getMoney());
        }
        // 天猫、非签约客户的与MRO的订单直接返回
        boolean isTmall = PmsUtils.isTmall(orderOrign);
        if ((isTmall && !surchargeCalculateMan.isContractedCustomer(customerCode)) || PmsUtils.isMROOrder(omsBizType)) {
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
        } else {
            // 查询客户折扣中的信息费优惠分段信息
            BigDecimal customerInformation = null;
            if (StringUtil.isNotEmpty(productType) && StringUtil.isNotEmpty(customerCode)) {
                List<PriceSectionSubEntity> priceSEctionSubList = surchargeCalculateMan.queryCustomerDiscountPriceSection(PriceEventDiscountSubEntity.FEE_TYPE_SMS, productType, customerCode, isHistory, billTime);
                if (priceSEctionSubList != null && priceSEctionSubList.size() > 0) {
                    surchargeDto.setHaveConfig(true);
                    customerInformation = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
                }
            }

            if (pList != null && pList.size() > 0) {
                surchargeDto.setAmount(BigDecimal.valueOf(pList.get(0).getMoney()));
                surchargeDto.setLockType(pList.get(0).getLockType().intValue());
            }

            // 活动
            BigDecimal eventMinSms = surchargeCalculateMan.getWightAndVolumnMinInSections(PriceEventDiscountSubEntity.FEE_TYPE_PAPER, startOrgCode, arrivalOrgCode, productType, customerCode, orderOrign, weight, volume, isHistory, billTime, originPositionInfo, destPositionInfo);
            if (eventMinSms != null) {
                surchargeDto.setHaveConfig(true);
                surchargeDto.setAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).max(eventMinSms));
                if (surchargeDto.getLockType() != null && (LockTypeConstants.LOCK_TYPE_DOWN == surchargeDto.getLockType() || LockTypeConstants.LOCK_TYPE_ALL == surchargeDto.getLockType())) {
                    surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(eventMinSms));
                }
            }

            // 客户折扣
            if (customerInformation != null) {
                surchargeDto = new SurchargeDto();
                surchargeDto.setAmount(customerInformation.max(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount())));
                surchargeDto.setMinAmount(customerInformation.min(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount())));
                surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_DOWN);
            }
        }

        // 返回值处理
        PmsUtils.procCalculateResult(surchargeDto, PmsConstants.ADDITIONAL_TYPE_PAPER, isInternalBelt, null, null, null, productType, null, null);
        surchargeDto.setStandardAmount(standardPrice);

        return surchargeDto;
    }

    @Override
    public SurchargeDto calculateInformationCharge(
            String productType, String customerCode, String orderOrign, BigDecimal weight,
            BigDecimal volume, String startOrgCode, String arrivalOrgCode, boolean isSmsService, String omsBizType, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        weight = PmsUtils.procBigDecimalNullUtil(weight);
        volume = PmsUtils.procBigDecimalNullUtil(volume);
        // 参数较验
        checkParamsByInformationCharge(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, omsBizType, orderOrign, isInternalBelt, isHistory, billTime);
        // 免限价
        if (isInternalBelt) {
            SurchargeDto surchargeDto = new SurchargeDto();
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(999999999));
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        SurchargeDto surchargeDto = new SurchargeDto();
        // 标准值
        BigDecimal standardPrice = null;
        // 查询标准附加费
        List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_SMS, productType, isHistory, billTime);
        if (pList != null && pList.size() > 0) {
            surchargeDto.setHaveConfig(true);
            standardPrice = BigDecimal.valueOf(pList.get(0).getMoney());
        }
        // 天猫、非签约客户的直接返回
        boolean isTmall = PmsUtils.isTmall(orderOrign);
        if (isTmall && !surchargeCalculateMan.isContractedCustomer(customerCode)) {
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
        } else {
            // 查询客户折扣中的信息费优惠分段信息
            BigDecimal customerInformation = null;
            if (StringUtil.isNotEmpty(productType) && StringUtil.isNotEmpty(customerCode)) {
                List<PriceSectionSubEntity> priceSEctionSubList = surchargeCalculateMan.queryCustomerDiscountPriceSection(PriceEventDiscountSubEntity.FEE_TYPE_SMS, productType, customerCode, isHistory, billTime);
                if (priceSEctionSubList != null && priceSEctionSubList.size() > 0) {
                    surchargeDto.setHaveConfig(true);
                    customerInformation = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
                }
            }

            if (pList != null && pList.size() > 0) {
                surchargeDto.setAmount(BigDecimal.valueOf(pList.get(0).getMoney()));
                surchargeDto.setLockType(pList.get(0).getLockType().intValue());
            }

            // 活动
            BigDecimal eventMinSms = surchargeCalculateMan.getWightAndVolumnMinInSections(PriceEventDiscountSubEntity.FEE_TYPE_SMS, startOrgCode, arrivalOrgCode, productType, customerCode, orderOrign, weight, volume, isHistory, billTime, originPositionInfo, destPositionInfo);
            if (eventMinSms != null) {
                surchargeDto.setHaveConfig(true);
                surchargeDto.setAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).max(eventMinSms));
                if (surchargeDto.getLockType() != null && (LockTypeConstants.LOCK_TYPE_DOWN == surchargeDto.getLockType() || LockTypeConstants.LOCK_TYPE_ALL == surchargeDto.getLockType())) {
                    surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(eventMinSms));
                }
            }

            // 客户折扣
            if (customerInformation != null) {
                surchargeDto = new SurchargeDto();
                surchargeDto.setAmount(customerInformation);
                surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_DOWN);
            }
        }

        // 返回值处理
        PmsUtils.procCalculateResult(surchargeDto, PmsConstants.ADDITIONAL_TYPE_SMS, isInternalBelt, null, null, null, productType, PmsUtils.isMROOrder(omsBizType), isSmsService);
        surchargeDto.setStandardAmount(standardPrice);

        return surchargeDto;
    }

    /**
     * 如果开单托运人不为签约客户则按照目前需求的标准读取基础数据配置的费率
     *
     * @return
     * @author 蒋落琛
     * @date 2016-6-14下午1:56:23
     * @update
     */
    private void changeNoContractRates(String productType, String customerCode, String orderOrign,
                                       BigDecimal weight, BigDecimal volume, String startOrgCode,
                                       String arrivalOrgCode, String collectionType,
                                       BigDecimal customerInformation, SurchargeDto surchargeDto, boolean isHistory, Date billTime, PriceCollectDeliveryFeeEntity priceCollectionDeliveryFee, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        if (customerInformation != null) {
            surchargeDto.setHaveConfig(true);
            surchargeDto.setAmount(customerInformation);
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_DOWN);
        }

        // 标准值
        if (priceCollectionDeliveryFee != null && priceCollectionDeliveryFee.getCollectDeliveryRate() != null) {
            surchargeDto.setHaveConfig(true);
            if (surchargeDto.getAmount() == null) {
                surchargeDto.setAmount(priceCollectionDeliveryFee.getCollectDeliveryRate());
                surchargeDto.setLockType(priceCollectionDeliveryFee.getRateLockType());
            } else {
                surchargeDto.setAmount(surchargeDto.getAmount().max(priceCollectionDeliveryFee.getCollectDeliveryRate()));
                if (surchargeDto.getLockType() != null && (LockTypeConstants.LOCK_TYPE_DOWN == surchargeDto.getLockType() || LockTypeConstants.LOCK_TYPE_ALL == surchargeDto.getLockType())) {
                    surchargeDto.setMinAmount(surchargeDto.getAmount().min(priceCollectionDeliveryFee.getCollectDeliveryRate()));
                }
            }
        }

        // 活动
        BigDecimal eventMinSms = surchargeCalculateMan.getWightAndVolumnMinInSections(PriceEventDiscountSubEntity.FEE_TYPE_COLLECTION_RATE, startOrgCode, arrivalOrgCode, productType, customerCode, orderOrign, weight, volume, isHistory, billTime, originPositionInfo, destPositionInfo);
        if (eventMinSms != null) {
            surchargeDto.setHaveConfig(true);
            surchargeDto.setAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).max(eventMinSms));
            if (surchargeDto.getLockType() != null && (LockTypeConstants.LOCK_TYPE_DOWN == surchargeDto.getLockType() || LockTypeConstants.LOCK_TYPE_ALL == surchargeDto.getLockType())) {
                surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(eventMinSms));
            }
        }
    }

    /**
     * 代收货款费率
     * 客户附加费中代收款手续费率不为空，并且在合同期内或价卡期内，则按客户代收款手续费率执行为最低收费
     */
    @Override
    public SurchargeDto calculateCollectionPayRate(String productType, String customerCode, String orderOrign,
                                                   BigDecimal weight, BigDecimal volume, String startOrgCode,
                                                   String arrivalOrgCode, String collectionType, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        weight = PmsUtils.procBigDecimalNullUtil(weight);
        volume = PmsUtils.procBigDecimalNullUtil(volume);
        // 参数验证
        checkParamsByCollectionPayRate(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, orderOrign, collectionType, isInternalBelt, isHistory, billTime);
        // 免限价
        if (isInternalBelt) {
            SurchargeDto surchargeDto = new SurchargeDto();
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(999999999));
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        SurchargeDto surchargeDto = new SurchargeDto();
        BigDecimal standardPrice = null;
        // 标准值
        PriceCollectDeliveryFeeEntity priceCollectionDeliveryFee = surchargeCalculateMan.queryCollectionPayFeeByParams(startOrgCode, productType, collectionType, isHistory, billTime, originPositionInfo);
        if (priceCollectionDeliveryFee != null && priceCollectionDeliveryFee.getCollectDeliveryRate() != null) {
            standardPrice = priceCollectionDeliveryFee.getCollectDeliveryRate();
            surchargeDto.setStandardAmount(standardPrice);
            surchargeDto.setHaveConfig(true);
        }

        // 查询客户折扣中的信息费优惠分段信息
        BigDecimal customerInformation = null;
        if (StringUtil.isNotEmpty(productType) && StringUtil.isNotEmpty(customerCode)) {
            List<PriceSectionSubEntity> priceSEctionSubList = surchargeCalculateMan.queryCustomerDiscountPriceSection(PriceEventDiscountSubEntity.FEE_TYPE_COLLECTION_RATE, productType, customerCode, isHistory, billTime);
            if (priceSEctionSubList != null && priceSEctionSubList.size() > 0) {
                customerInformation = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
                surchargeDto.setHaveConfig(true);
            }
        }
        // 客户编号不为空，合同时间与价格时间在有效期内，代收类型不为0（无）
        if (StringUtil.isNotEmpty(customerCode)) {
            if (surchargeCalculateMan.isContractedCustomerAndPc(customerCode) && !"0".equals(collectionType)) {
                // 客户折扣
                if (customerInformation != null) {
                    surchargeDto.setAmount(customerInformation);
                    surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
                    // 返回值处理
                    PmsUtils.procCalculateResult(surchargeDto, null, isInternalBelt, null, null, null, productType, null, null);
                    return surchargeDto;
                }
            }
        }
        changeNoContractRates(productType, customerCode, orderOrign, weight, volume, startOrgCode, arrivalOrgCode, collectionType, customerInformation, surchargeDto, isHistory, billTime, priceCollectionDeliveryFee, originPositionInfo, destPositionInfo);

        // 返回值处理
        PmsUtils.procCalculateResult(surchargeDto, null, isInternalBelt, null, null, null, productType, null, null);

        return surchargeDto;
    }

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
     * @return
     * @author 蒋落琛
     * @date 2016-6-14下午2:39:46
     * @update
     */
    @Override
    public SurchargeDto calculateCollectionPayCharge(String productType,
                                                     String customerCode, String orderOrign, BigDecimal weight,
                                                     BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                     String collectionType, BigDecimal collectionPayAmount, BigDecimal collectionPayRate, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        weight = PmsUtils.procBigDecimalNullUtil(weight);
        volume = PmsUtils.procBigDecimalNullUtil(volume);
        collectionPayAmount = PmsUtils.procBigDecimalNullUtil(collectionPayAmount);
        collectionPayRate = PmsUtils.procBigDecimalNullUtil(collectionPayRate);
        // 参数验证
        checkParamsByCollectionPayRate(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, orderOrign, collectionType, isInternalBelt, isHistory, billTime);
        // 免限价
        if (isInternalBelt) {
            SurchargeDto surchargeDto = new SurchargeDto();
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(999999999));
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        SurchargeDto surchargeDto = new SurchargeDto();
        BigDecimal standardPrice = null;
        // 标准值
        // 取小手续费与最大手续费
        BigDecimal lowestCollectDeliveryFee = null;
        BigDecimal highestCollectDeliveryFee = null;
        PriceCollectDeliveryFeeEntity priceCollectionDeliveryFee = surchargeCalculateMan.queryCollectionPayFeeByParams(startOrgCode, productType, collectionType, isHistory, billTime, originPositionInfo);
        if (priceCollectionDeliveryFee != null && priceCollectionDeliveryFee.getCollectDeliveryRate() != null) {
            // 标准费率 * 金额 * 0.001
            lowestCollectDeliveryFee = priceCollectionDeliveryFee.getLowestCollectDeliveryFee();
            highestCollectDeliveryFee = priceCollectionDeliveryFee.getHighestCollectDeliveryFee();
            surchargeDto.setMinAmount(lowestCollectDeliveryFee);
            surchargeDto.setMaxAmount(highestCollectDeliveryFee);
            surchargeDto.setLockType(priceCollectionDeliveryFee.getRateLockType());
            standardPrice = ((priceCollectionDeliveryFee.getCollectDeliveryRate().multiply(collectionPayAmount).multiply(BigDecimal.valueOf(0.001))).max(lowestCollectDeliveryFee)).min(highestCollectDeliveryFee);
            surchargeDto.setStandardAmount(standardPrice);
            surchargeDto.setAmount(standardPrice);
            surchargeDto.setHaveConfig(true);
        }

        // 实际值
        BigDecimal amount = null;
        if (lowestCollectDeliveryFee != null && highestCollectDeliveryFee != null) {
            amount = ((collectionPayRate.multiply(collectionPayAmount).multiply(BigDecimal.valueOf(0.001))).max(lowestCollectDeliveryFee)).min(highestCollectDeliveryFee);
        } else {
            amount = collectionPayRate.multiply(collectionPayAmount).multiply(BigDecimal.valueOf(0.001));
        }
        surchargeDto.setAmount(amount);
        if (surchargeDto.getLockType() != null && (LockTypeConstants.LOCK_TYPE_DOWN == surchargeDto.getLockType() || LockTypeConstants.LOCK_TYPE_ALL == surchargeDto.getLockType())) {
            surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(amount));
        }


        // 查询客户折扣中的信息费优惠分段信息
        BigDecimal customerInformation = null;
        if (StringUtil.isNotEmpty(productType) && StringUtil.isNotEmpty(customerCode)) {
            List<PriceSectionSubEntity> priceSEctionSubList = surchargeCalculateMan.queryCustomerDiscountPriceSection(PriceEventDiscountSubEntity.FEE_TYPE_COLLECTION, productType, customerCode, isHistory, billTime);
            if (priceSEctionSubList != null && priceSEctionSubList.size() > 0) {
                surchargeDto.setHaveConfig(true);
                customerInformation = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
                if (customerInformation != null) {
                    surchargeDto.setAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).max(customerInformation));
                    if (surchargeDto.getLockType() != null && (LockTypeConstants.LOCK_TYPE_DOWN == surchargeDto.getLockType() || LockTypeConstants.LOCK_TYPE_ALL == surchargeDto.getLockType())) {
                        surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(customerInformation));
                    }
                }
            }
        }

        // 活动
        BigDecimal eventMinSms = surchargeCalculateMan.getWightAndVolumnMinInSections(PriceEventDiscountSubEntity.FEE_TYPE_COLLECTION, startOrgCode, arrivalOrgCode, productType, customerCode, orderOrign, weight, volume, isHistory, billTime, originPositionInfo, destPositionInfo);
        if (eventMinSms != null) {
            surchargeDto.setHaveConfig(true);
            surchargeDto.setAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).max(eventMinSms));
            if (surchargeDto.getLockType() != null && (LockTypeConstants.LOCK_TYPE_DOWN == surchargeDto.getLockType() || LockTypeConstants.LOCK_TYPE_ALL == surchargeDto.getLockType())) {
                surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(eventMinSms));
            }
        }

        // 返回值处理
        if (surchargeDto.getLockType() == null) {
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_DOWN);
        }
        PmsUtils.procCalculateResult(surchargeDto, null, isInternalBelt, null, null, null, productType, null, null);

        return surchargeDto;
    }

    @Override
    public SurchargeDto calculateStandardPickupPrice(String productType,
                                                     String customerCode, String orderOrign, BigDecimal weight,
                                                     BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                     String pickUpWay, boolean isInternalBelt, boolean isHistory,
                                                     Date billTime) {
        // 参数验证
        checkParamsByPickupPrice(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, pickUpWay, orderOrign, isInternalBelt, isHistory, billTime);
        SurchargeDto surchargeDto = new SurchargeDto();
        // 免限价
        if (isInternalBelt) {
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        // 标准值
        BigDecimal standardPrice = null;
        // 非上门提货
        if (!PkpConstants.PICKUP_VISIT.equals(pickUpWay)) {
            standardPrice = BigDecimal.ZERO;
            surchargeDto.setHaveConfig(true);
        } else {
            // 查询标准附加费
            List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_PICKUP, productType, isHistory, billTime);
            if (pList != null && pList.size() > 0) {
                surchargeDto.setHaveConfig(true);
                standardPrice = BigDecimal.valueOf(pList.get(0).getMoney());
            }
        }
        surchargeDto.setStandardAmount(standardPrice);
        return surchargeDto;
    }

    @Override
    public SurchargeDto calculateStandardInsuredPrice(String productType,
                                                      String customerCode, String orderOrign, BigDecimal weight,
                                                      BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                      BigDecimal insuredRate, BigDecimal insuredMoney, String omsBizType,
                                                      boolean isInternalBelt, boolean isHistory, Date billTime) {
        // 参数验证
        checkParamsByInformationCharge(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, omsBizType, orderOrign, isInternalBelt, isHistory, billTime);
        SurchargeDto surchargeDto = new SurchargeDto();
        // 免限价
        if (isInternalBelt) {
            surchargeDto.setAmount(BigDecimal.ZERO);
            surchargeDto.setMinAmount(BigDecimal.ZERO);
            surchargeDto.setMaxAmount(BigDecimal.valueOf(999999999));
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        insuredMoney = PmsUtils.procBigDecimalNullUtil(insuredMoney);
        // 标准值
        BigDecimal standardPrice = null;
        // 查询标准费率
        List<PriceExtrafeeStandardEntity> list = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_INSURANCE_RATE, productType, isHistory, billTime);
        if (list != null && list.size() > 0) {
            standardPrice = BigDecimal.valueOf(list.get(0).getMoney()).multiply(insuredMoney)
                    .multiply(BigDecimal.valueOf(0.001));
            surchargeDto.setHaveConfig(true);
        }

        // 查询最低保价手续费
        List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_INSURANCE, productType, isHistory, billTime);
        // 如果保价额大于0
        if (pList != null && pList.size() > 0) {
            standardPrice = PmsUtils.procBigDecimalNullUtil(standardPrice).max(BigDecimal.valueOf(pList.get(0).getMoney()));
            surchargeDto.setHaveConfig(true);
        }

        surchargeDto.setStandardAmount(standardPrice);
        return surchargeDto;
    }

    @Override
    public SurchargeDto calculateStandardInsuredRate(String productType,
                                                     String customerCode, String orderOrign, BigDecimal weight,
                                                     BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                     boolean isInternalBelt, boolean isHistory, Date billTime) {
        // 参数验证
        checkParamsByInformationCharge(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, null, orderOrign, isInternalBelt, isHistory, billTime);
        SurchargeDto surchargeDto = new SurchargeDto();
        // 免限价
        if (isInternalBelt) {
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        // 标准值
        BigDecimal standardPrice = null;
        // 查询标准附加费
        List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_INSURANCE_RATE, productType, isHistory, billTime);
        if (pList != null && pList.size() > 0) {
            standardPrice = BigDecimal.valueOf(pList.get(0).getMoney());
            surchargeDto.setHaveConfig(true);
        }
        surchargeDto.setStandardAmount(standardPrice);
        return surchargeDto;
    }

    @Override
    public SurchargeDto calculateStandardIssuingCharge(String productType,
                                                       String customerCode, String orderOrign, BigDecimal weight,
                                                       BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                       String omsBizType, boolean isInternalBelt, boolean isHistory,
                                                       Date billTime) {
        // 参数验证
        checkParamsByInformationCharge(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, null, orderOrign, isInternalBelt, isHistory, billTime);
        SurchargeDto surchargeDto = new SurchargeDto();
        // 免限价
        if (isInternalBelt) {
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        // 标准值
        BigDecimal standardPrice = null;
        // 查询标准附加费
        List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_PAPER, productType, isHistory, billTime);
        if (pList != null && pList.size() > 0) {
            standardPrice = BigDecimal.valueOf(pList.get(0).getMoney());
            surchargeDto.setHaveConfig(true);
        }
        surchargeDto.setStandardAmount(standardPrice);
        return surchargeDto;
    }

    @Override
    public SurchargeDto calculateStandardInformationCharge(String productType,
                                                           String customerCode, String orderOrign, BigDecimal weight,
                                                           BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                           boolean isSmsService, String omsBizType, boolean isInternalBelt,
                                                           boolean isHistory, Date billTime) {
        // 参数较验
        checkParamsByInformationCharge(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, omsBizType, orderOrign, isInternalBelt, isHistory, billTime);
        SurchargeDto surchargeDto = new SurchargeDto();
        // 免限价
        if (isInternalBelt) {
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        // 标准值
        BigDecimal standardPrice = null;
        // 查询标准附加费
        List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_SMS, productType, isHistory, billTime);
        if (pList != null && pList.size() > 0) {
            standardPrice = BigDecimal.valueOf(pList.get(0).getMoney());
            surchargeDto.setHaveConfig(true);
        }
        surchargeDto.setStandardAmount(standardPrice);
        return surchargeDto;
    }

    @Override
    public SurchargeDto calculateStandardPackPrice(QueryPackChargeDto queryPackChargeDto) {
        String itemCode = queryPackChargeDto.getItemCode();
        String startOrgCode = queryPackChargeDto.getStartOrgCode();
        String productType = queryPackChargeDto.getProductType();
        BigDecimal num = queryPackChargeDto.getNum();
        // 参数验证
        checkParamsByPackPrice(itemCode, startOrgCode, productType, num);
        SurchargeDto surchargeDto = new SurchargeDto();
        // 免限价
        if (queryPackChargeDto.isInternalBelt()) {
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        // 标准值
        BigDecimal standardPrice = null;
        // 查标准包装方式
        PricePackageFeeStandardEntity params = new PricePackageFeeStandardEntity();
        params.setTransTypeCode(productType);
        params.setCode(itemCode);
        params.setHistory(queryPackChargeDto.isHistory());
        params.setBillTime(queryPackChargeDto.getBillTime());
        List<PricePackageFeeStandardEntity> packChargeList = pricePackageFeeStandardTyService.queryListByParam(params);
        if (packChargeList != null && packChargeList.size() > 0) {
            PricePackageFeeStandardEntity entity = packChargeList.get(0);
            BigDecimal minMoney = BigDecimal.valueOf(entity.getMinMoney());
            BigDecimal rate = BigDecimal.valueOf(entity.getRate());
            BigDecimal money = BigDecimal.valueOf(entity.getMoney());
            BigDecimal amount = num.multiply(money).multiply(rate);
            standardPrice = minMoney.max(amount);
            surchargeDto.setHaveConfig(true);
        }
        surchargeDto.setStandardAmount(standardPrice);
        return surchargeDto;
    }

    @Override
    public SurchargeDto calculateStandardCollectionPayRate(String productType, String customerCode, String orderOrign,
                                                           BigDecimal weight, BigDecimal volume, String startOrgCode,
                                                           String arrivalOrgCode, String collectionType, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        weight = PmsUtils.procBigDecimalNullUtil(weight);
        volume = PmsUtils.procBigDecimalNullUtil(volume);
        // 参数验证
        checkParamsByCollectionPayRate(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, orderOrign, collectionType, isInternalBelt, isHistory, billTime);
        SurchargeDto surchargeDto = new SurchargeDto();
        BigDecimal standardPrice = null;
        // 免限价
        if (isInternalBelt) {
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        // 标准值
        PriceCollectDeliveryFeeEntity priceCollectionDeliveryFee = surchargeCalculateMan.queryCollectionPayFeeByParams(startOrgCode, productType, collectionType, isHistory, billTime, originPositionInfo);
        if (priceCollectionDeliveryFee != null && priceCollectionDeliveryFee.getCollectDeliveryRate() != null) {
            standardPrice = priceCollectionDeliveryFee.getCollectDeliveryRate();
            surchargeDto.setHaveConfig(true);
        }
        surchargeDto.setStandardAmount(standardPrice);
        return surchargeDto;
    }

    @Override
    public SurchargeDto calculateStandardCollectionPayCharge(String productType,
                                                             String customerCode, String orderOrign, BigDecimal weight,
                                                             BigDecimal volume, String startOrgCode, String arrivalOrgCode,
                                                             String collectionType, BigDecimal collectionPayAmount, BigDecimal collectionPayRate, boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
        weight = PmsUtils.procBigDecimalNullUtil(weight);
        volume = PmsUtils.procBigDecimalNullUtil(volume);
        collectionPayAmount = PmsUtils.procBigDecimalNullUtil(collectionPayAmount);
        // 参数验证
        checkParamsByCollectionPayRate(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, orderOrign, collectionType, isInternalBelt, isHistory, billTime);
        SurchargeDto surchargeDto = new SurchargeDto();
        // 免限价
        if (isInternalBelt) {
            surchargeDto.setStandardAmount(BigDecimal.ZERO);
            surchargeDto.setHaveConfig(true);
            return surchargeDto;
        }
        BigDecimal standardPrice = null;
        // 标准值
        // 取小手续费与最大手续费
        BigDecimal lowestCollectDeliveryFee = BigDecimal.ZERO;
        BigDecimal highestCollectDeliveryFee = BigDecimal.ZERO;
        PriceCollectDeliveryFeeEntity priceCollectionDeliveryFee = surchargeCalculateMan.queryCollectionPayFeeByParams(startOrgCode, productType, collectionType, isHistory, billTime, originPositionInfo);
        if (priceCollectionDeliveryFee != null && priceCollectionDeliveryFee.getCollectDeliveryRate() != null) {
            // 标准费率 * 金额 * 0.001
            lowestCollectDeliveryFee = priceCollectionDeliveryFee.getLowestCollectDeliveryFee();
            highestCollectDeliveryFee = priceCollectionDeliveryFee.getHighestCollectDeliveryFee();
            standardPrice = ((priceCollectionDeliveryFee.getCollectDeliveryRate().multiply(collectionPayAmount).multiply(BigDecimal.valueOf(0.001))).max(lowestCollectDeliveryFee)).min(highestCollectDeliveryFee);
            surchargeDto.setStandardAmount(standardPrice);
            surchargeDto.setHaveConfig(true);
        }

        surchargeDto.setStandardAmount(standardPrice);
        return surchargeDto;
    }

    /**
     * 参数较验
     *
     * @author 蒋落琛
     * @date 2016-6-28下午2:50:37
     * @update
     */
    public void checkParamsByInformationCharge(String startOrgCode, String arrivalOrgCode,
                                               String productType, BigDecimal weight,
                                               BigDecimal volume, String customerCode, String omsBizType,
                                               String orderOrign, boolean isInternalBelt, boolean isHistory,
                                               Date billTime) {
        if (StringUtil.isEmpty(startOrgCode)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_STARTORGCODE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(arrivalOrgCode)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_ARRIVALORGCODE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(productType)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
        }
    }

    /**
     * 参数较验
     *
     * @author 蒋落琛
     * @date 2016-6-28下午2:50:37
     * @update
     */
    public void checkParamsByCollectionPayRate(String startOrgCode, String arrivalOrgCode,
                                               String productType, BigDecimal weight,
                                               BigDecimal volume, String customerCode,
                                               String orderOrign, String collectionType, boolean isInternalBelt, boolean isHistory,
                                               Date billTime) {
        if (StringUtil.isEmpty(startOrgCode)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_STARTORGCODE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(arrivalOrgCode)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_ARRIVALORGCODE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(productType)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(collectionType)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_COLLECTIONTYPE_ISNULL_EXCEPTION);
        }
    }

    /**
     * 参数较验
     *
     * @author 蒋落琛
     * @date 2016-6-28下午2:50:37
     * @update
     */
    public void checkParamsByPackPrice(String itemCode, String startOrgCode, String productType, BigDecimal num) {
        if (StringUtil.isEmpty(itemCode)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_PACKITEMCODE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(productType)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(startOrgCode)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_STARTORGCODE_ISNULL_EXCEPTION);
        }
        if (num == null) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_NUM_ISNULL_EXCEPTION);
        }
    }

    /**
     * 参数较验
     *
     * @author 蒋落琛
     * @date 2016-6-28下午2:50:37
     * @update
     */
    public void checkParamsByPickupPrice(String startOrgCode, String arrivalOrgCode,
                                         String productType, BigDecimal weight,
                                         BigDecimal volume, String customerCode, String pickUpWay,
                                         String orderOrign, boolean isInternalBelt, boolean isHistory,
                                         Date billTime) {
        if (StringUtil.isEmpty(startOrgCode)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_STARTORGCODE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(arrivalOrgCode)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_ARRIVALORGCODE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(productType)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
        }
        if (StringUtil.isEmpty(pickUpWay)) {
            throw new ChargeException(ChargeException.SURCHARGEPRICE_PICKUPWAY_ISNULL_EXCEPTION);
        }
    }

}
