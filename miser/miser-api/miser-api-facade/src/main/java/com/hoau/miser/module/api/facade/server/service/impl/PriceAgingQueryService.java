package com.hoau.miser.module.api.facade.server.service.impl;

import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import ro.isdc.wro.model.resource.support.hash.MD5HashStrategy;

import com.hoau.miser.module.api.facade.server.dao.PriceAgingDao;
import com.hoau.miser.module.api.facade.server.service.IPriceAgingQueryService;
import com.hoau.miser.module.api.facade.shared.define.BaseResponseEnum;
import com.hoau.miser.module.api.facade.shared.vo.PriceAgingConditionVo;
import com.hoau.miser.module.api.facade.shared.domain.PriceAgingResultEntity;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.common.shared.util.MD5EncryptUtil;
import com.hoau.miser.module.api.facade.shared.vo.BaseResponseVo;
import com.hoau.miser.module.util.StringUtils;

@Service
public class PriceAgingQueryService implements IPriceAgingQueryService {
	@Resource
	private PriceAgingDao priceAgingDao;

	@Resource
	private IPriceSectionService priceSectionService;

	@Override
	public BaseResponseVo<Object> queryPriceAgingListByParams(
			PriceAgingConditionVo entity) {

		BaseResponseVo<Object> response = new BaseResponseVo<Object>();

		try {
			BaseResponseVo<Object> responseCheck = VerifyParams(entity);
			if (!(BaseResponseEnum.SUCCESS.getStatus().equals(responseCheck
					.getStatus()))) {
				return responseCheck;
			}

			RowBounds rowBounds = new RowBounds();
			List<PriceAgingResultEntity> result = priceAgingDao
					.queryPriceAgingListByParams(entity, rowBounds);
			for (int idx = 0; idx < result.size(); idx++) {
				CalculatePrice(result.get(idx), entity);
			}

			response.setContent(result);
			response.setStatus(BaseResponseEnum.SUCCESS.getStatus());
			response.setMessage(BaseResponseEnum.SUCCESS.getMessage());
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(BaseResponseEnum.SYSTEM_ERROR.getStatus());
			response.setMessage(BaseResponseEnum.SYSTEM_ERROR.getMessage());
			return response;
		}

	}

	/**
	 * 
	 * @Description: 根据分段，体积重量计算价格
	 * @param @return
	 * @return List<PriceAgingResultEntity>
	 * @throws
	 * @author 赵金义
	 * @date 2016年4月25日
	 */
	public void CalculatePrice(PriceAgingResultEntity entity,
			PriceAgingConditionVo condition) {
		BigDecimal weightAmount = new BigDecimal("0.0");
		BigDecimal volumeAmount = new BigDecimal("0.0");
		BigDecimal weightAmountAfterDiscount = new BigDecimal("0.0");
		BigDecimal volumeAmountAfterDiscount = new BigDecimal("0.0");
		BigDecimal weightDiscount = new BigDecimal("1");
		BigDecimal volumeDiscount = new BigDecimal("1");

		// 计算运费折扣和折后价
		if (!StringUtils.isEmpty(entity.getFreightSectionCode())) {
			PriceSectionEntity freightSec = priceSectionService
					.queryPriceSectionByCode(entity.getFreightSectionCode());
			if (null != freightSec) {
				weightDiscount = GetWeightDiscount(freightSec,
						condition.getWeight());
				volumeDiscount = GetVolumeDiscount(freightSec,
						condition.getVolume());
			}
		}
		entity.setWeightPriceAfterDiscount(entity.getWeightPrice().multiply(
				weightDiscount));
		entity.setVolumnPriceAfterDiscount(entity.getVolumePrice().multiply(
				volumeDiscount));
		// 计算运费和折后运费
		if (null != condition.getWeight()) {
			weightAmount = entity.getWeightPrice().multiply(
					condition.getWeight());
			weightAmountAfterDiscount = entity.getWeightPriceAfterDiscount()
					.multiply(condition.getWeight());
		}
		if (null != condition.getVolume()) {
			volumeAmount = entity.getVolumePrice().multiply(
					condition.getVolume());
			volumeAmountAfterDiscount = entity.getVolumnPriceAfterDiscount()
					.multiply(condition.getVolume());
		}
		// 重量单价*重量与体积单价*体积结果取最大值
		entity.setFreightFeeAmount(weightAmount.compareTo(volumeAmount) >= 0 ? weightAmount
				: volumeAmount);
		// 折后重量单价*重量与折后体积单价*体积结果取最大值.
		entity.setFreightFeeAmountAfterDiscount(weightAmountAfterDiscount
				.compareTo(volumeAmountAfterDiscount) >= 0 ? weightAmountAfterDiscount
				: volumeAmountAfterDiscount);

		// 设置燃油费
		if (!StringUtils.isEmpty(entity.getFuelFeeSection())) {
			PriceSectionEntity fuelSec = priceSectionService
					.queryPriceSectionByCode(entity.getFuelFeeSection());
			if (null != fuelSec && null != fuelSec.getSubEntities()) {

				entity.setFuelFee(new BigDecimal(fuelSec.getSubEntities()
						.get(0).getPrice()));
			}

		}
	}

	/**
	 * 
	 * @Description: 从分段明细中，根据重量，匹配到符合条件的折扣
	 * @param @param section
	 * @param @param weight
	 * @param @return
	 * @return BigDecimal
	 * @throws
	 * @author 赵金义
	 * @date 2016年4月26日
	 */
	private BigDecimal GetWeightDiscount(PriceSectionEntity section,
			BigDecimal weight) {
		if (weight == null) {
			weight = new BigDecimal("0.0");
		}
		BigDecimal r = new BigDecimal("1");
		for (int i = 0; i < section.getSubEntities().size(); i++) {
			if ("WEIGHT".equals(section.getSubEntities().get(i)
					.getSectionAccodingItem())) {
				if ((section.getSubEntities().get(i).getStartValue()
						.compareTo(weight.doubleValue()) <= 0)
						&& (section.getSubEntities().get(i).getEndValue()
								.compareTo(weight.doubleValue()) >= 0)) {
					r = new BigDecimal(section.getSubEntities().get(i)
							.getPrice());
					break;
				}
			}
		}
		return r;
	}

	/**
	 * 
	 * @Description: 从分段明细中，根据体积，匹配到符合条件的折扣
	 * @param @param section
	 * @param @param volume
	 * @param @return
	 * @return BigDecimal
	 * @throws
	 * @author 赵金义
	 * @date 2016年4月27日
	 */
	private BigDecimal GetVolumeDiscount(PriceSectionEntity section,
			BigDecimal volume) {
		if (volume == null) {
			volume = new BigDecimal("0");
		}
		BigDecimal r = new BigDecimal("1");
		for (int i = 0; i < section.getSubEntities().size(); i++) {
			if ("VOLUMN".equals(section.getSubEntities().get(i)
					.getSectionAccodingItem())) {
				if ((section.getSubEntities().get(i).getStartValue()
						.compareTo(volume.doubleValue()) <= 0)
						&& (section.getSubEntities().get(i).getEndValue()
								.compareTo(volume.doubleValue()) >= 0)) {
					r = new BigDecimal(section.getSubEntities().get(i)
							.getPrice());
					break;
				}
			}
		}
		return r;
	}

	/**
	 * 
	 * @Description: 检查传入参数
	 * @param @param entity
	 * @param @return
	 * @return BaseResponseVo<Object>
	 * @throws
	 * @author 赵金义
	 * @date 2016年4月27日
	 */
	public BaseResponseVo<Object> VerifyParams(PriceAgingConditionVo entity) {
		BaseResponseVo<Object> response = new BaseResponseVo<Object>();
		if (entity == null) {
			response.setStatus(BaseResponseEnum.JSON_ERROR.getStatus());
			response.setMessage(BaseResponseEnum.JSON_ERROR.getMessage());
			return response;
		}
		String sendProvinceCode = entity.getSendProvinceCode();
		String sendCityCode = entity.getSendCityCode();
		String sendAreaCode = entity.getSendAreaCode();
		String arriveProvinceCode = entity.getArriveProvinceCode();
		String arriveCityCode = entity.getArriveCityCode();
		String arriveAreaCode = entity.getArriveAreaCode();
		String sendProvinceName = entity.getSendProvinceName();
		String sendCityName = entity.getSendCityName();
		String sendAreaName = entity.getSendAreaName();
		String arriveProvinceName = entity.getArriveProvinceName();
		String arriveCityName = entity.getArriveCityName();
		String arriveAreaName = entity.getArriveAreaName();
		boolean pcaisnull = true;
		if ((!StringUtils.isEmpty(sendProvinceName)
				&& !StringUtils.isEmpty(sendCityName)
				&& !StringUtils.isEmpty(sendAreaName)
				&& !StringUtils.isEmpty(arriveProvinceName)
				&& !StringUtils.isEmpty(arriveCityName) && !StringUtils
					.isEmpty(arriveAreaName))
				|| (!StringUtils.isEmpty(sendProvinceCode)
						&& !StringUtils.isEmpty(sendCityCode)
						&& !StringUtils.isEmpty(sendAreaCode)
						&& !StringUtils.isEmpty(arriveProvinceCode)
						&& !StringUtils.isEmpty(arriveCityCode) && !StringUtils
							.isEmpty(arriveAreaCode))) {
			pcaisnull = false;
		}
		if (pcaisnull) {
			response.setStatus(BaseResponseEnum.PRICEAGINGPARAM_CHECK_FAIL
					.getStatus());
			response.setMessage(BaseResponseEnum.PRICEAGINGPARAM_CHECK_FAIL
					.getMessage());
			return response;
		}
		if (null != (entity.getWeight())) {
			if (!isNumeric(entity.getWeight().toString())) {
				response.setStatus(BaseResponseEnum.PRICE_IS_NUM.getStatus());
				response.setMessage(BaseResponseEnum.PRICE_IS_NUM.getMessage());
				return response;
			}
		}
		if (null != (entity.getVolume())) {
			if (!isNumeric(entity.getVolume().toString())) {
				response.setStatus(BaseResponseEnum.PRICE_IS_NUM.getStatus());
				response.setMessage(BaseResponseEnum.PRICE_IS_NUM.getMessage());
				return response;
			}
		}
		response.setStatus(BaseResponseEnum.SUCCESS.getStatus());
		response.setMessage(BaseResponseEnum.SUCCESS.getMessage());
		return response;
	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
