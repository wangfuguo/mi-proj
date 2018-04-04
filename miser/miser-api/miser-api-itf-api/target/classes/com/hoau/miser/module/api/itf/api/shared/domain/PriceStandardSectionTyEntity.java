package com.hoau.miser.module.api.itf.api.shared.domain;

import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;

/**
 * @ClassName: PriceStandardSectionEntity
 * @Description: 分段标准价格Entity
 * @author zhangqingfu
 * @date 2016年5月4日 下午1:41:41
 *
 */
public class PriceStandardSectionTyEntity extends PriceSectionBaseTyEntity {

	private static final long serialVersionUID = -8187772927765781946L;

	public int getType() {
		return PCTyConstans.PC_TYPE_STAND;
	}


}
