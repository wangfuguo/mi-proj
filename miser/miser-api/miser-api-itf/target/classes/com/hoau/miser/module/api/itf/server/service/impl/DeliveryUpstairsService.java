package com.hoau.miser.module.api.itf.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IDeliveryUpstairsService;
import com.hoau.miser.module.api.itf.api.server.IPriceUpstairsTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceUpstairsEntity;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;
import com.hoau.miser.module.api.itf.server.constants.LockTypeConstants;
import com.hoau.miser.module.api.itf.server.constants.PkpConstants;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.util.PmsUtils;

/**
 * 送货上楼费
 * 
 * @author 蒋落琛
 * @date 2016-6-8下午2:23:31
 */
@Service
public class DeliveryUpstairsService implements IDeliveryUpstairsService {
	
	/**
	 * 上楼费
	 */
	@Resource
	private IPriceUpstairsTyService priceUpstairsTyService;
	
	/**
	 * 附加费公共信息管理类
	 */
	@Resource
	private SurchargeCalculateMan surchargeCalculateMan;

	@Override
	public SurchargeDto calculateDeliveryUpstairsCharge(String deliveryMethod, String productType,
			BigDecimal weight, BigDecimal volume, String orderOrign, String omsBizType, String customerCode, boolean isInternalBelt, boolean isHistory, Date billTime) {
		// 参数较验
		checkParams(productType, deliveryMethod, weight, volume, customerCode, omsBizType, orderOrign, isInternalBelt, isHistory, billTime);
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
		weight = weight == null ? BigDecimal.ZERO : weight;
		volume = volume == null ? BigDecimal.ZERO : volume;
		boolean isTmall = PmsUtils.isTmall(orderOrign);
		
		// 标准值
		BigDecimal standardPrice = null;
		
		// 客户自提或者送货上门或者天猫非签约订单，都不收取上楼费
		if (PkpConstants.PICK_UP_SELF.equals(deliveryMethod)
				|| PkpConstants.DELIVERY_HOME.equals(deliveryMethod) 
				|| (isTmall && !surchargeCalculateMan.isContractedCustomer(customerCode))) {
			surchargeDto.setAmount(BigDecimal.ZERO);
			surchargeDto.setMinAmount(BigDecimal.ZERO);
			surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
			surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
			standardPrice = BigDecimal.ZERO;
			surchargeDto.setHaveConfig(true);
		}else {
			// 上楼类型
			String upstairsType = PkpConstants.DELIVERY_UPSTAIRS_BIG
					.equals(deliveryMethod) ? PmsConstants.UPSTAIRS_TYPE_BIG
					: PmsConstants.UPSTAIRS_TYPE_LITTLE;
			// 上楼费查询条件
			PriceUpstairsEntity entity = new PriceUpstairsEntity();
			// 类型为大件上楼
			entity.setType(upstairsType);
			// 产品类型
			entity.setTransportType(productType);
			entity.setHistory(isHistory);
			entity.setBillTime(billTime);
			// 查询上楼费
			BigDecimal shslfy = BigDecimal.ZERO;
			PriceUpstairsEntity priceUpstairsEntity = priceUpstairsTyService.queryUpstairsByParam(entity);
			if(priceUpstairsEntity != null && priceUpstairsEntity.getSectionSubEntities() != null && priceUpstairsEntity.getSectionSubEntities().size() > 0){
				List<PriceSectionSubEntity> priceSEctionSubList = priceUpstairsEntity.getSectionSubEntities();
				if(priceSEctionSubList != null && priceSEctionSubList.size() > 0){
					// 分级收费
					shslfy = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
					surchargeDto.setHaveConfig(true);
				}
			}
			// 按格式保留小数点后面的数
			if (PmsUtils.isMROOrder(omsBizType)) {
				shslfy = BigDecimal.valueOf(PmsUtils.toMoneyFormat(shslfy.doubleValue(), 2, BigDecimal.ROUND_HALF_UP));
			} else {
				shslfy = BigDecimal.valueOf(PmsUtils.toMoneyFormat(shslfy.doubleValue(), 0));
			}
			standardPrice = shslfy;
			if (surchargeCalculateMan.isContractedCustomer(customerCode)) // 合同在有效期内的客户
			{
				// 上楼费不锁定
				surchargeDto.setMinAmount(BigDecimal.ZERO);
				surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
				surchargeDto.setAmount(shslfy);
				surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_NO);
			} else {
				// 上楼费向下锁定
				surchargeDto.setMinAmount(shslfy);
				surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
				surchargeDto.setAmount(shslfy);
				surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_DOWN);
			}
		}

		// 返回值处理
		PmsUtils.procCalculateResult(surchargeDto, null, isInternalBelt, deliveryMethod, null, null, productType, null, null);
		surchargeDto.setStandardAmount(standardPrice);
		
		return surchargeDto;
	}

	@Override
	public SurchargeDto calculateStandardDeliveryUpstairsCharge(
			String deliveryMethod, String productType, BigDecimal weight,
			BigDecimal volume, String orderOrign, String omsBizType,
			String customerCode, boolean isInternalBelt, boolean isHistory,
			Date billTime) {
		// 参数较验
		checkParams(productType, deliveryMethod, weight, volume, customerCode, omsBizType, orderOrign, isInternalBelt, isHistory, billTime);
		// 免限价
		if(isInternalBelt){
			SurchargeDto surchargeDto = new SurchargeDto();
			surchargeDto.setStandardAmount(BigDecimal.ZERO);
			surchargeDto.setHaveConfig(true);
			return surchargeDto;
		}
		SurchargeDto surchargeDto = new SurchargeDto();
		weight = weight == null ? BigDecimal.ZERO : weight;
		volume = volume == null ? BigDecimal.ZERO : volume;
		boolean isTmall = PmsUtils.isTmall(orderOrign);
		
		// 标准值
		BigDecimal standardPrice = null;
		
		// 客户自提或者送货上门或者天猫非签约订单，都不收取上楼费
		if (PkpConstants.PICK_UP_SELF.equals(deliveryMethod)
				|| PkpConstants.DELIVERY_HOME.equals(deliveryMethod) || (isTmall && !surchargeCalculateMan.isContractedCustomer(customerCode))) {
			standardPrice = BigDecimal.ZERO;
			surchargeDto.setHaveConfig(true);
		}else {
			// 上楼类型
			String upstairsType = PkpConstants.DELIVERY_UPSTAIRS_BIG
					.equals(deliveryMethod) ? PmsConstants.UPSTAIRS_TYPE_BIG
					: PmsConstants.UPSTAIRS_TYPE_LITTLE;
			// 上楼费查询条件
			PriceUpstairsEntity entity = new PriceUpstairsEntity();
			// 类型为大件上楼
			entity.setType(upstairsType);
			// 产品类型
			entity.setTransportType(productType);
			entity.setHistory(isHistory);
			entity.setBillTime(billTime);
			// 查询上楼费
			BigDecimal shslfy = BigDecimal.ZERO;
			PriceUpstairsEntity priceUpstairsEntity = priceUpstairsTyService.queryUpstairsByParam(entity);
			if(priceUpstairsEntity != null && priceUpstairsEntity.getSectionSubEntities() != null && priceUpstairsEntity.getSectionSubEntities().size() > 0){
				List<PriceSectionSubEntity> priceSEctionSubList = priceUpstairsEntity.getSectionSubEntities();
				if(priceSEctionSubList != null && priceSEctionSubList.size() > 0){
					// 分级收费
					shslfy = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
					surchargeDto.setHaveConfig(true);
				}
			}
			// 按格式保留小数点后面的数
			shslfy = BigDecimal.valueOf(PmsUtils.toMoneyFormat(shslfy.doubleValue(), 0));
			standardPrice = shslfy;
		}
		surchargeDto.setStandardAmount(standardPrice);
		return surchargeDto;
	}

	/**
	 * 参数较验
	 * 
	 * @param psv
	 * @author 蒋落琛
	 * @date 2016-6-28下午2:50:37
	 * @update
	 */
	public void checkParams(String productType, String deliveryMethod, BigDecimal weight,
			BigDecimal volume, String customerCode, String omsBizType,
			String orderOrign, boolean isInternalBelt, boolean isHistory,
			Date billTime) {
		if (StringUtil.isEmpty(productType)) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
		}
		if (StringUtil.isEmpty(deliveryMethod)) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_DELIVERYMETHOD_ISNULL_EXCEPTION);
		}
	}
}
