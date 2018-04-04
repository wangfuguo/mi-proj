package com.hoau.miser.module.api.itf.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.hoau.miser.module.api.itf.api.shared.domain.*;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IFuelChargeService;
import com.hoau.miser.module.api.itf.api.server.IPriceCorpTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceCustTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceStandardTyService;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.constants.LockTypeConstants;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.util.PmsUtils;

/**
 * 燃油费service
 *
 * @author 蒋落琛
 * @date 2016-6-14下午3:01:22
 */
@Service
public class FuelChargeService implements IFuelChargeService {

	/**
	 * 附加费公共信息管理类
	 */
	@Resource
	private SurchargeCalculateMan surchargeCalculateMan;

	/**
	 * 标准价格
	 */
	@Resource
	private IPriceStandardTyService priceStandardTyService;

	/**
	 * 网点价格
	 */
	@Resource
	private IPriceCorpTyService priceCorpTyService;

	/**
	 * 客户价格
	 */
	@Resource
	private IPriceCustTyService priceCustTyService;

	/**
	 * 计算燃油费
	 */
	@Override
	public SurchargeDto calculateFuelCharge(String productType,
			String orderOrign, BigDecimal weight,
			BigDecimal volume, String startOrgCode, String arrivalOrgCode, String omsBizType, String customerCode,
			boolean isInternalBelt, boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
		// 参数验证
		checkParams(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, omsBizType, orderOrign, isInternalBelt, isHistory, billTime);
		// 免限价
		if(isInternalBelt){
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
		boolean isTmall = PmsUtils.isTmall(orderOrign);
		// 如果是整车或经济拼车、如果是免限价，不锁定，返回0
		if(PmsUtils.isZC(productType) || isInternalBelt){
			surchargeDto.setAmount(BigDecimal.ZERO);
			surchargeDto.setMinAmount(BigDecimal.ZERO);
			surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
			surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_NO);
			standardPrice = BigDecimal.ZERO;
			surchargeDto.setStandardAmount(standardPrice);
			surchargeDto.setHaveConfig(true);
			return surchargeDto;
		}	
		// 查询标准价格
		PriceQueryParam baseTyParam = new PriceQueryParam();
		baseTyParam.setOriginCode(startOrgCode);
		baseTyParam.setDestCode(arrivalOrgCode);
		baseTyParam.setTransTypeCode(productType);
		baseTyParam.setCustNo(customerCode);
		baseTyParam.setBillTime(billTime);
		baseTyParam.setHistory(isHistory);
		baseTyParam.setOriginPositionInfo(originPositionInfo);
		baseTyParam.setDestPositionInfo(destPositionInfo);
		PriceStandardTyEntity priceStandardTyEntity = priceStandardTyService.queryPriceStandardTyByQueryParam(baseTyParam);
		if (priceStandardTyEntity != null && StringUtil.isNotEmpty(priceStandardTyEntity.getFuelFeeSection())) {
			standardPrice = surchargeCalculateMan.querySectionPrice(priceStandardTyEntity.getFuelFeeSection(), weight, volume);
			surchargeDto.setHaveConfig(true);
		}
		// 如果是MRO订单、天猫非签约，锁定，返回0
		if(PmsUtils.isMROOrder(omsBizType) || (isTmall && !surchargeCalculateMan.isContractedCustomer(customerCode))){
			surchargeDto.setAmount(BigDecimal.ZERO);
			surchargeDto.setMinAmount(BigDecimal.ZERO);
			surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
			surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
			surchargeDto.setStandardAmount(standardPrice);
			surchargeDto.setHaveConfig(true);
			return surchargeDto;
		} else {
			//最小限定
			BigDecimal minValue = BigDecimal.ZERO;
			//设置价卡中的燃油费
			BigDecimal ryf = BigDecimal.valueOf(4);

			// 客户价卡
			PriceCustTyEntity priceCustTyEntity = null;
			if(StringUtil.isNotEmpty(customerCode)){
				priceCustTyEntity = priceCustTyService.queryPriceCustTyByQueryParam(baseTyParam);
			}
			// 标准锁定方式
			int lockType = (surchargeDto == null || surchargeDto.getLockType() == null) ? LockTypeConstants.LOCK_TYPE_DOWN : surchargeDto.getLockType();
			// 查询客户折扣中的燃油费优惠分段信息
			List<PriceSectionSubEntity> priceSEctionSubList = null;
			if(StringUtil.isNotEmpty(productType) && StringUtil.isNotEmpty(customerCode)){
				priceSEctionSubList = surchargeCalculateMan.queryCustomerDiscountPriceSection(PriceEventDiscountSubEntity.FEE_TYPE_FUEL, productType, customerCode, isHistory, billTime);
			}
			if((priceCustTyEntity != null && StringUtil.isNotEmpty(priceCustTyEntity.getPriceCustSubTyEntity().getFuelFeeSection())) ||
					(priceSEctionSubList != null && priceSEctionSubList.size() > 0)){
				surchargeDto.setHaveConfig(true);
				if((priceCustTyEntity != null && StringUtil.isNotEmpty(priceCustTyEntity.getPriceCustSubTyEntity().getFuelFeeSection())) &&
						(priceSEctionSubList != null && priceSEctionSubList.size() > 0)){
					BigDecimal je = surchargeCalculateMan.querySectionPrice(priceCustTyEntity.getPriceCustSubTyEntity().getFuelFeeSection(), weight, volume);
					BigDecimal je1 = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
					ryf = je.max(je1); //显示值取最大值
					minValue = je.min(je1); //最小值取最小值
				}else if(priceCustTyEntity != null && StringUtil.isNotEmpty(priceCustTyEntity.getPriceCustSubTyEntity().getFuelFeeSection())){
					BigDecimal je = surchargeCalculateMan.querySectionPrice(priceCustTyEntity.getPriceCustSubTyEntity().getFuelFeeSection(), weight, volume);
					if(je.compareTo(BigDecimal.ZERO) == 1){
						ryf = je; //显示值取最大值
						minValue = je; //最小值取最小值
					}
				} else if(priceSEctionSubList != null && priceSEctionSubList.size() > 0){
					BigDecimal je = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
					if(je.compareTo(BigDecimal.ZERO) == 1){
						ryf = je; //显示值取最大值
						minValue = je; //最小值取最小值
					}
				}
			} else {
				// 网点价格
				PriceCorpTyEntity priceCorpTyEntity = priceCorpTyService.queryPriceCorpTyByQueryParam(baseTyParam);
				if(priceCorpTyEntity != null && StringUtil.isNotEmpty(priceCorpTyEntity.getFuelFeeSection())) {
					ryf = surchargeCalculateMan.querySectionPrice(priceCorpTyEntity.getFuelFeeSection(), weight, volume);
					minValue = ryf;
					surchargeDto.setHaveConfig(true);
				} else {
					if(priceStandardTyEntity != null){
						ryf = standardPrice;
						minValue = ryf;
					}
				}
			}

			//如果有客户价卡，但是客户价卡的燃油费分段为空，则取标准价卡中的燃油费分段
			/*if (ryf.compareTo(BigDecimal.ZERO) == 0) {
				if (priceStandardTyEntity != null && StringUtil.isNotEmpty(priceStandardTyEntity.getFuelFeeSection())) {
					ryf = standardPrice;
					minValue = ryf;
				}
			}*/
			
			// 活动
			BigDecimal eventMinSms = surchargeCalculateMan.getWightAndVolumnMinInSections(PriceEventDiscountSubEntity.FEE_TYPE_FUEL, startOrgCode, arrivalOrgCode, productType, customerCode, orderOrign, weight, volume, isHistory, billTime, originPositionInfo, destPositionInfo);
			if (eventMinSms != null)
			{
				minValue = eventMinSms;
				surchargeDto.setHaveConfig(true);
			}
			
			surchargeDto.setAmount(ryf.max(BigDecimal.valueOf(4)));
			surchargeDto.setMinAmount(minValue.max(BigDecimal.valueOf(4)));
			surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
			surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_DOWN);
		}

		// 返回值处理
		PmsUtils.procCalculateResult(surchargeDto, null, isInternalBelt, null, null, null, productType, null, null);
		surchargeDto.setStandardAmount(standardPrice);
		
		return surchargeDto;
	}

	@Override
	public SurchargeDto calculateStandardFuelCharge(String productType,
			String orderOrign, BigDecimal weight, BigDecimal volume,
			String startOrgCode, String arrivalOrgCode, String omsBizType,
			String customerCode, boolean isInternalBelt, boolean isHistory,
			Date billTime) {
		// 参数验证
		checkParams(startOrgCode, arrivalOrgCode, productType, weight, volume, customerCode, omsBizType, orderOrign, isInternalBelt, isHistory, billTime);
		SurchargeDto surchargeDto = new SurchargeDto();
		// 免限价
		if(isInternalBelt){
			surchargeDto.setStandardAmount(BigDecimal.ZERO);
			surchargeDto.setHaveConfig(true);
			return surchargeDto;
		}
		// 标准值
		BigDecimal standardPrice = null;
		// 如果是整车或经济拼车、如果是免限价，不锁定，返回0
		if(PmsUtils.isZC(productType) || isInternalBelt){
			standardPrice = BigDecimal.ZERO;
			surchargeDto.setHaveConfig(true);
		} else {
			// 查询网点价格
			PriceQueryParam baseTyParam = new PriceQueryParam();
			baseTyParam.setOriginCode(startOrgCode);
			baseTyParam.setDestCode(arrivalOrgCode);
			baseTyParam.setTransTypeCode(productType);
			baseTyParam.setCustNo(customerCode);
			baseTyParam.setBillTime(billTime);
			baseTyParam.setHistory(isHistory);
			PriceStandardTyEntity priceStandardTyEntity = priceStandardTyService.queryPriceStandardTyByQueryParam(baseTyParam);
			if (priceStandardTyEntity != null && StringUtil.isNotEmpty(priceStandardTyEntity.getFuelFeeSection())) {
				standardPrice = surchargeCalculateMan.querySectionPrice(priceStandardTyEntity.getFuelFeeSection(), weight, volume);
				surchargeDto.setHaveConfig(true);
			}
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
	public void checkParams(String startOrgCode, String arrivalOrgCode,
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
}
