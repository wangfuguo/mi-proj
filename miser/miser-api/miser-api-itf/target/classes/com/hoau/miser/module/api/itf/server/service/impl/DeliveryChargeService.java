package com.hoau.miser.module.api.itf.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IDeliveryChargeService;
import com.hoau.miser.module.api.itf.api.server.IPriceUpstairsTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceExtrafeeStandardEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceUpstairsEntity;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;
import com.hoau.miser.module.api.itf.server.constants.CplbConstant;
import com.hoau.miser.module.api.itf.server.constants.LockTypeConstants;
import com.hoau.miser.module.api.itf.server.constants.PkpConstants;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.util.PmsUtils;

/**
 * 送货费service
 * 
 * @author 蒋落琛
 * @date 2016-6-1下午5:39:39
 */
@Service
public class DeliveryChargeService implements IDeliveryChargeService {
	
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
	
	/**
	 * 计算送货费
	 */
	@Override
	public SurchargeDto calculateDeliveryCharge(String startOrgCode,
			String arrivalOrgCode, String productType, String subProductType, String deliveryMethod,
			BigDecimal weight, BigDecimal volume, String customerCode,
			String omsBizType, String orderOrign, boolean isInternalBelt,
			boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
		// 参数较验
		checkParams(startOrgCode,arrivalOrgCode, productType, deliveryMethod, weight, volume, customerCode, omsBizType, orderOrign, isInternalBelt, isHistory, billTime);
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
		// 返回结果
		SurchargeDto surchargeDto = new SurchargeDto();
		boolean isTmall = PmsUtils.isTmall(orderOrign);
		weight = PmsUtils.procBigDecimalNullUtil(weight);
		volume = PmsUtils.procBigDecimalNullUtil(volume);
		//是否显示的送货费金额为客户附加费中的送货费
		boolean showCustomerShf = false;
		
		// 标准值
		BigDecimal standardPrice = null;
		// 天猫订单
		if (isTmall){
			// 送货方式，客户自提
			if (PkpConstants.PICK_UP_SELF.equals(deliveryMethod) && !productType.equals(CplbConstant.TYPE_YAZ)) {
				surchargeDto.setAmount(BigDecimal.ZERO);
				surchargeDto.setMinAmount(BigDecimal.ZERO);
				surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
				surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_NO);
				standardPrice = BigDecimal.ZERO;
				surchargeDto.setHaveConfig(true);
			} else {
				// 上楼费查询条件
				PriceUpstairsEntity entity = new PriceUpstairsEntity();
				// 类型为大件上楼
				entity.setType(PmsConstants.UPSTAIRS_TYPE_TM);
				// 产品类型
				entity.setTransportType(productType);
				entity.setHistory(isHistory);
				entity.setBillTime(billTime);
				// 查询上楼费
				BigDecimal shslfy = BigDecimal.ZERO;
				PriceUpstairsEntity priceUpstairsEntity = priceUpstairsTyService.queryUpstairsByParam(entity);
				if(priceUpstairsEntity != null && priceUpstairsEntity.getSectionSubEntities() != null && priceUpstairsEntity.getSectionSubEntities().size() > 0){
					List<PriceSectionSubEntity> priceSEctionSubList = priceUpstairsEntity.getSectionSubEntities();
					// 分级收费
					BigDecimal s = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
					shslfy = s;
					surchargeDto.setHaveConfig(true);
				}
				
				// 按格式保留小数点后面的数
				if (PmsUtils.isMROOrder(omsBizType)) {
					shslfy = BigDecimal.valueOf(PmsUtils.toMoneyFormat(shslfy.doubleValue(), 2, BigDecimal.ROUND_HALF_UP));
				} else {
					shslfy = BigDecimal.valueOf(PmsUtils.toMoneyFormat(shslfy.doubleValue(), 0));
				}
				if (StringUtil.isNotEmpty(customerCode) && surchargeCalculateMan.isContractedCustomer(customerCode)) // 合同在有效期内的客户
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
				
				// 判断是否是易安装-支装一休
				BigDecimal distributionFee = null;
				if(CplbConstant.TYPE_YAZ.equals(productType) && CplbConstant.TYPE_ZZYT.equals(subProductType)){
					// 获取目的地城市类型的配送费定义
					distributionFee = surchargeCalculateMan.queryDistributionFeeCityType(arrivalOrgCode, productType, weight, volume, destPositionInfo);
					if(distributionFee != null){
						surchargeDto.setHaveConfig(true);
						standardPrice = distributionFee;
					}
				} else{
					// 获取目的地城市类型的送货费定义
					distributionFee = surchargeCalculateMan.queryDeliveryFeeCityType(arrivalOrgCode, productType, weight, volume, destPositionInfo);
					if(distributionFee != null){
						surchargeDto.setHaveConfig(true);
						standardPrice = distributionFee;
					}
				}
			}
		} else {
			//不是客户自提,需要分别计算送货费和上楼费
			if (!PkpConstants.PICK_UP_SELF.equals(deliveryMethod) || productType.equals(CplbConstant.TYPE_YAZ)){
				// 判断是否是易安装-支装一休
				if(CplbConstant.TYPE_YAZ.equals(productType) && CplbConstant.TYPE_ZZYT.equals(subProductType)){
					// 获取目的地城市类型的配送费定义
					BigDecimal distributionFee = surchargeCalculateMan.queryDistributionFeeCityType(arrivalOrgCode, productType, weight, volume, destPositionInfo);
					// 获取目的地城市类型的配送费定义
					if(distributionFee != null){
						surchargeDto.setHaveConfig(true);
						surchargeDto.setAmount(distributionFee);
						surchargeDto.setMinAmount(distributionFee);
						surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_DOWN);
						standardPrice = distributionFee;
					}
				} else {
					// 查询送货的标准附加费
					// 查询条件
					List<PriceExtrafeeStandardEntity> pList = surchargeCalculateMan.queryPriceExtrafeeStandard(PmsConstants.ADDITIONAL_TYPE_DELIVERY, productType, isHistory, billTime);
					if(pList != null && pList.size() > 0){
						surchargeDto.setHaveConfig(true);
						surchargeDto.setAmount(BigDecimal.valueOf(pList.get(0).getMoney()));
						surchargeDto.setLockType(pList.get(0).getLockType().intValue());
					}
					// 标准附加费锁定方式
					int lockType = (surchargeDto == null || surchargeDto.getLockType() == null) ? LockTypeConstants.LOCK_TYPE_DOWN : surchargeDto.getLockType();
					
					// 查询客户折扣中的送货费优惠分段信息
					if(StringUtil.isNotEmpty(productType) && StringUtil.isNotEmpty(customerCode)){
						List<PriceSectionSubEntity> priceSEctionSubList = surchargeCalculateMan.queryCustomerDiscountPriceSection(PriceEventDiscountSubEntity.FEE_TYPE_DELIVERY, productType, customerCode, isHistory, billTime);
						if(priceSEctionSubList != null && priceSEctionSubList.size() > 0){
							surchargeDto.setHaveConfig(true);
							BigDecimal je = PmsUtils.calculateSlf(priceSEctionSubList, weight, volume);
							if(je.compareTo(BigDecimal.ZERO) == 1){
								//Chenyl @201-11-05 根据赵东旗要求此处直接显示客户附加费中的送货费，且后续也不再设置
								surchargeDto.setAmount(je); //显示值取最大值
								showCustomerShf = true;
								if (lockType == LockTypeConstants.LOCK_TYPE_DOWN || lockType == LockTypeConstants.LOCK_TYPE_ALL) 
								{
									surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(je)); //最小值取最小值
								}
								surchargeDto.setLockType(lockType);
							}
						}
					}
					
					// 获取目的地城市类型的送货费定义
					BigDecimal fee = surchargeCalculateMan.queryDeliveryFeeCityType(arrivalOrgCode, productType, weight, volume, destPositionInfo);
					if(fee != null){
						surchargeDto.setHaveConfig(true);
						surchargeDto.setAmount(showCustomerShf ? surchargeDto.getAmount() : PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).max(fee)); //显示值取最大值
						standardPrice = fee;
						if (lockType == LockTypeConstants.LOCK_TYPE_DOWN || lockType == LockTypeConstants.LOCK_TYPE_ALL) 
						{
							surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(fee)); //最小值取最小值
						}
						surchargeDto.setLockType(lockType);
					}
					
					// 活动
					BigDecimal eventMinDelivery = surchargeCalculateMan.getWightAndVolumnMinInSections(PriceEventDiscountSubEntity.FEE_TYPE_DELIVERY, startOrgCode, arrivalOrgCode, productType, customerCode, orderOrign, weight, volume, isHistory, billTime, originPositionInfo, destPositionInfo);
					if (eventMinDelivery != null) 
					{
						surchargeDto.setHaveConfig(true);
						surchargeDto.setAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).max(eventMinDelivery));
						if (lockType == LockTypeConstants.LOCK_TYPE_DOWN || lockType == LockTypeConstants.LOCK_TYPE_ALL) 
						{
							surchargeDto.setMinAmount(PmsUtils.procBigDecimalNullUtil(surchargeDto.getAmount()).min(eventMinDelivery));
						}
					}
				}
			} else {
				surchargeDto.setHaveConfig(true);
				surchargeDto.setAmount(BigDecimal.ZERO);
				surchargeDto.setMinAmount(BigDecimal.ZERO);
				surchargeDto.setMaxAmount(BigDecimal.valueOf(9999999.99));
				surchargeDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
				standardPrice = BigDecimal.ZERO;
			}
		}
		
		// 返回值处理
		PmsUtils.procCalculateResult(surchargeDto, PmsConstants.ADDITIONAL_TYPE_DELIVERY, isInternalBelt, deliveryMethod, null, null, productType, null, null);
		surchargeDto.setStandardAmount(standardPrice);
		return surchargeDto;
	}

	/**
	 * 计算送货费标准值
	 */
	public SurchargeDto calculateStandardDeliveryCharge(String startOrgCode,
			String arrivalOrgCode, String productType, String subProductType, String deliveryMethod,
			BigDecimal weight, BigDecimal volume, String customerCode,
			String omsBizType, String orderOrign, boolean isInternalBelt,
			boolean isHistory, Date billTime, OrgPositionInfoTyEntity originPositionInfo, OrgPositionInfoTyEntity destPositionInfo) {
		// 参数较验
		checkParams(startOrgCode,arrivalOrgCode, productType, deliveryMethod, weight, volume, customerCode, omsBizType, orderOrign, isInternalBelt, isHistory, billTime);
		// 免限价
		if(isInternalBelt){
			SurchargeDto surchargeDto = new SurchargeDto();
			surchargeDto.setStandardAmount(BigDecimal.ZERO);
			surchargeDto.setHaveConfig(true);
			return surchargeDto;
		}
		// 返回结果
		SurchargeDto surchargeDto = new SurchargeDto();
		weight = PmsUtils.procBigDecimalNullUtil(weight);
		volume = PmsUtils.procBigDecimalNullUtil(volume);
		
		// 标准值
		BigDecimal standardPrice = null;
		//不是客户自提,需要分别计算送货费和上楼费
		if (!PkpConstants.PICK_UP_SELF.equals(deliveryMethod)){
			// 判断是否是易安装-支装一体
			if(CplbConstant.TYPE_YAZ.equals(productType) && CplbConstant.TYPE_ZZYT.equals(subProductType)){
				// 获取目的地城市类型的配送费定义
				BigDecimal distributionFee = surchargeCalculateMan.queryDistributionFeeCityType(arrivalOrgCode, productType, weight, volume, destPositionInfo);
				// 获取目的地城市类型的配送费定义
				if(distributionFee != null){
					surchargeDto.setHaveConfig(true);
					standardPrice = distributionFee;
				}
			} else {
				// 获取目的地城市类型的送货费定义
				BigDecimal fee = surchargeCalculateMan.queryDeliveryFeeCityType(arrivalOrgCode, productType, weight, volume, destPositionInfo);
				if(fee != null){
					surchargeDto.setHaveConfig(true);
					standardPrice = fee;
				}
			}
		} else {
			surchargeDto.setHaveConfig(true);
			standardPrice = BigDecimal.ZERO;
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
			String productType, String deliveryMethod, BigDecimal weight,
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
		if (StringUtil.isEmpty(deliveryMethod)) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_DELIVERYMETHOD_ISNULL_EXCEPTION);
		}
	}
}
