package com.hoau.miser.module.api.itf.api.server;

import java.util.List;

import com.hoau.miser.module.api.itf.api.shared.dto.SpecialServerCalculateResultDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SpecialServerPriceConditionDto;

/**
 * 特服费
 * 
 * @author 蒋落琛
 * @date 2016-6-15下午2:51:06
 */
public interface ISpecialServerPriceService {

	/**
	 * 计算单项特服费
	 * 
	 * @param specialServerPriceConditionDto
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-15下午2:50:56
	 * @update
	 */
	public SpecialServerCalculateResultDto calculateUnitermSpecialPrice(
			SpecialServerPriceConditionDto specialServerPriceConditionDto);
	
	/**
	 * 计算标准单项特服费
	 * 
	 * @param specialServerPriceConditionDto
	 * @return
	 * @author 蒋落琛
	 * @date 2016-7-5下午12:04:08
	 * @update
	 */
	public SpecialServerCalculateResultDto calculateStandardUnitermSpecialPrice(
			SpecialServerPriceConditionDto specialServerPriceConditionDto);

	/**
	 * 初始加载公司所支持的特服费
	 * 
	 * @param specialServerPriceConditionDto
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-15下午2:51:02
	 * @update
	 */
	public List<SpecialServerCalculateResultDto> calculateListSpecialPrice(
			SpecialServerPriceConditionDto specialServerPriceConditionDto);
}
