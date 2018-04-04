package com.hoau.miser.module.api.itf.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IExtrafeeAddValueFeeTyService;
import com.hoau.miser.module.api.itf.api.server.ISpecialServerPriceService;
import com.hoau.miser.module.api.itf.api.shared.domain.ExtrafeeAddValueFeeEntity;
import com.hoau.miser.module.api.itf.api.shared.dto.SpecialServerCalculateResultDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SpecialServerPriceConditionDto;
import com.hoau.miser.module.api.itf.server.constants.LockTypeConstants;
import com.hoau.miser.module.api.itf.server.constants.SpecialPriceConstants;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;

/**
 * 计算特服费
 *
 * @author 蒋落琛
 * @date 2016-6-15下午2:58:03
 */
@Service
public class SpecialServerPriceService implements
		ISpecialServerPriceService {
	
	@Resource
	private IExtrafeeAddValueFeeTyService extrafeeAddValueFeeTyService;

	/**
	 * 计算单项特服费
	 */
	@Override
	public SpecialServerCalculateResultDto calculateUnitermSpecialPrice(
			SpecialServerPriceConditionDto specialServerPriceConditionDto) {
		SpecialServerCalculateResultDto resultDto = new SpecialServerCalculateResultDto();
		//校验请求参数
		checkParams(specialServerPriceConditionDto);
		// 免限价
		if(specialServerPriceConditionDto.isInternalBelt()){
			resultDto.setPriceValue(BigDecimal.ZERO);
			resultDto.setStandardAmount(BigDecimal.ZERO);
			resultDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
			resultDto.setHaveConfig(true);
			return resultDto;
		}
		// 标准值
		BigDecimal standardPrice = null;
		
		//获取公司特服费项目列表
		ExtrafeeAddValueFeeEntity psv = new ExtrafeeAddValueFeeEntity();
		psv.setTransTypeCode(specialServerPriceConditionDto.getProductType());
		psv.setCode(specialServerPriceConditionDto.getItemCode().toString());
		psv.setHistory(specialServerPriceConditionDto.isHistory());
		psv.setBillTime(specialServerPriceConditionDto.getBillTime());
		List<ExtrafeeAddValueFeeEntity> efList = extrafeeAddValueFeeTyService.queryListByParam(psv);
		
		//计算特服费
		BigDecimal specialPrice = null;
		if(efList != null && efList.size() > 0){
			specialPrice = calculateItemPrice(specialServerPriceConditionDto, efList.get(0));
			standardPrice = specialPrice;
			resultDto.setPriceValue(specialPrice);
			resultDto.setItemCode(specialServerPriceConditionDto.getItemCode());
			resultDto.setItemName(efList.get(0).getCodeName());
			resultDto.setLockType(efList.get(0).getLockType().intValue());
			resultDto.setUnitValue(new BigDecimal(specialServerPriceConditionDto.getUnitermCount()));
			handleResult(resultDto,efList.get(0));
			resultDto.setHaveConfig(true);
		}

		// 返回值处理
		resultDto.setStandardAmount(standardPrice);
		return resultDto;
	}
	
	/**
	 * 计算标准单项特服费
	 */
	@Override
	public SpecialServerCalculateResultDto calculateStandardUnitermSpecialPrice(
			SpecialServerPriceConditionDto specialServerPriceConditionDto) {
		SpecialServerCalculateResultDto resultDto = new SpecialServerCalculateResultDto();
		//校验请求参数
		checkParams(specialServerPriceConditionDto);
		// 免限价
		if(specialServerPriceConditionDto.isInternalBelt()){
			resultDto.setPriceValue(BigDecimal.ZERO);
			resultDto.setStandardAmount(BigDecimal.ZERO);
			resultDto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
			resultDto.setHaveConfig(true);
			return resultDto;
		}
		// 标准值
		BigDecimal standardPrice = null;
		
		//获取公司特服费项目列表
		ExtrafeeAddValueFeeEntity psv = new ExtrafeeAddValueFeeEntity();
		psv.setTransTypeCode(specialServerPriceConditionDto.getProductType());
		psv.setCode(specialServerPriceConditionDto.getItemCode().toString());
		psv.setHistory(specialServerPriceConditionDto.isHistory());
		psv.setBillTime(specialServerPriceConditionDto.getBillTime());
		List<ExtrafeeAddValueFeeEntity> efList = extrafeeAddValueFeeTyService.queryListByParam(psv);
		
		//计算特服费
		BigDecimal specialPrice = null;
		if(efList != null && efList.size() > 0){
			specialPrice = calculateItemPrice(specialServerPriceConditionDto, efList.get(0));
			standardPrice = specialPrice;
			resultDto.setHaveConfig(true);
		}
		resultDto.setStandardAmount(standardPrice);
		return resultDto;
	}
	
	/**
	 * 初始加载计算特服费
	 */
	@Override
	public List<SpecialServerCalculateResultDto> calculateListSpecialPrice(
			SpecialServerPriceConditionDto specialServerPriceConditionDto) {
		List<SpecialServerCalculateResultDto> resultDtoList = new ArrayList<SpecialServerCalculateResultDto>();
		SpecialServerCalculateResultDto resultDto = null;
		if (specialServerPriceConditionDto == null) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PARAMS_ISNULL_EXCEPTION);
		}
		// 产品类型不能为空
		if (StringUtil.isEmpty(specialServerPriceConditionDto.getProductType())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
		}

		// 获取公司特服费项目列表
		ExtrafeeAddValueFeeEntity psv = new ExtrafeeAddValueFeeEntity();
		psv.setTransTypeCode(specialServerPriceConditionDto.getProductType());
		psv.setHistory(specialServerPriceConditionDto.isHistory());
		psv.setBillTime(specialServerPriceConditionDto.getBillTime());
		List<ExtrafeeAddValueFeeEntity> efList = extrafeeAddValueFeeTyService.queryListByParam(psv);
		
		for (ExtrafeeAddValueFeeEntity entity : efList) {
			// 过滤安装费
			if(SpecialPriceConstants.COME_TO_INSTALL != Integer.valueOf(entity.getCode())){
				resultDto = new SpecialServerCalculateResultDto();
				resultDto.setItemCode(Integer.valueOf(entity.getCode()));
				resultDto.setItemName(entity.getCodeName());
				resultDto.setLockType(entity.getLockType().intValue());
				handleResult(resultDto, entity);
				
				resultDtoList.add(resultDto);
			}
		}
		return resultDtoList;
	}
	
	/**
	 * 统一处理
	 * 
	 * @param resultDto
	 * @param specialEntity
	 * @author 蒋落琛
	 * @date 2016-6-15下午4:19:34
	 * @update
	 */
	private void handleResult(SpecialServerCalculateResultDto resultDto, ExtrafeeAddValueFeeEntity specialEntity){
		resultDto.setPriceName("费用");
		if("weight".equals(specialEntity.getCalculationType())){
			resultDto.setUnitName("重量");
		}else if("votes".equals(specialEntity.getCalculationType())){
			resultDto.setUnitName("票数");
		}else if("volume".equals(specialEntity.getCalculationType())){
			resultDto.setUnitName("体积");
		}else if("packingVolume".equals(specialEntity.getCalculationType())){
			resultDto.setUnitName("包装体积");
		}else if("number".equals(specialEntity.getCalculationType())){
			resultDto.setUnitName("个数");
		}
		switch(Integer.valueOf(specialEntity.getCode())){
		//华宇签单传真返回、华宇签单原件返回、超市派送服务费、进仓送货服务费、机械装卸服务费，这5项的特服费，数量固定为1，不得修改【增加：专车送货】
		//新增客户签单传真、原件返回
		case SpecialPriceConstants.SIGN_FAX_RETURN:
		case SpecialPriceConstants.SIGN_ORIGINAL_RETURN:
		case SpecialPriceConstants.SUPERMARKET_DELIVERY_SERVICE:
		case SpecialPriceConstants.ENTRY_DELIVERY_SERVICE:
		case SpecialPriceConstants.MECHANICAL_HANDLE_SERVICE:
		case SpecialPriceConstants.SPECIAL_CAR_DELIVERY://zhangche
		case SpecialPriceConstants.SUPER_LONG_CARGO_SERVICE: //超长超高货物服务费
		case SpecialPriceConstants.CUSTOMER_FAX_RETURN:	//客户签单传真返回
		case SpecialPriceConstants.CUSTOMER_ORIGINAL_RETURN: //客户签单原件返回
			resultDto.setEdit(false);
		
		//上楼服务费、人工分拣，数量默认为轻重货对应的体积和重量，向下锁定。【增加：人工分拣及入库】
		//case SpecialPriceConstants.UPSTAIRS_SERVICE: //上楼服务费
		case SpecialPriceConstants.MANUAL_SORTING_STORAGE://人工分拣	
			resultDto.setEdit(true);
			
		case SpecialPriceConstants.LONG_DISTANCE_DELIVERY_SERVICE:
		case SpecialPriceConstants.LARGE_CARGO_SERVICE: //大型货物服务费
			resultDto.setEdit(true);
		
		default:
			resultDto.setEdit(true);
		}
	}
	
	/**
	 * 计算每一项特服费
	 * 
	 * @param specialServerPriceConditionDto
	 * @param specialEntity
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-15下午4:19:48
	 * @update
	 */
	public BigDecimal calculateItemPrice(SpecialServerPriceConditionDto specialServerPriceConditionDto,
			ExtrafeeAddValueFeeEntity specialEntity) {
		boolean isHeavy = specialServerPriceConditionDto.getGoodsType().equals("0") ? true : false;
		//数量
		BigDecimal unitermCount = new BigDecimal(specialServerPriceConditionDto.getUnitermCount());
		
		BigDecimal weightUnitPrice = BigDecimal.valueOf(specialEntity.getWeightPrice());
		BigDecimal volumeUnitPrice = BigDecimal.valueOf(specialEntity.getLightPrice());
		//数量*特服费重量/体积单价计算结果
		BigDecimal weightCalResult = unitermCount.multiply(weightUnitPrice);
		BigDecimal volumeCalResult = unitermCount.multiply(volumeUnitPrice);
		//最低价
		BigDecimal lowestPrice = BigDecimal.valueOf(specialEntity.getLowestMoney());
		
		BigDecimal temp = new BigDecimal(0);
		switch(Integer.valueOf(specialEntity.getCode()))
		{
		case SpecialPriceConstants.SIGN_FAX_RETURN:
		case SpecialPriceConstants.SIGN_ORIGINAL_RETURN:
		case SpecialPriceConstants.SUPERMARKET_DELIVERY_SERVICE:
		case SpecialPriceConstants.LONG_DISTANCE_DELIVERY_SERVICE:
		case SpecialPriceConstants.ENTRY_DELIVERY_SERVICE:
		case SpecialPriceConstants.MECHANICAL_HANDLE_SERVICE:
		case SpecialPriceConstants.CUSTOMER_FAX_RETURN:	//客户签单传真返回
		case SpecialPriceConstants.CUSTOMER_ORIGINAL_RETURN: //客户签单原件返回
			/*
			 * return isHeavy ? weightCalResult //重货 : volumeCalResult; //轻货
			 */
			return isHeavy ? lowestPrice.max(weightCalResult)
					: lowestPrice.max(volumeCalResult);
		/*case SpecialPriceConstants.UPSTAIRS_SERVICE: //上楼服务费
			//默认的段内单价和扣减基数
			sectionUnitPrice = isHeavy ? new BigDecimal(0.3) : new BigDecimal(100); 
			sectionDiscountBase = isHeavy ? new BigDecimal(100) : new BigDecimal(0.5);
			//计算分段折扣
			if(sectionDiscountList != null && sectionDiscountList.size() != 0){
				discount = calculateSubsectionDiscount(sectionDiscountList,specialServerPriceConditionDto,isHeavy);
				if(discount.length == 2){
					sectionUnitPrice = discount[0];
					sectionDiscountBase = discount[1];
				}
			}
//			Math.max(最低收费, 最低收费+(数量-折扣基数)*段内单价)
			temp = unitermCount.subtract(sectionDiscountBase);
			temp = temp.multiply(sectionUnitPrice);
			temp = lowestPrice.add(temp);
			return lowestPrice.max(temp); //对于重货或轻货，数量就代表了重量单价和轻货单价
*/			
		case SpecialPriceConstants.LARGE_CARGO_SERVICE: //大型货物服务费
		case SpecialPriceConstants.SUPER_LONG_CARGO_SERVICE: //超长超高货物服务费
			/*//默认的段内单价和扣减基数
			sectionUnitPrice = new BigDecimal(0.3);
			sectionDiscountBase = new BigDecimal(0);
			if(sectionDiscountList != null && sectionDiscountList.size() != 0){
				discount = calculateSubsectionDiscount(sectionDiscountList,specialServerPriceConditionDto,isHeavy);
				if(discount.length == 2){
					sectionUnitPrice = discount[0];
					sectionDiscountBase = discount[1];
//					return 是否重货 ? Math.max(最低收费,  数量 * (重量-折扣基数)*段内单价):Math.max(最低收费,  数量 * (体积-折扣基数)*段内单价)
					if(isHeavy){
						temp = weight.subtract(sectionDiscountBase).multiply(unitermCount).multiply(sectionUnitPrice);
						return lowestPrice.max(temp);
					}else{
						temp = volume.subtract(sectionDiscountBase).multiply(unitermCount).multiply(sectionUnitPrice);
						return lowestPrice.max(temp);
					}
				}else{
					return lowestPrice;
				}
			}else {*/
//				是否重货 ？ Math.max(最低收费,  数量 * 重货单价) : Math.max(最低收费,  数量 * 轻货单价)
				return isHeavy ? 
						lowestPrice.max(weightCalResult)
								: lowestPrice.max(volumeCalResult);
						

			/*}*/
		case SpecialPriceConstants.MANUAL_SORTING_STORAGE://人工分拣
//			isHeavy ? Math.max(最低收费, if(数量<=300, 0, (数量-300)*重货单价)): Math.max(最低收费, if(数量<=1, 0, (数量-1)*轻货单价))
			if(isHeavy){
				if(unitermCount.intValue() < 300){
					temp = new BigDecimal(0);
				}else{
					temp = new BigDecimal(unitermCount.intValue()-300).multiply(weightUnitPrice);
				}
			}else{
				if(unitermCount.intValue() < 1){
					temp = new BigDecimal(0);
				}else{
					temp = new BigDecimal(unitermCount.intValue()-1).multiply(volumeUnitPrice);
				}
			}
			return lowestPrice.max(temp);
		default:
			return isHeavy ? 
					lowestPrice.max(weightCalResult)
							: lowestPrice.max(volumeCalResult);
		}
	}
	
	/**
	 * 判断轻重货
	 * 
	 * @param specialServerPriceConditionDto
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-15下午4:19:57
	 * @update
	 */
	/*private boolean isHeavyGoodType(SpecialServerPriceConditionDto specialServerPriceConditionDto){
		BigDecimal weight = specialServerPriceConditionDto.getGoodsWeight().multiply(specialServerPriceConditionDto.getWeightUnitPrice());
		BigDecimal volume = specialServerPriceConditionDto.getGoodsVolume().multiply(specialServerPriceConditionDto.getVolumeUnitPrice());
		int result = weight.compareTo(volume);
		switch(result){
		case -1 :
			return false;
		case 1 :
			return true;
		default :
			return true;
		}
	}*/
	
	/**
	 * 校验请求参数
	 * 
	 * @param specialServerPriceConditionDto
	 * @author 蒋落琛
	 * @date 2016-6-15下午4:20:03
	 * @update
	 */
	private void checkParams(SpecialServerPriceConditionDto specialServerPriceConditionDto){
		if (specialServerPriceConditionDto == null) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PARAMS_ISNULL_EXCEPTION);
		}
		// 特服费项目编号不能为空
		if(specialServerPriceConditionDto.getItemCode() == null){
			throw new ChargeException(ChargeException.SURCHARGEPRICE_SPECIAL_ITEMCODE_ISNULL_EXCEPTION);
		}
		// 产品类型不能为空
		if (StringUtil.isEmpty(specialServerPriceConditionDto.getProductType())) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_PRODUCTTYPE_ISNULL_EXCEPTION);
		}
		// 轻重货类型不能为空
		if(StringUtil.isEmpty(specialServerPriceConditionDto.getGoodsType())){
			throw new ChargeException(ChargeException.SURCHARGEPRICE_GOODSTYPE_ISNULL_EXCEPTION);
		}
		// 单项服务项目个数不能为空
		if(specialServerPriceConditionDto.getUnitermCount() == null){
			throw new ChargeException(ChargeException.SURCHARGEPRICE_UNITERMCOUN_ISNULL_EXCEPTION);
		}
		/*// 重量不能为空
		if (specialServerPriceConditionDto.getGoodsWeight() == null) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_GOODSWEIGHT_ISNULL_EXCEPTION);
		}
		// 体积不能为空
		if (specialServerPriceConditionDto.getGoodsVolume() == null) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_GOODSVOLUME_ISNULL_EXCEPTION);
		}
		// 重量单价不能为空
		if (specialServerPriceConditionDto.getWeightUnitPrice() == null) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_WEIGHTUNITPRICE_ISNULL_EXCEPTION);
		}
		// 体积单价不能为空
		if (specialServerPriceConditionDto.getVolumeUnitPrice() == null) {
			throw new ChargeException(ChargeException.SURCHARGEPRICE_VOLUMEUNITPRICE_ISNULL_EXCEPTION);
		}*/
	}

}
