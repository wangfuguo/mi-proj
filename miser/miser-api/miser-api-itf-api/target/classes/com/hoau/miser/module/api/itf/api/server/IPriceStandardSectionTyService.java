package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardSectionTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

/**
 * @ClassName: IPriceStandardSectionTyService
 * @Description: 易入户标准价格管理Service
 * @author 廖文强
 * @date 2016/7/5 10:35
 *
 */
public interface IPriceStandardSectionTyService {

	/**
	 * 查询易入户标准价格
	 * @Param  [queryParam]
	 * @Return com.hoau.miser.module.api.itf.api.shared.domain.PriceStandardSectionTyEntity
	 * @Throws
	 * @Author 廖文强
	 * @Date 2016/7/5 10:35
	 * @Version v1
	 */
	public PriceStandardSectionTyEntity queryByPriceSectionQueryParam(PriceQueryParam queryParam);


}
